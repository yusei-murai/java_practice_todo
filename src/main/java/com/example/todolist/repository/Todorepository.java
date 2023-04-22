package com.example.todolist.repository;

import com.example.todolist.entity.Todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class Todorepository extends JpaRepository<Todo,Integer>{
}
