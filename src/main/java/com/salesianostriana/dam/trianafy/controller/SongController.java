package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.dto.ConverterSong;
import com.salesianostriana.dam.trianafy.dto.CreateSong;
import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.service.ArtistService;
import com.salesianostriana.dam.trianafy.service.PlaylistService;
import com.salesianostriana.dam.trianafy.service.SongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class SongController {

    private SongService songService;
    private ConverterSong cS;

    private ArtistService artistService;

    private PlaylistService playlistService;

    @Operation(summary = "Crea una nueva canción y la añade a la lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Creada la canción con éxito",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Song.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos o en la petición, por lo que no se pudo implemntar ni crear",
                    content = @Content),
    })
    @PostMapping("/song/")
    public ResponseEntity<Song> newArtist(@RequestBody CreateSong dto){

        if (songService.findById(dto.getIdArtist()).isEmpty()){

            return ResponseEntity
                    .badRequest()
                    .build();
        }

        Song new = cS.createSong(dto, songService.findById(dto.getIdArtist()).get().getArtist());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(songService.add(new));

       }

    @Operation(summary = "Muestra la lista con todas las canciones existentes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la playlist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreateSong.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado ninguna playlist",
                    content = @Content),
    })
    @GetMapping("/song/")
    public ResponseEntity<List<Song>> findAll() {

        if (songService.findAll().isEmpty()){

            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(songService.findAll());
    }

    @Operation(summary = "Muestra por id la información de la canción")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la canción con exito",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Song.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Fallo de id, canción inexistente",
                    content = @Content),
    })
    @GetMapping("/song/{id}")
    public ResponseEntity<Song> findById(@PathVariable Long id) {

        if (songService.findById(id).isEmpty()){

            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.of((songService.findById(id)));
    }

    @Operation(summary = "Editar la información de una canción por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha editado con éxito",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreateSong.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la canción con ese Id",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Petición errónea",
                    content = @Content),
    })
    @PutMapping("/song/{id}")
    public ResponseEntity<Song> editArtist(@RequestBody Song song,
                                             @PathVariable Long id) {

        if (songService.findById(id).isEmpty()) {

            return ResponseEntity
                    .notFound()
                    .build();

        }
        if (artistService.findById(id).isEmpty()) {

            return ResponseEntity
                    .badRequest()
                    .build();

        } else {

            return ResponseEntity.of(
                    songService.findById(id)
                            .map(old -> {
                                old.setTitle(song.getTitle());
                                old.setArtist(song.getArtist());
                                old.setAlbum(song.getAlbum());
                                old.setYear(song.getYear());
                                return Optional.of(songService.add(old));
                            })
                            .orElse(Optional.empty())
            );
        }

    }

    @Operation(summary = "Elimina la canción que tenga el ID seleccionado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha eliminado con éxito",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Song.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Canción no encontrada por ese id, por lo que no se pudo elliminar",
                    content = @Content),
    })
    @DeleteMapping("/song/{id}")
    public ResponseEntity<?> deleteArtis(@PathVariable Long id) {

        if (songService.findById(id).isEmpty()){

            return ResponseEntity
                    .notFound()
                    .build();

        }else {



        }

        songService.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
