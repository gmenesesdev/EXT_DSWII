package ex_grupo11.ex_api_grupo11.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import ex_grupo11.ex_api_grupo11.models.Lamina;
import ex_grupo11.ex_api_grupo11.models.UsuarioAlbum;
import ex_grupo11.ex_api_grupo11.models.UsuarioAlbumLamina;
import ex_grupo11.ex_api_grupo11.repositories.LaminaRepository;
import ex_grupo11.ex_api_grupo11.repositories.UsuarioAlbumLaminaRepository;
import ex_grupo11.ex_api_grupo11.repositories.UsuarioAlbumRepository;
import ex_grupo11.ex_api_grupo11.responses.AlbumResponse;
import ex_grupo11.ex_api_grupo11.responses.LaminaResponse;
import ex_grupo11.ex_api_grupo11.responses.MultipleLaminasRequest;
import ex_grupo11.ex_api_grupo11.responses.TipoCartaResponse;
import ex_grupo11.ex_api_grupo11.responses.UsuarioAlbumLaminaResponse;
import ex_grupo11.ex_api_grupo11.responses.UsuarioAlbumLaminasRequest;
import ex_grupo11.ex_api_grupo11.responses.UsuarioAlbumResponse;
import ex_grupo11.ex_api_grupo11.responses.UsuarioResponse;

@Service
public class UsuarioAlbumLaminaService {

    @Autowired
    private UsuarioAlbumLaminaRepository usuarioAlbumLaminaRepository;

    @Autowired
    private UsuarioAlbumRepository usuarioAlbumRepository;

    @Autowired
    private LaminaRepository laminaRepository;

    public List<UsuarioAlbumLaminaResponse> getAllUsuarioAlbumLaminas() {
        return usuarioAlbumLaminaRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public UsuarioAlbumLaminaResponse getUsuarioAlbumLaminaById(Long id) {
        UsuarioAlbumLamina usuarioAlbumLamina = usuarioAlbumLaminaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Registro no encontrado con el ID: " + id));
        return mapToResponse(usuarioAlbumLamina);
    }

    public UsuarioAlbumLaminaResponse createUsuarioAlbumLamina(UsuarioAlbumLaminasRequest request) {
        UsuarioAlbum usuarioAlbum = usuarioAlbumRepository.findById(request.getIdUsuarioAlbum())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Usuario álbum no encontrado con el ID: " + request.getIdUsuarioAlbum()));

        Lamina lamina = laminaRepository.findById(request.getIdLamina())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Lámina no encontrada con el ID: " + request.getIdLamina()));

        UsuarioAlbumLamina usuarioAlbumLamina = new UsuarioAlbumLamina();
        usuarioAlbumLamina.setUsuarioAlbum(usuarioAlbum);
        usuarioAlbumLamina.setLamina(lamina);
        usuarioAlbumLamina.setCantidad(request.getCantidad());

        UsuarioAlbumLamina.EstadoLamina estado;
        if (request.getCantidad() == 0) {
            estado = UsuarioAlbumLamina.EstadoLamina.FALTANTE;
        } else if (request.getCantidad() == 1) {
            estado = UsuarioAlbumLamina.EstadoLamina.COLECCIONADA;
        } else {
            estado = UsuarioAlbumLamina.EstadoLamina.REPETIDA;
        }
        usuarioAlbumLamina.setEstado(estado);

        UsuarioAlbumLamina savedEntity = usuarioAlbumLaminaRepository.save(usuarioAlbumLamina);
        return mapToResponse(savedEntity);
    }

    public List<UsuarioAlbumLaminaResponse> createMultipleLaminas(MultipleLaminasRequest request) {
        System.out.println("Cuerpo completo: " + request);
        request.getLaminas().forEach(laminaRequest -> {
            System.out.println("Lamina Request: " + laminaRequest);
        });
        if (request == null || request.getLaminas() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cuerpo de la solicitud no puede ser nulo.");
        }

        return request.getLaminas().stream().map(laminaRequest -> {
            if (laminaRequest.getIdUsuarioAlbum() == null || laminaRequest.getIdLamina() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Las IDs de UsuarioAlbum y Lamina no pueden ser nulas.");
            }

            System.out.println("Procesando UsuarioAlbum ID: " + laminaRequest.getIdUsuarioAlbum());
            System.out.println("Procesando Lamina ID: " + laminaRequest.getIdLamina());

            UsuarioAlbum usuarioAlbum = usuarioAlbumRepository.findById(laminaRequest.getIdUsuarioAlbum())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Usuario álbum no encontrado con el ID: " + laminaRequest.getIdUsuarioAlbum()));

            Lamina lamina = laminaRepository.findById(laminaRequest.getIdLamina())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Lámina no encontrada con el ID: " + laminaRequest.getIdLamina()));

            UsuarioAlbumLamina usuarioAlbumLamina = new UsuarioAlbumLamina();
            usuarioAlbumLamina.setUsuarioAlbum(usuarioAlbum);
            usuarioAlbumLamina.setLamina(lamina);
            usuarioAlbumLamina.setCantidad(laminaRequest.getCantidad());

            UsuarioAlbumLamina.EstadoLamina estado;
            if (laminaRequest.getCantidad() == 0) {
                estado = UsuarioAlbumLamina.EstadoLamina.FALTANTE;
            } else if (laminaRequest.getCantidad() == 1) {
                estado = UsuarioAlbumLamina.EstadoLamina.COLECCIONADA;
            } else {
                estado = UsuarioAlbumLamina.EstadoLamina.REPETIDA;
            }
            usuarioAlbumLamina.setEstado(estado);

            return usuarioAlbumLamina;
        }).map(usuarioAlbumLaminaRepository::save)
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public UsuarioAlbumLaminaResponse updateLaminaCantidad(Long id, int cantidad) {
        if (cantidad < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cantidad no puede ser menor a 0.");
        }

        UsuarioAlbumLamina usuarioAlbumLamina = usuarioAlbumLaminaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Registro no encontrado con el ID: " + id));

        usuarioAlbumLamina.setCantidad(cantidad);

        UsuarioAlbumLamina.EstadoLamina estado;
        if (cantidad == 0) {
            estado = UsuarioAlbumLamina.EstadoLamina.FALTANTE;
        } else if (cantidad == 1) {
            estado = UsuarioAlbumLamina.EstadoLamina.COLECCIONADA;
        } else {
            estado = UsuarioAlbumLamina.EstadoLamina.REPETIDA;
        }
        usuarioAlbumLamina.setEstado(estado);

        UsuarioAlbumLamina updatedEntity = usuarioAlbumLaminaRepository.save(usuarioAlbumLamina);
        return mapToResponse(updatedEntity);
    }

    private UsuarioAlbumLaminaResponse mapToResponse(UsuarioAlbumLamina entity) {
        UsuarioResponse usuarioResponse = new UsuarioResponse(
                entity.getUsuarioAlbum().getUsuario().getIdUsuario(),
                entity.getUsuarioAlbum().getUsuario().getNombreUsuario(),
                entity.getUsuarioAlbum().getUsuario().getCorreo());

        AlbumResponse albumResponse = new AlbumResponse(
                entity.getUsuarioAlbum().getAlbum().getIdAlbum(),
                entity.getUsuarioAlbum().getAlbum().getNombreAlbum(),
                entity.getUsuarioAlbum().getAlbum().getDescripcion(),
                entity.getUsuarioAlbum().getAlbum().getImagen());

        UsuarioAlbumResponse usuarioAlbumResponse = new UsuarioAlbumResponse(
                entity.getUsuarioAlbum().getIdUsuarioAlbum(),
                usuarioResponse,
                albumResponse);

        TipoCartaResponse tipoCartaResponse = new TipoCartaResponse(
                entity.getLamina().getTipoCarta().getIdTipo(),
                entity.getLamina().getTipoCarta().getNombreTipo());

        LaminaResponse laminaResponse = new LaminaResponse(
                entity.getLamina().getIdLamina(),
                entity.getLamina().getNombreLamina(),
                tipoCartaResponse,
                entity.getLamina().getImagen());

        String estado;
        if (entity.getCantidad() == 0) {
            estado = "FALTANTE";
        } else if (entity.getCantidad() == 1) {
            estado = "COLECCIONADA";
        } else {
            estado = "REPETIDA";
        }

        return new UsuarioAlbumLaminaResponse(
                entity.getIdUsuarioAlbumLamina(),
                usuarioAlbumResponse,
                laminaResponse,
                entity.getCantidad(),
                estado);
    }
}
