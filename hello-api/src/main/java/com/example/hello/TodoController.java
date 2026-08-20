package com.example.hello;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@RestController
public class TodoController  {
    private TodoRepository todoRepository;
    public TodoController(TodoRepository repository) {
        todoRepository = repository;
    }

    //查询方法
    @GetMapping("/todos")
    public List<Todo> getTodos() {
        return todoRepository.findAll();
    }
}
