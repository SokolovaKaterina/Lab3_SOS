//package com.example.demo.repository;
//
//
//import com.example.demo.domain.Task;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//
//public interface TaskRepository extends JpaRepository<Task,Long> {
//    @Query(value = "SELECT u FROM Task u WHERE u.name=:name")
//    List<Task> findByName(@Param("name") String name);
//}


package com.example.demo.repository;

import com.example.demo.domain.Organisation;
import com.example.demo.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query(value = "SELECT u FROM Task u WHERE u.name = :name")
    List<Task> findByName(@Param("name") String name);

    //     Метод для поиска задач по организации
//    @Query(value = "SELECT u FROM Organisation u WHERE u.name = :name")
    List<Task> findByOrganisation(Organisation organisation);
}
