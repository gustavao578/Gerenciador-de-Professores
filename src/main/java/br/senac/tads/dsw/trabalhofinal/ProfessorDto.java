package br.senac.tads.dsw.trabalhofinal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProfessorDto {
    private Integer id;

    @NotBlank(message = "O nome do professor é obrigatório")
    @Size(min = 5, max = 50, message = "O nome do professor deve conter entre 5 e 50 caracteres")
    private String nome;

    @NotBlank(message = "A matrícula do professor é obrigatória")
    @Size(min = 5, max = 50, message = "A matrícula do professor deve conter entre 5 e 20 caracteres")
    private String matricula;

    @NotBlank(message = "A disciplina do professor é obrigatória")
    @Size(min = 5, max = 50, message = "A disciplina do professor deve conter entre 5 e 100 caracteres")
    private String disciplina;

    private boolean ativo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    
}
