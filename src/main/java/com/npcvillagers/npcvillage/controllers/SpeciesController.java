package com.npcvillagers.npcvillage.controllers;

import com.npcvillagers.npcvillage.models.enums.Species;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/species")
public class SpeciesController {

    @GetMapping("/{speciesName}")
    public ResponseEntity<List<String>> getSpeciesWithSubspecies(@PathVariable String speciesName) {
        Species species = Species.valueOf(speciesName.toUpperCase());
        if (species == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(species.getSubspecies());
    }
}
