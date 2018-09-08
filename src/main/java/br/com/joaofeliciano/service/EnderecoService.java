package br.com.joaofeliciano.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.joaofeliciano.model.Endereco;
import br.com.joaofeliciano.repository.EnderecoRepository;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Endereco atualizar(Long codigo, Endereco enderecoParametro) {
		
		Optional<Endereco> enderecoSalvo = enderecoRepository.findById(codigo);
		
		if (enderecoSalvo.isPresent()) {
			BeanUtils.copyProperties(enderecoParametro, enderecoSalvo.get(), "codigo");
			return enderecoRepository.save(enderecoParametro);
		}
	
		return null;
	}
}
