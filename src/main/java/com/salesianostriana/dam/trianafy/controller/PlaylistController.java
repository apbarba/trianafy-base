package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.service.PlaylistService;
import com.salesianostriana.dam.trianafy.service.SongService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class PlaylistController {

    private PlaylistService playlistService;

    //Método que añade una canción con un dto(no se ha implementado aún)
    @PostMapping("/list/")
    public ResponseEntity<Playlist> newArtist(@RequestBody Playlist playlist){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(playlistService.add(playlist));
    }
    //Método para obtener la lista de los artistas
    @GetMapping("/list/")
    public ResponseEntity<List<Playlist>> findAll() {

        return ResponseEntity.ok(playlistService.findAll());
    }

    //Método para obtener a un artista por su id

    @GetMapping("/list/{id}")
    public ResponseEntity<Playlist> findById(@PathVariable Long id) {

        return ResponseEntity.of((playlistService.findById(id)));
    }
    //Método que edita a un artista por su id
    @PutMapping("/list/{id}")
    public ResponseEntity<Playlist> editArtist(@RequestBody Playlist list,
                                           @PathVariable Long id) {

        return ResponseEntity.of(
                playlistService.findById(id)
                        .map(old -> {
                            old.setName(list.getName());
                            old.setDescription(list.getDescription());
                            old.setSongs(list.getSongs());

                            return  Optional.of(playlistService.add(old));
                        })
                        .orElse(Optional.empty())
        );
    }
    //Método que elimina a un artista pero no elimina las canciones de la playlist
    @DeleteMapping("/song/{id}")
    public ResponseEntity<?> deleteArtis(@PathVariable Long id) {

        playlistService.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
