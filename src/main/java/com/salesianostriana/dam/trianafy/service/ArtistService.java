package com.salesianostriana.dam.trianafy.service;


import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository repo;

    //Método para añadir un artista nuevo
    @PostMapping("/artist/")
    public Artist add(Artist artist) {

        return repo.save(artist);
    }
    //Método para obtener la lista de los artistas
    @GetMapping("/artist/")
    public List<Artist> findAll() {

        return repo.findAll();
    }

    //Método para obtener a un artista por su id
    @GetMapping("/artist/{id}")
    public Optional<Artist> findById(Long id) {

        return repo.findById(id);
    }
    //Método que edita a un artista por su id
    @PutMapping("/artist/{id}")
    public Artist edit(Artist artist) {

        return repo.save(artist);
    }
    //Método que elimina a un artista pero no elimina las canciones de la playlist
    @DeleteMapping("/artist/{id}")
    public void delete(Artist artist) {

        repo.delete(artist);
    }
    //Método para eliminar a un artista por su id, en este caso eliminariamos sus canciones de la playlist
    @DeleteMapping("/artist/{id}")
    public void deleteById(Long id) {

        repo.deleteById(id);
    }

}
