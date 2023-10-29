package com.example.demo.services.implementations;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.TaskDTO;
import com.example.demo.dto.TaskStatistics;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.enums.TaskStatus;
import com.example.demo.mappers.TaskMapper;
import com.example.demo.repos.TaskRepository;
import com.example.demo.repos.UserRepository;
import com.example.demo.services.interfaces.ITaskService;

@Service
public class TaskServiceImpl implements ITaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public TaskDTO updateTask(Long taskId, Task updatedTask) {
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setDueDate(updatedTask.getDueDate());
        existingTask.setStatus(updatedTask.getStatus());

        if (updatedTask.getStatus() != null && updatedTask.getStatus() == TaskStatus.COMPLETED) {
            existingTask.setCompletedDate(LocalDate.now());
        }

        return TaskMapper.mapToDTO(taskRepository.save(existingTask));
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return TaskMapper.mapToDTO(taskRepository.findAll());
    }

    @Override
    public List<TaskDTO> getTasksByStatus(TaskStatus status) {
        return TaskMapper.mapToDTO(taskRepository.findByStatus(status));
    }

    public boolean assignTaskToUser(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
          .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        User user = userRepository.findById(userId)
          .orElseThrow(() -> new EntityNotFoundException("User not found"));

        task.setAssignedUser(user);
        taskRepository.save(task);

        return task.getAssignedUser() != null && task.getAssignedUser().getId() == userId;
    }

    public List<TaskDTO> getTasksAssignedToUser(Long userId) {
        List<Task> tasks = taskRepository.findByAssignedUser_Id(userId);
        return TaskMapper.mapToDTO(tasks);
    }

    public TaskDTO setTaskProgress(Long taskId, int progress) {
        Task task = taskRepository.findById(taskId)
          .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        if (progress < 0 || progress > 100) {
            throw new IllegalArgumentException("Progress should be between 0 and 100.");
        }

        task.setProgress(progress);
        return TaskMapper.mapToDTO(taskRepository.save(task));
    }

    public List<TaskDTO> getOverdueTasks() {
        LocalDate currentDate = LocalDate.now();
        List<Task> overdueTasks = taskRepository.findByDueDateBeforeAndStatusNot(currentDate, TaskStatus.COMPLETED);
        return TaskMapper.mapToDTO(overdueTasks);
    }

    public List<TaskDTO> getCompletedTasksByDateRange(LocalDate startDate, LocalDate endDate) {
        return TaskMapper.mapToDTO(taskRepository.findByCompletedDateBetween(startDate, endDate));
    }

    public TaskStatistics getTaskStatistics() {
        List<Task> allTasks = taskRepository.findAll();
        long totalTasks = allTasks.size();
        long completedTasks = allTasks.stream()
          .filter(task -> task.getStatus() == TaskStatus.COMPLETED)
          .count();
        double percentageCompleted = (double) completedTasks / totalTasks * 100;

        return new TaskStatistics(totalTasks, completedTasks, percentageCompleted);
    }
}
