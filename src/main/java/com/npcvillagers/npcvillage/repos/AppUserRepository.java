package com.npcvillagers.npcvillage.repos;


import com.npcvillagers.npcvillage.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    public AppUser findByUsername(String username);
}
