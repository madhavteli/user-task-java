package com.example.demo.repos;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Task;
import com.example.demo.enums.TaskStatus;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(TaskStatus status);
    List<Task> findByCompletedDateBetween(LocalDate startDate, LocalDate endDate);
    List<Task> findByAssignedUser_Id(Long userId);
    List<Task> findByDueDateBeforeAndStatusNot(LocalDate currentDate, TaskStatus status);


}
