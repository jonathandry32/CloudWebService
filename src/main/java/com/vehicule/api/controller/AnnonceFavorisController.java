package com.vehicule.api.controller;

import com.vehicule.api.entity.AnnonceFavoris;
import com.vehicule.api.entity.User;
import com.vehicule.api.entity.Annonce;
import com.vehicule.api.repository.AnnonceFavorisRepository;
import com.vehicule.api.repository.UserRepository;
import com.vehicule.api.repository.AnnonceRepository;
import com.vehicule.api.services.AnnonceFavorisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
public class AnnonceFavorisController {
    private final AnnonceFavorisService annonceFavorisService;
    private final AnnonceFavorisRepository annonceFavorisRepository;
    private final UserRepository userRepository;
    private final AnnonceRepository annonceRepository;
    
    @Autowired
    public AnnonceFavorisController(AnnonceFavorisService annonceFavorisService, AnnonceFavorisRepository annonceFavorisRepository,
    UserRepository userRepository,AnnonceRepository annonceRepository){
        this.annonceFavorisService = annonceFavorisService;
        this.annonceFavorisRepository = annonceFavorisRepository;
        this.userRepository = userRepository;
        this.annonceRepository = annonceRepository;
    }

    @GetMapping("/annoncefavoris")
    public List<AnnonceFavoris> findAll(){
        return annonceFavorisRepository.findAll();
    }

    @PostMapping("/annoncefavoris")
    public AnnonceFavoris save(Long idUser,Long idAnnonce){
        User user = userRepository.findById(idUser).get();
        Annonce annonce = annonceRepository.findById(idAnnonce).get();
        return annonceFavorisService.saveAnnonceFavoris(user,annonce);
    }

    @GetMapping("/annoncefavoris/{id}")
    public Optional<AnnonceFavoris> find(@PathVariable("id") Long id){
        return annonceFavorisRepository.findById(id);
    }

    @PutMapping("/annoncefavoris/{id}")
    public AnnonceFavoris modif(@PathVariable Long id, @RequestBody AnnonceFavoris v){
        return annonceFavorisService.updateAnnonceFavoris(id, v);
    }

    @DeleteMapping("/annoncefavoris/{id}")
    public void deleteById(@PathVariable Long id){
        annonceFavorisService.deleteAnnonceFavoris(id);
    }
    
    @DeleteMapping("/annoncefavoris/unlike")
    public void suppFav(Long idAnnonce,Long idUser){
        annonceFavorisService.supprimerFavori(idAnnonce,idUser);
    }

    @DeleteMapping("/annoncefavoris")
    public void deleteAll(){
        annonceRepository.deleteAll();
    }

    @GetMapping("/annoncefavoris/user/{userId}")
    public List<AnnonceFavoris> getFavoriteAnnoncesByUserId(@PathVariable Long userId) {
        return annonceFavorisService.getFavoriteAnnoncesByUserId(userId);
    }
    
}
