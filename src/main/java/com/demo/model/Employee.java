package com.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    private String id;
    private String name;
    private int randomNumber;

    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
    }

}