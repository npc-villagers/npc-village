package com.npcvillagers.npcvillage.controllers;

import com.npcvillagers.npcvillage.models.Npc;
import com.npcvillagers.npcvillage.repos.AppUserRepository;
import com.npcvillagers.npcvillage.repos.NpcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@Controller
public class NpcController {

    @Autowired
    AppUserRepository appUserRepo;

    NpcRepository npcRepository;

    @GetMapping("/create")
    public String getCreate(@ModelAttribute("npc") Npc npc, Model m, Principal p) {
        if (p != null) {  //not strictly needed if WebSecurityConfig is set up properly
            if (npc == null) {
                npc = new Npc(); // Create a new NPC object
            }
            m.addAttribute("npc", npc); // Add the NPC object to the model

            return "create";
        }

        return "login";
    }
}
