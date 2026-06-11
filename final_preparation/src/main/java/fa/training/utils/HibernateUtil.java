package fa.training.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    // private constructor to prevent outside instantiate
    private HibernateUtil(){}

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
