package com.recruitment.task.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "app_user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String login;

    private int requestCount = 1;

    public void incrementRequestCount() {
        requestCount++;
    }
}
