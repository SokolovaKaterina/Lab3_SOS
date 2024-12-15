//package com.example.demo.application;
//
//
//import com.example.demo.domain.Task;
//import com.example.demo.repository.TaskRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//
//@Service
//public class TaskService {
//    @Autowired
//    private TaskRepository taskRepository;
//    @Transactional
//    public Task createTask(Task task){
//        return taskRepository.save(task);
//    }
//    @Transactional
//    public void removeTask(long id){
//        taskRepository.deleteById(id);
//    }
//    @Transactional
//    public Task searchTask(long id){
//        return taskRepository.findById(id).get();
//    }
//
//    @Transactional
//    public List<Task> findByName(String name){
//        return taskRepository.findByName(name);
//    }
//
//    public Iterable<Task> all (){
//        Iterable<Task> tasks = taskRepository.findAll();
//        return tasks;
//    }
//    @Transactional
//    public void takeTask (long id){
//        Task task = taskRepository.findById(id).get();
//        task.setStatus(true);
//        taskRepository.save(task);
//    }
//}



////package com.example.demo.application;
////
////
////import com.example.demo.domain.Task;
////import com.example.demo.repository.TaskRepository;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.stereotype.Service;
////import org.springframework.transaction.annotation.Transactional;
////
////import java.util.List;
////
////
////@Service
////public class TaskService {
////    @Autowired
////    private TaskRepository taskRepository;
////    @Transactional
////    public Task createTask(Task task){
////        return taskRepository.save(task);
////    }
////    @Transactional
////    public void removeTask(long id){
////        taskRepository.deleteById(id);
////    }
////    @Transactional
////    public Task searchTask(long id){
////        return taskRepository.findById(id).get();
////    }
////
////    @Transactional
////    public List<Task> findByName(String name){
////        return taskRepository.findByName(name);
////    }
////
////    public Iterable<Task> all (){
////        Iterable<Task> tasks = taskRepository.findAll();
////        return tasks;
////    }
////    @Transactional
////    public void takeTask (long id){
////        Task task = taskRepository.findById(id).get();
////        task.setStatus(true);
////        taskRepository.save(task);
////    }
////}
//
//
//package com.example.demo.application;
//
//import com.example.demo.domain.Task;
//import com.example.demo.repository.TaskRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//public class TaskService {
//    @Autowired
//    private TaskRepository taskRepository;
//
//    @Transactional
//    public Task createTask(Task task){
//        return taskRepository.save(task);
//    }
//
//    @Transactional
//    public void removeTask(long id){
//        taskRepository.deleteById(id);
//    }
//
//    @Transactional
//    public Task searchTask(long id){
//        return taskRepository.findById(id).orElse(null);
//    }
//
//    @Transactional
//    public List<Task> findByName(String name){
//        return taskRepository.findByName(name);
//    }
//
//    public Iterable<Task> all (){
//        return taskRepository.findAll();
//    }
//
//    @Transactional
//    public void takeTask (long id){
//        Task task = taskRepository.findById(id).orElse(null);
//        if (task != null) {
//            task.setStatus(true);
//            taskRepository.save(task);
//        }
//    }
//}



package com.example.demo.application;

import com.example.demo.domain.Organisation;
import com.example.demo.domain.Task;
import com.example.demo.repository.OrganisationRepository;
import com.example.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private OrganisationRepository organisationRepository;
    @Autowired
    private OrganisationService organisationService; // Внедрение сервиса организаций


//    @Transactional
//    public Task createTask(Task task) {
//        return taskRepository.save(task);
//    }

    @Transactional
    public Task createTask(Task task) {
        try {
            return taskRepository.save(task);
        } catch (Exception e) {
            System.err.println("Ошибка при сохранении задачи: " + e.getMessage());
            throw e;
        }
    }



    @Transactional
    public void removeTask(long id) {
        taskRepository.deleteById(id);
    }

    @Transactional
    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }


    @Transactional
    public Task searchTask(long id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Transactional
    public List<Task> findByName(String name) {
        return taskRepository.findByName(name);
    }

    public Iterable<Task> all() {
        return taskRepository.findAll();
    }

    @Transactional
    public void takeTask(long id) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task != null) {
            task.setStatus(true);
            taskRepository.save(task);
        }
    }

    @Transactional
    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    // Новый метод для поиска задач по организации
    @Transactional
    public List<Task> findByOrganisation(Organisation organisation) {
        return taskRepository.findByOrganisation(organisation);
    }
}
