package ex_grupo11.ex_api_grupo11.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ex_grupo11.ex_api_grupo11.responses.LaminaRequest;
import ex_grupo11.ex_api_grupo11.responses.LaminaResponse;
import ex_grupo11.ex_api_grupo11.services.LaminaService;

@RestController
@RequestMapping("/lamina")
public class LaminaController {

    @Autowired
    private LaminaService laminaService;

    @GetMapping
    public ResponseEntity<List<LaminaResponse>> getAllLaminas() {
        return ResponseEntity.ok(laminaService.getAllLaminas());
    }

    @GetMapping("/{idLamina}")
    public ResponseEntity<LaminaResponse> getLaminaByID(@PathVariable("idLamina") Long idLamina) {
        return ResponseEntity.ok(laminaService.getLaminaByID(idLamina));
    }

    @PostMapping
    public ResponseEntity<LaminaResponse> createLamina(@RequestBody LaminaRequest laminaRequest) {
        return ResponseEntity.status(201).body(laminaService.createLamina(laminaRequest));
    }

    @PutMapping("/{idLamina}")
    public ResponseEntity<LaminaResponse> updateLamina(@PathVariable("idLamina") Long idLamina,
            @RequestBody LaminaRequest laminaRequest) {
        return ResponseEntity.ok(laminaService.updateLamina(idLamina, laminaRequest));
    }

    @DeleteMapping("/{idLamina}")
    public ResponseEntity<String> deleteLamina(@PathVariable("idLamina") Long idLamina) {
        laminaService.deleteLamina(idLamina);
        return ResponseEntity.ok("Lámina eliminada correctamente con ID: " + idLamina);
    }
}
