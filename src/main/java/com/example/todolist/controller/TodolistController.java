package com.example.todolist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/todo/create")
    public ModelAndView createTodo(ModelAndView mv){
        mv.setViewName("todoForm");
        mv.addObject("todoData", mv);

        return mv;
    }

    @PostMapping("/todo/create")
    private ModelAndView showTodoList(@ModelAttribute @Validated TodoData todoData,BindingResult result,ModelAndView mv){
        boolean isValid = todoService.isValid(todoData, result);
        if(!result.hasErrors() && isValid){
            Todo todo = todoData.toEntity();
            todorepository.saveAndFlush(todo);

            return showTodoList(mv);
        }else{
            mv.setViewName("todoForm");
            return mv;
        }
    }

    @PostMapping("/todo/cancel")
	public String cancel() {
		return "redirect:/todo";
	}
}