package com.TODO.TODO.controllers;

import com.TODO.TODO.entities.TodoItem;
import com.TODO.TODO.entities.TodoList;
import com.TODO.TODO.repositories.TodoItemRepository;
import com.TODO.TODO.repositories.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/")
public class TodoListController {

    @Autowired
    TodoListRepository todoListRepository;
    @Autowired
    TodoItemRepository todoItemRepository;

    @GetMapping("/lists")
    public List<TodoList> getAllLists() {
        return todoListRepository.findAll();
    }

    @GetMapping("/lists/{id}")
    public ResponseEntity<TodoList> getListById(@PathVariable(value = "id") Long listId, @RequestParam(value = "priority", defaultValue = "-1") String priority, @RequestParam(value = "completed", defaultValue = "-1") String completed) {
        TodoList list = todoListRepository.findOne(listId);
        if (list == null) {
            return ResponseEntity.notFound().build();
        }
        if (Integer.parseInt(priority) != (-1)) {
            list.setTodoItem(list.getPriorityItem(Integer.parseInt(priority)));
        }
        if (Integer.parseInt(completed) != (-1)) {
            Boolean complete = Integer.parseInt(completed) == 0 ? false : true;
            list.setTodoItem(list.getCompletedItem(complete));
        }

        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/lists")
    public TodoList createList(@Valid @RequestBody TodoList newList) {
        return todoListRepository.save(newList);
    }

    @PostMapping("/lists/{id}")
    public TodoList createItem(@Valid @RequestBody TodoItem newItem, @PathVariable(value = "id") Long listId) {
        TodoList list = todoListRepository.findOne(listId);
        list.addTodoItem(newItem);
        todoItemRepository.save(newItem);
        return todoListRepository.save(list);
    }

    @PutMapping("/lists/{id}")
    public ResponseEntity<TodoList> updateList(@PathVariable(value = "id") Long todoListId,
                                               @Valid @RequestBody TodoList todoListDetails) {
        TodoList todoList = todoListRepository.findOne(todoListId);
        if (todoList == null) {
            return ResponseEntity.notFound().build();
        }
        todoList.setName(todoListDetails.getName());
        todoList.setColor(todoListDetails.getColor());

        TodoList updatedtodoList = todoListRepository.save(todoList);
        return ResponseEntity.ok(updatedtodoList);
    }

    @PutMapping("/lists/{id}/{todoId}")
    public ResponseEntity<TodoList> updateTodoItem(@PathVariable(value = "id") Long todoListId,
                                                   @Valid @RequestBody TodoItem todoItemDetails, @PathVariable(value = "todoId") Long todoItemId) {
        TodoList todoList = todoListRepository.findOne(todoListId);
        if (todoList == null) {
            return ResponseEntity.notFound().build();
        }
        todoList.updateTodoItem(todoItemDetails);
        todoItemRepository.save(todoItemDetails);
        TodoList updatedtodoList = todoListRepository.save(todoList);
        return ResponseEntity.ok(updatedtodoList);
    }

    @DeleteMapping("/lists/{id}")
    public ResponseEntity<TodoList> deletetodoList(@PathVariable(value = "id") Long todoListId) {
        TodoList todoList = todoListRepository.findOne(todoListId);
        if (todoList == null) {
            return ResponseEntity.notFound().build();
        }

        todoListRepository.delete(todoList);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/lists/{id}/{todoId}")
    public ResponseEntity<TodoList> deletetodoList(@PathVariable(value = "id") Long todoListId, @PathVariable(value = "todoId") Long todoItemId) {
        TodoList todoList = todoListRepository.findOne(todoListId);
        if (todoList == null) {
            return ResponseEntity.notFound().build();
        }
        TodoItem todoItem = todoItemRepository.findOne(todoItemId);
        todoList.deleteTodoItem(todoItem);
        todoListRepository.save(todoList);
        return ResponseEntity.ok().build();
    }
}
