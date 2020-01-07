package com.wedding.demo.repositories;

import com.wedding.demo.models.Table;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<Table, Long> {
}
