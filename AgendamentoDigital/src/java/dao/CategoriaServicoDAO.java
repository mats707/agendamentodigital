package dao;

import dao.interfaces.ICategoriaServicoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelos.CategoriaServico;
import util.ConectaBanco;

public class CategoriaServicoDAO implements ICategoriaServicoDAO {

    private static final String CADASTRA_NOVA_CATEGORIA = "INSERT INTO sistema.CategoriaServico (id, nome, descricao, categoriapai)\n"
            + "VALUES (nextval('sistema.sqn_categoriaservico'),?,?,?);";
    private static final String CADASTRA_SUB_CATEGORIA = "INSERT INTO sistema.CategoriaServico (id, nome, descricao, categoriapai)\n"
            + "VALUES (nextval('sistema.sqn_categoriaservico'),?,?,?);";
    private static final String ALTERA_SERVICO = "UPDATE sistema.CategoriaServico "
            + "SET nome = ?, "
            + "descricao = ?, "
            + "categoriapai = ? "
            + "WHERE id = ?";
    private static final String BUSCAR = "SELECT s.id, s.nome, s.descricao, s.categoriapai FROM sistema.CategoriaServico s WHERE s.nome ilike ?";
    private static final String BUSCAR_CATEGORIA = "SELECT s.id, s.nome, s.descricao, s.categoriapai FROM sistema.CategoriaServico s WHERE s.id = ? AND s.categoriapai = ?";
    private static final String BUSCAR_NOME = "SELECT s.id, s.nome, s.descricao, s.categoriapai FROM sistema.CategoriaServico s WHERE UPPER(s.nome) = UPPER(?) AND s.categoriapai = ?";
    private static final String LISTAR = "SELECT s.id, s.nome, s.descricao, s.categoriapai FROM sistema.CategoriaServico s WHERE s.id != 0 ORDER BY s.categoriapai, s.nome";
    private static final String DELETAR = "DELETE FROM sistema.CategoriaServico WHERE id = ?";
    private static final String BUSCA_COMPLETA = "SELECT s.id, s.nome, s.descricao, s.categoriapai FROM sistema.CategoriaServico s WHERE s.id=? AND s.nome=? AND s.descricao=? AND S.categoriapai=?";

    private Connection conexao;

    @Override
    public String cadastraCategoria(CategoriaServico categoriaServico) {

        String sqlReturnCode = "0";

        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CADASTRA_NOVA_CATEGORIA, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, categoriaServico.getNome());
            pstmt.setString(2, categoriaServico.getDescricao());
            pstmt.setInt(3, categoriaServico.getCategoriaPai().getIdCategoriaServico());
            pstmt.execute();

            final ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                categoriaServico.setIdCategoriaServico(rs.getInt("id"));
            }

            return sqlReturnCode;

        } catch (SQLException sqlErro) {

            sqlReturnCode = sqlErro.getSQLState();

            return sqlReturnCode;

        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    @Override
    public CategoriaServico buscarNome(CategoriaServico categoriaServico) {

        CategoriaServico categoriaExistente = new CategoriaServico();

        try {

            //Conexao
            conexao = ConectaBanco.getConexao();

            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(BUSCAR_NOME);

            pstmt.setString(1, categoriaServico.getNome());
            pstmt.setInt(2, categoriaServico.getCategoriaPai().getIdCategoriaServico());
            //executa
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                CategoriaServico categoriaServicoPai = new CategoriaServico();

                categoriaExistente.setIdCategoriaServico(Integer.parseInt(rs.getString("id")));
                categoriaExistente.setNome(rs.getString("nome"));
                categoriaExistente.setDescricao(rs.getString("descricao"));

                categoriaServicoPai.setIdCategoriaServico(Integer.parseInt(rs.getString("categoriapai")));
                categoriaExistente.setCategoriaPai(categoriaServicoPai);
            }

            return categoriaExistente;

        } catch (Exception ex) {
            return categoriaExistente;
        } finally {

            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(CategoriaServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public String cadastraSubCategoria(CategoriaServico categoriaServico) {

        String sqlReturnCode = "0";

        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CADASTRA_SUB_CATEGORIA, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, categoriaServico.getNome());
            pstmt.setString(2, categoriaServico.getDescricao());
            pstmt.setInt(3, categoriaServico.getCategoriaPai().getIdCategoriaServico());
            pstmt.execute();

            final ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                categoriaServico.setIdCategoriaServico(rs.getInt("id"));
            }

            return sqlReturnCode;

        } catch (SQLException sqlErro) {

            sqlReturnCode = sqlErro.getSQLState();

            return sqlReturnCode;

        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    @Override
    public ArrayList<CategoriaServico> listar() {

        ArrayList<CategoriaServico> listaServico = new ArrayList<>();

        try {

            //Conexao
            conexao = ConectaBanco.getConexao();
            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(LISTAR);
            //executa
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                CategoriaServico novoServico = new CategoriaServico();
                novoServico.setIdCategoriaServico(Integer.parseInt(rs.getString("id")));
                novoServico.setNome(rs.getString("nome"));
                novoServico.setDescricao(rs.getString("descricao"));

                CategoriaServico categoriaPai = new CategoriaServico();
                categoriaPai.setIdCategoriaServico(Integer.parseInt(rs.getString("categoriapai")));

                novoServico.setCategoriaPai(categoriaPai);

                listaServico.add(novoServico);
            }
            return listaServico;

        } catch (Exception ex) {

            return listaServico;

        } finally {

            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(CategoriaServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public void buscar(CategoriaServico categoriaServico) {

        try {

            //Conexao
            conexao = ConectaBanco.getConexao();

            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(BUSCAR);

            pstmt.setString(1, categoriaServico.getNome());

            categoriaServico.setNome(null);

            //executa
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                CategoriaServico categoriaServicoPai = new CategoriaServico();

                categoriaServico.setIdCategoriaServico(Integer.parseInt(rs.getString("id")));
                categoriaServico.setNome(rs.getString("nome"));
                categoriaServico.setDescricao(rs.getString("descricao"));
                categoriaServicoPai.setIdCategoriaServico(Integer.parseInt(rs.getString("categoriapai")));
                categoriaServico.setCategoriaPai(categoriaServicoPai);
            }

        } catch (Exception ex) {

        } finally {

            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(CategoriaServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public void buscarCategoria(CategoriaServico categoriaServico) {

        try {

            //Conexao
            conexao = ConectaBanco.getConexao();

            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(BUSCAR_CATEGORIA);
            
            pstmt.setInt(1, categoriaServico.getIdCategoriaServico());
            pstmt.setInt(2, categoriaServico.getCategoriaPai().getIdCategoriaServico());
            
            //executa
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                CategoriaServico categoriaServicoPai = new CategoriaServico();

                categoriaServico.setIdCategoriaServico(Integer.parseInt(rs.getString("id")));
                categoriaServico.setNome(rs.getString("nome"));
                categoriaServico.setDescricao(rs.getString("descricao"));
                categoriaServicoPai.setIdCategoriaServico(Integer.parseInt(rs.getString("categoriapai")));
                categoriaServico.setCategoriaPai(categoriaServicoPai);
            }

        } catch (Exception ex) {

        } finally {

            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(CategoriaServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public CategoriaServico buscaCompleta(CategoriaServico categoriaServico) {

        try {

            //Conexao
            conexao = ConectaBanco.getConexao();

            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(BUSCA_COMPLETA);

            pstmt.setInt(1, categoriaServico.getIdCategoriaServico());
            pstmt.setString(2, categoriaServico.getNome());
            pstmt.setString(3, categoriaServico.getDescricao());
            pstmt.setInt(4, categoriaServico.getCategoriaPai().getIdCategoriaServico());

            categoriaServico.setIdCategoriaServico(null);
            categoriaServico.setNome(null);
            categoriaServico.setDescricao(null);
            categoriaServico.setCategoriaPai(null);

            //executa
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                categoriaServico.setIdCategoriaServico(Integer.parseInt(rs.getString("id")));
                categoriaServico.setNome(rs.getString("nome"));
                categoriaServico.setDescricao(rs.getString("descricao"));
                categoriaServico.getCategoriaPai().setIdCategoriaServico(Integer.parseInt(rs.getString("categoriapai")));
            }

            return categoriaServico;

        } catch (Exception ex) {

            return categoriaServico;

        } finally {

            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(CategoriaServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public String alterar(CategoriaServico categoriaServico) {

        String sqlReturnCode = "0";

        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(ALTERA_SERVICO, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, categoriaServico.getNome());
            pstmt.setString(2, categoriaServico.getDescricao());
            pstmt.setInt(3, categoriaServico.getCategoriaPai().getIdCategoriaServico());
            pstmt.setInt(4, categoriaServico.getIdCategoriaServico());

            pstmt.execute();

            final ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                categoriaServico.setIdCategoriaServico(rs.getInt("id"));
            }

            return sqlReturnCode;

        } catch (SQLException sqlErro) {

            sqlReturnCode = sqlErro.getSQLState();

            return sqlReturnCode;

        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    @Override
    public boolean excluir(CategoriaServico categoriaServico) {

        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(DELETAR);
            pstmt.setInt(1, categoriaServico.getIdCategoriaServico());
            pstmt.execute();

            return true;

        } catch (SQLException sqlErro) {

            return false;

        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
};
