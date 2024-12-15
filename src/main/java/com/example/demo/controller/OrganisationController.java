package com.example.demo.controller;

import com.example.demo.application.DepartmentService;
import com.example.demo.application.OrganisationService;
import com.example.demo.application.StaffService;
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

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Controller
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class OrganisationController {
    @Autowired
    private StaffService staffService;
    @Autowired
    private OrganisationService organisationService;
    @Autowired
    private TaskService taskService;
    @GetMapping("/organisation")
    public String mainOrg(@RequestParam(required = false) String filter,
                          @RequestParam(required = false)  Long orgId,
                          @RequestParam(required = false)  Long staffId,
                          Model model) {
        Iterable<Organisation> organisations = organisationService.all();
        Iterable<Staff> staff = staffService.all();
        if (filter != null && !filter.isEmpty()) {
            organisations = organisationService.findByName(filter);
        } else {
            organisations = organisationService.all();
        }
        if (orgId != null && staffId != null) {
            Staff boss = staffService.searchStaff(staffId);
            Organisation organisation = organisationService.searchOrganisation(orgId);
            organisation.setBoss(boss);
            organisationService.createOrganisation(organisation);
        }
        Iterable<Task> tasks = taskService.all();

        model.addAttribute("organisations", organisations);
        model.addAttribute("filter", filter);
        model.addAttribute("staff", staff);
        model.addAttribute("tasks", tasks);  // Добавьте список задач в модель

        return "organisation";
    }

    @PostMapping("/assignBoss")
    public String assignBoss(@RequestParam(required = false) Long orgId,
                             @RequestParam(required = false) Long staffId,
                             Model model) {
        try {
            Organisation organisation = organisationService.searchOrganisation(orgId);
            Staff boss = staffService.searchStaff(staffId);

            if (organisation != null && boss != null) {
                organisation.setBoss(boss); // Устанавливаем директора
                organisationService.createOrganisation(organisation); // Сохраняем изменения
            }

            // Обновляем данные для отображения на странице
            Iterable<Organisation> organisations = organisationService.all();
            Iterable<Staff> staff = staffService.all();
            Iterable<Task> tasks = taskService.all();

            model.addAttribute("organisations", organisations);
            model.addAttribute("staff", staff);
            model.addAttribute("tasks", tasks);

        } catch (NoSuchElementException e) {
            model.addAttribute("error", "Организация или сотрудник не найдены.");
        }
        return "organisation"; // Возвращаем на страницу с организациями
    }


//    @PostMapping("/organisation")
//    public String addOrg(@RequestParam String name,
//                         @RequestParam String description,
//                         @RequestParam String address,
//                         Map<String, Object> model) {
//        Organisation organisation = new Organisation(name, description, address);
//        organisationService.createOrganisation(organisation);
//        Iterable<Organisation> organisations = organisationService.all();
//        Iterable<Staff> staff = staffService.all();
//        model.put("staff", staff);
//        model.put("organisations", organisations);
//        return "organisation";
//    }

    @PostMapping("/organisation")
    public String addOrg(@RequestParam String name,
                         @RequestParam String description,
                         @RequestParam String address,
                         Model model) {
        Organisation organisation = new Organisation(name, description, address);
        organisationService.createOrganisation(organisation);  // Проверьте реализацию этого метода
        Iterable<Organisation> organisations = organisationService.all();  // Загрузка всех организаций
        Iterable<Staff> staff = staffService.all();  // Загрузка всех сотрудников
        Iterable<Task> tasks = taskService.all();

        model.addAttribute("organisations", organisations);  // Добавляем в модель
        model.addAttribute("staff", staff);  // Добавляем сотрудников в модель
        model.addAttribute("tasks", tasks);
        return "organisation";  // Возвращаем шаблон
    }


//    @PostMapping("/deleteOrganisation")
//    public String delOrg(@RequestParam(required = false) String name,
//                         Map<String, Object> model) {
//        List<Organisation> organisations = organisationService.findByName(name);
//        for (Organisation organisation:organisations){
//            organisationService.removeOrganisation(organisation.getId());
//        }
//        Iterable<Organisation> org = organisationService.all();
//        model.put("organisations", org);
//        Iterable<Staff> staff = staffService.all();
//        model.put("staff", staff);
//        return "organisation";
//    }

    @PostMapping("/deleteOrganisation")
    public String delOrg(@RequestParam(required = false) String name,
                         Model model) {
        if (name != null && !name.isEmpty()) {
            List<Organisation> organisations = organisationService.findByName(name);
            for (Organisation organisation : organisations) {
                organisationService.removeOrganisation(organisation.getId());  // Удаление организации
            }
        }

        Iterable<Organisation> org = organisationService.all();  // Загрузка всех организаций
        Iterable<Staff> staff = staffService.all();  // Загрузка сотрудников
        Iterable<Task> tasks = taskService.all();

        model.addAttribute("organisations", org);  // Добавляем в модель
        model.addAttribute("staff", staff);  // Добавляем сотрудников в модель
        model.addAttribute("tasks", tasks);
        return "organisation";  // Возвращаем шаблон
    }

}
