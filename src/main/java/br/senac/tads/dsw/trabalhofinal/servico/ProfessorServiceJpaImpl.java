package br.senac.tads.dsw.trabalhofinal.servico;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.senac.tads.dsw.trabalhofinal.NaoEncontradoException;
import br.senac.tads.dsw.trabalhofinal.ProfessorAlteracaoDto;
import br.senac.tads.dsw.trabalhofinal.ProfessorDto;
import br.senac.tads.dsw.trabalhofinal.entidade.ProfessorEntity;
import br.senac.tads.dsw.trabalhofinal.repositorio.ProfessorRepository;

@Service
public class ProfessorServiceJpaImpl implements ProfessorService {

    private final ProfessorRepository professorRepository;

    public ProfessorServiceJpaImpl(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public List<ProfessorDto> listarProfessores() {
        List<ProfessorDto> resultado = new ArrayList<>();
        for(ProfessorEntity entity : professorRepository.findAll()){
            ProfessorDto dto = toDto(entity);
            resultado.add(dto);
        }
        return resultado;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProfessorDto> obterProfessor(Integer id) {
        Optional<ProfessorEntity> optEntity = professorRepository.findById(id.longValue());
        if (optEntity.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(toDto(optEntity.get()));
    }

    @Override
    @Transactional
    public ProfessorDto incluirNovoProfessor(ProfessorDto dto) {
        ProfessorEntity entity = toEntity(dto);
        ProfessorEntity salva = professorRepository.save(entity);
        return toDto(salva);
    }

    @Override
    @Transactional
    public ProfessorDto alterarProfessor(Long id, ProfessorAlteracaoDto professorAlteracao) {
        Optional<ProfessorEntity> optEntity = professorRepository.findById(id);
        if (optEntity.isEmpty()) {
            throw new NaoEncontradoException("Professor" + professorAlteracao.getNome() + "não encontrado");
        }
        ProfessorEntity entity = optEntity.get();

        entity.setNome(professorAlteracao.getNome());
        entity.setMatricula(professorAlteracao.getMatricula());
		entity.setObservacoes(professorAlteracao.getObservacoes());
        entity.setDisciplina(professorAlteracao.getDisciplina());
        entity.setAtivo(professorAlteracao.isAtivo());

        ProfessorEntity salva = professorRepository.save(entity);
        return toDto(salva);
    }

    @Override
    @Transactional
    public void excluirProfessor(Long id) {
        Optional<ProfessorEntity> optEntity = professorRepository.findById(id);
        if (optEntity.isEmpty()) {
            throw new NaoEncontradoException("Professor não encontrado");
        }
        ProfessorEntity entity = optEntity.get();
        professorRepository.delete(entity);
    }

    private ProfessorDto toDto(ProfessorEntity entity) {
        ProfessorDto dto = new ProfessorDto();
        dto.setId(entity.getId().intValue());
        dto.setNome(entity.getNome());
        dto.setMatricula(entity.getMatricula());
		dto.setObservacoes(entity.getObservacoes());
        dto.setDisciplina(entity.getDisciplina());
        dto.setAtivo(entity.isAtivo());

        return dto;
    }

    private ProfessorEntity toEntity(ProfessorDto dto) {
        ProfessorEntity entity = new ProfessorEntity();
        entity.setNome(dto.getNome());
        entity.setMatricula(dto.getMatricula());
		entity.setObservacoes(dto.getObservacoes());
        entity.setDisciplina(dto.getDisciplina());
        entity.setAtivo(dto.isAtivo());

        return entity;
    }
}

