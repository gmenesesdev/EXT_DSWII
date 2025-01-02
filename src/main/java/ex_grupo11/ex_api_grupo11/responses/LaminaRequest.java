package ex_grupo11.ex_api_grupo11.responses;

import lombok.Data;

@Data
public class LaminaRequest {
    private String nombreLamina;
    private Long idTipo;
    private String imagen;
}
