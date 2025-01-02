package ex_grupo11.ex_api_grupo11.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import ex_grupo11.ex_api_grupo11.models.Album;
import ex_grupo11.ex_api_grupo11.repositories.AlbumRepository;
import ex_grupo11.ex_api_grupo11.responses.AlbumResponse;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    public List<AlbumResponse> getAllAlbums() {
        return albumRepository.findAll().stream().map(alb -> new AlbumResponse(
                alb.getIdAlbum(),
                alb.getNombreAlbum(),
                alb.getDescripcion(),
                alb.getImagen())).collect(Collectors.toList());
    }

    public AlbumResponse getAlbumByID(Long idAlbum) {

        if (idAlbum == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ID del álbum no puede ser nulo.");
        }

        Album album = albumRepository.findById(idAlbum)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Álbum no encontrado con el ID: " + idAlbum));

        return new AlbumResponse(
                album.getIdAlbum(),
                album.getNombreAlbum(),
                album.getDescripcion(),
                album.getImagen());
    }

    public void deleteAlbum(Long idAlbum) {
        if (idAlbum == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ID del álbum no puede ser nulo.");
        }
        boolean exists = albumRepository.existsById(idAlbum);
        if (!exists) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El álbum no existe con el ID: " + idAlbum);
        }
        albumRepository.deleteById(idAlbum);
    }

}
