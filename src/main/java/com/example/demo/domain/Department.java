package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name="department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private Integer countStaff;
    private String rooms;

//    @OneToOne
//    @JoinColumn(name="boss_id")
//    private Staff boss;

    @OneToOne
    @JoinColumn(name = "head_id", referencedColumnName = "id", nullable = true)
    private Staff head;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Staff> staffList;

    public Staff getHead() {
        return head;
    }

    public void setHead(Staff head) {
        this.head = head;
    }

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Staff> staff;

    public Department(){}
    public Department(String name, String rooms) {
        this.name=name;
        this.rooms=rooms;
        this.countStaff=0;
        this.head=null;
    }
}
