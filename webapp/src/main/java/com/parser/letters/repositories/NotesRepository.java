package com.parser.letters.repositories;

import com.parser.letters.models.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<Notes, Long> {
	
	Notes findByTitle(final String title);
}
