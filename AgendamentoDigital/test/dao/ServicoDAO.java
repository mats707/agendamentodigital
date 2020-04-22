package dao;

import dao.interfaces.IServicoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelos.Servico;
import util.ConectaBanco;

public class ServicoDAO implements IServicoDAO {

    private static final String CADASTRA_NOVA_CATEGORIA = "INSERT INTO sistema.servico (id, nome, descricao, valor)\n"
            + "VALUES (nextval('sistema.sqn_servico'),?,?,?);";
    private static final String CADASTRA_SUB_CATEGORIA = "INSERT INTO sistema.servico (id, nome, descricao, valor, servicopai)\n"
            + "VALUES (nextval('sistema.sqn_servico'),?,?,?,?);";
    private static final String CADASTRA_NOVO_SERVICO = "INSERT INTO sistema.servico (id, nome, descricao, valor, servicopai)\n"
            + "VALUES (nextval('sistema.sqn_servico'),?,?,?,?);";
    private static final String ALTERA_SERVICO = "UPDATE sistema.servico "
            + "SET nome = ?, "
            + "descricao = ?, "
            + "valor = ? "
            + "servicoPai = ? "
            + "WHERE id = ?";
    private static final String BUSCAR = "SELECT s.id, s.nome, s.descricao, s.valor, s.servicoPai FROM sistema.servico s WHERE s.nome=?";
    private static final String LISTAR = "SELECT s.id, s.nome, s.descricao, s.valor, s.servicoPai FROM sistema.servico s ORDER BY s.nome";
    private static final String DELETAR = "DELETE FROM sistema.servico WHERE id = ?";
    private static final String BUSCA_COMPLETA = "SELECT s.id, s.nome, s.descricao, s.valor, s.servicoPai FROM sistema.servico s WHERE s.id=? AND s.nome=? AND s.descricao=? AND s.valor=? AND S.servicoPai=?";

    private Connection conexao;

    @Override
    public String cadastraNovaCategoria(Servico servico) {

        String sqlReturnCode = "0";

        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CADASTRA_NOVA_CATEGORIA, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, servico.getNome());
            pstmt.setString(2, servico.getDescricao());
            pstmt.setDouble(3, servico.getValor());
            pstmt.execute();

            final ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                servico.setIdServico(rs.getInt("id"));
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
    public String cadastraNovoServico(Servico servico) {

        String sqlReturnCode = "0";

        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CADASTRA_NOVO_SERVICO, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, servico.getNome());
            pstmt.setString(2, servico.getDescricao());
            pstmt.setDouble(3, servico.getValor());
            if (servico.getServicoPai().getIdServico() == null) {
                pstmt.setString(4, null);
            } else {
                pstmt.setInt(4, servico.getServicoPai().getIdServico());
            }
            pstmt.execute();

            final ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                servico.setIdServico(rs.getInt("id"));
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
    public ArrayList<Servico> listar() {

        ArrayList<Servico> listaServico = new ArrayList<Servico>();

        try {

            //Conexao
            conexao = ConectaBanco.getConexao();
            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(LISTAR);
            //executa
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Servico novoServico = new Servico();
                novoServico.setNome(rs.getString("nome"));
                novoServico.setDescricao(rs.getString("descricao"));
                novoServico.setValor(Double.parseDouble(rs.getString("valor")));
                novoServico.getServicoPai().setIdServico(Integer.parseInt(rs.getString("servicoPai")));

                //add na lista
                listaServico.add(novoServico);
            }
            return listaServico;

        } catch (Exception ex) {

            return listaServico;

        } finally {

            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public void buscar(Servico servico) {

        try {

            //Conexao
            conexao = ConectaBanco.getConexao();

            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(BUSCAR);

            pstmt.setString(1, servico.getNome());

            servico.setNome(null);

            //executa
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Servico servicoPai = new Servico();

                servico.setIdServico(Integer.parseInt(rs.getString("id")));
                servico.setNome(rs.getString("nome"));
                servico.setDescricao(rs.getString("descricao"));
                servico.setValor(Double.parseDouble(rs.getString("valor")));
                servicoPai.setIdServico(Integer.parseInt(rs.getString("servicopai")));
                servico.setServicoPai(servicoPai);
            }

        } catch (Exception ex) {

        } finally {

            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public Servico buscaCompleta(Servico servico) {

        try {

            //Conexao
            conexao = ConectaBanco.getConexao();

            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(BUSCA_COMPLETA);

            pstmt.setInt(1, servico.getIdServico());
            pstmt.setString(2, servico.getNome());
            pstmt.setString(3, servico.getDescricao());
            pstmt.setDouble(4, servico.getValor());
            pstmt.setInt(5, servico.getServicoPai().getIdServico());

            servico.setIdServico(null);
            servico.setNome(null);
            servico.setDescricao(null);
            servico.setValor(0);

            //executa
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                servico.setIdServico(Integer.parseInt(rs.getString("id")));
                servico.setNome(rs.getString("nome"));
                servico.setDescricao(rs.getString("descricao"));
                servico.setValor(Double.parseDouble(rs.getString("valor")));
                servico.getServicoPai().setIdServico(Integer.parseInt(rs.getString("servicoPai")));
            }

            return servico;

        } catch (Exception ex) {

            return servico;

        } finally {

            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public String alterarServico(Servico servico) {

        String sqlReturnCode = "0";

        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(ALTERA_SERVICO, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, servico.getNome());
            pstmt.setString(2, servico.getDescricao());
            pstmt.setDouble(3, servico.getValor());
            pstmt.setInt(4, servico.getIdServico());
            pstmt.execute();

            final ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                servico.setIdServico(rs.getInt("id"));
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
    public boolean excluir(Servico servico) {

        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(DELETAR);
            pstmt.setInt(1, servico.getIdServico());
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
