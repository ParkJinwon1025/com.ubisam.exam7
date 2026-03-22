package com.ubisam.exam7.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name = "exam_bookGroup")
public class BookGroup {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Integer bookCount;

    @Transient
    private String keyword;

}
