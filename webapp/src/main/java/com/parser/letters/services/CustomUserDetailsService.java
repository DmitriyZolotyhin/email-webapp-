package com.parser.letters.services;

import java.util.*;

import com.parser.letters.controllers.dao.DAOImpl;
import com.parser.letters.controllers.dao.HibernateSessionFactoryUtil;
import com.parser.letters.models.Role;
import com.parser.letters.models.User;
import com.parser.letters.models.classes4Hibernate.Email2;
import com.parser.letters.repositories.RoleRepository;
import com.parser.letters.repositories.UserRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService extends DAOImpl implements UserDetailsService  {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public User findUserByEmail(String email) {
	    return userRepository.findByEmail(email);
	}
	
//	public void saveUser(User user, String role) {
//	    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//	    user.setEnabled(true);
//	    Role userRole = roleRepository.findByRole(role);
//	    user.setRoles(new HashSet<>(Arrays.asList(userRole)));
//	    userRepository.save(user);
//	}
	
	public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        userRepository.save(user);
    }
	
	@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);  
        if(user != null) {
            List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
            return buildUserForAuthentication(user, authorities);
        } else {
            throw new UsernameNotFoundException("username not found");
        }
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        userRoles.forEach((role) -> {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        });

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }



    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class.getName());

    @Override
    // сохраняем в БД новую строку
    public void saveEntry(Email2 email2) {
        super.saveEntry(email2);
    }

    @Override
    // обновляем существующую строку в БД по адресу почты
    public void updateEntry(Email2 email2) {
        super.updateEntry(email2);
    }

    // обновляем существующую строку в БД по номеру записи
    public void updateEntry(int id) {
        Email2 email2 = HibernateSessionFactoryUtil.getSessionFactory().openSession().
                get(Email2.class, id);
        super.updateEntry(email2);
    }

    @Override
    // удаляем строку из БД по почтовому адресу
    public void delEntry(Email2 email2) {
        super.delEntry(email2);
    }

    @Override
    // удаляем строку из БД по номеру записи
    public void delEntry(int id) {
        super.delEntry(id);
    }

    @Override
    // получаем почту по номеру записи
    public String getEmail(int id) {
        return super.getEmail(id);
    }

    @Override
    // получаем статус записи по её номеру (новая, готовится к отправке, отправляется и тд)
    public String getStatus(int id) {
        return super.getStatus(id);
    }

    @Override
    // получаем статусы несколькуих записей по их номерам
    public Map<String, String> getStatuses(int... ids) {
        return super.getStatuses(ids);
    }

    @Override
    // получаем список почтовых адресов, взятых с общего url
    public List<String> getEmailsByUrl(String url) {
        return super.getEmailsByUrl(url);
    }

    @Override
    // получаем список всех почтовых адресов и их "родительских" url из БД
    public List<Email2> getAll() {
        return super.getAll();
    }

    @Transactional
    // удаляем все записи в таблице БД
    public void delAll() {

        logger.debug("Method delAll() started;");

        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("delete from Email");

        Transaction tx = session.beginTransaction();
        query.executeUpdate();
        tx.commit();

        logger.debug("Method delAll() finished;"  + "\n");
    }


}
