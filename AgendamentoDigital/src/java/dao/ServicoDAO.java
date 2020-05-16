package dao;

import dao.interfaces.IServicoDAO;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelos.Funcionario;
import modelos.CategoriaServico;
import modelos.Servico;
import util.ConectaBanco;

public class ServicoDAO implements IServicoDAO {

    private static final String CADASTRAR = "INSERT INTO sistema.servico (id, nome, descricao, valor, duracao, categoria, funcionarios, camposadicionais) \n"
            + "VALUES (nextval('sistema.sqn_servico'),?,?,?::NUMERIC::MONEY ,?,?,?,NULL);";
    private static final String CADASTRA_NOVO_SERVICO = "INSERT INTO sistema.servico (id, nome, descricao, valor, servicopai)\n"
            + "VALUES (nextval('sistema.sqn_servico'),?,?,?,?);";
    private static final String ALTERA_SERVICO = "UPDATE sistema.servico "
            + "SET nome = ?, "
            + "descricao = ?, "
            + "valor = ? "
            + "servicoPai = ? "
            + "WHERE id = ?";
    private static final String BUSCAR = "SELECT s.id, s.nome, s.descricao, s.valor, s.servicoPai FROM sistema.servico s WHERE s.nome=?";
    private static final String LISTAR = "SELECT id, nome, descricao, valor, duracao, categoria, funcionarios, camposadicionais FROM sistema.servico ORDER BY nome";
    private static final String DELETAR = "DELETE FROM sistema.servico WHERE id = ?";
    private static final String BUSCA_COMPLETA = "SELECT s.id, s.nome, s.descricao, s.valor, s.servicoPai FROM sistema.servico s WHERE s.id=? AND s.nome=? AND s.descricao=? AND s.valor=? AND S.servicoPai=?";

    private Connection conexao;

    public static String removeLastCharacter(String str) {
        String result = null;
        if ((str != null) && (str.length() > 0)) {
            result = str.substring(0, str.length() - 1);
        }
        return result;
    }

    @Override
    public String cadastrar(Servico servico) {

        conexao = ConectaBanco.getConexao();

        //'{"1","2"}'
        ArrayList<String> funcionarios = new ArrayList<String>();
        //conexao.createArrayOf("VARCHAR", new Object[]{"1", "2", "3"});

        for (Funcionario funcionario : servico.getFuncionarios()) {
            funcionarios.add(String.valueOf(funcionario.getIdFuncionario()));
        }
        Array arrayFuncionarios = null;

        String sqlReturnCode = "0";

        PreparedStatement pstmt = null;
        try {
            pstmt = conexao.prepareStatement(CADASTRAR, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, servico.getNome());
            pstmt.setString(2, servico.getDescricao());
            pstmt.setDouble(3, servico.getValor());
            pstmt.setTime(4, servico.getDuracao());
            pstmt.setInt(5, servico.getCategoria().getIdCategoriaServico());
            try {
                arrayFuncionarios = conexao.createArrayOf("INTEGER", funcionarios.toArray());
            } catch (SQLException ex) {
                Logger.getLogger(ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            pstmt.setArray(6, arrayFuncionarios);

            pstmt.execute();

            final ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                servico.setIdServico(Integer.parseInt(rs.getString("id")));
            }

            return sqlReturnCode;

        } catch (SQLException sqlErro) {

            sqlReturnCode = sqlErro.getSQLState();

            return pstmt.toString();

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

        ArrayList<Servico> listaServico = new ArrayList<>();
        ArrayList<Funcionario> listaFuncionario = new ArrayList<>();
        String[] funcionarios = null;
        Array arrayFuncionarios = null;
        ArrayList<Integer> listaCamposAdicionais = new ArrayList<>();
        String[] camposAdicionais = null;
        Array arrayCamposAdicionais = null;
        
        try {

            //Conexao
            conexao = ConectaBanco.getConexao();
            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(LISTAR);
            //executa
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Servico novoServico = new Servico();
                novoServico.setIdServico(rs.getInt("id"));
                novoServico.setNome(rs.getString("nome"));
                novoServico.setDescricao(rs.getString("descricao"));
                novoServico.setValor(Double.parseDouble(rs.getString("valor")));
                novoServico.setDuracao(Time.valueOf(rs.getString("duracao")));
                try {
                    arrayFuncionarios = conexao.createArrayOf("INTEGER", (Object[]) arrayFuncionarios.getArray());
                } catch (SQLException ex) {
                    Logger.getLogger(ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                arrayFuncionarios = rs.getArray("funcionarios");
                funcionarios = (String[])arrayFuncionarios.getArray();
                for (int i=0; i< funcionarios.length; i++) {
                     Funcionario novoFuncionario =  new Funcionario();
                     novoFuncionario.setIdFuncionario(Integer.parseInt(funcionarios[i]));
                     listaFuncionario.add(novoFuncionario);
                }
                novoServico.setFuncionarios(listaFuncionario);
                
                try {
                    arrayCamposAdicionais = conexao.createArrayOf("INTEGER", (Object[]) arrayCamposAdicionais.getArray());
                } catch (SQLException ex) {
                    Logger.getLogger(ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                arrayCamposAdicionais = rs.getArray("camposadicionais");
                camposAdicionais = (String[])arrayCamposAdicionais.getArray();
                for (int i=0; i< camposAdicionais.length; i++) {
                     Integer campoAdicional = null;
                     campoAdicional = Integer.parseInt(camposAdicionais[i]);
                     listaCamposAdicionais.add(campoAdicional);
                }
                novoServico.setCamposadicionais(listaCamposAdicionais);

                CategoriaServico objCategoria = new CategoriaServico();
                objCategoria.setIdCategoriaServico(rs.getInt("categoria"));

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
                servico.setIdServico(Integer.parseInt(rs.getString("id")));
                servico.setNome(rs.getString("nome"));
                servico.setDescricao(rs.getString("descricao"));
                servico.setValor(Double.parseDouble(rs.getString("valor")));
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

            servico.setIdServico(null);
            servico.setNome(null);
            servico.setDescricao(null);
            servico.setValor(null);

            //executa
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                servico.setIdServico(Integer.parseInt(rs.getString("id")));
                servico.setNome(rs.getString("nome"));
                servico.setDescricao(rs.getString("descricao"));
                servico.setValor(Double.parseDouble(rs.getString("valor")));
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
