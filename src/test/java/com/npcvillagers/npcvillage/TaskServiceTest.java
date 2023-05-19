package com.npcvillagers.npcvillage;

import com.npcvillagers.npcvillage.models.Npc;
import com.npcvillagers.npcvillage.models.Task;
import com.npcvillagers.npcvillage.repos.TaskRepository;
import com.npcvillagers.npcvillage.services.OpenAiApiHandler;
import com.npcvillagers.npcvillage.services.TaskProcessingService;
import com.npcvillagers.npcvillage.services.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Test
    public void testCreateAndProcessTask() {
        // Arrange
        Npc npc = new Npc();  // create a test Npc
        Task task = new Task(); // create a test Task
        task.setNpc(npc);

        // Create mock dependencies
        TaskRepository mockTaskRepository = Mockito.mock(TaskRepository.class);
        TaskProcessingService mockTaskProcessingService = Mockito.mock(TaskProcessingService.class);

        // Stub the save method in mockTaskRepository to return the test task when called
        when(mockTaskRepository.save(any(Task.class))).thenReturn(task);

        // Inject mock dependencies into TaskService
        TaskService taskService = new TaskService();
        taskService.setTaskRepository(mockTaskRepository);
        taskService.setTaskProcessingService(mockTaskProcessingService);

        // Act
        Task result = taskService.createAndProcessTask(npc);

        // Assert
        verify(mockTaskRepository, times(1)).save(any(Task.class));  // Assert that save was called once
        verify(mockTaskProcessingService, times(1)).processTask(any(Task.class));  // Assert that processTask was called once
        assertEquals(task, result);  // Assert that the returned task is the one we expected
    }

    @Test
    public void testProcessTaskWithException() {
        // Arrange
        Npc npc = new Npc();  // create a test Npc
        Task task = new Task(); // create a test Task
        task.setNpc(npc);

        // Create mock dependencies
        OpenAiApiHandler mockOpenAiApiHandler = Mockito.mock(OpenAiApiHandler.class);
        TaskRepository mockTaskRepository = Mockito.mock(TaskRepository.class);

        // Stub the processNpc method in mockOpenAiApiHandler to throw an exception when called
        when(mockOpenAiApiHandler.processNpc(any(Npc.class))).thenThrow(new RuntimeException("Test exception"));

        // Inject mock dependencies into TaskProcessingService
        TaskProcessingService taskProcessingService = new TaskProcessingService();
        taskProcessingService.setOpenAiApiHandler(mockOpenAiApiHandler);
        taskProcessingService.setTaskRepository(mockTaskRepository);

        // Act
        taskProcessingService.processTask(task);

        // Assert
        assertEquals("Test exception", task.getError());  // Assert that the task's error is set to the exception message
        verify(mockTaskRepository, times(1)).save(task);  // Assert that save was called once
    }

}
