package com.vehicule.api.controller;

import com.vehicule.api.entity.Annonce;
import com.vehicule.api.entity.User;
import com.vehicule.api.entity.Modele;
import com.vehicule.api.entity.Carburant;
import com.vehicule.api.repository.AnnonceRepository;
import com.vehicule.api.repository.UserRepository;
import com.vehicule.api.repository.ModeleRepository;
import com.vehicule.api.repository.CarburantRepository;
import com.vehicule.api.services.AnnonceService;
import com.vehicule.api.services.VenteAnnonceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
public class AnnonceController {
    private final AnnonceService annonceService;
    private final AnnonceRepository annonceRepository;
    private final UserRepository userRepository;
    private final ModeleRepository modeleRepository;
    private final CarburantRepository carburantRepository;
    private final VenteAnnonceService venteannonceService;
    
    @Autowired
    public AnnonceController(AnnonceService annonceService, AnnonceRepository annonceRepository,
    UserRepository userRepository,ModeleRepository modeleRepository,CarburantRepository carburantRepository,
    VenteAnnonceService venteannonceService){
        this.annonceService = annonceService;
        this.venteannonceService = venteannonceService;
        this.annonceRepository = annonceRepository;
        this.userRepository = userRepository;
        this.modeleRepository = modeleRepository;
        this.carburantRepository = carburantRepository;
    }

    @GetMapping("/auth/annonces")
    public List<Annonce> findAll(){
        return annonceRepository.findAll();
    }

    @PostMapping("/annonce")
    public Annonce save(String description,Long idUser,Long idModele,Long idCarburant,double prix,double kilometrage){
        User proprietaire = userRepository.findById(idUser).get();
        Modele modele = modeleRepository.findById(idModele).get();
        Carburant carburant = carburantRepository.findById(idCarburant).get();
        double commission=0;
        int etat=0;
        int status=0;
        return annonceService.saveAnnonce(description,proprietaire,modele,carburant,prix,commission,kilometrage,etat,status);
    }

    @GetMapping("/auth/annonces/{id}")
    public Optional<Annonce> find(@PathVariable("id") Long id){
        return annonceRepository.findById(id);
    }

    @PutMapping("/annonces/{id}")
    public Annonce modif(@PathVariable Long id, @RequestBody Annonce v){
        return annonceService.updateAnnonce(id, v);
    }

    @DeleteMapping("/annonces/{id}")
    public void deleteById(@PathVariable Long id){
        annonceService.deleteAnnonce(id);
    }

    @DeleteMapping("/annonces")
    public void deleteAll(){
        annonceRepository.deleteAll();
    }

    @GetMapping("/annonces/user/{userId}")
    public List<Annonce> getAnnoncesByUserId(@PathVariable Long userId) {
        return annonceService.getAnnoncesByUserId(userId);
    }
    
    @PutMapping("/annonces/sell")
    public void updateStatusByIdAnnonce(@RequestParam Long idAnnonce,Long idUser) {
        annonceService.updateStatusByIdAnnonce(idAnnonce);
        User acheteur = userRepository.findById(idUser).get();
        Annonce annonce = annonceRepository.findById(idAnnonce).get();
        venteannonceService.saveVenteAnnonce(acheteur,annonce);
    }

    @PutMapping("/annonces/validate")
    public void updateEtatByIdAnnonce(@RequestParam Long idAnnonce,double commission) {
        annonceService.updateEtatByIdAnnonce(idAnnonce,commission);
    }

    @GetMapping("/auth/annonces/encours")
    public List<Annonce> getAnnouncementsByEtatAndStatus() {
        int etat = 10;
        int status = 0;
        return annonceService.getAnnouncementsByEtatAndStatus(etat, status);
    }
    
    @GetMapping("/annonces/vendus")
    public List<Annonce> getAnnonceVendu() {
        int etat = 10;
        int status = 10;
        return annonceService.getAnnouncementsByEtatAndStatus(etat, status);
    }
    
    @GetMapping("/annonces/nonvalide")
    public List<Annonce> getAnnonceNonValide() {
        int etat = 0;
        int status = 0;
        return annonceService.getAnnouncementsByEtatAndStatus(etat, status);
    }
}
