package fa.training.dao;

import fa.training.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeAll;

public abstract class AbstractDAOTest {

    protected static SessionFactory sessionFactory;

    @BeforeAll
    static void initSessionFactory() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    protected void cleanTable(String tableName) {
        try (Session session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            session.createNativeMutationQuery("DELETE FROM " + tableName).executeUpdate();
            transaction.commit();
        }
    }

}
