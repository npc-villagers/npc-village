package com.npcvillagers.npcvillage.repos;


import com.npcvillagers.npcvillage.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
