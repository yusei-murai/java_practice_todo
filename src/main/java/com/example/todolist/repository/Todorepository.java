package com.example.todolist.repository;

import com.example.todolist.entity.Todo;

import java.util.List;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo,Integer>{
    List<Todo> findByTitleLike(String title);
	List<Todo> findByImportance(Integer importance);
	List<Todo> findByUrgency(Integer urgency);
	List<Todo> findByDeadlineBetweenOrderByDeadlineAsc(Date from, Date to);
	List<Todo> findByDeadlineGreaterThanEqualOrderByDeadlineAsc(Date from);
	List<Todo> findByDeadlineLessThanEqualOrderByDeadlineAsc(Date to);
	List<Todo> findByDone(String done);
}
