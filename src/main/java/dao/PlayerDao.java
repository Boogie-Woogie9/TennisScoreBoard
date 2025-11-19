package dao;

import model.Player;
import util.HibernateSessionFactoryUtil;
import java.util.List;

public class PlayerDao implements GenericDao<Player> {
    public void save(Player entity) {
        HibernateSessionFactoryUtil.getSessionFactory().openSession().persist(entity);
    }

    public Player findById(Long id) {
        return HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()
                .find(Player.class, id);
    }

    public List<Player> findAll() {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession()
                .createQuery("SELECT p FROM Player p", Player.class)
                .getResultList();
    }

    public void delete(Player entity) {
        HibernateSessionFactoryUtil.getSessionFactory().openSession()
                .remove(entity);
    }

    @Override
    public void update(Player entity) {
        HibernateSessionFactoryUtil.getSessionFactory().openSession()
                .merge(entity);
    }
}


//    public void save(Player entity) {
//        executeInsideTransaction(session -> session.persist(entity));
//    }
//
//    public Player findById(Long id) {
//        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()){
//            Player player = session.find(Player.class, id);
//            return player;
//        }
//    }
//
//    public List<Player> findAll() {
//        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()){
//            return session.createQuery("from players", Player.class).list();
//        }
//    }
//
//    public void delete(Player entity) {
//        executeInsideTransaction(session -> session.remove(entity));
//    }
//
//    @Override
//    public void update(Player entity) {
//        executeInsideTransaction(session -> session.merge(entity));
//    }
//
//    private void executeInsideTransaction(java.util.function.Consumer<Session> action) {
//        Transaction tx = null;
//        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
//            tx = (Transaction) session.beginTransaction();
//            action.accept(session);
//            tx.commit();
//        } catch (Exception e) {
//            // Попробуем откатить, если транзакция была создана
//            if (tx != null) {
//                try {
//                    tx.rollback();
//                } catch (Exception rbEx) {
//                    // Логируем, но не маскируем основное исключение
//                    System.err.println("Rollback failed: " + rbEx.getMessage());
//                }
//            }
//            throw new RuntimeException("Ошибка при работе с Hibernate", e);
//        }
//    }
//}