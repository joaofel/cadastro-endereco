package br.com.joaofeliciano.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.joaofeliciano.model.Usuario;
import br.com.joaofeliciano.repository.UsuarioRepository;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/usuario")
public class UsuarioResource {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@ApiOperation(
			value="Consultar usuarios", 
			notes="Essa operação lista todos os usuarios cadastrados.")
	@GetMapping
	public List<Usuario> listar() {
		return usuarioRepository.findAll();
	}
	
	@ApiOperation(
			value="Consultar usuario", 
			notes="Essa operação consulta usuario pelo codigo informado.")
	@GetMapping("/{codigo}")
	public ResponseEntity<Usuario> buscarByCodigo(@PathVariable Long codigo) {
		Optional<Usuario> usuario = usuarioRepository.findById(codigo);
		return usuario.isPresent() ? ResponseEntity.ok(usuario.get()) : ResponseEntity.notFound().build();
	}
}
