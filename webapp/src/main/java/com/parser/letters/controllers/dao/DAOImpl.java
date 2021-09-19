package com.parser.letters.controllers.dao;

import com.parser.letters.models.classes4Hibernate.Email2;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;





// класс отвечает за всё взаимодействие с БД
public class DAOImpl implements DAO {

    private static final Logger logger = LoggerFactory.getLogger(DAOImpl.class.getName());

    // сохраняем в БД новую строку
    public void saveEntry(Email2 email2) {

        logger.debug("Method saveEntry() started with email: " + email2);

        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(email2);

        transaction.commit();
        session.close();

        logger.debug("Method saveEntry() finished;");
    }

    // обновляем существующую строку в БД
    public void updateEntry(Email2 email2) {
        logger.debug("Method updateEntry() started with email: " + email2);
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(email2);

        transaction.commit();
        session.close();

        logger.debug("Method saveEntry() finished;");
    }

    // удаляем строку из БД по почтовому адресу
    public void delEntry(Email2 email2) {
        logger.debug("Method delEntry() started with email: " + email2);
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(email2);

        transaction.commit();
        session.close();

        logger.debug("Method saveEntry() finished;");
    }

    // удаляем строку из БД по номеру записи
    public void delEntry(int id) {
        logger.debug("Method delEntry() started with id: " + id);
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(session.get(Email2.class, id));

        transaction.commit();
        session.close();

        logger.debug("Method saveEntry() finished;");
    }

    // получаем почту по номеру записи
    public String getEmail(int id) {
        logger.debug("Method getEmail() started with id: " + id);

        String address = HibernateSessionFactoryUtil.getSessionFactory().openSession().
                get(Email2.class, id).getAddress();

        logger.debug("Method getEmail() finished with result: " + address);
        return address;
    }

    // получаем статус записи по её номеру (новая, готовится к отправке, отправляется и тд)
    public String getStatus(int id) {
        logger.debug("Method getStatus() started with id: " + id);

        String status = HibernateSessionFactoryUtil.getSessionFactory().openSession().
                get(Email2.class, id).getStatus().name();

        logger.debug("Method saveEntry() finished with result: " + status);
        return status;
    }

    // получаем статусы несколькуих записей по их номерам
    public Map<String, String> getStatuses(int... ids) {
        logger.debug("Method getStatuses() started with ids: " + ids);

        Map<String, String> statuses = new HashMap<String, String>();

        for (int id : ids) {
            String address = getEmail(id);
            String status = getStatus(id);
            statuses.put(address, status);
        }

        logger.debug("Method saveEntry() finished with result: " + statuses);
        return statuses;
    }

    // получаем список почтовых адресов, взятых с общего url
    public List<String> getEmailsByUrl(String url) {
        logger.debug("Method getEmailsByUrl() started with url: " + url);

        ArrayList<String> mailsByUrl = new ArrayList<String>();

        List<Email2> mails = (List<Email2>) HibernateSessionFactoryUtil.getSessionFactory().
                openSession().createQuery("From Email").list();

        for (Email2 eml : mails) {
            if (eml.getUrl().equals(url)) {
                mailsByUrl.add(eml.getAddress());
            }
        }

        logger.debug("Method saveEntry() finished with result: " + mailsByUrl);
        return mailsByUrl;
    }

    // получаем список всех почтовых адресов и их "родительских" url из БД
    public List<Email2> getAll() {
        logger.debug("Method getAll() started;");

        List<Email2> mails = (List<Email2>) HibernateSessionFactoryUtil.getSessionFactory().
                openSession().createQuery("From Email").list();

        logger.debug("Method saveEntry() finished with result: " + mails);
        return mails;
    }
}
