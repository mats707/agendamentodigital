package modelos;

/**
 *
 * @author alunocmc
 *
 */
public class Servico {
    
    private Integer idServico;

    private String nome;
    
    private String descricao;
    
    private double valor;
    
    private Servico servicoPai;

    public Integer getIdServico() {
        return idServico;
    }

    public void setIdServico(Integer idServico) {
        this.idServico = idServico;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Servico getServicoPai() {
        return servicoPai;
    }

    public void setServicoPai(Servico servicoPai) {
        this.servicoPai = servicoPai;
    }

    public Servico(Integer idServico, String nome, String descricao, double valor, Servico servicoPai) {
        this.idServico = idServico;
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.servicoPai = servicoPai;
    }
    
    public Servico() {
    }
    
    

}
