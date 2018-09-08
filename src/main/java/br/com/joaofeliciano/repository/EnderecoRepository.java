package br.com.joaofeliciano.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.joaofeliciano.model.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

}
