package ru.puchinets.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import ru.puchinets.dto.authorization.Authification;
import ru.puchinets.dto.authorization.LoginDto;
import ru.puchinets.dto.request.UserRequestDto;
import ru.puchinets.dto.response.CompanyResponseDto;
import ru.puchinets.dto.response.UserResponseDto;
import ru.puchinets.service.AuthificationService;
import ru.puchinets.service.CompanyService;
import ru.puchinets.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@SessionAttributes({"authification"})
public class LoginController {

    private final AuthificationService authificationService;
    private final CompanyService companyService;
    private final UserService userService;

    @GetMapping
    public String loginPage(@ModelAttribute("user") LoginDto user) {
        System.out.println();
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") LoginDto user, Model model) {
        Authification authorizantion = authificationService.login(user);
        if (authorizantion.authificated()) {
            model.addAttribute("authification", authorizantion);
            return "redirect:/tasks/my";
        } else {
            return "login";
        }
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("newUser") @Validated UserRequestDto newUser, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<CompanyResponseDto> companies = companyService.findAll();
            model.addAttribute("companies", companies);
            return "register";
        }
        UserResponseDto userResponseDto = userService.create(newUser);
        Authification authorizantion = new Authification(userResponseDto.id(), true, userResponseDto.company().id());
        model.addAttribute("authification", authorizantion);
        return "redirect:/tasks/my";
    }

    @GetMapping("/register")
    public String registerPage(@ModelAttribute("newUser") UserRequestDto newUser, Model model) {
        List<CompanyResponseDto> companies = companyService.findAll();
        model.addAttribute("companies", companies);
        return "register";
    }
}
