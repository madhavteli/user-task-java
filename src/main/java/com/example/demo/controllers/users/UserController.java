package com.example.demo.controllers.users;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.TaskDTO;
import com.example.demo.entity.User;
import com.example.demo.repos.UserRepository;
import com.example.demo.services.interfaces.ITaskService;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserRepository repository;
  @Autowired
  private ITaskService taskService;

  @PostMapping("/add")
  public ResponseEntity<User> addUser(@RequestBody User user) {
    return new ResponseEntity<>(repository.save(user), HttpStatus.CREATED);
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
    if (repository.existsById(userId)) {
      repository.deleteById(userId);
      return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    } else {
      return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/{userId}")
  public ResponseEntity<User> getUser(@PathVariable Long userId) {
    Optional<User> userOptional = repository.findById(userId);
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      return new ResponseEntity<>(user, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping
  public ResponseEntity<List<User>> getAllUsers() {
    List<User> users = repository.findAll();
    return new ResponseEntity<>(users, HttpStatus.OK);
  }

  @GetMapping("/{userId}/tasks")
  public ResponseEntity<List<TaskDTO>> getTasksAssignedToUser(@PathVariable Long userId) {
    List<TaskDTO> assignedTasks = taskService.getTasksAssignedToUser(userId);
    return new ResponseEntity<>(assignedTasks, HttpStatus.OK);
  }
}
