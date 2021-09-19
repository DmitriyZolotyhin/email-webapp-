package com.parser.letters.repositories;


import com.parser.letters.models.classes4Hibernate.Email2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email2, Long> {

}
