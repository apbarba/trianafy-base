package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.service.SongService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class SongController {

    private SongService songService;

    //Método que añade una canción con un dto(no se ha implementado aún)
    @PostMapping("/song/")
    public ResponseEntity<Song> newArtist(@RequestBody Song song){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(songService.add(song));
    }
    //Método para obtener la lista de los artistas
    @GetMapping("/song/")
    public ResponseEntity<List<Song>> findAll() {

        return ResponseEntity.ok(songService.findAll());
    }

    //Método para obtener a un artista por su id

    @GetMapping("/song/{id}")
    public ResponseEntity<Song> findById(@PathVariable Long id) {

        return ResponseEntity.of((songService.findById(id)));
    }
    //Método que edita a un artista por su id
    @PutMapping("/song/{id}")
    public ResponseEntity<Song> editArtist(@RequestBody Song song,
                                             @PathVariable Long id) {

        return ResponseEntity.of(
                songService.findById(id)
                        .map(old -> {
                            old.setTitle(song.getTitle());
                            old.setArtist(song.getArtist());
                            old.setAlbum(song.getAlbum());
                            old.setYear(song.getYear());
                            return  Optional.of(songService.add(old));
                        })
                        .orElse(Optional.empty())
        );
    }
    //Método que elimina a un artista pero no elimina las canciones de la playlist
    @DeleteMapping("/song/{id}")
    public ResponseEntity<?> deleteArtis(@PathVariable Long id) {

        songService.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
