package br.com.joaofeliciano.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.joaofeliciano.model.Endereco;
import br.com.joaofeliciano.repository.EnderecoRepository;

@ExtendWith(MockitoExtension.class)
class EnderecoServiceTest {

	@Mock
	private EnderecoRepository enderecoRepository;

	@InjectMocks
	private EnderecoService enderecoService;

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
	void atualizarDeveCopiarPropriedadesESalvarQuandoEnderecoExiste() {
		Endereco existente = novoEndereco("Rua Antiga");
		existente.setCodigo(1L);

		Endereco parametro = novoEndereco("Rua Nova");

		when(enderecoRepository.findById(1L)).thenReturn(Optional.of(existente));
		when(enderecoRepository.save(any(Endereco.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Endereco resultado = enderecoService.atualizar(1L, parametro);

		assertThat(resultado).isNotNull();
		// deve persistir a entidade existente (preservando o codigo), nao o parametro
		assertThat(resultado.getCodigo()).isEqualTo(1L);
		assertThat(resultado.getRua()).isEqualTo("Rua Nova");
		verify(enderecoRepository).save(existente);
	}

	@Test
	void atualizarDeveRetornarNullQuandoEnderecoNaoExiste() {
		Endereco parametro = novoEndereco("Rua Nova");

		when(enderecoRepository.findById(99L)).thenReturn(Optional.empty());

		Endereco resultado = enderecoService.atualizar(99L, parametro);

		assertThat(resultado).isNull();
		verify(enderecoRepository, never()).save(any(Endereco.class));
	}
}
