package ru.puchinets.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.puchinets.dto.authorization.Authification;
import ru.puchinets.dto.request.CommentRequestDto;
import ru.puchinets.dto.request.TaskRequestDto;
import ru.puchinets.dto.response.TaskResponseDto;
import ru.puchinets.dto.response.UserResponseDto;
import ru.puchinets.enums.TaskStatus;
import ru.puchinets.service.CommentService;
import ru.puchinets.service.TagService;
import ru.puchinets.service.TaskService;
import ru.puchinets.service.UserService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tasks")
@SessionAttributes({"authification", "lastPage"})
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;
    private final TagService tagService;
    private final CommentService commentService;

    @GetMapping("/my")
    String myTasksPage(Model model, @SessionAttribute("authification") Authification authification) {
        if (!authification.authificated() || authification.id() == null) return "redirect:/login";
        List<TaskResponseDto> tasks = taskService.getAllTasksForUser(authification.id());
        model.addAttribute("tasks", tasks);
        model.addAttribute("name", "Tasks for me");
        model.addAttribute("addr", "/tasks/other");
        model.addAttribute("addr_name", "To my tasks for other");
        return "tasks/tasks";
    }

    @GetMapping("/other")
    String otherTasksPage(Model model, @SessionAttribute("authification") Authification authification) {
        if (!authification.authificated() || authification.id() == null) return "redirect:/login";
        List<TaskResponseDto> tasks = taskService.getAllCreatedTasksForUser(authification.id());
        model.addAttribute("tasks", tasks);
        model.addAttribute("name", "My tasks for other");
        model.addAttribute("addr", "/tasks/my");
        model.addAttribute("addr_name", "To tasks for me");
        return "tasks/tasks";
    }

    @GetMapping("/{id}")
    String detailsPage(@PathVariable("id") Long id,
                       @SessionAttribute("authification") Authification authification,
                       Model model) {
        if (!authification.authificated() || authification.id() == null) return "redirect:/login";
        Optional<TaskResponseDto> mayBeTask = taskService.getById(id);
        if (mayBeTask.isEmpty()) return "redirect:/tasks";
        CommentRequestDto comment = new CommentRequestDto(null, null, null, authification.id(), id);
        model.addAttribute("task", mayBeTask.get());
        model.addAttribute("comments", commentService.findAllByTask(id));
        model.addAttribute("lastPage", "redirect:/tasks/" + id);
        model.addAttribute("comment", comment);

        return "tasks/details";
    }

    @GetMapping("/new")
    String newTaskPage(Model model, @SessionAttribute("authification") Authification authification, @ModelAttribute("task") TaskRequestDto task) {
        if (!authification.authificated() || authification.id() == null) return "redirect:/login";
        List<UserResponseDto> users = userService.findUsersByCompanyId(authification.companyId());
        model.addAttribute("users", users);
        model.addAttribute("tags", tagService.findAllByCompanyId(authification.companyId()));
        return "tasks/new";
    }

    @PostMapping
    String addTask(@ModelAttribute("task") @Valid TaskRequestDto task, Model model, @SessionAttribute("authification") Authification authification) {
        if (!authification.authificated() || authification.id() == null) return "redirect:/login";
        taskService.createByAuthor(task, authification.id());
        return "redirect:/tasks/other";
    }

    @GetMapping("/{id}/edit")
    String editTaskPage(Model model,
                        @SessionAttribute("authification") Authification authification,
                        @PathVariable("id") Long id,
                        @ModelAttribute("task") TaskRequestDto task) {
        if (!authification.authificated() || authification.id() == null) return "redirect:/login";
        Optional<TaskResponseDto> mayBeTask = taskService.getById(id);
        if (mayBeTask.isEmpty()) return "redirect:/tasks/other";
        List<UserResponseDto> users = userService.findUsersByCompanyId(authification.companyId());
        TaskResponseDto resp = mayBeTask.get();
        task = new TaskRequestDto(resp.id(), resp.description(), resp.content(), resp.user().id(), resp.taskStatus(), resp.tag().id(), null, null);
        model.addAttribute("users", users);
        model.addAttribute("task", task);
        model.addAttribute("tags", tagService.findAllByCompanyId(authification.companyId()));
        model.addAttribute("taskStatuses", TaskStatus.list());
        return "tasks/edit";
    }

    @PutMapping("/{id}")
    String editTask(@ModelAttribute("task") @Valid TaskRequestDto task,
                    Model model,
                    @SessionAttribute("authification") Authification authification,
                    @PathVariable("id") Long id) {
        if (!authification.authificated() || authification.id() == null) return "redirect:/login";
        taskService.update(authification.id(), id, task);
        return "redirect:/tasks/other";
    }

    @DeleteMapping("/{id}")
    String deleteTask(@SessionAttribute("authification") Authification authification, @PathVariable("id") Long id) {
        if (!authification.authificated() || authification.id() == null) return "redirect:/login";
        taskService.delete(id);
        return "redirect:/tasks/other";
    }

}
