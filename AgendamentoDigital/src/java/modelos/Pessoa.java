package modelos;

import java.util.Date;

public class Pessoa {

    private Integer idPessoa;

    private String nomePessoa;

    private Date dataNascimento;
    
    private Usuario usuario;

    public Integer getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Integer idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNome() {
        return nomePessoa;
    }

    public void setNome(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Pessoa(Integer idPessoa, String nomePessoa, Date dataNascimento, Usuario usuario){
        this.idPessoa = idPessoa;
        this.nomePessoa = nomePessoa;
        this.dataNascimento = dataNascimento;
        this.usuario = usuario;
    }

    public Pessoa() {
    }

}
