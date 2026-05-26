package br.senac.tads.dsw.trabalhofinal.controle;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.RequestBody;

import br.senac.tads.dsw.trabalhofinal.ProfessorAlteracaoDto;
import br.senac.tads.dsw.trabalhofinal.ProfessorDto;
import br.senac.tads.dsw.trabalhofinal.servico.ProfessorService;
import br.senac.tads.dsw.trabalhofinal.NaoEncontradoException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

	private final ProfessorService professorService;

	public ProfessorController(ProfessorService professorService) {
		this.professorService = professorService;
	}

	@GetMapping
	public List<ProfessorDto> listarProfessores() {
		return professorService.listarProfessores();
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProfessorDto> buscarProfessorPorId(@PathVariable Integer id) {
		Optional<ProfessorDto> optProfessor = professorService.obterProfessor(id);

		if (optProfessor.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(optProfessor.get());
	}

	@PostMapping
	public ResponseEntity<?> incluirNovoProfessor(@RequestBody @Valid ProfessorDto professor) {
		ProfessorDto professorCriado = professorService.incluirNovoProfessor(professor);
		URI uri = ServletUriComponentsBuilder
			.fromCurrentRequest().path("/{id}")
			.buildAndExpand(professorCriado.getId()).toUri();

		return ResponseEntity.status(HttpStatus.CREATED)
			.location(uri).body(professorCriado);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> alterarProfessor(@PathVariable Long id, @RequestBody @Valid ProfessorAlteracaoDto alteracoes) {
		ProfessorDto professorAtualizado = professorService.alterarProfessor(id, alteracoes);
		return ResponseEntity.ok(professorAtualizado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluirProfessor(@PathVariable Long id) {
		professorService.excluirProfessor(id);
		return ResponseEntity.noContent().build();
	}

	@ExceptionHandler(NaoEncontradoException.class)
	public ResponseEntity<?> tratarNaoEncontradoException(NaoEncontradoException ex) {
		ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
		return ResponseEntity.of(pd).build();
	}

}
