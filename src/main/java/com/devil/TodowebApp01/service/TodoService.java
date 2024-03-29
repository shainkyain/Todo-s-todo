package com.devil.TodowebApp01.service;

import com.devil.TodowebApp01.model.Todo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class TodoService {

    private static List<Todo> todos = new ArrayList<>();
    private static int todoCount= 0;
    static {
        todos.add(new Todo(++todoCount , "d" , "doo this descript" ,
                LocalDate.now().plusYears(1), false));
        todos.add(new Todo(++todoCount , "d" , "doo 2 descript" ,
                LocalDate.now().plusYears(1), false));
        todos.add(new Todo(++todoCount , "d" , "doo 3 descript" ,
                LocalDate.now().plusYears(1), false));
    }

    public List<Todo> findByUsername(String username){
        Predicate<? super Todo> predecate = todo -> todo.getUsername().equalsIgnoreCase(username);
    return  todos.stream().filter(predecate).toList();
    }

    public void addTodo(String username , String description , LocalDate targetDate , boolean done){

        Todo todo = new Todo(++todoCount ,username, description , targetDate, done);
        todos.add(todo);
    }

    public void deleteTodo(int id){
        Predicate<? super Todo> predecate = todo -> todo.getId()==id;
        todos.removeIf(predecate);
    }


//    public Todo findById(int id) {
//        Predicate<? super Todo> predecate = todo -> todo.getId()==id;
//       Todo todo =  todos.stream().filter(predecate).findFirst().get();
//       return todo;
//    }
}
