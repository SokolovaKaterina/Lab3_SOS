//package com.example.demo.domain;
//
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//@Entity
//@Getter
//@Setter
//@Table(name="tasks")
//public class Task {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//    private String name;
//    private String phoneNumber;
//    private String description;
//    private Integer price;
//    private Boolean status;
//
//    public Task(){}
//    public Task(String name,String phoneNumber, String description, Integer price) {
//        this.name = name;
//        this.phoneNumber = phoneNumber;
//        this.description = description;
//        this.price = price;
//        this.status = false;
//    }
//
//
//}

package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String phoneNumber;
    private String description;
    //    private Integer price;
    private Boolean status;

    @ManyToOne
    @JoinColumn(name="organisation_id", nullable = true)
    private Organisation organisation;

    public Task() {}

    public Task(String name, String phoneNumber, String description, Organisation organisation) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.description = description;
//        this.price = price;
        this.status = false;
        this.organisation = organisation;
    }
}
