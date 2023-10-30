package com.example.demo.dto;

import java.time.LocalDate;

import com.example.demo.enums.TaskPriorityLevel;
import com.example.demo.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private LocalDate completedDate;
    private TaskStatus status;
    private Long assignedUserId;
    private int progress;
    private TaskPriorityLevel taskPriorityLevel;
}
