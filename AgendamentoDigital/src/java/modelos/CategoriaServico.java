package modelos;

/**
 *
 * @author alunocmc
 *
 */
public class CategoriaServico {

    private Integer idCategoriaServico;

    private String nome;

    private String descricao;

    private CategoriaServico categoriaPai;

    private Boolean ativo;

    public Integer getIdCategoriaServico() {
        return idCategoriaServico;
    }

    public void setIdCategoriaServico(Integer idCategoriaServico) {
        this.idCategoriaServico = idCategoriaServico;
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

    public CategoriaServico getCategoriaPai() {
        return categoriaPai;
    }

    public void setCategoriaPai(CategoriaServico categoriaPai) {
        this.categoriaPai = categoriaPai;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public CategoriaServico(Integer idCategoriaServico, String nome, String descricao, CategoriaServico categoriaPai, Boolean ativo) {
        this.idCategoriaServico = idCategoriaServico;
        this.nome = nome;
        this.descricao = descricao;
        this.categoriaPai = categoriaPai;
        this.ativo = ativo;
    }

    public CategoriaServico(Integer editedCategoriaPai) {
        this.idCategoriaServico = editedCategoriaPai;
    }

    public CategoriaServico() {
    }

}
