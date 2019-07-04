package com.thisara.web.Database;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="person")
public class Person {

    @Id
    @Column(name="id")
    private int id;

    @Id
    @Column(name="firstName")
    private String firstName;


    public Person(int id, String firstName) {
        this.id = id;
        this.firstName = firstName;
    }

    public Person() {
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

}