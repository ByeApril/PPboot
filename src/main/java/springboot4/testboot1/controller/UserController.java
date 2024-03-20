package springboot4.testboot1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springboot4.testboot1.model.User;
import springboot4.testboot1.service.UserService;
import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {

        model.addAttribute("user", new User());
        return "user-form";
    }

    @PostMapping("/saveUser")
    public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "user-form";
        }
        userService.addUser(user);
        return "redirect:/users";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("userId") Long id, Model model) {
        User user = userService.getUserById(id);
        if (user == null) {
            model.addAttribute("errorMessage", "User not found");
            List<User> users = userService.getAllUsers();
            model.addAttribute("users", users);
            return "users";
        }
        model.addAttribute("user", user);
        return "user-form";
    }


    @PostMapping("/updateUser")
    public String updateUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "user-form";
        }
        userService.updateUser(user);
        return "redirect:/users";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("userId") Long id, Model model) {
        User user = userService.getUserById(id);
        if (user == null) {
            model.addAttribute("errorMessage", "User not found");
            List<User> users = userService.getAllUsers();
            model.addAttribute("users", users);
            return "users";
        } else {
            userService.deleteUser(id);
            return "redirect:/users";
        }
    }

}