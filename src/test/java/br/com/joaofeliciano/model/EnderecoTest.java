package br.com.joaofeliciano.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class EnderecoTest {

	private Endereco comCodigo(Long codigo) {
		Endereco endereco = new Endereco();
		endereco.setCodigo(codigo);
		return endereco;
	}

	@Test
	void enderecosComMesmoCodigoDevemSerIguais() {
		assertThat(comCodigo(1L))
				.isEqualTo(comCodigo(1L))
				.hasSameHashCodeAs(comCodigo(1L));
	}

	@Test
	void enderecosComCodigosDiferentesNaoDevemSerIguais() {
		assertThat(comCodigo(1L)).isNotEqualTo(comCodigo(2L));
	}

	@Test
	void enderecosComCodigoNuloNaoDevemSerIguaisAUmComCodigo() {
		assertThat(comCodigo(null)).isNotEqualTo(comCodigo(1L));
	}
}
