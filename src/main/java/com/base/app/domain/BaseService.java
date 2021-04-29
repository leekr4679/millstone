package com.base.app.domain;

import com.base.app.core.domain.base.CoreBaseService;
import com.base.app.core.domain.base.CoreJPAQueryDSLRepository;
import com.base.app.domain.diary.QDiary;
import com.base.app.domain.system.resource.QResources;
import com.base.app.domain.system.role.QRole;
import com.base.app.domain.user.QUser;

import java.io.Serializable;

public class BaseService<T, ID extends Serializable> extends CoreBaseService<T, ID> {
    protected QUser qUser = QUser.user;
    protected QRole qRole = QRole.role;
    protected QResources qResources = QResources.resources;
    protected QDiary qDiary = QDiary.diary;

    protected CoreJPAQueryDSLRepository<T, ID> repository;

    public BaseService(CoreJPAQueryDSLRepository<T, ID> repository) {
        super(repository);
        this.repository = repository;
    }
}
