package com.base.app.core.domain.base;

import com.base.app.core.code.Types.DataStatus;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

public abstract class CoreBaseService<T, ID extends Serializable> extends CoreFilterService<T> {
    protected CoreJPAQueryDSLRepository<T, ID> repository;

    @PersistenceContext
    protected EntityManager em;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    public CoreBaseService(CoreJPAQueryDSLRepository<T, ID> repository) {
        this.repository = repository;
    }

    public List<T> findAll() { return this.repository.findAll(); }

    public List<T> findAll(Sort sort) { return this.repository.findAll(sort); }

    public Page<T> findAll(Pageable pageable, String searchParams) {
        return this.filter(this.findAll(pageable.getSort()), pageable, searchParams);
    }

    public List<T> findAll(Iterable<ID> iterable) {
        return this.repository.findAll((Sort) iterable);
    }

    public T findOne(Predicate predicate) {
        return (T) this.repository.findOne(predicate);
    }

    public List<T> findAll(Predicate predicate) {
        return this.toList(this.repository.findAll(predicate));
    }

    public List<T> findAll(Predicate predicate, Sort sort) {
        return this.toList(this.repository.findAll(predicate, sort));
    }

    public List<T> toList(Iterable<T> iterable) {
        if (iterable == null) {
            return Collections.emptyList();
        } else {
            List<T> list = new ArrayList();
            Iterator var3 = iterable.iterator();

            while(var3.hasNext()) {
                T item = (T) var3.next();
                list.add(item);
            }

            return list;
        }
    }

    public List<T> findAll(Predicate predicate, OrderSpecifier... orderSpecifiers) {
        return this.toList(this.repository.findAll(predicate, orderSpecifiers));
    }

    public List<T> findAll(OrderSpecifier... orderSpecifiers) {
        return this.toList(this.repository.findAll(orderSpecifiers));
    }

    public Page<T> findAll(Predicate predicate, Pageable pageable) {
        return this.repository.findAll(predicate, pageable);
    }

    public long count(Predicate predicate) {
        return this.repository.count(predicate);
    }

    public boolean exists(Predicate predicate) {
        return this.repository.exists(predicate);
    }

    public void flush() {
        this.repository.flush();
    }

    @Transactional
    public <S extends T> S saveAndFlush(S object) {
        return this.repository.saveAndFlush(object);
    }

    @Transactional
    public void deleteInBatch(Iterable<T> iterable) {
        this.repository.deleteInBatch(iterable);
    }

    @Transactional
    public void deleteAllInBatch() {
        this.repository.deleteAllInBatch();
    }

    public T getOne(ID id) {
        return this.repository.getOne(id);
    }

    public Page<T> findAll(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Transactional
    public <S extends T> S save(S var) {
        boolean deleted = false;
        if (var instanceof CoreCrudModel) {
            CoreCrudModel crudModel = (CoreCrudModel)var;
            if (crudModel.getDataStatus() == DataStatus.DELETED) {
                deleted = true;
            }
        }

        if (deleted) {
            this.repository.delete(var);
        } else {
            this.repository.save(var);
        }

        return var;
    }

    @Transactional
    public <S extends T> Collection<S> save(Collection<S> vars) {
        vars.forEach(this::save);
        return vars;
    }

    public T findOne(ID var1) {
        return (T) this.repository.findById(var1);
    }

    public boolean exists(ID var1) {
        return this.repository.existsById(var1);
    }

    public long count() {
        return this.repository.count();
    }

    @Transactional
    public void delete(ID var1) {
        this.repository.deleteById(var1);
    }

    @Transactional
    public void delete(T var1) {
        this.repository.delete(var1);
    }

    @Transactional
    public void delete(Iterable<? extends T> var1) {
        this.repository.delete((T) var1);
    }

    @Transactional
    public void deleteAll() {
        this.repository.deleteAll();
    }

    public int getInt(Integer integer) {
        return integer == null ? 0 : integer;
    }

    public long getLong(Long _long) {
        return _long == null ? 0L : _long;
    }

    public int getInt(BigDecimal bigDecimal) {
        return bigDecimal == null ? 0 : bigDecimal.intValue();
    }

    public long getLong(BigDecimal bigDecimal) {
        return bigDecimal == null ? 0L : bigDecimal.longValue();
    }

    protected String like(String field) {
        return "%" + field + "%";
    }

    public boolean isNotEmpty(String value) {
        return StringUtils.isNotEmpty(value);
    }

    public boolean isEmpty(String value) {
        return StringUtils.isEmpty(value);
    }

    public boolean isEmpty(Collection<?> list) {
        return list == null || list.size() == 0;
    }

    public boolean isNotEmpty(Collection<?> list) {
        return !this.isEmpty(list);
    }

    public boolean equals(Object o1, Object o2) {
        if (o1 == null) {
            return false;
        } else {
            return o2 == null ? false : o1.equals(o2);
        }
    }

    public boolean notEquals(Object o1, Object o2) {
        return !this.equals(o1, o2);
    }

    protected JPAQuery<T> select() {
        return new JPAQuery(this.em);
    }

    protected JPAUpdateClause update(EntityPath<?> entityPath) {
        return new JPAUpdateClause(this.em, entityPath);
    }

    protected JPADeleteClause delete(EntityPath<?> entityPath) {
        return new JPADeleteClause(this.em, entityPath);
    }
}
