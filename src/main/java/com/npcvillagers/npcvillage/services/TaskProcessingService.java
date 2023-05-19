package com.npcvillagers.npcvillage.services;

import com.npcvillagers.npcvillage.models.Npc;
import com.npcvillagers.npcvillage.models.Task;
import com.npcvillagers.npcvillage.repos.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TaskProcessingService {
    @Autowired
    private OpenAiApiHandler openAiApiHandler;
    @Autowired
    private TaskRepository taskRepository;

    @Async("taskExecutor")
    public void processTask(Task task) {
        try {
            Npc npc = task.getNpc();
            npc = openAiApiHandler.processNpc(npc);
            task.setCompleted(true);
            task.setNpc(npc);
        } catch (Exception e) {
            task.setError(e.getMessage());
        } finally {
            taskRepository.save(task);  // update the task as completed or error
        }
    }

    public OpenAiApiHandler getOpenAiApiHandler() {
        return openAiApiHandler;
    }

    public void setOpenAiApiHandler(OpenAiApiHandler openAiApiHandler) {
        this.openAiApiHandler = openAiApiHandler;
    }

    public TaskRepository getTaskRepository() {
        return taskRepository;
    }

    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
}

