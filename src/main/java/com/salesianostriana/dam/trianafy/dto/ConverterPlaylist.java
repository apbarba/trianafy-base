package com.salesianostriana.dam.trianafy.dto;

import com.salesianostriana.dam.trianafy.model.Playlist;
import org.springframework.stereotype.Component;

@Component

public class ConverterPlaylist {

    public Playlist createPlaylist(CreatePlaylist dto){

        return Playlist.builder()
                .description(dto.getDescription())
                .name(dto.getName())
                .build();
    }

    public GetPlaylist getPlaylist(Playlist playlist){

        return GetPlaylist.builder()
                .id(playlist.getId())
                .name(playlist.getName())
                .numberSongs(playlist.getSongs().size())
                .build();
    }
}
