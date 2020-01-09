package com.wedding.demo.repositories;

import com.wedding.demo.models.Offre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OffreRepository extends JpaRepository<Offre, Long> {

    public List<Offre> findByDisponibleIsTrue();
}
