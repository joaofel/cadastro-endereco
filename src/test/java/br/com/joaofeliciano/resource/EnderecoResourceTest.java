package br.com.joaofeliciano.resource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import br.com.joaofeliciano.model.Endereco;
import br.com.joaofeliciano.repository.EnderecoRepository;
import br.com.joaofeliciano.service.EnderecoService;

@WebMvcTest(EnderecoResource.class)
class EnderecoResourceTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private EnderecoRepository enderecoRepository;

	@MockitoBean
	private EnderecoService enderecoService;

	private static final String JSON_VALIDO = """
			{
			  "rua": "Rua A",
			  "numero": "10",
			  "complemento": "Apto 1",
			  "bairro": "Centro",
			  "cep": "12345-678",
			  "cidade": "Sao Paulo",
			  "estado": "SP"
			}
			""";

	private Endereco enderecoComCodigo(Long codigo) {
		Endereco endereco = new Endereco();
		endereco.setCodigo(codigo);
		endereco.setRua("Rua A");
		endereco.setNumero("10");
		endereco.setBairro("Centro");
		endereco.setCep("12345-678");
		endereco.setCidade("Sao Paulo");
		endereco.setEstado("SP");
		return endereco;
	}

	@Test
	void listarDeveRetornarEnderecos() throws Exception {
		when(enderecoRepository.findAll()).thenReturn(List.of(enderecoComCodigo(1L)));

		mockMvc.perform(get("/endereco"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].codigo").value(1))
				.andExpect(jsonPath("$[0].rua").value("Rua A"));
	}

	@Test
	void buscarByCodigoDeveRetornarOkQuandoExiste() throws Exception {
		when(enderecoRepository.findById(1L)).thenReturn(Optional.of(enderecoComCodigo(1L)));

		mockMvc.perform(get("/endereco/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.codigo").value(1));
	}

	@Test
	void buscarByCodigoDeveRetornarNotFoundQuandoNaoExiste() throws Exception {
		when(enderecoRepository.findById(99L)).thenReturn(Optional.empty());

		mockMvc.perform(get("/endereco/99"))
				.andExpect(status().isNotFound());
	}

	@Test
	void criarDeveRetornarCreatedQuandoValido() throws Exception {
		when(enderecoRepository.save(any(Endereco.class))).thenReturn(enderecoComCodigo(1L));

		mockMvc.perform(post("/endereco")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON_VALIDO))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.codigo").value(1));

		verify(enderecoRepository).save(any(Endereco.class));
	}

	@Test
	void criarDeveRetornarBadRequestQuandoCodigoJaInformado() throws Exception {
		String jsonComCodigo = """
				{
				  "codigo": 5,
				  "rua": "Rua A",
				  "numero": "10",
				  "cep": "12345-678",
				  "cidade": "Sao Paulo",
				  "estado": "SP"
				}
				""";

		mockMvc.perform(post("/endereco")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonComCodigo))
				.andExpect(status().isBadRequest());
	}

	@Test
	void criarDeveRetornarBadRequestQuandoCamposObrigatoriosAusentes() throws Exception {
		String jsonInvalido = """
				{
				  "numero": "10"
				}
				""";

		mockMvc.perform(post("/endereco")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonInvalido))
				.andExpect(status().isBadRequest());
	}

	@Test
	void removerDeveRetornarNoContentQuandoExiste() throws Exception {
		when(enderecoRepository.findById(1L)).thenReturn(Optional.of(enderecoComCodigo(1L)));

		mockMvc.perform(delete("/endereco/1"))
				.andExpect(status().isNoContent());

		verify(enderecoRepository).deleteById(1L);
	}

	@Test
	void removerDeveRetornarBadRequestQuandoNaoExiste() throws Exception {
		when(enderecoRepository.findById(99L)).thenReturn(Optional.empty());

		mockMvc.perform(delete("/endereco/99"))
				.andExpect(status().isBadRequest());
	}

	@Test
	void atualizarDeveRetornarOkQuandoAtualizado() throws Exception {
		when(enderecoService.atualizar(eq(1L), any(Endereco.class))).thenReturn(enderecoComCodigo(1L));

		mockMvc.perform(put("/endereco/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON_VALIDO))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.codigo").value(1));
	}

	@Test
	void atualizarDeveRetornarNotFoundQuandoServicoRetornaNull() throws Exception {
		when(enderecoService.atualizar(eq(99L), any(Endereco.class))).thenReturn(null);

		mockMvc.perform(put("/endereco/99")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON_VALIDO))
				.andExpect(status().isNotFound());
	}

	@Test
	void atualizarDeveRetornarNotFoundQuandoServicoLancaIllegalArgument() throws Exception {
		when(enderecoService.atualizar(eq(1L), any(Endereco.class)))
				.thenThrow(new IllegalArgumentException("nao encontrado"));

		mockMvc.perform(put("/endereco/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON_VALIDO))
				.andExpect(status().isNotFound());
	}
}
