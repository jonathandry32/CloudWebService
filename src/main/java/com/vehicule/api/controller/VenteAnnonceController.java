package com.vehicule.api.controller;

import com.vehicule.api.entity.VenteAnnonce;
import com.vehicule.api.entity.User;
import com.vehicule.api.entity.Annonce;
import com.vehicule.api.repository.VenteAnnonceRepository;
import com.vehicule.api.repository.UserRepository;
import com.vehicule.api.repository.AnnonceRepository;
import com.vehicule.api.services.VenteAnnonceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
public class VenteAnnonceController {
    private final VenteAnnonceService venteannonceService;
    private final VenteAnnonceRepository venteannonceRepository;
    private final UserRepository userRepository;
    private final AnnonceRepository annonceRepository;
    
    @Autowired
    public VenteAnnonceController(VenteAnnonceService venteannonceService, VenteAnnonceRepository venteannonceRepository,
    UserRepository userRepository,AnnonceRepository annonceRepository){
        this.venteannonceService = venteannonceService;
        this.venteannonceRepository = venteannonceRepository;
        this.userRepository = userRepository;
        this.annonceRepository = annonceRepository;
    }

    @GetMapping("/venteannonces")
    public List<VenteAnnonce> findAll(){
        return venteannonceRepository.findAll();
    }

    @PostMapping("/venteannonce")
    public VenteAnnonce save(Long idUser,Long idAnnonce){
        User acheteur = userRepository.findById(idUser).get();
        Annonce annonce = annonceRepository.findById(idAnnonce).get();
        return venteannonceService.saveVenteAnnonce(acheteur,annonce);
    }

    @GetMapping("/venteannonces/{id}")
    public Optional<VenteAnnonce> find(@PathVariable("id") Long id){
        return venteannonceRepository.findById(id);
    }

    @PutMapping("/venteannonces/{id}")
    public VenteAnnonce modif(@PathVariable Long id, @RequestBody VenteAnnonce v){
        return venteannonceService.updateVenteAnnonce(id, v);
    }

    @DeleteMapping("/venteannonces/{id}")
    public void deleteById(@PathVariable Long id){
        venteannonceService.deleteVenteAnnonce(id);
    }

    @DeleteMapping("/venteannonces")
    public void deleteAll(){
        annonceRepository.deleteAll();
    }
}
