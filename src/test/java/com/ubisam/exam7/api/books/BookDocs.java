package com.ubisam.exam7.api.books;

import java.util.Map;
import org.springframework.stereotype.Component;

import com.ubisam.exam7.domain.Book;

import io.u2ware.common.docs.MockMvcRestDocs;

@Component
public class BookDocs extends MockMvcRestDocs {

    public Book newEntity(String... entity) {

        Book body = new Book();
        body.setName(entity.length > 0 ? entity[0] : super.randomText("name"));
        body.setLocation(entity.length > 1 ? entity[1] : super.randomText("location"));
        body.setCode(entity.length > 2 ? entity[2] : super.randomText("code"));
        return body;
    }

    public Map<String, Object> updateEntity(Map<String, Object> entity, String name) {
        entity.put("name", name);
        return entity;
    }

}
