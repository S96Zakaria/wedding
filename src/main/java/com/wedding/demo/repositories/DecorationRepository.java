package com.wedding.demo.repositories;

import com.wedding.demo.models.Contact;
import com.wedding.demo.models.Decoration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DecorationRepository extends JpaRepository<Decoration, Long> {
}
