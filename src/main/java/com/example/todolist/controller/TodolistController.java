package com.example.todolist.controller;

import org.springframework.stereotype.Controller;

import com.example.todolist.repository.Todorepository;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstractor
public class TodolistController {
    private final Todorepository todorepository;
    
}
