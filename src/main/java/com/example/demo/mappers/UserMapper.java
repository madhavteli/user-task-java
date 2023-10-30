package com.example.demo.mappers;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;

public class UserMapper {
  public static UserDTO mapToDTO(User user) {
    UserDTO userDTO = new UserDTO();
    userDTO.setId(user.getId());
    userDTO.setFirstName(user.getFirstName());
    userDTO.setLastName(user.getLastName());
    userDTO.setEmail(user.getEmail());
    userDTO.setDateOfBirth(user.getDateOfBirth());
    userDTO.setGender(user.getGender());
    userDTO.setPhoneNumber(user.getPhoneNumber());
    userDTO.setActive(user.isActive());
    if (null == user.getTasks()) {
      userDTO.setTaskIds(new ArrayList<>());
    } else {
      userDTO.setTaskIds(user.getTasks().stream()
                           .map(Task::getId)
                           .toList());
    }
    return userDTO;
  }

  public static List<UserDTO> mapToDTOList(List<User> userList) {
    return userList.stream()
      .map(UserMapper::mapToDTO)
      .toList();
  }

}
