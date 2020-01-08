package com.wedding.demo.repositories;

import com.wedding.demo.models.Offre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OffreRepository extends JpaRepository<Offre, Long> {

//    @Query("select C from Offre C where c.disponible=?1 ")
//    public
}
