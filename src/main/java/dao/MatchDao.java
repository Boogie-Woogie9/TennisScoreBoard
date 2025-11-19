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

    public List<Match> findPage(int limit, int offset, String filter) {
        String baseQuery = """
                SELECT m FROM Match m
                WHERE (:filter IS NULL\s
                       OR m.player1.name LIKE :pattern\s
                       OR m.player2.name LIKE :pattern)
                ORDER BY m.id DESC
                """;
        return HibernateSessionFactoryUtil.getSessionFactory().openSession()
                .createQuery(baseQuery, Match.class)
                .setParameter("filter", filter)
                .setParameter("pattern", filter == null ? null : "%" + filter + "%")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public int countAll(String filter) {
        String countQuery = """
                SELECT COUNT(m.id) FROM Match m
                WHERE (:filter IS NULL
                       OR m.player1.name LIKE :pattern
                       OR m.player2.name LIKE :pattern)
                """;
        Long count = HibernateSessionFactoryUtil.getSessionFactory().openSession()
                .createQuery(countQuery, Long.class)
                .setParameter("filter", filter)
                .setParameter("pattern", filter == null ? null : "%" + filter + "%")
                .getSingleResult();
        return count.intValue();
    }
}
