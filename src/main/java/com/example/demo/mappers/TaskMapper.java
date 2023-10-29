package com.example.demo.mappers;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.dto.TaskDTO;
import com.example.demo.entity.Task;

public class TaskMapper {
  public static List<TaskDTO> mapToDTO(List<Task> tasks) {
    List<TaskDTO> taskDTOList = new ArrayList<>();
    for (Task task : tasks) {
      taskDTOList.add(mapToDTO(task));
    }
    return taskDTOList;
  }


  public static TaskDTO mapToDTO(Task task) {
    TaskDTO taskDTO = new TaskDTO();
    taskDTO.setId(task.getId());
    taskDTO.setTitle(task.getTitle());
    taskDTO.setDescription(task.getDescription());
    taskDTO.setDueDate(task.getDueDate());
    taskDTO.setCompletedDate(task.getCompletedDate());
    taskDTO.setStatus(task.getStatus());
    taskDTO.setProgress(task.getProgress());

    if (task.getAssignedUser() != null) {
      taskDTO.setAssignedUserId(task.getAssignedUser().getId());
    }

    return taskDTO;
  }
}
