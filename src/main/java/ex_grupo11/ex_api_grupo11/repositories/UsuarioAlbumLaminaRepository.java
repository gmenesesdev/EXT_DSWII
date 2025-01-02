package ex_grupo11.ex_api_grupo11.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ex_grupo11.ex_api_grupo11.models.UsuarioAlbumLamina;

@Repository
public interface UsuarioAlbumLaminaRepository extends JpaRepository<UsuarioAlbumLamina, Long> {

}
