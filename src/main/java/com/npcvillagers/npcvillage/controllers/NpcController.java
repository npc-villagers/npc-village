package com.npcvillagers.npcvillage.controllers;

import com.npcvillagers.npcvillage.models.enums.Species;
import com.npcvillagers.npcvillage.repos.NpcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;


public class NpcController {
    private final NpcRepository npcRepository;

    @Autowired
    public NpcController(NpcRepository npcRepository) {
        this.npcRepository = npcRepository;
    }

    @GetMapping("/species/{speciesName}")
    public String getSpeciesWithSubspecies(@PathVariable String speciesName, Model m, Principal p, RedirectAttributes redir) {

        Species species = findSpeciesByName(speciesName);
        if (species == null) {
            redir.addFlashAttribute("errorMessage", "Species not found!");
        }

        m.addAttribute("subspecies", species.getSubspecies());
        return "species/subspecies";
    }


}
