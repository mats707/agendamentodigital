package modelos;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;

/**
 *
 * @author alunocmc
 *
 */
public class Servico {

    private Integer idServico;

    private String nome;

    private String descricao;

    private BigDecimal valor;

    private Duration duracao;

    private CategoriaServico categoria;
    
    private ArrayList<Funcionario> funcionarios;

    private ArrayList<Integer> camposadicionais;

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

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Duration getDuracao() {
        return duracao;
    }

    public void setDuracao(Duration duracao) {
        this.duracao = duracao;
    }

    public CategoriaServico getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaServico categoria) {
        this.categoria = categoria;
    }

    public ArrayList<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(ArrayList<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public ArrayList<Integer> getCamposadicionais() {
        return camposadicionais;
    }

    public void setCamposadicionais(ArrayList<Integer> camposadicionais) {
        this.camposadicionais = camposadicionais;
    }

    public Servico(Integer idServico, String nome, String descricao, BigDecimal valor, Duration duracao, CategoriaServico categoria, ArrayList<Funcionario> funcionarios, ArrayList<Integer> camposadicionais) {
        this.idServico = idServico;
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.duracao = duracao;
        this.categoria = categoria;
        this.funcionarios = funcionarios;
        this.camposadicionais = camposadicionais;
    }

    public Servico(Integer idServico) {
        this.idServico = idServico;
    }

    public Servico() {
    }

}
