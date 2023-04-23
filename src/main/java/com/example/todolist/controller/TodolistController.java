package com.example.todolist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.todolist.repository.Todorepository;
import com.example.todolist.entity.Todo;

import lombok.AllArgsConstructor;
import java.util.List;


@Controller
@AllArgsConstructor
public class TodolistController {
    private final Todorepository todorepository;

    @GetMapping("/todo")
    private ModelAndView showTodoList(ModelAndView mv){
        mv.setViewName("todoList");
        List<Todo> todoList = todorepository.findAll();
        mv.addObject("todoList", todoList);

        return mv;
    }
}
