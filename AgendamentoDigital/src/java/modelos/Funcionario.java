package modelos;

import java.util.Date;

public class Funcionario extends Pessoa {

    private Integer idFuncionario;

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public Funcionario(Integer idFuncionario, Usuario usuario, Integer idPessoa, String nome, Date dataNascimento) {
        super(idPessoa, nome, dataNascimento, usuario);
        this.idFuncionario = idFuncionario;
    }

    public Funcionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }
    
    public Funcionario() {
    }

}
