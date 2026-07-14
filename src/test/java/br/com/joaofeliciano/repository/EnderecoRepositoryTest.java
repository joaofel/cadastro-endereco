package br.com.joaofeliciano.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import br.com.joaofeliciano.model.Endereco;

@DataJpaTest
class EnderecoRepositoryTest {

	@Autowired
	private EnderecoRepository enderecoRepository;

	private Endereco novoEndereco() {
		Endereco endereco = new Endereco();
		endereco.setRua("Rua A");
		endereco.setNumero("10");
		endereco.setBairro("Centro");
		endereco.setCep("12345-678");
		endereco.setCidade("Sao Paulo");
		endereco.setEstado("SP");
		return endereco;
	}

	@Test
	void deveSalvarEGerarCodigo() {
		Endereco salvo = enderecoRepository.save(novoEndereco());

		assertThat(salvo.getCodigo()).isNotNull();
	}

	@Test
	void deveBuscarPorCodigo() {
		Endereco salvo = enderecoRepository.save(novoEndereco());

		Optional<Endereco> encontrado = enderecoRepository.findById(salvo.getCodigo());

		assertThat(encontrado).isPresent();
		assertThat(encontrado.get().getRua()).isEqualTo("Rua A");
	}

	@Test
	void deveListarTodos() {
		enderecoRepository.save(novoEndereco());
		enderecoRepository.save(novoEndereco());

		assertThat(enderecoRepository.findAll()).hasSize(2);
	}

	@Test
	void deveRemoverPorCodigo() {
		Endereco salvo = enderecoRepository.save(novoEndereco());

		enderecoRepository.deleteById(salvo.getCodigo());

		assertThat(enderecoRepository.findById(salvo.getCodigo())).isEmpty();
	}
}
