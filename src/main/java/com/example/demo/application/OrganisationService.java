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
public class OrganisationService {
    @Autowired
    private OrganisationRepository organisationRepository ;
    @Autowired
    private TaskService taskService;

    @Transactional
    public Organisation createOrganisation(Organisation organisation){
        return organisationRepository.save(organisation);
    }
//    @Transactional
//    public void removeOrganisation(long id){
//        organisationRepository.deleteById(id);
//    }

//    @Transactional
//    public void removeOrganisation(Long id) {
//        Organisation organisation = organisationRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Organisation not found"));
//
//        // Удаление связанных задач
//        List<Task> tasks = taskRepository.findByOrganisation(organisation);
//        for (Task task : tasks) {
//            taskRepository.delete(task);
//        }
//
//        // Удаление организации
//        organisationRepository.delete(organisation);
//    }

    @Transactional
    public void removeOrganisation(Long orgId) {
        // Найти организацию
        Organisation organisation = organisationRepository.findById(orgId).orElse(null);
        if (organisation != null) {
            // Обновить задачи, связанные с организацией
            List<Task> tasks = taskService.findByOrganisation(organisation);
            for (Task task : tasks) {
                task.setOrganisation(null);  // Удаляем связь
                taskService.saveTask(task);  // Сохраняем изменения
            }
            // Удаляем организацию
            organisationRepository.deleteById(orgId);
        }
    }

    @Transactional
    public Organisation searchOrganisation(long id){
        return organisationRepository.findById(id).get();
    }
    @Transactional
    public List<Organisation> findByName(String name){
        return organisationRepository.findByName(name);
    }
    public Iterable<Organisation> all (){
        Iterable<Organisation> organisations = organisationRepository.findAll();
        return organisations;
    }

    public Organisation getOrganisationById(long id) {
        return organisationRepository.findById(id).orElse(null);
    }
}
