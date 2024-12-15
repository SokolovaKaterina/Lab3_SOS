package com.example.demo.application;

import com.example.demo.domain.Department;
import com.example.demo.domain.Organisation;
import com.example.demo.domain.Staff;
import com.example.demo.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class StaffService {
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private DepartmentService departmentService; // Добавим зависимость для работы с департаментами

    @Autowired
    private OrganisationService organisationService;

    public void updateStaff(Staff staff) {
        staffRepository.save(staff); // Сохраняем измененного сотрудника
    }

    @Transactional
    public Staff createStaff(Staff staff){
       return staffRepository.save(staff);
    }
//    @Transactional
//    public void removeStaff(long id){
//        staffRepository.deleteById(id);
//    }

    @Transactional
    public List<Staff> findByDepartment(Department department) {
        return staffRepository.findByDepartment(department); // Находим сотрудников по департаменту
    }

//    @Transactional
//    public void removeStaff(long id) {
//        // Ищем сотрудника
//        Staff staff = staffRepository.findById(id).orElse(null);
//        if (staff != null) {
//            Department department = staff.getDepartment(); // Получаем департамент сотрудника
//            if (department != null) {
//                // Уменьшаем количество сотрудников в департаменте
//                department.setCountStaff(department.getCountStaff() - 1);
//                // Сохраняем департамент после изменения количества сотрудников
//                departmentService.updateDepartment(department);
//            }
//            // Удаляем сотрудника
//            staffRepository.deleteById(id);
//        }
//    }

//    @Transactional
//    public void removeStaff(Long id) {
//        // Найти сотрудника
//        Staff staff = staffRepository.findById(id).orElse(null);
//        if (staff != null) {
//            // Уменьшаем количество сотрудников в департаменте
//            Department department = staff.getDepartment();
//            if (department != null) {
//                // Проверяем, является ли сотрудник руководителем департамента
//                if (staff.equals(department.getHead())) {
//                    department.setHead(null); // Убираем сотрудника из роли руководителя
//                }
//
//                department.setCountStaff(department.getCountStaff() - 1); // Уменьшаем количество сотрудников
//                departmentService.createDepartment(department); // Сохраняем изменения департамента
//            }
//
//            // Проверяем, является ли сотрудник руководителем организации
//            Organisation organisation = staff.getOrganisation();
//            if (organisation != null && staff.equals(organisation.getBoss())) {
//                organisation.setBoss(null); // Убираем сотрудника из роли руководителя
//                organisationService.createOrganisation(organisation); // Сохраняем изменения в организации
//            }
//
//            // Удаляем сотрудника
//            staffRepository.deleteById(id);
//        }
//    }

//    @Transactional
//    public void removeStaff(Long id) {
//        // Найти сотрудника
//        Staff staff = staffRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Сотрудник не найден"));
//
//        // Уменьшить количество сотрудников в департаменте
//        Department department = staff.getDepartment();
//        if (department != null) {
//            // Если сотрудник является руководителем департамента, обнуляем ссылку
////            if (staff.equals(department.getHead())) {
////                department.setHead(null);
////            }
//            department.getStaff().remove(staff);
//            department.setCountStaff(department.getCountStaff() - 1);
//            departmentService.createDepartment(department); // Сохраняем изменения департамента
//        }
//
//        // Проверяем, является ли сотрудник руководителем организации
//        Organisation organisation = staff.getOrganisation(); // Убедитесь, что связь с организацией существует
//        if (organisation != null && staff.equals(organisation.getBoss())) {
//            organisation.setBoss(null); // Убираем сотрудника из роли руководителя
//            organisationService.createOrganisation(organisation); // Сохраняем изменения в организации
//        }
//
//        // Удаляем сотрудника
//        staffRepository.delete(staff);
//    }


    @Transactional
    public void removeStaff(Long id) {
        // Найти сотрудника
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Сотрудник не найден"));

        // Уменьшить количество сотрудников в департаменте
        Department department = staff.getDepartment();
        if (department != null) {
            // Если сотрудник является руководителем департамента, обнуляем ссылку
            if (staff.equals(department.getHead())) {
                department.setHead(null); // Убираем сотрудника из роли руководителя
            }
            department.getStaff().remove(staff);
            department.setCountStaff(department.getCountStaff() - 1);
            departmentService.createDepartment(department); // Сохраняем изменения департамента
        }

        // Проверяем, является ли сотрудник руководителем организации
        Organisation organisation = staff.getOrganisation();
        if (organisation != null && staff.equals(organisation.getBoss())) {
            organisation.setBoss(null); // Убираем сотрудника из роли руководителя
            organisationService.createOrganisation(organisation); // Сохраняем изменения в организации
        }

        // Удаляем сотрудника
        staffRepository.delete(staff);
    }




//    @Transactional
//    public void removeStaff(Long id) {
//        // Найти сотрудника
//        Staff staff = staffRepository.findById(id).orElseThrow(() -> new RuntimeException("Сотрудник не найден"));
//
//        // Если сотрудник является руководителем департамента
//        Department department = staff.getDepartment();
//        if (department != null && staff.equals(department.getHead())) {
//            department.setHead(null); // Убираем ссылку на сотрудника как руководителя
//        }
//
//        // Уменьшить количество сотрудников
//        if (department != null) {
//            department.setCountStaff(department.getCountStaff() - 1);
//        }
//
//        // Удалить сотрудника
//        staffRepository.delete(staff);
//    }

//    @Transactional
//    public void removeStaff(Long id) {
//        Staff staff = staffRepository.findById(id).orElseThrow(() -> new RuntimeException("Сотрудник не найден"));
//        staffRepository.delete(staff); // Все связанные операции выполнятся автоматически
//    }


//    @Transactional
//    public Staff searchStaff(long id){
//        return staffRepository.findById(id).get();
//    }

    public Staff searchStaff(Long id) {
        return staffRepository.findById(id).orElse(null);
    }


    @Transactional
    public List<Staff> findByName(String name){
        return staffRepository.findByName(name);
    }
    public Iterable<Staff> all (){
        Iterable<Staff> staffs = staffRepository.findAll();
        return staffs;
    }
}
