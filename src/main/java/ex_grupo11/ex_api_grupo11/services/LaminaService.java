package ex_grupo11.ex_api_grupo11.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import ex_grupo11.ex_api_grupo11.models.Lamina;
import ex_grupo11.ex_api_grupo11.models.TipoCarta;
import ex_grupo11.ex_api_grupo11.repositories.LaminaRepository;
import ex_grupo11.ex_api_grupo11.repositories.TipoCartaRepository;
import ex_grupo11.ex_api_grupo11.responses.LaminaRequest;
import ex_grupo11.ex_api_grupo11.responses.LaminaResponse;
import ex_grupo11.ex_api_grupo11.responses.TipoCartaResponse;

@Service
public class LaminaService {

    @Autowired
    private LaminaRepository laminaRepository;

    @Autowired
    private TipoCartaRepository tipoCartaRepository;

    public List<LaminaResponse> getAllLaminas() {
        return laminaRepository.findAll().stream()
                .map(lam -> new LaminaResponse(
                        lam.getIdLamina(),
                        lam.getNombreLamina(),
                        new TipoCartaResponse(lam.getTipoCarta().getIdTipo(), lam.getTipoCarta().getNombreTipo()),
                        lam.getImagen()))
                .collect(Collectors.toList());
    }

    public LaminaResponse getLaminaByID(Long idLamina) {
        if (idLamina == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ID de la lámina no puede ser nulo.");
        }

        Lamina lamina = laminaRepository.findById(idLamina)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Lámina no encontrada con el ID: " + idLamina));

        return new LaminaResponse(
                lamina.getIdLamina(),
                lamina.getNombreLamina(),
                new TipoCartaResponse(lamina.getTipoCarta().getIdTipo(), lamina.getTipoCarta().getNombreTipo()),
                lamina.getImagen());
    }

    public LaminaResponse createLamina(LaminaRequest laminaRequest) {
        if (laminaRequest.getNombreLamina() == null || laminaRequest.getNombreLamina().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El nombre de la lámina no puede ser nulo o vacío.");
        }

        TipoCarta tipoCarta = tipoCartaRepository.findById(laminaRequest.getIdTipo())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Tipo de carta no encontrado con el ID: " + laminaRequest.getIdTipo()));

        Lamina lamina = new Lamina();
        lamina.setNombreLamina(laminaRequest.getNombreLamina());
        lamina.setTipoCarta(tipoCarta);
        lamina.setImagen(laminaRequest.getImagen());

        Lamina savedLamina = laminaRepository.save(lamina);

        return new LaminaResponse(
                savedLamina.getIdLamina(),
                savedLamina.getNombreLamina(),
                new TipoCartaResponse(savedLamina.getTipoCarta().getIdTipo(),
                        savedLamina.getTipoCarta().getNombreTipo()),
                savedLamina.getImagen());
    }

    public LaminaResponse updateLamina(Long idLamina, LaminaRequest laminaRequest) {
        if (idLamina == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ID de la lámina no puede ser nulo.");
        }
        if (laminaRequest.getNombreLamina() == null || laminaRequest.getNombreLamina().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El nombre de la lámina no puede ser nulo o vacío.");
        }

        Lamina lamina = laminaRepository.findById(idLamina)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Lámina no encontrada con el ID: " + idLamina));

        TipoCarta tipoCarta = tipoCartaRepository.findById(laminaRequest.getIdTipo())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Tipo de carta no encontrado con el ID: " + laminaRequest.getIdTipo()));

        lamina.setNombreLamina(laminaRequest.getNombreLamina());
        lamina.setTipoCarta(tipoCarta);
        lamina.setImagen(laminaRequest.getImagen());

        Lamina updatedLamina = laminaRepository.save(lamina);

        return new LaminaResponse(
                updatedLamina.getIdLamina(),
                updatedLamina.getNombreLamina(),
                new TipoCartaResponse(updatedLamina.getTipoCarta().getIdTipo(),
                        updatedLamina.getTipoCarta().getNombreTipo()),
                updatedLamina.getImagen());
    }

    public void deleteLamina(Long idLamina) {
        if (idLamina == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ID de la lámina no puede ser nulo.");
        }
        boolean exists = laminaRepository.existsById(idLamina);
        if (!exists) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La lámina no existe con el ID: " + idLamina);
        }
        laminaRepository.deleteById(idLamina);
    }
}
