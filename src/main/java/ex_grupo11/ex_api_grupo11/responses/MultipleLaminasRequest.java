package ex_grupo11.ex_api_grupo11.responses;

import java.util.List;

import lombok.Data;

@Data
public class MultipleLaminasRequest {
    private List<UsuarioAlbumLaminaRequest> laminas;
}
