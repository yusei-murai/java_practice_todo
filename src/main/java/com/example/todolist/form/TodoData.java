package com.example.todolist.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import com.example.todolist.entity.Todo;

import lombok.Data;

@Data
public class TodoData {
    private Integer id;

    @NotBlank(message="件名")
    private String title;

    @NotNull(message="重要度")
    private Integer importance;

    @Min(value = 0,message="緊急度")
    private Integer urgency;

    private String deadline;
    private String done;

    public Todo toEntity(){
        Todo todo = new Todo();
        todo.setId(id);
        todo.setTitle(title);
		todo.setImportance(importance);
		todo.setUrgency(urgency);
		todo.setDone(done);
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		long ms;

        try {
            ms = sdFormat.parse(deadline).getTime();
			todo.setDeadline(new Date(ms));
        } catch (ParseException e) {
            todo.setDeadline(null);
        }

        return todo;
    }
}
