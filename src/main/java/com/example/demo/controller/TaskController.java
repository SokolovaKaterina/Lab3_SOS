//package com.example.demo.controller;
//
//
//import com.example.demo.application.TaskService;
//import com.example.demo.domain.Task;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.Map;
//
//@Controller
//public class TaskController {
//    @Autowired
//    private TaskService taskService;
//
//    @GetMapping("/task")
//    public String mainTask(@RequestParam(required = false) String filter, Model model) {
//        Iterable<Task> tasks = taskService.all();
//        if (filter != null && !filter.isEmpty()) {
//            tasks = taskService.findByName(filter);
//        } else {
//            tasks = taskService.all();
//        }
//        model.addAttribute("tasks", tasks);
//        model.addAttribute("filter", filter);
//        return "task";
//    }
//    @PostMapping("/task")
//    public String addTask(@RequestParam String name,
//                          @RequestParam String phoneNumber,
//                          @RequestParam String description,
//                          @RequestParam Integer price,
//                          Map<String, Object> model) {
//        Task task = new Task(name, phoneNumber, description, price);
//        taskService.createTask(task);
//        Iterable<Task> tasks = taskService.all();
//        model.put("tasks", tasks);
//        return "task";
//    }
//
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    @PostMapping("/deleteTask")
//    public String delTask(@RequestParam(required = false) long id,
//                           Map<String, Object> model) {
//        Task task = taskService.searchTask(id);
//        taskService.removeTask(task.getId());
//        Iterable<Task> tasks = taskService.all();
//        model.put("tasks", tasks);
//        return "taskAdmin";
//    }
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    @PostMapping("/taskSolution")
//    public String solTask(@RequestParam(required = false) long id,
//                          Map<String, Object> model) {
//        taskService.takeTask(id);
//        Iterable<Task> tasks = taskService.all();
//        model.put("tasks", tasks);
//        return "taskAdmin";
//    }
//    @GetMapping("/taskAdmin")
//    public String taskAdmin(@RequestParam(required = false) String filter, Model model) {
//        Iterable<Task> tasks = taskService.all();
//        if (filter != null && !filter.isEmpty()) {
//            tasks = taskService.findByName(filter);
//        } else {
//            tasks = taskService.all();
//        }
//        model.addAttribute("tasks", tasks);
//        model.addAttribute("filter", filter);
//        return "taskAdmin";
//    }
//
//
//}




package com.example.demo.controller;

import com.example.demo.application.OrganisationService;
import com.example.demo.application.TaskService;
import com.example.demo.domain.Department;
import com.example.demo.domain.Organisation;
import com.example.demo.domain.Staff;
import com.example.demo.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private OrganisationService organisationService;

    @GetMapping("/task")
    public String mainTask(@RequestParam(required = false) String filter,
                           @RequestParam(required = false) Long taskId,
                           @RequestParam(required = false) Long orgId,
                           Model model) {
        Iterable<Task> tasks = taskService.all();
        Iterable<Organisation> organisations = organisationService.all();

        // Фильтрация задач по имени
        if (filter != null && !filter.isEmpty()) {
            tasks = taskService.findByName(filter);
        }

        // Привязка задачи к организации
        if (taskId != null && orgId != null) {
            Task task = taskService.searchTask(taskId);
            Organisation organisation = organisationService.searchOrganisation(orgId);

            if (task != null && organisation != null && task.getOrganisation() == null) {
                task.setOrganisation(organisation);
                taskService.createTask(task);
            }
        }

        model.addAttribute("tasks", tasks);
        model.addAttribute("filter", filter);
        model.addAttribute("organisations", organisations);
        return "task";
    }

    @PostMapping("/task")
    public String addTask(@RequestParam String name,
                          @RequestParam String phoneNumber,
                          @RequestParam String description,
                          @RequestParam(required = false) Long orgId,
                          @RequestParam(required = false) Long taskIdForOrganisation,
                          Map<String, Object> model) {

        // Логика для добавления задачи с организацией или без нее
        Organisation organisation = null;
        if (orgId != null) {
            // Если передан ID организации, ищем организацию в базе данных
            organisation = organisationService.searchOrganisation(orgId);
            if (organisation == null) {
                model.put("error", "Организация с указанным ID не найдена");
                model.put("tasks", taskService.all());
                model.put("organisations", organisationService.all());
                return "task";
            }
        }

        // Создание новой задачи
        Task task = new Task(name, phoneNumber, description, organisation);

        // Сохраняем задачу в базе данных
        taskService.createTask(task);

        // Логика для добавления организации по ID задачи
        if (taskIdForOrganisation != null) {
            Task taskToUpdate = taskService.searchTask(taskIdForOrganisation);
            if (taskToUpdate != null) {
                taskToUpdate.setOrganisation(organisation);
                taskService.createTask(taskToUpdate);  // Обновляем задачу с привязанной организацией
            }
        }

        // Обновляем модель для отображения
        Iterable<Task> tasks = taskService.all();
        Iterable<Organisation> organisations = organisationService.all();

        model.put("tasks", tasks);
        model.put("organisations", organisations);
        return "task";
    }


    @PostMapping("/assignOrganisationToTask")
    public String assignOrganisationToTask(@RequestParam long taskId,
                                           @RequestParam long orgId,
                                           Map<String, Object> model) {

        // Получаем задачу по ID
        Task task = taskService.searchTask(taskId);

        if (task == null) {
            model.put("error", "Задача с указанным ID не найдена");
            return "task";
        }

        // Проверяем, есть ли уже привязанная организация
        if (task.getOrganisation() != null) {
            model.put("error", "Задача уже привязана к организации");
        } else {
            // Получаем организацию по ID
            Organisation organisation = organisationService.getOrganisationById(orgId);

            if (organisation == null) {
                model.put("error", "Организация с указанным ID не найдена");
            } else {
                // Назначаем организацию задаче
                task.setOrganisation(organisation);
                taskService.createTask(task);  // Сохраняем обновленную задачу
                model.put("success", "Организация успешно назначена задаче");
            }
        }

        // Обновляем модель с задачами и организациями
        Iterable<Task> tasks = taskService.all();
        Iterable<Organisation> organisations = organisationService.all();

        model.put("tasks", tasks);
        model.put("organisations", organisations);
        return "task";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/deleteTask")
    public String delTask(@RequestParam(required = false) long id,
                          Map<String, Object> model) {
        Task task = taskService.searchTask(id);
        taskService.removeTask(task.getId());
        Iterable<Task> tasks = taskService.all();
        model.put("tasks", tasks);
        return "taskAdmin";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/taskSolution")
    public String solTask(@RequestParam(required = false) long id,
                          Map<String, Object> model) {
        taskService.takeTask(id);
        Iterable<Task> tasks = taskService.all();
        model.put("tasks", tasks);
        return "taskAdmin";
    }

    @GetMapping("/taskAdmin")
    public String taskAdmin(@RequestParam(required = false) String filter, Model model) {
        Iterable<Task> tasks = taskService.all();
        if (filter != null && !filter.isEmpty()) {
            tasks = taskService.findByName(filter);
        } else {
            tasks = taskService.all();
        }
        model.addAttribute("tasks", tasks);
        model.addAttribute("filter", filter);
        return "taskAdmin";
    }
}
