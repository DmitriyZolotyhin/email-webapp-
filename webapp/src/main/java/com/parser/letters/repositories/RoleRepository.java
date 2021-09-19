package com.parser.letters.repositories;

import com.parser.letters.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByRole(final String role);

}
