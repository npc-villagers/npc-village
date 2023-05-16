package com.npcvillagers.npcvillage.controllers;

import com.npcvillagers.npcvillage.models.AppUser;
import com.npcvillagers.npcvillage.models.Npc;
import com.npcvillagers.npcvillage.models.NpcForm;
import com.npcvillagers.npcvillage.repos.AppUserRepository;
import com.npcvillagers.npcvillage.repos.NpcRepository;
import com.npcvillagers.npcvillage.services.NpcFactory;
import jakarta.servlet.http.HttpSession;
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
    AppUserRepository appUserRepository;

    @Autowired
    NpcRepository npcRepository;

    @Autowired
    NpcFactory npcFactory;

    @GetMapping("/create")
    public String getCreateDefault(Model m, Principal p, RedirectAttributes redir) {
        if (p != null) {
            AppUser user = appUserRepository.findByUsername(p.getName());
            m.addAttribute("username", user.getUsername());
            NpcForm npcForm = new NpcForm();
            m.addAttribute("npcForm", npcForm);
            return "create";
        } else {
            redir.addFlashAttribute("errorMessage", "You must be logged in to create NPCs!");
            return "redirect:/login";
        }
    }

    @GetMapping("/create/{npcId}")
    public String getNpc(@PathVariable("npcId") Long npcId, Model m, Principal p, RedirectAttributes redir, HttpSession session) {
        if (p != null) {
            AppUser appUser = appUserRepository.findByUsername(p.getName());
            m.addAttribute("username", appUser.getUsername());

            Optional<Npc> createdNpc = npcRepository.findById(npcId);
            if (createdNpc.isPresent()) {
                Npc npc = createdNpc.get();

                // Check if the NPC is already saved in the village
                boolean isSaved = appUser.getNpcs().contains(npc);
                m.addAttribute("isSaved", isSaved);

                // Get npcForm from session if available, or create a new one
                NpcForm npcForm = (NpcForm) session.getAttribute("npcForm");
                if (npcForm == null) {
                    npcForm = npcFactory.createNpcForm(npc);
                    session.setAttribute("npcForm", npcForm);
                }
                System.out.println(npc.toString());
                m.addAttribute("npcForm", npcForm);
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
    public String createNpc(@ModelAttribute NpcForm npcForm, HttpSession session, RedirectAttributes redir, Principal p) {
        if (p != null) {
            Npc npc = npcFactory.createNpc(npcForm);
            npcRepository.save(npc);  // save the npc to the database
            session.setAttribute("npcForm", npcForm);  // add npcForm to the session
            return "redirect:/create/" + npc.getId();  // redirect to the GET handler with the npc ID
        } else {
            redir.addFlashAttribute("errorMessage", "You must be logged in to create NPCs!");
            return "redirect:/login";
        }
    }

    @PostMapping("/saveToMyVillage")
    public String saveNpc(NpcForm npcForm, Long npcId, HttpSession session, RedirectAttributes redir, Model m, Principal p) {
        if (p != null) {
            AppUser appUser = appUserRepository.findByUsername(p.getName());
            Optional<Npc> createdNpc = npcRepository.findById(npcId);
            if (createdNpc.isPresent()) {
                Npc npc = createdNpc.get();
                appUser.addNpc(npc);
                npcRepository.save(npc);  // save the npc to the database
                appUserRepository.save(appUser); // save the user to the database

                npcForm = npcFactory.createNpcForm(npc);
                session.setAttribute("npcForm", npcForm);  // add npcForm to the session
                return "redirect:/create/" + npc.getId();  // redirect to the GET handler with the npc ID
            } else {
                redir.addFlashAttribute("errorMessage", "NPC not found!");
                return "redirect:/create";
            }
        } else {
            redir.addFlashAttribute("errorMessage", "You must be logged in to create NPCs!");
            return "redirect:/login";
        }
    }

    @GetMapping("/myVillage")
    public String createNpc(Model m, Principal p, RedirectAttributes redir) {
        if (p != null) {
            AppUser user = appUserRepository.findByUsername(p.getName());
            m.addAttribute("user", user);
            m.addAttribute("username", user.getUsername());
            return "myvillage";
        } else {
            redir.addFlashAttribute("errorMessage", "You must be logged in to see your Village!");
            return "redirect:/login";
        }
    }
}
