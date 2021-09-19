package com.parser.letters.repositories;

import com.parser.letters.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByEmail(final String email);

}
