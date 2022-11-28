package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.dto.ConverterPlaylist;
import com.salesianostriana.dam.trianafy.dto.CreatePlaylist;
import com.salesianostriana.dam.trianafy.dto.GetPlaylist;
import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.service.PlaylistService;
import com.salesianostriana.dam.trianafy.service.SongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor

public class PlaylistController {

    private final PlaylistService playlistService;

    private final SongService songService;

    private final ConverterPlaylist converterPlaylist;


    @Operation(summary = "Crea una playlist nueva a base de una dto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Creada con éxitp",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreatePlaylist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Es obligatorio el nombre de la playlist",
                    content = @Content),
    })
    @PostMapping("/list/")
    public ResponseEntity<Playlist> newArtist(@RequestBody CreatePlaylist dto) {

        if (dto.getName().isEmpty()) {

            return ResponseEntity
                    .badRequest()
                    .build();
        }

        Playlist playlist = converterPlaylist.createPlaylist(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(playlistService.add(playlist));
    }

    @Operation(summary = "Muestra todas las listas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Estas son las playlists encontradas",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetPlaylist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se pudo encontrr ninguna playlist",
                    content = @Content),
    })
    @GetMapping("/list/")
    public ResponseEntity<List<GetPlaylist>> findAll() {

        if (playlistService.findAll().isEmpty()) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        List<GetPlaylist> list =
                playlistService.findAll()
                        .stream()
                        .map(converterPlaylist::getPlaylist)
                        .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(list);
    }

    @Operation(summary = "Por Id, muestra la información de esa playlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la playlist con ese ID",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetPlaylist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado una playlist con ese ID",
                    content = @Content),
    })
    @GetMapping("/list/{id}")
    public ResponseEntity<Playlist> findById(@PathVariable Long id) {

        if (playlistService.findById(id).isEmpty()) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.of((playlistService.findById(id)));
    }

    @Operation(summary = "Edita la playlist por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Editado con éxito",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se encuentra la playlist con ese Id",
                    content = @Content),
    })
    @PutMapping("/list/{id}")
    public ResponseEntity<GetPlaylist> editArtist(@RequestBody CreatePlaylist dto,
                                                  @PathVariable Long id) {

        if (playlistService.findById(id).isEmpty()) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        playlistService.findById(id).get().setName(dto.getName());
        playlistService.findById(id).get().setDescription(dto.getDescription());
        playlistService.add(playlistService.findById(id).get());

        return ResponseEntity
                .ok()
                .body(converterPlaylist.getPlaylist(playlistService.findById(id).get()));


    }

    @Operation(summary = "Borra una playlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "La playlist se ha borrado con éxito",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No existe la playlist, por lo que no se pudo eliminar",
                    content = @Content),
    })
    @DeleteMapping("/list/{id}")
    public ResponseEntity<Playlist> deleteArtis(@PathVariable Long id) {

        if (playlistService.findById(id).isEmpty()) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        playlistService.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Muestra todas las canciones de una playlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se muestra con éxito",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "La playlist no existe (fallo de Id)",
                    content = @Content)
    })
    @GetMapping("/list/{id}/song")
    public ResponseEntity<List<Song>> findAllSongs(@PathVariable Long id) {

        if (playlistService.findById(id).isEmpty()) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity
                .ok()
                .body(playlistService.findById(id).get().getSongs());
    }

    @Operation(summary = "Añade una canción por ID a una playlist por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Añadido con éxito",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha podido encontrar la playlist o una canción por esa id",
                    content = @Content),
    })
    @PostMapping("/list/{id1}/song/{id2}")
    public ResponseEntity<Playlist> addSongInPlayListById(@PathVariable Long id1,
                                                          @PathVariable Long id2) {

        if (songService.findById(id2).isEmpty() || playlistService.findById(id1).isEmpty())  {

            return ResponseEntity
                    .notFound()
                    .build();
        } else {

            playlistService.findById(id1).get().addSong(songService.findById(id2).get());

            return ResponseEntity
                    .ok()
                    .body(playlistService.add(playlistService.findById(id1).get()));
        }
    }

    @Operation(summary = "Busca una canción dentro de una playlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Cancion encontrada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "La playlist no existe o la canción no existe (fallo de id)",
                    content = @Content)
    })
    @GetMapping("/list/{id1}/song/{id2}")
    public ResponseEntity<List<Song>> findSongByIdInPlaylist(@PathVariable Long id1,
                                                             @PathVariable Long id2) {

        List<Song> list;
        if (playlistService.findById(id1).isEmpty() || songService.findById(id2).isEmpty()) {

            return ResponseEntity
                    .notFound()
                    .build();

        } else {

            list = playlistService.findById(id1).get().getSongs();

        }

        return ResponseEntity
                .ok()
                .body(list);
    }


    @Operation(summary = "Borra una canción de la Playlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Canción eliminada con éxito",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Canción inexistente dentro de la Playlist indicada",
                    content = @Content),
    })
    @DeleteMapping("/list/{id}/song/{id2}")
    public ResponseEntity<Playlist> deleteSong(@PathVariable Long id,
                                               @PathVariable Long id2) {

        if (playlistService.findById(id).isEmpty() || songService.findById(id2).isEmpty()) {

            return ResponseEntity
                    .notFound()
                    .build();
        }
        playlistService.findById(id).get().deleteSong(songService.findById(id2).get());
        playlistService.add(playlistService.findById(id).get());

        return ResponseEntity
                .noContent()
                .build();

    }
}
