package ex_grupo11.ex_api_grupo11.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class UsuarioAlbumLamina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuarioAlbumLamina;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_album", nullable = false)
    private UsuarioAlbum usuarioAlbum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_lamina", nullable = false)
    private Lamina lamina;

    @Column(nullable = false)
    private Integer cantidad;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoLamina estado;

    public enum EstadoLamina {
        FALTANTE, COLECCIONADA, REPETIDA
    }
}
