package com.example.demo.repository;

import com.example.demo.domain.Department;
import com.example.demo.domain.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    @Query(value = "SELECT u FROM Staff u WHERE u.FCs=:FCs")
    List<Staff> findByName(@Param("FCs") String FCs);

    List<Staff> findByDepartment(Department department);
}
