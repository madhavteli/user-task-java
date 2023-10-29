package com.example.demo.controllers.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.DateRange;
import com.example.demo.dto.TaskDTO;
import com.example.demo.dto.TaskStatistics;
import com.example.demo.entity.Task;
import com.example.demo.enums.TaskStatus;
import com.example.demo.services.interfaces.ITaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private ITaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long taskId, @RequestBody Task updatedTask) {
        TaskDTO task = taskService.updateTask(taskId, updatedTask);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<TaskDTO> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PostMapping("/{taskId}/assign")
    public ResponseEntity<String> assignTaskToUser(@PathVariable Long taskId, @RequestBody Long userId) {
        boolean isAssigned = taskService.assignTaskToUser(taskId, userId);
        if(isAssigned) {
            return new ResponseEntity<>("assigned successfully.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Something went wrong. Please try again", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/{taskId}/progress")
    public ResponseEntity<TaskDTO> setTaskProgress(
      @PathVariable Long taskId,
      @RequestBody int progress) {
        TaskDTO updatedTask = taskService.setTaskProgress(taskId, progress);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<TaskDTO>> getOverdueTasks() {
        List<TaskDTO> overdueTaskDTOs = taskService.getOverdueTasks();
        return new ResponseEntity<>(overdueTaskDTOs, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskDTO>> getTasksByStatus(@PathVariable TaskStatus status) {
        List<TaskDTO> tasks = taskService.getTasksByStatus(status);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/completed")
    public ResponseEntity<List<TaskDTO>> getCompletedTasksByDateRange(@RequestBody DateRange dateRange) {
        List<TaskDTO> completedTasks = taskService.getCompletedTasksByDateRange(dateRange.getStartDate(), dateRange.getEndDate());
        return new ResponseEntity<>(completedTasks, HttpStatus.OK);
    }

    @GetMapping("/statistics")
    public ResponseEntity<TaskStatistics> getTaskStatistics() {
        TaskStatistics taskStatistics = taskService.getTaskStatistics();
        return new ResponseEntity<>(taskStatistics, HttpStatus.OK);
    }

}
