package com.salesianostriana.dam.trianafy.dto;

import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.model.Song;
import org.springframework.stereotype.Component;

@Component
public class ConverterSong {

    public Song createSong(CreateSong create, Artist artist){

        return Song.builder()
                .title(create.getTitle())
                .album(create.getAlbum())
                .artist(artist)
                .year(create.getYear())
                .build();
    }

    public GetSong getSong(Song s, Artist artist){

        return GetSong.builder()
                .id(s.getId())
                .title(s.getTitle())
                .album(s.getAlbum())
                .year(s.getYear())
                .artist(artist)
                .build();
    }
}
