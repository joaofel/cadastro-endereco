package br.com.joaofeliciano.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import br.com.joaofeliciano.model.Endereco;
import br.com.joaofeliciano.repository.EnderecoRepository;

@SpringBootTest
@Transactional
class EnderecoServiceIntegrationTest {

	@Autowired
	private EnderecoService enderecoService;

	@Autowired
	private EnderecoRepository enderecoRepository;

	private Endereco novoEndereco(String rua) {
		Endereco endereco = new Endereco();
		endereco.setRua(rua);
		endereco.setNumero("10");
		endereco.setBairro("Centro");
		endereco.setCep("12345-678");
		endereco.setCidade("Sao Paulo");
		endereco.setEstado("SP");
		return endereco;
	}

	@Test
	void atualizarDeveModificarRegistroExistenteSemCriarDuplicata() {
		Endereco salvo = enderecoRepository.save(novoEndereco("Rua Antiga"));
		Long codigo = salvo.getCodigo();

		Endereco resultado = enderecoService.atualizar(codigo, novoEndereco("Rua Nova"));

		assertThat(resultado.getCodigo()).isEqualTo(codigo);
		assertThat(resultado.getRua()).isEqualTo("Rua Nova");
		// nao deve inserir um novo registro: continua havendo apenas um
		assertThat(enderecoRepository.count()).isEqualTo(1);
		assertThat(enderecoRepository.findById(codigo)).get()
				.extracting(Endereco::getRua).isEqualTo("Rua Nova");
	}

	@Test
	void atualizarDeveRetornarNullQuandoCodigoInexistente() {
		Endereco resultado = enderecoService.atualizar(999L, novoEndereco("Rua X"));

		assertThat(resultado).isNull();
	}
}
