package br.senac.tads.dsw.trabalhofinal.servico;

import java.util.List;
import java.util.Optional;

import br.senac.tads.dsw.trabalhofinal.ProfessorAlteracaoDto;
import br.senac.tads.dsw.trabalhofinal.ProfessorDto;

public interface ProfessorService {

    List<ProfessorDto> listarProfessores();

    Optional<ProfessorDto> obterProfessor(Integer id);

    ProfessorDto incluirNovoProfessor(ProfessorDto professor);

    ProfessorDto alterarProfessor(Long id, ProfessorAlteracaoDto alteracoes);

     void excluirProfessor(Long id);
}
