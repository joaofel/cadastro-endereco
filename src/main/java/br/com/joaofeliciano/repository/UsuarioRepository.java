package br.com.joaofeliciano.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.joaofeliciano.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
