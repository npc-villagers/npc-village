package com.npcvillagers.npcvillage.controllers;

import com.npcvillagers.npcvillage.models.AppUser;
import com.npcvillagers.npcvillage.models.Npc;
import com.npcvillagers.npcvillage.models.NpcForm;
import com.npcvillagers.npcvillage.models.Task;
import com.npcvillagers.npcvillage.repos.AppUserRepository;
import com.npcvillagers.npcvillage.repos.NpcRepository;
import com.npcvillagers.npcvillage.repos.TaskRepository;
import com.npcvillagers.npcvillage.services.NpcFactory;
import com.npcvillagers.npcvillage.services.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.*;

@Controller
public class NpcController {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    NpcRepository npcRepository;

    @Autowired
    NpcFactory npcFactory;

    @Autowired
    TaskService taskService;

    @Autowired
    TaskRepository taskRepository;

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

            Npc npc = npcRepository.findById(npcId).orElse(null);

            if (npc != null) {
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
    public String createNpc(@ModelAttribute NpcForm npcForm,
                            @RequestParam("creationMethod") String creationMethod,
                            HttpSession session,
                            RedirectAttributes redir,
                            Principal p,
                            Model m) {
        if (p != null) {
            Npc npc = npcFactory.createNpc(npcForm);
            // The npcForm is added, but only handled after the eventual redirect to /create/{npcId}
            session.setAttribute("npcForm", npcForm);

            if ("Manual".equals(creationMethod)) {
                npcRepository.save(npc);  // save the npc to the database

                return "redirect:/create/" + npc.getId();  // redirect to the GET handler with the npc ID
            } else if ("AI Assistant".equals(creationMethod)) {

                Task task = taskService.createAndProcessTask(npc);

                // Add the taskId to the model so it can be passed to the loading page
                m.addAttribute("taskId", task.getId());

                // Return the loading page
                return "loading";
            } else {
                redir.addFlashAttribute("errorMessage", "Unknown creation method");

                return "redirect:/error";
            }
        } else {
            redir.addFlashAttribute("errorMessage", "You must be logged in to create NPCs!");

            return "redirect:/login";
        }

    }


    @PostMapping("/saveToMyVillage")
    public String saveNpc(Long npcId, HttpSession session, RedirectAttributes redir, Model m, Principal p) {
        if (p != null) {
            AppUser appUser = appUserRepository.findByUsername(p.getName());
            Npc npc = npcRepository.findById(npcId).orElse(null);
            if (npc != null) {
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

    @GetMapping("/checkStatus/{taskId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkStatus(@PathVariable Long taskId) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("completed", task.isCompleted());
            data.put("npcId", task.getNpc().getId());
            data.put("error", task.getError());
            return ResponseEntity.ok(data);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Task not found"));
        }
    }

    @GetMapping("/errorPage")
    public String errorPage(@RequestParam String errorMessage, Model model) {
        model.addAttribute("errorMessage", errorMessage);
        return "error";
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
            // fetch and delete the associated Task if it exists
            Task task = taskRepository.findByNpcId(npcId);
            if (task != null) {
                taskRepository.delete(task);
            }

            npcRepository.delete(npcToBeDeleted);
        } else {
            redir.addFlashAttribute("errorMessage", "NPC not found!");
        }

        return new RedirectView("/myvillage");
    }
}
