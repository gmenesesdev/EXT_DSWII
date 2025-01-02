package ex_grupo11.ex_api_grupo11.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LaminaResponse {
    private Integer idLamina;
    private String nombreLamina;
    private TipoCartaResponse tipoCarta;
    private String imagen;
}
