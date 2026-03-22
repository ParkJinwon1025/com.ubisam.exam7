package com.ubisam.exam7.api.books;

import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import com.ubisam.exam7.domain.Book;

@Component
@RepositoryEventHandler
public class BookHandler {

    // Create, Delete, Save만 JPA가 지원해줌.
    @HandleBeforeCreate
    public void beforeCreate(Book e) throws Exception {

        System.out.println("[HandleBeforeCreate]" + e);

    }

    @HandleAfterCreate
    public void afterCreate(Book e) throws Exception {

        System.out.println("[HandleAfterCreate]" + e);

    }

    @HandleBeforeSave
    public void beforeSave(Book e) throws Exception {

        System.out.println("[HandlebeforeSave]" + e);

    }

    @HandleAfterSave
    public void afterSave(Book e) throws Exception {

        System.out.println("[HandleAfterSave]" + e);

    }

    @HandleBeforeDelete
    public void beforeDelete(Book e) throws Exception {

        System.out.println("[HandlebeforeDelete]" + e);

    }

    @HandleAfterDelete
    public void afterDelete(Book e) throws Exception {

        System.out.println("[HandleAfterSave]" + e);

    }
}
