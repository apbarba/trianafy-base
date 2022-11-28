package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.dto.CreateSong;
import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import com.salesianostriana.dam.trianafy.service.ArtistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.util.List;
import java.util.Optional;

@Controller
public class ArtistController {

    private ArtistService artistService;

    private ArtistRepository repo;


    @Operation(summary = "Crea a un artista nuevo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Artista creado con éxito",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Song.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos o en la petición, por lo que no se pudo crear",
                    content = @Content),
    })
    @PostMapping("/artist/")
    public ResponseEntity<Artist> newArtist(@RequestBody Artist artist){

        if (artist.getName().isEmpty()){

            return ResponseEntity
                    .badRequest()
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(artistService.add(artist));
    }

    @Operation(summary = "Muestra la lista con todos los artistas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la lista de los artistas",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreateSong.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado ningun artista",
                    content = @Content),
    })
    @GetMapping("/artist/")
    public ResponseEntity<List<Artist>> findAll() {

        if (artistService.findAll().isEmpty()){

            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(artistService.findAll());
    }

    @Operation(summary = "Se muestra a un artista por el ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha encontrado al artista",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontró a un artista con ese ID",
                    content = @Content),
    })
    @GetMapping("/artist/{id}")
    public ResponseEntity<Artist> findById(@PathVariable Long id) {

        if (artistService.findById(id).isEmpty()){

            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.of((artistService.findById(id)));
    }

    @Operation(summary = "Edita las información de un artista por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "El artista ha sido modificado con éxito",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningún artista (fallo de id)",
                    content = @Content),
    })
    @PutMapping("/artist/{id}")
    public ResponseEntity<Artist> editArtist(@RequestBody Artist artist,
                                             @PathVariable Long id) {

        if (artistService.findById(id).isEmpty()){

            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.of(
                artistService.findById(id)
                        .map(old -> {
                            old.setName(artist.getName());
                            return  Optional.of(artistService.add(old));
                        })
                        .orElse(Optional.empty())
        );
    }

    @Operation(summary = "Elimina al artista que tenga el ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "El artista ha sido eliminado con éxito",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se encuentra el artista con ese ID",
                    content = @Content),
    })
    @DeleteMapping("/artist/{id}")
    public ResponseEntity<?> deleteArtis(@PathVariable Long id) {

        if (artistService.findById(id).isEmpty()){

            return ResponseEntity
                    .notFound()
                    .build();
        }else {


        }

        repo.deleteById(id);

        return ResponseEntity
                .noContent()
                .build();
    }

}
