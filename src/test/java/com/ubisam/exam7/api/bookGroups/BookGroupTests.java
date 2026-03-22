package com.ubisam.exam7.api.bookGroups;

import static io.u2ware.common.docs.MockMvcRestDocs.is2xx;
import static io.u2ware.common.docs.MockMvcRestDocs.post;
import static io.u2ware.common.docs.MockMvcRestDocs.delete;
import static io.u2ware.common.docs.MockMvcRestDocs.result;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static io.u2ware.common.docs.MockMvcRestDocs.put;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.u2ware.common.docs.MockMvcRestDocs.print;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.ubisam.exam7.domain.Book;
import com.ubisam.exam7.domain.BookGroup;

import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;

@SpringBootTest
@AutoConfigureMockMvc
public class BookGroupTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookGroupDocs docs;

    @Autowired
    private BookGroupRepository bookGroupRepository;

    @Test
    public void contextLoads() throws Exception {

        // CRUD - C
        mockMvc.perform(post("/api/bookGroups").content(docs::newEntity, "bookGroup"))
                .andExpect(is2xx()).andDo(print()).andDo(result(docs::context, "entity1"));

        // CRUD - R
        String url = docs.context("entity1", "$._links.self.href");
        mockMvc.perform(post(url)).andExpect(is2xx());

        // CRUD - U
        Map<String, Object> body = docs.context("entity1", "$");
        mockMvc.perform(put(url).content(docs::updateEntity, body, "bookGroup1234"))
                .andExpect(is2xx());

        // CRUD - D
        mockMvc.perform(delete(url)).andExpect(is2xx());

    }

    // Handler 테스트
    @Test
    public void contextLoads2() throws Exception {

        List<BookGroup> result;
        boolean hasResult;

        List<BookGroup> bookList = new ArrayList<>();
        for (int i = 1; i <= 40; i++) {
            bookList.add(docs.newEntity("bookGroup" + i, i + ""));
        }

        bookGroupRepository.saveAll(bookList);

        // 1. 이름을 통한 책 그룹 찾기
        JpaSpecificationBuilder<BookGroup> query = JpaSpecificationBuilder.of(BookGroup.class);
        query.where().and().eq("name", "bookGroup20");
        result = bookGroupRepository.findAll(query.build());
        hasResult = result.stream().anyMatch(u -> "bookGroup20".equals(u.getName()));
        assertEquals(true, hasResult);

        // 2. 책 개수를 통한 책 그룹 찾기
        JpaSpecificationBuilder<BookGroup> query1 = JpaSpecificationBuilder.of(BookGroup.class);
        query1.where().and().eq("bookCount", 3);
        result = bookGroupRepository.findAll(query1.build());
        hasResult = result.stream().anyMatch(u -> 3 == u.getBookCount());
        assertEquals(true, hasResult);

    }

    // Search 테스트
    @Test
    public void contextLoads3() throws Exception {

        // 책 Group 40개 추가
        List<BookGroup> result;
        boolean hasResult;

        List<BookGroup> bookList = new ArrayList<>();
        for (int i = 1; i <= 40; i++) {
            bookList.add(docs.newEntity("bookGroup" + i, i + ""));
        }

        bookGroupRepository.saveAll(bookList);

        String url = "/api/bookGroups/search";

        // 1. Search 책 그룹이름으로 찾기
        mockMvc.perform(post(url).content(docs::setKeyword, "bookGroup4")).andDo(print()).andExpect(is2xx());

        // 2. Search - 페이지 네이션 5개씩 8페이지
        mockMvc.perform(post(url).param("size", "5")).andExpect(is2xx());

        // 3. Search - sort, bookCount
        mockMvc.perform(post(url).param("sort", "bookCount,desc")).andExpect(is2xx()).andDo(print());
    }

}
