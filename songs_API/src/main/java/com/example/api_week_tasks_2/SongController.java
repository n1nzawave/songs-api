package com.example.api_week_tasks_2;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/song")
public class SongController {
    private final List<Song> songs;

    public SongController(){
        this.songs = new ArrayList<>();
    }

    //CREATE
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Song> addSong(
            @RequestParam("songName") String songName,
            @RequestParam("artist") String artist,
            @RequestParam("file") MultipartFile file
    ){
        try {
            Song newSong = new Song();
            newSong.setName(songName);
            newSong.setArtist(artist);
            newSong.setAudioData(file.getBytes());
            songs.add(newSong);
            return new ResponseEntity<>(newSong, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/{index}/play", produces = "audio/mpeg")
    public ResponseEntity<byte[]> playSong(@PathVariable int index){
        if (index < 0 || index >= songs.size()) {
            return ResponseEntity.notFound().build();
        }

        Song song = songs.get(index);
        return ResponseEntity.ok(song.getAudioData());
    }

    //READALL
    @GetMapping
    public ResponseEntity<List<Song>> getAllSongs(){
        return ResponseEntity.ok(songs);
    }

    //READBYINDEX
    @GetMapping("/{index}")
    public ResponseEntity<Song> getSongByIndex(@PathVariable int index){
        if (index < 0 || index >= songs.size()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(songs.get(index));
    }

    //UPDATE
    @PutMapping("/{index}")
    public ResponseEntity<Song> updateSong(@PathVariable int index, @RequestBody Song newSong){
        if (index < 0 || index >= songs.size()){
            return ResponseEntity.notFound().build();
        }
        songs.set(index, newSong);
        return ResponseEntity.ok(newSong);
    }

    //DELETE
    @DeleteMapping("/{index}")
    public ResponseEntity<Song> deleteSong(@PathVariable int index){
        if (index < 0 || index >= songs.size()){
            return ResponseEntity.notFound().build();
        }
        songs.remove(index);
        return ResponseEntity.noContent().build();
    }

}
