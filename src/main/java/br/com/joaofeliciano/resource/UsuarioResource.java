package br.com.joaofeliciano.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.joaofeliciano.model.Usuario;
import br.com.joaofeliciano.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/usuario")
public class UsuarioResource {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Operation(
			summary="Consultar usuarios",
			description="Essa operação lista todos os usuarios cadastrados.")
	@GetMapping
	public List<Usuario> listar() {
		return usuarioRepository.findAll();
	}
	
	@Operation(
			summary="Consultar usuario",
			description="Essa operação consulta usuario pelo codigo informado.")
	@GetMapping("/{codigo}")
	public ResponseEntity<Usuario> buscarByCodigo(@PathVariable Long codigo) {
		return usuarioRepository.findById(codigo)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
}
