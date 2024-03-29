package com.devil.TodowebApp01.controller;

import com.devil.TodowebApp01.model.Todo;
import com.devil.TodowebApp01.service.TodoService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

//@Controller
@SessionAttributes("name")
public class TodoController {


    private TodoService todoService;
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @RequestMapping("/list-todos")
    public String  listAllTodos(ModelMap model){
        String username = getLoggedInUsername(model);
    List<Todo> todos = todoService.findByUsername(username);
     model.addAttribute("todos", todos);

        return "ListTodos";
    }

    @RequestMapping("/welcome")
    public String welcomePage(){
        return "welcome";
    }
    @RequestMapping(value = "/add-todo", method = RequestMethod.GET)
    public String showNewTodoPage(){
        return "newTodo";
    }

    @RequestMapping(value = "/add-todo", method = RequestMethod.POST)
    public String addTodoPage(@RequestParam String description , ModelMap model){
    String username = getLoggedInUsername(model);
        todoService.addTodo(username , description ,
                            LocalDate.now().plusMonths(6), false);
        return "redirect:list-todos";
    }

    private static String getLoggedInUsername(ModelMap model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @RequestMapping("/delete-todo/{id}")
    public String deleteTodo(@PathVariable("id") int id){
    todoService.deleteTodo(id);
        return  "redirect:/list-todos";
    }
//
//    @RequestMapping(value = "/update-todo" , method = RequestMethod.PUT)
//    public String showUpdateTodo(){
////        Todo todo = todoService.findById(id);
////        model.addAttribute("todo" , todo);
//        return  "UpdateTodo";
//    }
}
