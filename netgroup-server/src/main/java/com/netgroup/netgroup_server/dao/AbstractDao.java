package com.netgroup.netgroup_server.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractDao<T> {
    @PersistenceContext
    private EntityManager entityManager;

    public T getById(Long id, Class<T> clazz) {
       return getEntityManager().find(clazz, id);
    }

    @Transactional
    public void save(T t){
       getEntityManager().persist(t);
    }
    @Transactional
    public void update(T t){
       getEntityManager().merge(t);
    }
    @Transactional
    public void delete(T t){
        getEntityManager().remove(t);
    }
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
