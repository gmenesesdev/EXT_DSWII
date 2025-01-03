package ex_grupo11.ex_api_grupo11.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ex_grupo11.ex_api_grupo11.responses.MultipleLaminasRequest;
import ex_grupo11.ex_api_grupo11.responses.UsuarioAlbumLaminaResponse;
import ex_grupo11.ex_api_grupo11.responses.UsuarioAlbumLaminasRequest;
import ex_grupo11.ex_api_grupo11.services.UsuarioAlbumLaminaService;

@RestController
@RequestMapping("/usuario-album-lamina")
public class UsuarioAlbumLaminaController {

    @Autowired
    private UsuarioAlbumLaminaService usuarioAlbumLaminaService;

    @GetMapping
    public ResponseEntity<List<UsuarioAlbumLaminaResponse>> getAllUsuarioAlbumLaminas() {
        return ResponseEntity.ok(usuarioAlbumLaminaService.getAllUsuarioAlbumLaminas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioAlbumLaminaResponse> getUsuarioAlbumLaminaById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioAlbumLaminaService.getUsuarioAlbumLaminaById(id));
    }

    @PostMapping
    public ResponseEntity<UsuarioAlbumLaminaResponse> createUsuarioAlbumLamina(
            @RequestBody UsuarioAlbumLaminasRequest request) {
        return ResponseEntity.status(201).body(usuarioAlbumLaminaService.createUsuarioAlbumLamina(request));
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<UsuarioAlbumLaminaResponse>> createMultipleLaminas(
            @RequestBody MultipleLaminasRequest request) {
        return ResponseEntity.status(201).body(usuarioAlbumLaminaService.createMultipleLaminas(request));
    }

    @PutMapping("/{id}/cantidad")
    public ResponseEntity<UsuarioAlbumLaminaResponse> updateLaminaCantidad(@PathVariable Long id,
            @RequestParam int cantidad) {
        return ResponseEntity.ok(usuarioAlbumLaminaService.updateLaminaCantidad(id, cantidad));
    }
}