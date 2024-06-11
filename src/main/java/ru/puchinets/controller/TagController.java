package ru.puchinets.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.puchinets.dto.authorization.Authification;
import ru.puchinets.dto.request.TagRequestDto;
import ru.puchinets.dto.response.TagResponseDto;
import ru.puchinets.enums.TagType;
import ru.puchinets.service.TagService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tags")
@SessionAttributes({"authification"})
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    public String tagsPage(Model model,
                           @SessionAttribute("authification") Authification authification,
                           @ModelAttribute TagRequestDto newTag) {
        if (!authification.authificated() || authification.id() == null) return "redirect:/login";
        List<TagResponseDto> tags = tagService.findAllByCompanyId(authification.companyId());
        model.addAttribute("tags", tags);
        model.addAttribute("types", TagType.list());
        model.addAttribute("newTag", newTag);
        return "tags/tags";
    }

    @PostMapping
    public String addTag(@SessionAttribute("authification") Authification authification,
                         @ModelAttribute TagRequestDto newTag) {
        if (!authification.authificated() || authification.id() == null) return "redirect:/login";
        tagService.create(new TagRequestDto(newTag.id(), newTag.name(), newTag.color(), newTag.type(), authification.companyId()));
        return "redirect:/tags";
    }

    @GetMapping("/{id}/edit")
    public String tagEditPage(Model model,
                              @SessionAttribute("authification") Authification authification,
                              @PathVariable("id") Long id,
                              @ModelAttribute TagRequestDto tag) {
        if (!authification.authificated() || authification.id() == null) return "redirect:/login";
        Optional<TagResponseDto> maybeTag = tagService.getById(id);
        if (maybeTag.isEmpty()) return "redirect:/tags";
        tag = new TagRequestDto(maybeTag.get().id(), maybeTag.get().name(), maybeTag.get().color(), maybeTag.get().type(), authification.companyId());
        model.addAttribute("tag", tag);
        model.addAttribute("types", TagType.list());
        return "tags/edit";
    }

    @PutMapping("/{id}")
    public String tagUpdate(@PathVariable("id") Long id,
                            @SessionAttribute("authification") Authification authification,
                            @ModelAttribute TagRequestDto tag) {
        if (!authification.authificated() || authification.id() == null) return "redirect:/login";
        tagService.update(id, tag);
        return "redirect:/tags";
    }

    @DeleteMapping("/{id}")
    public String tagDelete(@PathVariable("id") Long id,
                              @SessionAttribute("authification") Authification authification) {
        if (!authification.authificated() || authification.id() == null) return "redirect:/login";
        tagService.delete(id);
        return "redirect:/tags";
    }
}
