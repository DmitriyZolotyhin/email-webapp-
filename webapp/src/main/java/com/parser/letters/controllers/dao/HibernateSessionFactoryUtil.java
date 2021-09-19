package com.parser.letters.controllers.dao;

import com.parser.letters.models.classes4Hibernate.Email2;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




// в этом классе создаем фабрику сессий подключения к БД
public class HibernateSessionFactoryUtil {

    private static final Logger logger = LoggerFactory.getLogger(HibernateSessionFactoryUtil.class.getName());

    private static SessionFactory sessionFactory;

    public HibernateSessionFactoryUtil() {
    }

    public static SessionFactory getSessionFactory() {

        logger.debug("Method getSessionFactory() started;");

        if (sessionFactory == null) {
            // создаем шаблон настройки подключения и добавляем в него нужный класс
            //Configuration configuration = new Configuration().configure();
            Configuration configuration = new Configuration().configure();

            configuration.addAnnotatedClass(Email2.class);

            // создаем шаблон самого подключения с новыми настройками
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().
                    applySettings(configuration.getProperties());

            // знакомим фабрику подключений с новым шаблоном подключения
            sessionFactory = configuration.buildSessionFactory(builder.build());
            logger.info("SessionFactory created successfully;");

        }

        logger.debug("Method getSessionFactory() finished;");
        return sessionFactory;
    }
}
