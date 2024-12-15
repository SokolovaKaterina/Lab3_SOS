package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="staff")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String FCs;
    private String address;
    private String post;
    private String birthDate;
//    @ManyToOne
//    @JoinColumn(name="department_id")
//    Department department;
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = true)
    private Department department;

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;
    public Staff() {
    }
    public Staff(String FCs,String address,String post,String birthDate, Department department) {
        this.FCs=FCs;
        this.address=address;
        this.post=post;
        this.birthDate=birthDate;
        this.department=department;
    }

}
