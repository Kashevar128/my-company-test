package org.example.application.test;

import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManagerFactory;


public class createTables {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
    }
}
