package com.example.demo.services.interfaces;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.dto.TaskDTO;
import com.example.demo.dto.TaskStatistics;
import com.example.demo.entity.Task;
import com.example.demo.enums.TaskStatus;

public interface ITaskService {
    Task createTask(Task task);
    TaskDTO updateTask(Long taskId, Task updatedTask);
    void deleteTask(Long taskId);
    List<TaskDTO> getAllTasks();
    List<TaskDTO> getTasksByStatus(TaskStatus status);
    boolean assignTaskToUser(Long taskId, Long userId);
    List<TaskDTO> getTasksAssignedToUser(Long userId);
    TaskDTO setTaskProgress(Long taskId, int progress);
    List<TaskDTO> getOverdueTasks();
    List<TaskDTO> getCompletedTasksByDateRange(LocalDate startDate, LocalDate endDate);
    TaskStatistics getTaskStatistics();

}
