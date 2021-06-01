package com.wat.recipesapp.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;


@Controller
public class UserController {
    private final UserService userService;


    private static final Logger log = LoggerFactory.getLogger(UserController.class.getName());

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/register")
    public String register(@ModelAttribute UserDTO userDTO, Model model) {
        model.addAttribute("userDTO", userDTO);
        return "register";
    }

    @PostMapping("/user/register")
    public String save(@Valid UserDTO userDTO, BindingResult bindingResult) {
        if(userService.existsByEmail(userDTO.getEmail())){
            bindingResult.addError(new FieldError("userDTO","email","Email address already in use"));
        }

        if(!userDTO.getPassword().equals(userDTO.getRpassword())){
            bindingResult.addError(new FieldError("userDTO","rpassword","Passwords must be the same"));
        }

        if(bindingResult.hasErrors()){
            return "register";
        }
        log.info(">> userDTO: {}", userDTO.toString());
        userService.register(userDTO);
        return "redirect:/login";
    }


}
