package modelos;

import modelos.interfaces.ILogavel;

/**
 *
 * @author alunocmc
 *
 */
public class Usuario implements ILogavel {
    
    private Integer idUsuario;

    private String email;

    private String senha;
    
    private Long celular;

    private PerfilDeAcesso perfil;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public PerfilDeAcesso getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilDeAcesso perfil) {
        this.perfil = perfil;
    }

    public Long getCelular() {
        return celular;
    }

    public void setCelular(Long celular) {
        this.celular = celular;
    }

    public Usuario(Integer idUsuario, String email, String senha, Long celular, PerfilDeAcesso perfil) {
        this.idUsuario = idUsuario;
        this.email = email;
        this.senha = senha;
        this.celular = celular;
        this.perfil = perfil;
    }

    public Usuario() {
    }

    @Override
    public boolean logar(Usuario usuarioDAO) {
        //Verifica se o email encontrado na DAO é igual ao usuário instanciado
        if (usuarioDAO.getEmail().equals(this.email)
                && usuarioDAO.getSenha().equals(this.senha)) {

            return true;

        } else {

            return false;

        }
    }

}
