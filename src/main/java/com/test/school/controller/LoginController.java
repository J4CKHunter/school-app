package com.test.school.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView displayLoginPage(@RequestParam(value = "error", required = false) String error,
                                         @RequestParam(value = "logout", required = false) String logout,
                                         @RequestParam(value = "register", required = false) String register){

        String errorMessage = null;

        if(error != null){
            errorMessage = "Username or Password is incorrect.";
        }

        // logout başarısız olduğu durumda ne olacağı belli değil !
        else if(logout != null){
            errorMessage = "You have been successfully logged out.";
        }

        else if(register != null){
            errorMessage = "your registration successful. Login with registered credentials.";
        }

        ModelAndView modelAndView = new ModelAndView("login.html");
        modelAndView.addObject("errorMessage", errorMessage);

        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                               HttpServletResponse response){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return new ModelAndView("redirect:/login?logout=true");
    }
}
