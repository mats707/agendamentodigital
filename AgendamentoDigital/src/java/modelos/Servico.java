package modelos;

import java.sql.Time;
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

    private Double valor;

    private Time duracao;

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

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Time getDuracao() {
        return duracao;
    }

    public void setDuracao(Time duracao) {
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

    public Servico(Integer idServico, String nome, String descricao, Double valor, Time duracao, CategoriaServico categoria, ArrayList<Funcionario> funcionarios, ArrayList<Integer> camposadicionais) {
        this.idServico = idServico;
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.duracao = duracao;
        this.categoria = categoria;
        this.funcionarios = funcionarios;
        this.camposadicionais = camposadicionais;
    }

    public Servico() {
    }

}
