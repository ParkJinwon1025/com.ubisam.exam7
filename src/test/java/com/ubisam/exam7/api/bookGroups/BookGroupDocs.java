package com.ubisam.exam7.api.bookGroups;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

import com.ubisam.exam7.domain.BookGroup;

import io.u2ware.common.docs.MockMvcRestDocs;

@Component
public class BookGroupDocs extends MockMvcRestDocs {

    public BookGroup newEntity(String... entity) {

        BookGroup body = new BookGroup();
        body.setName(entity.length > 0 ? entity[0] : super.randomText("name"));
        body.setBookCount(entity.length > 1 ? Integer.valueOf(entity[1]) : super.randomInt());
        return body;
    }

    public Map<String, Object> updateEntity(Map<String, Object> entity, String name) {
        entity.put("name", name);
        return entity;
    }

    public Map<String, Object> setKeyword(String keyword) {

        Map<String, Object> entity = new HashMap<>();
        entity.put("keyword", keyword);
        return entity;

    }

}
