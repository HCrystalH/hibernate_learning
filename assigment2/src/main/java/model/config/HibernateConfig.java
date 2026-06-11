package model.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConfig {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // Create the SessionFactory from hibernate.cfg.xml
                sessionFactory = new Configuration()
                        .configure("hibernate.cfg.xml")
                        .buildSessionFactory();
            } catch (Throwable ex) {
                // Make sure you log the exception, as it might be swallowed
                throw new ExceptionInInitializerError(ex);
            }
        }
        return sessionFactory;
    }
}
