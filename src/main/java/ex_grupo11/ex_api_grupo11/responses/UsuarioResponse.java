package ex_grupo11.ex_api_grupo11.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponse {
    private Integer idUsuario;
    private String nombreUsuario;
    private String correo;
}
