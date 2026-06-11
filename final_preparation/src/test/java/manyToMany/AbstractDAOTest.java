package manyToMany;

import fa.training.utils.HibernateUtil;
import org.hibernate.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractDAOTest {

    protected Session session;

    protected Transaction transaction;

    @BeforeEach
    void setUp() {

        session = HibernateUtil.getSessionFactory().openSession();

        transaction = session.beginTransaction();
    }

    @AfterEach
    void tearDown() {

        if(transaction != null) {
            transaction.rollback();
        }

        if(session != null) {
            session.close();
        }
    }
}