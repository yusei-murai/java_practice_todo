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

import com.example.todolist.repository.TodoRepository;
import com.example.todolist.service.TodoService;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;

import com.example.todolist.dao.TodoDaoImpl;
import com.example.todolist.entity.Todo;
import com.example.todolist.form.TodoData;
import com.example.todolist.form.TodoQuery;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class TodolistController {
    private final TodoRepository todoRepository;
    private final TodoService todoService;
    private final HttpSession session;

    @PersistenceContext
    private  EntityManager entityManager;
    TodoDaoImpl todoDaoImpl;

    @PostConstruct
    private void init() {
        todoDaoImpl = new TodoDaoImpl(entityManager);
    }

    @GetMapping("/todo")
    private ModelAndView showTodoList(ModelAndView mv){
        mv.setViewName("todoList");
        List<Todo> todoList = todoRepository.findAll();
        mv.addObject("todoList", todoList);
        mv.addObject("todoQuery", new TodoQuery());

        return mv;
    }

    @GetMapping("/todo/create")
    public ModelAndView createTodo(ModelAndView mv){
        mv.setViewName("todoForm");
        mv.addObject("todoData", new TodoData());

        return mv;
    }

    @PostMapping("/todo/create")
    private String createTodo(@ModelAttribute @Validated TodoData todoData,BindingResult result,ModelAndView mv){
        boolean isValid = todoService.isValid(todoData, result);
        if(!result.hasErrors() && isValid){
            Todo todo = todoData.toEntity();
            todoRepository.saveAndFlush(todo);

            return "redirect:/todo";
        }else{
            return "todoForm";
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

    @PostMapping("/todo/query")
    public ModelAndView queryTodo(@ModelAttribute TodoQuery todoQuery,BindingResult result,ModelAndView mv) {
        mv.setViewName("todoList");

        List<Todo> todoList = null;
        if(todoService.isValid(todoQuery, result)){
            todoList = todoDaoImpl.findByJPQL(todoQuery);
        }
        mv.addObject("todoList",todoList);

        return mv;
    }
}