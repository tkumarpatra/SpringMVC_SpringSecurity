package com.fsd.cts.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fsd.cts.model.User;
import com.fsd.cts.service.SecurityService;
import com.fsd.cts.service.UserService;
import com.fsd.cts.validator.UserValidator;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.save(userForm);
        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());
        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");
        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");
        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public ModelAndView welcome(@ModelAttribute("userForm") User userForm, 
    			BindingResult bindingResult, ModelAndView model,
    			 HttpServletRequest request, HttpServletResponse response) {
        
    	String username = null != request ? request.getUserPrincipal().getName(): "";
    	userForm = userService.findByUsername(username);
    	model.addObject("userDetails", userForm);
    	model.setViewName("welcome");
    	return model;
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, ModelAndView modelView) {

    	if (bindingResult.hasErrors()) {
    		modelView.addObject("userDetails", userForm);
            
            modelView.setViewName("redirect:/welcome");
            return modelView;
        }
        userService.updateUserDetails(userForm);
        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());
        modelView.addObject("userDetails", userForm);
        modelView.setViewName("redirect:/welcome");
        return modelView;
    }
}
