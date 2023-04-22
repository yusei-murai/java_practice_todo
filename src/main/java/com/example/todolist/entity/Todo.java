package com.example.todolist.entity;

import java.sql.Date;

import org.springframework.boot.autoconfigure.web.WebProperties.Resources.Chain.Strategy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="todo")
@Data
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String value;

    @Column(name = "importance")
	private Integer importance;
	
	@Column(name = "urgency")
	private Integer urgency;
	
	@Column(name = "deadline")
	private Date deadline;
	
	@Column(name = "done")
	private String done;
}
