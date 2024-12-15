package com.example.demo.application;

import com.example.demo.domain.Department;
import com.example.demo.domain.Staff;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository ;
    @Autowired
    private StaffRepository staffRepository;
    @Transactional
    public Department createDepartment(Department department){
        return departmentRepository.save(department);
    }

    public void updateDepartment(Department department) {
        departmentRepository.save(department); // Сохраняем изменения в департаменте
    }

    public void removeHead(Department department) {
        if (department.getHead() != null) {
            department.setHead(null);
            createDepartment(department); // Сохранение изменений
        }
    }


//    @Transactional
//    public boolean removeDepartment(Long id){
//        Department dep = departmentRepository.findById(id).get();
//        if (dep != null && dep.getCountStaff()==0){
//            departmentRepository.deleteById(id);
//            return true;
//        }
//        else { return false;}
//    }
//

    @Transactional
    public boolean removeDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Департамент не найден"));

        // Обнуляем департамент у всех сотрудников, которые в нем находятся
        List<Staff> staffInDept = staffRepository.findByDepartment(department);
        for (Staff staff : staffInDept) {
            staff.setDepartment(null); // Убираем департамент у сотрудника
            staffRepository.save(staff); // Сохраняем изменения
        }

        // Удаляем департамент
        departmentRepository.delete(department);
        return true;
    }



//    @Transactional
//    public Department searchDepartment(Long id){
//        return departmentRepository.findById(id).get();
//    }

    public Department searchDepartment(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }


    @Transactional
    public List<Department> findByName(String name){
        return departmentRepository.findByName(name);
    }

    public Iterable<Department> all (){
        Iterable<Department> departments = departmentRepository.findAll();
        return departments;
    }
}
