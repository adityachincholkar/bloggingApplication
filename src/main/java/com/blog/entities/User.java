package com.blog.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
    @Column(name = "user_name" , nullable = false, length = 100)
    private String email;
    private String password;
    private String about;
}
