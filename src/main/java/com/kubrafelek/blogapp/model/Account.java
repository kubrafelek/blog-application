package com.kubrafelek.blogapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    String email;

    @JsonIgnore
    String password;

    String firstName;

    String lastName;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    String adminRole;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    List<Post> posts;
}
