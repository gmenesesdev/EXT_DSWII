package ex_grupo11.ex_api_grupo11.responses;

import lombok.Data;

@Data
public class UsuarioAlbumLaminasRequest {
    private Long idUsuarioAlbum;
    private Long idLamina;
    private Integer cantidad;
}
