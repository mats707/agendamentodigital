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

    public CategoriaServico(Integer idCategoriaServico, String nome, String descricao, CategoriaServico categoriaPai) {
        this.idCategoriaServico = idCategoriaServico;
        this.nome = nome;
        this.descricao = descricao;
        this.categoriaPai = categoriaPai;
    }

    public CategoriaServico() {
    }

}
