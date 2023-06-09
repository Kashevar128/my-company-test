package org.example.application.services;

import lombok.RequiredArgsConstructor;
import org.example.application.hibernate.HibernateManagerFactory;
import org.example.application.interfaces.MyCallback;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor

public class HibernateService {

    private final HibernateManagerFactory hibernateManagerFactory;

    private EntityManager entityManager;

    public <T> T executeQuery(MyCallback<T> callback) {
        T call = null;
        try {
            entityManager.getTransaction().begin();
            call = callback.call();
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return call;
    }

    public EntityManager getEntityManager() {
        entityManager = hibernateManagerFactory.getEntityManager();
        return entityManager;
    }
}
