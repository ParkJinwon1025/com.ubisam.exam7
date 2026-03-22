package com.ubisam.exam7.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "exam_book")
public class Book {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String location;
    private String code;

}
