package com.ubisam.exam7.api.books;

import static io.u2ware.common.docs.MockMvcRestDocs.is2xx;
import static io.u2ware.common.docs.MockMvcRestDocs.post;
import static io.u2ware.common.docs.MockMvcRestDocs.delete;
import static io.u2ware.common.docs.MockMvcRestDocs.get;
import static io.u2ware.common.docs.MockMvcRestDocs.result;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.u2ware.common.docs.MockMvcRestDocs.print;
import static io.u2ware.common.docs.MockMvcRestDocs.put;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.web.servlet.MockMvc;

import com.ubisam.exam7.domain.Book;

import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;

@SpringBootTest
@AutoConfigureMockMvc
public class BookTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookDocs docs;

    @Autowired
    private BookRepository bookRepository;

    // CRUD
    @Test
    public void contextLoads() throws Exception {

        // CRUD - C
        mockMvc.perform(post("/api/books").content(docs::newEntity, "book"))
                .andDo(print()).andExpect(is2xx()).andDo(result(docs::context, "entity1"));

        // CRUD - R
        String url = docs.context("entity1", "$._links.self.href");
        mockMvc.perform(get(url)).andExpect(is2xx());

        // CRUD - U
        Map<String, Object> body = docs.context("entity1", "$");
        mockMvc.perform(put(url).content(docs::updateEntity, body, "book1234")).andExpect(is2xx());

        // CRUD - Delete
        mockMvc.perform((delete(url))).andExpect(is2xx());

    }

    // Handler
    @Test
    public void contextLoads2() throws Exception {

        Specification<Book> spec;
        List<Book> result;
        boolean hasResult;

        // 책 40개 추가
        List<Book> bookList = new ArrayList<>();
        for (int i = 1; i <= 40; i++) {
            bookList.add(docs.newEntity("book" + i, i + "층", i + ""));
        }
        bookRepository.saveAll(bookList);

        // 1. 이름을 통한 책 찾기
        JpaSpecificationBuilder<Book> query = JpaSpecificationBuilder.of(Book.class);
        spec = query.where().and().eq("name", "book4").build();
        result = bookRepository.findAll(spec);

        // 쿼리 결과 행 1개를 찾고 그 행의 name 필드가 "book4"인지 검사
        hasResult = result.stream().anyMatch(u -> "book4".equals(u.getName()));

        // hasResult의 값이 true인지 확인
        assertEquals(true, hasResult);

        // 2. 위치를 통한 책 찾기
        JpaSpecificationBuilder<Book> query1 = JpaSpecificationBuilder.of(Book.class);
        spec = query1.where().and().eq("location", "5층").build();
        result = bookRepository.findAll(spec);

        // 쿼리 결과 행 1개를 찾고 그 행의 name 필드가 "book4"인지 검사
        hasResult = result.stream().anyMatch(u -> "5층".equals(u.getLocation()));

        // hasResult의 값이 true인지 확인
        assertEquals(true, hasResult);

        // 3. 코드를 통한 책 찾기
        JpaSpecificationBuilder<Book> query2 = JpaSpecificationBuilder.of(Book.class);
        spec = query2.where().and().eq("code", "10").build();
        result = bookRepository.findAll(spec);

        // 쿼리 결과 행 1개를 찾고 그 행의 name 필드가 "book4"인지 검사
        hasResult = result.stream().anyMatch(u -> "10".equals(u.getCode()));

        // hasResult의 값이 true인지 확인
        assertEquals(true, hasResult);

    }

    // Search
    @Test
    public void contextLoads3() throws Exception {

        // 책 40개 추가
        List<Book> bookList = new ArrayList<>();
        for (int i = 1; i <= 40; i++) {
            bookList.add(docs.newEntity("book" + i, i + "층", i + ""));
        }
        bookRepository.saveAll(bookList);

        // 1. Search - 책 이름
        mockMvc.perform(get("/api/books/search/findByName").param("name", "book20")).andExpect(is2xx());

        // 2. Search - 책 위치
        mockMvc.perform(get("/api/books/search/findByLocation").param("location", "11층")).andExpect(is2xx());

        // 3. Search - 코드
        mockMvc.perform(get("/api/books/search/findByCode").param("code", "31")).andExpect(is2xx());

        // 4. Search - 페이지네이션 5개씩 8페이지
        mockMvc.perform(get("/api/books").param("size", "5")).andExpect(is2xx());

        // 5. Search - 정렬, code, desc
        mockMvc.perform(get("/api/books").param("sort", "code,desc")).andExpect(is2xx());
    }

}
