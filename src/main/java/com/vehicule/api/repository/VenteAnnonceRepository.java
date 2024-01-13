package com.vehicule.api.repository;

import com.vehicule.api.entity.VenteAnnonce;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenteAnnonceRepository extends JpaRepository<VenteAnnonce, Long> {
}
