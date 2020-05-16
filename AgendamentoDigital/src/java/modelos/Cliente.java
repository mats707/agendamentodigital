package modelos;

import java.util.Date;

public class Cliente extends Pessoa {

    private Integer idCliente;

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Cliente(Integer idCliente, Usuario usuario, Integer idPessoa, String nome, Date dataNascimento) {
        super(idPessoa, nome, dataNascimento, usuario);
        this.idCliente = idCliente;
    }
    
    public Cliente() {
    }

}
