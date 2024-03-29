package com.devil.TodowebApp01.controller;

import com.devil.TodowebApp01.TodoRepository;
import com.devil.TodowebApp01.model.Todo;
import com.devil.TodowebApp01.service.TodoService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@SessionAttributes("name")
public class TodoControllerJpa {


    private TodoService todoService;
    private TodoRepository todoRepository;

//    Todo Service is not Applicable anywhere in the project its just an backup or begining code
//    or we can say its depricated and removde
    public TodoControllerJpa(TodoService todoService ,TodoRepository todoRepository) {
//        this.todoService = todoService;
        this.todoRepository = todoRepository;
    }

    @RequestMapping("/list-todos")
    public String  listAllTodos(ModelMap model){
        String username = getLoggedInUsername(model);
    List<Todo> todos = todoRepository.findByUsername(username);
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
    public String addTodoPage(@RequestParam String description , ModelMap model ,Todo todo){

            String username = getLoggedInUsername(model);
//            todo.setTargetDate(LocalDate.now().plusMonths(6));
            todo.setUsername(username);
            todoRepository.save(todo);
        return "redirect:list-todos";
    }

    private static String getLoggedInUsername(ModelMap model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @RequestMapping("/delete-todo/{id}")
    public String deleteTodo(@PathVariable("id") int id){
//    todoService.deleteTodo(id);
        todoRepository.deleteById(id);
        return  "redirect:/list-todos";
    }

    @RequestMapping(value = "/update-todo/{id}" , method = RequestMethod.GET)
    public String showUpdateTodo(@PathVariable("id") int id,  Todo todo , ModelMap model){

        todo = todoRepository.findById(id).get();
        model.addAttribute("todo" , todo);
        return  "UpdateTodo";
    }

    @RequestMapping(value = "/save-updated-todo", method = RequestMethod.POST)
    public String updateTodo( ModelMap model , Todo todo){

        String username = getLoggedInUsername(model);
        todo.setUsername(username);
        todoRepository.save(todo);

        return "redirect:list-todos";
    }
}
