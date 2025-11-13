package dao;

import model.Match;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateSessionFactoryUtil;
import java.util.List;

public class MatchDao implements GenericDao<Match> {

    @Override
    public void save(Match entity) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        session.persist(entity);
        tx.commit();
        session.close();
    }

    @Override
    public Match findById(Long id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().find(Match.class, id);
    }

    @Override
    public List<Match> findAll() {
        return HibernateSessionFactoryUtil.getSessionFactory()
                .openSession()
                .createQuery("SELECT m FROM Match m", Match.class)
                .getResultList();
    }

    @Override
    public void delete(Match entity) {
        HibernateSessionFactoryUtil.getSessionFactory()
                .openSession()
                .remove(entity);
    }

    @Override
    public void update(Match entity) {
        HibernateSessionFactoryUtil.getSessionFactory()
                .openSession()
                .merge(entity);
    }
}
