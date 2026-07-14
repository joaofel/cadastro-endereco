package br.com.joaofeliciano.event.listener;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import br.com.joaofeliciano.event.RecursoCriadoEvent;

class RecursoCriadoListenerTest {

	private final RecursoCriadoListener listener = new RecursoCriadoListener();

	@AfterEach
	void limparContexto() {
		RequestContextHolder.resetRequestAttributes();
	}

	@Test
	void deveAdicionarHeaderLocationComCodigo() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRequestURI("/endereco");
		request.setServerName("localhost");
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		MockHttpServletResponse response = new MockHttpServletResponse();
		RecursoCriadoEvent evento = new RecursoCriadoEvent(this, response, "1");

		listener.onApplicationEvent(evento);

		assertThat(response.getHeader("Location")).endsWith("/endereco/1");
	}
}
