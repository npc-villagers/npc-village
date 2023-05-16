package com.npcvillagers.npcvillage.controllers;

import com.npcvillagers.npcvillage.models.Npc;
import com.npcvillagers.npcvillage.repos.AppUserRepository;
import com.npcvillagers.npcvillage.repos.NpcRepository;
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

    @GetMapping("/create")
    public String getCreateDefault(Model m, Principal p, RedirectAttributes redir) {
        System.out.println("I'm here");
        if (p != null) {
            Npc npc = new Npc();
            m.addAttribute("npc", npc);
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
            System.out.println(createdNpc.toString());
            if (createdNpc.isPresent()) {
                Npc npc = createdNpc.get();
                m.addAttribute("npc", npc);
                return "npcView";
            } else {
                redir.addFlashAttribute("errorMessage", "NPC not found!");
                return "redirect:/create";
            }
        } else {
            redir.addFlashAttribute("errorMessage", "You must be logged in to view users!");
            return "redirect:/login";
        }
    }

    @PostMapping("/create")
    public String createNpc(@ModelAttribute Npc npc, RedirectAttributes redir, Principal p) {
        if (p != null) {
            // Call the setters to randomize fields as necessary
            npc.setSpecies(npc.getSpecies());
            npc.setSubspecies(npc.getSubspecies());
            npc.setGender(npc.getGender());
            npc.setAlignment(npc.getAlignment());
            npc.setAgeCategory(npc.getAgeCategory());
            npc.setAge(npc.getAge());
            npc.setOccupationCategory(npc.getOccupationCategory());
            npc.setOccupation(npc.getOccupation());
            npc.setCharacterClass(npc.getCharacterClass());
            npc.setCampaignStyle(npc.getCampaignStyle());
            npc.setPlayerRelationship(npc.getPlayerRelationship());

            Npc savedNpc = npcRepository.save(npc);  // save the npc to the database
            return "redirect:/create/" + savedNpc.getId();  // redirect to the GET handler with the npc ID
        } else {
            redir.addFlashAttribute("errorMessage", "You must be logged in to create NPCs!");
            return "redirect:/login";
        }
    }

}
