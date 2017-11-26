package com.TODO.TODO.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "TodoItem")
@Table(name = "todo_item")
@EntityListeners(AuditingEntityListener.class)
public class TodoItem implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }

    private Boolean completed = false;
    private Integer priority = 0;


    @NotBlank
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_id")
    private TodoList todo_list;
    @JsonIgnore
    public TodoList getTodo_list() {
        return todo_list;
    }

    public void setTodo_list(TodoList todo_list) {
        this.todo_list = todo_list;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Boolean getCompleted() {

        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public void setId(Long id) {

        this.id = id;
    }
}
