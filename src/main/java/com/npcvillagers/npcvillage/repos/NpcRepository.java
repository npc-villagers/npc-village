package com.npcvillagers.npcvillage.repos;

import com.npcvillagers.npcvillage.models.Npc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NpcRepository extends JpaRepository<Npc, Long> {
}
