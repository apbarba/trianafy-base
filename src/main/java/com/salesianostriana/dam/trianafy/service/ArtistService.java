package com.salesianostriana.dam.trianafy.service;


import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository repo;


    public Artist add(Artist artist) {
        return repo.save(artist);
    }

    public Optional<Artist> findById(Long id) {
        return repo.findById(id);
    }

    public List<Artist> findAll() {
        return repo.findAll();
    }

    public Artist edit(Artist artist) {
        return repo.save(artist);
    }

    public void delete(Artist artist) {
        repo.delete(artist);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

}
