package ex_grupo11.ex_api_grupo11.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ex_grupo11.ex_api_grupo11.responses.AlbumResponse;
import ex_grupo11.ex_api_grupo11.services.AlbumService;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @GetMapping
    public ResponseEntity<List<AlbumResponse>> getAllAlbums() {
        return ResponseEntity.ok(albumService.getAllAlbums());
    }
    

}
