package com.npcvillagers.npcvillage.services;

import com.npcvillagers.npcvillage.models.Npc;
import com.npcvillagers.npcvillage.models.Task;
import com.npcvillagers.npcvillage.repos.NpcRepository;
import com.npcvillagers.npcvillage.repos.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TaskService {

    @Autowired
    private NpcRepository npcRepository;

    @Autowired
    private OpenAiApiHandler openAiApiHandler;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskProcessingService taskProcessingService;

    @Transactional
    public Task createAndProcessTask(Npc npc) {
        Task task = new Task();
        task.setNpc(npc);
        task = taskRepository.save(task);  // save the task to get a generated ID
        taskProcessingService.processTask(task); // Start the async process

        return task;
    }

    public NpcRepository getNpcRepository() {
        return npcRepository;
    }

    public void setNpcRepository(NpcRepository npcRepository) {
        this.npcRepository = npcRepository;
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

    public TaskProcessingService getTaskProcessingService() {
        return taskProcessingService;
    }

    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void setTaskProcessingService(TaskProcessingService taskProcessingService) {
        this.taskProcessingService = taskProcessingService;
    }
}


