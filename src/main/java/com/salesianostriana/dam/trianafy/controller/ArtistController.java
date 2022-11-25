package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import com.salesianostriana.dam.trianafy.service.ArtistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ArtistController {

    private final ArtistRepository repo;
    private ArtistService artistService;

    public ArtistController(ArtistRepository repo) {
        this.repo = repo;
    }

    //Método para añadir un artista nuevo
    /*
    /Retornamos con un http statius create para la creación de un
    nuevo artista en la que se guardará en la lista creada
    co el body y save repo
     */
    @PostMapping("/artist/")
    public ResponseEntity<Artist> newArtist(@RequestBody Artist artist){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(repo.save(artist));
    }
    //Método para obtener la lista de los artistas
    /*
     * Declaramos en el ResponseEntity el tipo de la lista y dentro de esta
     * la lista que queremos obtener y mostrar, que en este caso es
     * la de los artistas.
     *
     * Se devolverá el tipo ok del responseEntity y la lista de los
     * artistas
     * */
    @GetMapping("/artist/")
    public ResponseEntity<List<Artist>> findAll() {

        return ResponseEntity.ok(repo.findAll());
    }

    //Método para obtener a un artista por su id
    /*
     * Debemos de pasarle al método el parámetro identificativo por el que queremos
     * obtener ese artista enconcretro, en este caso es el id
     *
     * Por lo que devolveremos gracias al tipo of al artista que tiene
     * en concreto el id especificado
     * */
    @GetMapping("/artist/{id}")
    public ResponseEntity<Artist> findById(@PathVariable Long id) {

        return ResponseEntity.of((repo.findById(id)));
    }
    //Método que edita a un artista por su id
    @PutMapping("/artist/{id}")
    public ResponseEntity<Artist> editArtist(@RequestBody Artist artist,
                                             @PathVariable Long id) {

        return ResponseEntity.of(
                repo.findById(id)
                        .map(old -> {
                            old.setName(artist.getName());
                            return  Optional.of(repo.save(old));
                        })
                        .orElse(Optional.empty())
        );
    }
    //Método que elimina a un artista pero no elimina las canciones de la playlist
    @DeleteMapping("/artist/{id}")
    public ResponseEntity<?> deleteArtis(@PathVariable Long id) {

        if (repo.existsById(id))

            repo.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    //Método para eliminar a un artista por su id, en este caso eliminariamos sus canciones de la playlist
    @DeleteMapping("/artist/{id}")
    public void deleteById(Long id) {

        repo.deleteById(id);
    }
}
