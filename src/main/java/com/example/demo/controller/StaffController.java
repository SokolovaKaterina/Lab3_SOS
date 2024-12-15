package com.example.demo.controller;

import com.example.demo.application.DepartmentService;
import com.example.demo.application.StaffService;
import com.example.demo.domain.Department;
import com.example.demo.domain.Organisation;
import com.example.demo.domain.Role;
import com.example.demo.domain.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class StaffController {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private StaffService staffService;
    @GetMapping("/staff")
    public String mainStaff(@RequestParam(required = false) String filter, Model model) {
        Iterable<Staff> staff = staffService.all();
        if (filter != null && !filter.isEmpty()) {
            staff = staffService.findByName(filter);
        } else {
            staff = staffService.all();
        }
        Iterable<Department> departments = departmentService.all();
        model.addAttribute("departments", departments);
        model.addAttribute("filter", filter);
        model.addAttribute("staff", staff);
        return "staff";
    }

    @PostMapping("/staff")
    public String addStaff(@RequestParam String FCs,
                         @RequestParam String address,
                         @RequestParam String post,
                         @RequestParam String birthDate,
                         @RequestParam Long depId,
                         Map<String, Object> model) {
        Department department = departmentService.searchDepartment(depId);
        Staff employee = new Staff(FCs, address, post, birthDate, department);
        staffService.createStaff(employee);
        department.setCountStaff(department.getCountStaff()+1);
        departmentService.createDepartment(department);
        Iterable<Staff> staff = staffService.all();
        Iterable<Department> departments = departmentService.all();
        model.put("departments", departments);
        model.put("staff", staff);
        return "staff";
    }


    @PostMapping("/assignDepartment")
    public String assignDepartment(@RequestParam Long staffId,
                                   @RequestParam Long depId,
                                   Model model) {
        try {
            // Найти сотрудника и департамент
            Staff staff = staffService.searchStaff(staffId);
            Department department = departmentService.searchDepartment(depId);

            if (staff == null || department == null) {
                throw new IllegalArgumentException("Сотрудник или департамент не найдены");
            }

            // Назначить департамент сотруднику
            staff.setDepartment(department);
            staffService.createStaff(staff); // Сохранить изменения сотрудника

            // Обновить счётчик сотрудников в департаменте
            department.setCountStaff(department.getCountStaff() + 1);
            departmentService.createDepartment(department);

        } catch (Exception e) {
            model.addAttribute("error", "Ошибка назначения департамента сотруднику: " + e.getMessage());
            return "errorPage"; // Отображение страницы с ошибкой
        }

        // Обновить данные для отображения на странице
        model.addAttribute("staff", staffService.all());
        model.addAttribute("departments", departmentService.all());
        return "staff";
    }


//    @PostMapping("/deleteStaff")
//    public String delStaff(@RequestParam(required = false) long id,
//                         Map<String, Object> model) {
//        Staff employee = staffService.searchStaff(id);
//        staffService.removeStaff(employee.getId());
//        Iterable<Staff> allStaff = staffService.all();
//        model.put("staff", allStaff);
//
//        Iterable<Department> departments = departmentService.all();
//        model.put("departments", departments);
//        return "staff";
//    }

//    @PostMapping("/deleteStaff")
//    public String delStaff(@RequestParam Long id,
//                           Map<String, Object> model) {
//        staffService.removeStaff(id);  // Вызов метода для удаления сотрудника и уменьшения количества сотрудников в департаменте
//        Iterable<Staff> allStaff = staffService.all();
//        model.put("staff", allStaff);
//
//        Iterable<Department> departments = departmentService.all();
//        model.put("departments", departments);
//        return "staff";
//    }

//    @PostMapping("/deleteStaff")
//    public String delStaff(@RequestParam Long id,
//                           Map<String, Object> model) {
//        try {
//            staffService.removeStaff(id);  // Удаление сотрудника
//        } catch (Exception e) {
//            model.put("error", "Ошибка удаления сотрудника: " + e.getMessage());
//            return "errorPage"; // Отобразите пользовательскую страницу с ошибкой
//        }
//
//        model.put("staff", staffService.all());
//        model.put("departments", departmentService.all());
//        return "staff";
//    }

//    @PostMapping("/deleteStaff")
//    public String delStaff(@RequestParam Long id,
//                           Map<String, Object> model) {
//        try {
//            staffService.removeStaff(id); // Удаление сотрудника
//        } catch (Exception e) {
//            model.put("error", "Ошибка удаления сотрудника: " + e.getMessage());
//            return "errorPage"; // Отображение пользовательской страницы с ошибкой
//        }
//
//        model.put("staff", staffService.all());
//        model.put("departments", departmentService.all());
//        return "staff";
//    }

    @PostMapping("/deleteStaff")
    public String delStaff(@RequestParam Long staffId, Model model) {
        try {
            staffService.removeStaff(staffId);  // Удаление сотрудника
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка удаления сотрудника: " + e.getMessage());
            return "errorPage"; // Отображение страницы с ошибкой
        }

        model.addAttribute("staff", staffService.all());
        model.addAttribute("departments", departmentService.all());
        return "staff";
    }




}
