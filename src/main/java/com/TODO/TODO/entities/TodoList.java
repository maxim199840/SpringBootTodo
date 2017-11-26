package com.TODO.TODO.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "entity")
@Table(name = "todo_list")
@EntityListeners(AuditingEntityListener.class)
public class TodoList implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    private String color;

    @OneToMany(mappedBy = "todo_list", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TodoItem> todoItem = new ArrayList<>();

    public void deleteTodoItem(TodoItem item){
        todoItem.remove(item);
        item.setTodo_list(null);
    }

    public void addTodoItem(TodoItem item){
        this.todoItem.add(item);
        item.setTodo_list(this);
    }


    public List<TodoItem> getTodoItem() {
        return todoItem;
    }
    public List<TodoItem> getPriorityItem(Integer priority){
        List<TodoItem> priorityItems = new ArrayList<>();
        for(TodoItem i: todoItem){
            if(i.getPriority() == priority){
                priorityItems.add(i);
            }
        }
        return priorityItems;
    }

    public List<TodoItem> getCompletedItem(Boolean completed){
        List<TodoItem> completedItems = new ArrayList<>();
        for(TodoItem i: todoItem){
            if(i.getCompleted() == completed){
                completedItems.add(i);
            }
        }
        return completedItems;
    }

    public void updateTodoItem(TodoItem todoItemDetails){
        for(int i = 0; i < todoItem.size();i++ ){
            if(todoItem.get(i).getId() == todoItemDetails.getId()){
                todoItem.get(i).setCompleted(todoItemDetails.getCompleted());
                todoItem.get(i).setPriority(todoItemDetails.getPriority());
                todoItem.get(i).setDescription(todoItemDetails.getDescription());
            }
            todoItem.get(i).setTodo_list(this);
        }
    }


    public void setTodoItem(List<TodoItem> todoItem) {
        this.todoItem = todoItem;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
