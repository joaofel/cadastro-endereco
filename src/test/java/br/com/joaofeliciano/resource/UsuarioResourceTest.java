package br.com.joaofeliciano.resource;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import br.com.joaofeliciano.model.Usuario;
import br.com.joaofeliciano.repository.UsuarioRepository;

@WebMvcTest(UsuarioResource.class)
class UsuarioResourceTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private UsuarioRepository usuarioRepository;

	private Usuario usuarioComCodigo(Long codigo) {
		Usuario usuario = new Usuario();
		usuario.setCodigo(codigo);
		usuario.setNome("Joao");
		usuario.setEmail("joao@teste.com");
		usuario.setSenha("segredo");
		return usuario;
	}

	@Test
	void listarDeveRetornarUsuarios() throws Exception {
		when(usuarioRepository.findAll()).thenReturn(List.of(usuarioComCodigo(1L)));

		mockMvc.perform(get("/usuario"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].codigo").value(1))
				.andExpect(jsonPath("$[0].nome").value("Joao"));
	}

	@Test
	void buscarByCodigoDeveRetornarOkQuandoExiste() throws Exception {
		when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioComCodigo(1L)));

		mockMvc.perform(get("/usuario/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.email").value("joao@teste.com"));
	}

	@Test
	void buscarByCodigoDeveRetornarNotFoundQuandoNaoExiste() throws Exception {
		when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

		mockMvc.perform(get("/usuario/99"))
				.andExpect(status().isNotFound());
	}
}
