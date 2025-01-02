package ex_grupo11.ex_api_grupo11.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Album album = albumRepository.findById(idAlbum)
                .orElseThrow(() -> new RuntimeException("Album no encontrado"));
        return new AlbumResponse(
                album.getIdAlbum(),
                album.getNombreAlbum(),
                album.getDescripcion(),
                album.getImagen());
    }

    public void deleteAlbum(Long idAlbum) {
        if(idAlbum == null) {
            throw new RuntimeException("El id del album no puede ser nulo");
        }
        boolean exists = albumRepository.existsById(idAlbum);
        if(!exists) {
            throw new RuntimeException("El album no existe");
        } 
        albumRepository.deleteById(idAlbum);
    }

}
