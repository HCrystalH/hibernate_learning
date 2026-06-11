package fa.training.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConfig {
    // private constructor to prevent outside instantiate
    private HibernateConfig(){}

    /**
     * This holder class will NOT load to memory until getSessionFactory is called
     * JVM ensure this instantiation is thread-safe
     */
    private static class Holder{
        private static final SessionFactory INSTANCE;

        static {
            try {
                Configuration configuration = new Configuration();
                configuration.configure("hibernate.cfg.xml");
                INSTANCE = configuration.buildSessionFactory();
            } catch (Exception e) {
                throw new ExceptionInInitializerError(e.getMessage());
            }
        }
    }

    public static SessionFactory getSessionFactory(){
        return Holder.INSTANCE;
    }
}
