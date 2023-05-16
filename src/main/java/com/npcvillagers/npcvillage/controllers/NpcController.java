package com.npcvillagers.npcvillage.controllers;

import com.npcvillagers.npcvillage.models.Npc;
import com.npcvillagers.npcvillage.models.NpcForm;
import com.npcvillagers.npcvillage.repos.AppUserRepository;
import com.npcvillagers.npcvillage.repos.NpcRepository;
import com.npcvillagers.npcvillage.services.NpcFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Optional;

@Controller
public class NpcController {

    @Autowired
    AppUserRepository appUserRepo;

    @Autowired
    NpcRepository npcRepository;

    @Autowired
    NpcFactory npcFactory;

    @GetMapping("/create")
    public String getCreateDefault(Model m, Principal p, RedirectAttributes redir) {
        System.out.println("I'm here");
        if (p != null) {
            NpcForm npcForm = new NpcForm();
            m.addAttribute("npcForm", npcForm);
            return "create";
        } else {
            redir.addFlashAttribute("errorMessage", "You must be logged in to create NPCs!");
            return "redirect:/login";
        }
    }

    @GetMapping("/create/{npcId}")
    public String getNpc(@PathVariable("npcId") Long npcId, Model m, Principal p, RedirectAttributes redir) {
        if (p != null) {
            Optional<Npc> createdNpc = npcRepository.findById(npcId);
            if (createdNpc.isPresent()) {
                Npc npc = createdNpc.get();
                if (!m.containsAttribute("npcForm")) {
                    m.addAttribute("npcForm", npcFactory.createNpcForm(npc));
                }
                m.addAttribute("npc", npc);
                return "npcView";
            } else {
                redir.addFlashAttribute("errorMessage", "NPC not found!");
                return "redirect:/create";
            }
        } else {
            redir.addFlashAttribute("errorMessage", "You must be logged in to create NPCs!");
            return "redirect:/login";
        }
    }

    @PostMapping("/create")
    public String createNpc(@ModelAttribute NpcForm npcForm, RedirectAttributes redir, Principal p) {
        if (p != null) {
            Npc npc = npcFactory.createNpc(npcForm);
            npcRepository.save(npc);  // save the npc to the database
            redir.addFlashAttribute("npcForm", npcForm);  // add npcForm as a flash attribute
            return "redirect:/create/" + npc.getId();  // redirect to the GET handler with the npc ID
        } else {
            redir.addFlashAttribute("errorMessage", "You must be logged in to create NPCs!");
            return "redirect:/login";
        }
    }

}
