package ex_grupo11.ex_api_grupo11.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioAlbumLaminaResponse {
    private Integer idUsuarioAlbumLamina;
    private UsuarioAlbumResponse usuarioAlbum;
    private LaminaResponse lamina;
    private Integer cantidad;
    private String estado; // Usar enums si es necesario
}
