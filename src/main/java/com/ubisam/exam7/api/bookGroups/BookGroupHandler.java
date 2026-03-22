package com.ubisam.exam7.api.bookGroups;

import java.io.Serializable;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import com.ubisam.exam7.domain.BookGroup;

import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;
import io.u2ware.common.data.rest.core.annotation.HandleAfterRead;
import io.u2ware.common.data.rest.core.annotation.HandleBeforeRead;

@Component
@RepositoryEventHandler
public class BookGroupHandler {

    @HandleBeforeRead
    public void beforeRead(BookGroup e, Specification<BookGroup> spec) throws Exception {

        System.out.println("[HandleBeforeRead]" + e);
        JpaSpecificationBuilder<BookGroup> query = JpaSpecificationBuilder.of(BookGroup.class);
        query.where().and().eq("name", e.getKeyword())
                .build(spec);

    }

    @HandleAfterRead
    public void afterRead(BookGroup e, Serializable r) throws Exception {

        System.out.println("[HandleAfterRead]" + e);
        System.out.println("[HandleAfterRead]" + r);

    }

    // Create, Delete, Save만 JPA가 지원해줌.
    @HandleBeforeCreate
    public void beforeCreate(BookGroup e) throws Exception {

        System.out.println("[HandleBeforeCreate]" + e);

    }

    @HandleAfterCreate
    public void afterCreate(BookGroup e) throws Exception {

        System.out.println("[HandleAfterCreate]" + e);

    }

    @HandleBeforeSave
    public void beforeSave(BookGroup e) throws Exception {

        System.out.println("[HandlebeforeSave]" + e);

    }

    @HandleAfterSave
    public void afterSave(BookGroup e) throws Exception {

        System.out.println("[HandleAfterSave]" + e);

    }

    @HandleBeforeDelete
    public void beforeDelete(BookGroup e) throws Exception {

        System.out.println("[HandlebeforeDelete]" + e);

    }

    @HandleAfterDelete
    public void afterDelete(BookGroup e) throws Exception {

        System.out.println("[HandleAfterDelete]" + e);

    }

}
