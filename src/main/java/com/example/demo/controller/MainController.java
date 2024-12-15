package com.example.demo.controller;

import com.example.demo.application.DepartmentService;
import com.example.demo.application.OrganisationService;
import com.example.demo.application.TaskService;
import com.example.demo.domain.Department;
import com.example.demo.domain.Organisation;
import com.example.demo.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private OrganisationService organisationService;
    @Autowired
    private TaskService taskService;
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/mainAdmin")
    public String mainAdmin(){
        return "mainAdmin";
    }
    @GetMapping("/mainUser")
    public String mainUser(Model model){
        Iterable<Task> tasks = taskService.all();
        model.addAttribute("tasks", tasks);
        return "mainUser";
    }
    @GetMapping("/")
    public String main(Model model) {
        Iterable<Organisation> organisations = organisationService.all();
        Iterable<Department> departments = departmentService.all();
        model.addAttribute("departments", departments);
        model.addAttribute("organisations", organisations);
        return "greeting";
    }

}
