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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;
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
            String username = p.getName();
            AppUser loggedInUser = appUserRepository.findByUsername(username);
            if (loggedInUser != null) {
               m.addAttribute("username", username);
            }
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
    public String saveNpc(Long npcId, HttpSession session, RedirectAttributes redir, Model m, Principal p) {
        if (p != null) {
            AppUser appUser = appUserRepository.findByUsername(p.getName());
            Optional<Npc> createdNpc = npcRepository.findById(npcId);
            if (createdNpc.isPresent()) {
                Npc npc = createdNpc.get();
                appUser.addNpc(npc);
                npcRepository.save(npc);  // save the npc to the database
                appUserRepository.save(appUser); // save the user to the database

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

    @GetMapping("/myvillage")
    public String showMyVillage(Model m, Principal p, RedirectAttributes redir) {
        if (p != null) {
            AppUser user = appUserRepository.findByUsername(p.getName());
            m.addAttribute("user", user);
            m.addAttribute("username", user.getUsername());
            List<Npc> npcs = user.getNpcs();
            m.addAttribute("npcs", npcs);

            return "myvillage";  // Return the view name
        } else {
            redir.addFlashAttribute("errorMessage", "You must be logged in to see your village!");
            return "redirect:/login";
        }
    }

    @GetMapping("/myvillage/{npcId}")
    public String showMyVillage(@PathVariable Long npcId, Model m, Principal p, RedirectAttributes redir) {
        if (p != null) {
            AppUser user = appUserRepository.findByUsername(p.getName());
            m.addAttribute("user", user);
            m.addAttribute("username", user.getUsername());
            List<Npc> npcs = user.getNpcs();
            m.addAttribute("npcs", npcs);

            Npc npc = npcRepository.findById(npcId).orElse(null);
            if (npc != null) {
                m.addAttribute("npc", npc);
                NpcForm npcForm = npcFactory.createNpcForm(npc);
                m.addAttribute("npcForm", npcForm);
                return "myvillage";  // Return the view name with npcForm
            } else {
                redir.addFlashAttribute("errorMessage", "NPC not found!");
                return "redirect:/myvillage";
            }
        } else {
            redir.addFlashAttribute("errorMessage", "You must be logged in to see your village!");
            return "redirect:/login";
        }
    }

    @PutMapping("/updateNpc/{npcId}")
    public String updateNpc(@ModelAttribute NpcForm npcForm, @PathVariable Long npcId, RedirectAttributes redir, Principal p) {
        if (p != null) {
            Npc npctoUpdate = npcRepository.findById(npcId).orElse(null);

            if (npctoUpdate != null) {
                npctoUpdate = npcFactory.updateNpc(npcForm, npctoUpdate);
                npcRepository.save(npctoUpdate);  // save the updated npc to the database
                return "redirect:/myvillage";  // Redirect to the my village page
            } else {
                redir.addFlashAttribute("errorMessage", "NPC not found!");
                return "redirect:/create";
            }
        } else {
            redir.addFlashAttribute("errorMessage", "You must be logged in to edit NPCs!");
            return "redirect:/login";
        }
    }

    @DeleteMapping("/myvillage/{npcId}")
    public RedirectView deleteNpc(@PathVariable Long npcId, RedirectAttributes redir) {
        Npc npcToBeDeleted = npcRepository.findById(npcId).orElse(null);

        if (npcToBeDeleted != null) {
            npcRepository.delete(npcToBeDeleted);
        } else {
            redir.addFlashAttribute("errorMessage", "NPC not found!");
        }
        return new RedirectView("/myvillage");
    }
}
