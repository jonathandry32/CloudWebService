package com.vehicule.api.controller;

import com.vehicule.api.entity.PhotoAnnonce;
import com.vehicule.api.entity.Annonce;
import com.vehicule.api.repository.PhotoAnnonceRepository;
import com.vehicule.api.repository.AnnonceRepository;
import com.vehicule.api.services.PhotoAnnonceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;
import java.io.IOException;

@RestController
public class PhotoAnnonceController {
    private final PhotoAnnonceService photoannonceService;
    private final PhotoAnnonceRepository photoannonceRepository;
    private final AnnonceRepository annonceRepository;
    
    @Autowired
    public PhotoAnnonceController(PhotoAnnonceService photoannonceService, PhotoAnnonceRepository photoannonceRepository,AnnonceRepository annonceRepository){
        this.photoannonceService = photoannonceService;
        this.photoannonceRepository = photoannonceRepository;
        this.annonceRepository = annonceRepository;
    }

    @GetMapping("/photoannonces")
    public List<PhotoAnnonce> findAll(){
        return photoannonceRepository.findAll();
    }

    @GetMapping("/photoannonces/annonce/{idAnnonce}")
    public List<PhotoAnnonce> getPhotosByAnnonceId(@PathVariable Long idAnnonce) {
        return photoannonceService.getPhotosByAnnonceId(idAnnonce);
    }

    @PostMapping("/photoannonce")
    public PhotoAnnonce save(Long idAnnonce, MultipartFile file) throws IOException {
        Annonce annonce = annonceRepository.findById(idAnnonce).get();
        String fileUrl = photoannonceService.uploadPhoto(file);
        return photoannonceService.savePhotoAnnonce(annonce, fileUrl);
    }
}
