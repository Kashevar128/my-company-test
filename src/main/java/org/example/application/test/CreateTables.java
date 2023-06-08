package org.example.application.test;

import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManagerFactory;


public class CreateTables {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
    }
}
