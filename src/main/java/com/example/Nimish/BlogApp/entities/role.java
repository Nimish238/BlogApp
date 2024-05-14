package com.example.Nimish.BlogApp.entities;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class role {

    @Id
    private int id;

    @Column
    private String name;

    public String getName() {
        return this.name;
    }


}
