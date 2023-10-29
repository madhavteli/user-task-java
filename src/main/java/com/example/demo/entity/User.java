package com.example.demo.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;

  @Column(name = "first_name")
  String firstName;

  @Column(name = "last_name")
  String lastName;

  @Column(name = "email")
  String email;

  @Column(name = "date_of_birth")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  LocalDate dateOfBirth;

  @Column(name = "gender")
  String gender;

  @Column(name = "phone_number")
  Long phoneNumber;

  @Column(name = "active")
  boolean active;

  @OneToMany(mappedBy = "assignedUser")
  List<Task> tasks;
}