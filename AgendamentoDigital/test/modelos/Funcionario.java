package modelos;

import java.util.Date;

public class Funcionario extends Pessoa {

    private Integer idCliente;

    private Usuario usuario;

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Funcionario(Integer idCliente, Usuario usuario, Integer idPessoa, String nome, Date dataNascimento) {
        super(idPessoa, nome, dataNascimento);
        this.idCliente = idCliente;
        this.usuario = usuario;
    }
    
    public Funcionario() {
    }

}
