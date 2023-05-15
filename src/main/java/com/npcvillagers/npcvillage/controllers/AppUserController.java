package com.npcvillagers.npcvillage.controllers;


import com.npcvillagers.npcvillage.models.AppUser;
import com.npcvillagers.npcvillage.repos.AppUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import jakarta.servlet.ServletException;

import java.security.Principal;


@Controller
public class AppUserController {
    @Autowired
    AppUserRepository appUserRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private HttpServletRequest request;


    @GetMapping("/")
    public String getHomePage(Model m, Principal p) {
        if (p != null) {
            String username = p.getName();
            AppUser user = appUserRepo.findByUsername(username);

            m.addAttribute("username", username);
        }
        return "index.html";
    }


    @GetMapping("/login")
    public String getLoginPage(Principal p) {
//        if (p != null) {
////            return "redirect:/";
//        }
        return "login.html";
    }

    @GetMapping("/signup")
    public String getSignUpPage() {
        return "signup.html";
    }

    @PostMapping("/signup")
    public RedirectView createUser(String username, String password, String firstName, String lastName, RedirectAttributes redir) {
        // Check if the username already exists
        if (appUserRepo.findByUsername(username) != null) {
            redir.addFlashAttribute("errorMessage", "That username already exists!");
            return new RedirectView("/signup?error");
        }

        AppUser newUser = new AppUser(username, passwordEncoder.encode(password), firstName, lastName);
        newUser.setUsername(username);
        appUserRepo.save(newUser);
        authWithHttpServletRequest(username, password);
        return new RedirectView("/");
    }

    public void authWithHttpServletRequest(String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException e) {
            System.out.println("Error while logging in!");
            e.printStackTrace();
        }
    }

    @GetMapping("/profile")
    public String getMyProfile(Model m, Principal p) {
        if(p != null) { //not strictly needed if WebSecurityConfig is set up properly
            AppUser user = appUserRepo.findByUsername(p.getName());
            m.addAttribute("user", user);
            m.addAttribute("username", user.getUsername());
            return "profile";
        }
        return "login";
    }

    @PutMapping("/profile")
    public RedirectView editProfile(Principal p, String username, String firstName, String lastName, Long id, RedirectAttributes redir) {
        AppUser user = appUserRepo.findById(id).orElseThrow();
        if(p != null) { //not strictly needed if WebSecurityConfig is set up properly
            user.setUsername(username);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            appUserRepo.save(user);

            // include lines below if your principal is not updating
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(),
                    user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            redir.addFlashAttribute("errorMessage", "You are not permitted to edit this profile!");
        }

        return new RedirectView("/profile");
    }

}