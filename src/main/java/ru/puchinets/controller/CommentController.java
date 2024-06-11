package ru.puchinets.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.puchinets.dto.authorization.Authification;
import ru.puchinets.dto.request.CommentRequestDto;
import ru.puchinets.service.CommentService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
@SessionAttributes({"authification", "lastPage"})
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    String addComment(@ModelAttribute("comment") CommentRequestDto comment,
                      @SessionAttribute("authification") Authification authification,
                      @SessionAttribute("lastPage") String lastPage) {
        if (!authification.authificated() || authification.id() == null) return "redirect:/login";
        commentService.create(comment);
        return lastPage;
    }
}
