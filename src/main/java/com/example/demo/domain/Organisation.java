package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Table(name="organisation")
public class Organisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String address;
    private String description;
    @OneToOne
    @JoinColumn(name="boss_id")
    private Staff boss;

    public Staff getBoss(){
        return boss;
    }
    public void setBoss(Staff boss) {
        this.boss = boss;
    }

    public Organisation(){}

    public Organisation(String name,  String description, String address) {
        this.name=name;
        this.description=description;
        this.address=address;
        this.boss=null;
    }

}
