package br.com.joaofeliciano.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.joaofeliciano.event.RecursoCriadoEvent;
import br.com.joaofeliciano.model.Endereco;
import br.com.joaofeliciano.repository.EnderecoRepository;
import br.com.joaofeliciano.service.EnderecoService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/endereco")
public class EnderecoResource {
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private EnderecoService enderecoService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@ApiOperation(
			value="Consultar enderecos", 
			notes="Essa operação lista todos os enderecos cadastrados.")
	@GetMapping
	public List<Endereco> listar() {
		return enderecoRepository.findAll();
	}

	@ApiOperation(
			value="Consultar endereco", 
			notes="Essa operação consulta endereco pelo codigo informado.")
	@GetMapping("/{codigo}")
	public ResponseEntity<Endereco> buscarByCodigo(@PathVariable Long codigo) {
		Optional<Endereco> endereco = enderecoRepository.findById(codigo);
		return endereco.isPresent() ? ResponseEntity.ok(endereco.get()) : ResponseEntity.notFound().build();
	}
	
	@ApiOperation(
			value="Cadastrar endereco", 
			notes="Essa operação cadastra enderecos.")
	@PostMapping
	public ResponseEntity<Endereco> criar(@Valid @RequestBody Endereco enderecoParametro, 
			HttpServletResponse httpServletResponse) {
		
		if(enderecoParametro.getCodigo() == null || enderecoParametro.getCodigo() == 0) {
			
			Endereco endereco = enderecoRepository.save(enderecoParametro);
			publisher.publishEvent(new RecursoCriadoEvent(this, httpServletResponse, 
					endereco.getCodigo().toString()));
			
			return ResponseEntity.status(HttpStatus.CREATED).body(endereco);
		} 
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@ApiOperation(
			value="Excluir endereco", 
			notes="Essa operação exclui enderecos.")
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity remover(@PathVariable Long codigo) {
		
		Optional<Endereco> endereco = enderecoRepository.findById(codigo);
		
		if(!endereco.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		enderecoRepository.deleteById(codigo);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@ApiOperation(
			value="Atualizar endereco", 
			notes="Essa operação atualiza enderecos.")
	@PutMapping("/{codigo}")
	public ResponseEntity<Endereco> atualizar(@PathVariable Long codigo, @Valid @RequestBody Endereco enderecoParametro) {
		try {
			Endereco enderecoSalvo = enderecoService.atualizar(codigo, enderecoParametro);
			
			if(enderecoSalvo == null) {
				return ResponseEntity.notFound().build();
			}
			
			return ResponseEntity.ok(enderecoSalvo);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
