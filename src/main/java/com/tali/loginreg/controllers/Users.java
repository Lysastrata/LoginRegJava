package com.tali.loginreg.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tali.loginreg.validators.UserValidator;
import com.tali.loginreg.models.User;
import com.tali.loginreg.services.UserService;

@Controller
public class Users {
	private UserService userService;
	private UserValidator userValidator;

	public Users(UserService userService, UserValidator userValidator) {
	    this.userService = userService;
	    this.userValidator = userValidator;
	}
	
	@RequestMapping("/login")
    public String login(@RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, Model model, @Valid @ModelAttribute("user") User user, BindingResult result) {
        if(error != null) {
            model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
        }
        if(logout != null) {
            model.addAttribute("logoutMessage", "Logout Successfull!");
        }
        System.out.println(userService.findCountOfAdmins());
        return "loginPage.jsp";
    }
	
	@PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        userValidator.validate(user, result);
        if (result.hasErrors()) {
            return "loginPage.jsp"; 
        }else {
        userService.saveWithUserRole(user);
        return "redirect:/login"; 
        }
    }
	@RequestMapping(value = {"/", "/user/dashboard"})
    public String user(Principal principal, Model model) {
        String username = principal.getName();
        model.addAttribute("currentUser", userService.findByUsername(username));
        return "dashboard.jsp";
    }
	@RequestMapping("/admin")
    public String admin(Principal principal, Model model) {
        String username = principal.getName();
        model.addAttribute("currentUser", userService.findByUsername(username));
        List <User> users = userService.allUsers();
        List<User> admins = new ArrayList<User>();
        List<User> norms = new ArrayList<User>();
        for(User i : users) {
        		i.getRoles().get(0).getName();
        		if (i.getRoles().get(0).getName().equals("ROLE_ADMIN")) {
				admins.add(i);
        		}
        		else {
        			norms.add(i);
        		}
        }
        model.addAttribute("admins", admins);
        model.addAttribute("norms", norms);


        System.out.println(users.get(0).getRoles().get(0).getName());
		model.addAttribute("users", users);
		
        return "adminDash.jsp";
    }
	@RequestMapping("admin/delete/{id}")
    public String destroyUser(@PathVariable("id") Long id) {
        userService.destroyUser(id);
        return "redirect:/admin";
	}
	@RequestMapping("admin/make/{id}")
    public String changeUserToAdmin(@PathVariable("id") Long id) {
        userService.changeUserToAdminRole(id);
        return "redirect:/admin";
	}
}
