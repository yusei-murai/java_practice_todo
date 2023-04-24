package com.example.todolist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.todolist.repository.Todorepository;
import com.example.todolist.service.TodoService;

import jakarta.servlet.http.HttpSession;

import com.example.todolist.entity.Todo;
import com.example.todolist.form.TodoData;

import lombok.AllArgsConstructor;
import java.util.List;


@Controller
@AllArgsConstructor
public class TodolistController {
    private final Todorepository todoRepository;
    private final TodoService todoService;
    private final HttpSession session;

    @GetMapping("/todo")
    private ModelAndView showTodoList(ModelAndView mv){
        mv.setViewName("todoList");
        List<Todo> todoList = todoRepository.findAll();
        mv.addObject("todoList", todoList);

        return mv;
    }

    @GetMapping("/todo/create")
    public ModelAndView createTodo(ModelAndView mv){
        mv.setViewName("todoForm");
        mv.addObject("todoData", new TodoData());

        return mv;
    }

    @PostMapping("/todo/create")
    private ModelAndView showTodoList(@ModelAttribute @Validated TodoData todoData,BindingResult result,ModelAndView mv){
        boolean isValid = todoService.isValid(todoData, result);
        if(!result.hasErrors() && isValid){
            Todo todo = todoData.toEntity();
            todoRepository.saveAndFlush(todo);

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

    @GetMapping("/todo/{id}")
    public ModelAndView nameTodoById(@PathVariable(name = "id") int id,ModelAndView mv) {
        mv.setViewName("todoForm");
        Todo todo = todoRepository.findById(id).get();
        mv.addObject("todoData",todo);
        session.setAttribute("mode", "update");
        
        return mv;
    }

    @PostMapping("/todo/update")
	public String updateTodo(@ModelAttribute @Validated TodoData todoData,BindingResult result,Model model) {
        boolean isValid = todoService.isValid(todoData, result);
        if(!result.hasErrors() && isValid){
            Todo todo = todoData.toEntity();
            todoRepository.saveAndFlush(todo);

            return "redirect:/todo";
        }else{
            return "todoForm";
        }
	}

    @PostMapping("/todo/delete")
	public String updateTodo(@ModelAttribute TodoData todoData) {
        todoRepository.deleteById(todoData.getId());
        return "redirect:/todo";
	}
}