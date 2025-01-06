package com.example.demo.user;


import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SiteUser {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(unique = true)
    private String username;//이름

    @Column(unique = true)
    private String myName;  //


    private String password;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phoneNumber;

}
