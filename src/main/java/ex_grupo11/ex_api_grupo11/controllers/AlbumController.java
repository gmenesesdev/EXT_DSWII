package ex_grupo11.ex_api_grupo11.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ex_grupo11.ex_api_grupo11.responses.AlbumResponse;
import ex_grupo11.ex_api_grupo11.services.AlbumService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @GetMapping
    public ResponseEntity<List<AlbumResponse>> getAllAlbums() {
        return ResponseEntity.ok(albumService.getAllAlbums());
    }
    
    @GetMapping("/{idAlbum}")
    public ResponseEntity<AlbumResponse> getAlbumByID(@PathVariable("idAlbum") Long idAlbum) {
        if (idAlbum == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ID del álbum no puede ser nulo.");
        }
        return ResponseEntity.ok(albumService.getAlbumByID(idAlbum));
    }

    @DeleteMapping("/{idAlbum}")
    public ResponseEntity<String> deleteAlbumByID(@PathVariable("idAlbum") Long idAlbum) {
        if (idAlbum == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ID del álbum no puede ser nulo.");
        }

        try {
            albumService.deleteAlbum(idAlbum);
            return ResponseEntity.ok("Álbum eliminado correctamente con ID: " + idAlbum);
        } catch (ResponseStatusException e) {
            throw e; // Lanza el error si el servicio ya maneja la excepción
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno al eliminar el álbum.");
        }
    }

}
