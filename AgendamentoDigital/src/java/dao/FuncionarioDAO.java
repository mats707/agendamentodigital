package dao;

import dao.interfaces.IFuncionarioDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelos.Funcionario;
import modelos.PerfilDeAcesso;
import modelos.Pessoa;
import modelos.Usuario;
import util.ConectaBanco;

public class FuncionarioDAO implements IFuncionarioDAO {

    private static final String LISTAR = "SELECT id, pessoa FROM sistema.funcionario ORDER BY id;";
    private static final String LISTAR_PESSOA = "SELECT f.id as idFunc, p.id as idPessoa, p.nome, p.dataNascimento, u.id as idUsuario, u.email, u.celular, pf.nome as perfil FROM sistema.funcionario f INNER JOIN sistema.pessoa p ON f.pessoa = p.id INNER JOIN sistema.usuario u ON p.usuario = u.id INNER JOIN sistema.perfilacesso pf ON u.perfil = pf.id ORDER BY f.id;";
    private static final String BUSCAR_COMPLETO_ID = "SELECT f.id as idFunc, p.id as idPessoa, p.nome, p.dataNascimento, u.id as idUsuario, u.email, u.celular, pf.nome as perfil FROM sistema.funcionario f INNER JOIN sistema.pessoa p ON f.pessoa = p.id INNER JOIN sistema.usuario u ON p.usuario = u.id INNER JOIN sistema.perfilacesso pf ON u.perfil = pf.id WHERE f.id = ? ORDER BY f.id;";
    private static final String BUSCAR_ID = "SELECT f.id as idFunc, p.id as idPessoa, p.nome, p.dataNascimento FROM sistema.funcionario f INNER JOIN sistema.pessoa p ON f.pessoa = p.id WHERE f.id = ? ORDER BY f.id;";
    private static final String BUSCAR = "SELECT * FROM sistema.funcionario WHERE nome ilike ?;";
    private static final String INSERT =
            "INSERT INTO sistema.funcionario (id, nome, dataNascimento, endereco, sexo, estadoCivil, qtdFilhos, profissao, escolaridade)\n"
            + "  VALUES (NEXTVAL('sqn_funcionario'),\n"
            + "		?,\n"
            + "		?,\n"
            + "		(SELECT id FROM endereco WHERE id = ?),\n"
            + "		(SELECT id FROM sexo WHERE id = ?),\n"
            + "		(SELECT id FROM estadoCivil WHERE id = ?),\n"
            + "		?,\n"
            + "		(SELECT id FROM profissao WHERE id = ?),\n"
            + "		(SELECT id FROM escolaridade WHERE id = ?)\n"
            + "  );";
    private static final String DELETE = "DELETE FROM funcionario WHERE id=?;";
    private static final String UPDATE = "UPDATE funcionario SET nome=? WHERE id=?;";

    private Connection conexao;

    @Override
    public ArrayList<Funcionario> listar() {

        //cria uma array de Obj Tipo
        ArrayList<Funcionario> listaFuncionario = new ArrayList<Funcionario>();

        try {

            //Conexao
            conexao = ConectaBanco.getConexao();

            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(LISTAR);

            //executa
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                //a cada loop
                Funcionario novoFuncionario = new Funcionario();
                novoFuncionario.setIdFuncionario(Integer.parseInt(rs.getString("id")));
                novoFuncionario.setIdPessoa(Integer.parseInt(rs.getString("pessoa")));

                //add na lista
                listaFuncionario.add(novoFuncionario);
            }
            return listaFuncionario;

        } catch (Exception ex) {

            return listaFuncionario;

        } finally {

            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public ArrayList<Funcionario> listarCompleto() {

        //cria uma array de Obj Tipo
        ArrayList<Funcionario> listaFuncionario = new ArrayList<Funcionario>();

        try {

            //Conexao
            conexao = ConectaBanco.getConexao();

            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(LISTAR_PESSOA);

            //executa
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                //a cada loop
                Funcionario novoFuncionario = new Funcionario();
                novoFuncionario.setIdFuncionario(Integer.parseInt(rs.getString("idFunc")));
                novoFuncionario.setIdPessoa(Integer.parseInt(rs.getString("idPessoa")));
                novoFuncionario.setNome(rs.getString("nome"));
                novoFuncionario.setDataNascimento(rs.getDate("dataNascimento"));
                
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(Integer.parseInt(rs.getString("idUsuario")));
                usuario.setEmail(rs.getString("email"));
                usuario.setCelular(Long.parseLong(rs.getString("celular")));
                usuario.setPerfil(PerfilDeAcesso.valueOf(rs.getString("perfil")));
                
                novoFuncionario.setUsuario(usuario);

                //add na lista
                listaFuncionario.add(novoFuncionario);
            }
            return listaFuncionario;

        } catch (Exception ex) {

            return listaFuncionario;

        } finally {

            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public void listarCompletoId(Funcionario funcionario) {

        try {

            //Conexao
            conexao = ConectaBanco.getConexao();

            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(BUSCAR_COMPLETO_ID);
            
            //Passa ID como parametro
            pstmt.setInt(1, funcionario.getIdFuncionario());

            //executa
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                funcionario.setIdFuncionario(Integer.parseInt(rs.getString("idFunc")));
                funcionario.setIdPessoa(Integer.parseInt(rs.getString("idPessoa")));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setDataNascimento(rs.getDate("dataNascimento"));
                
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(Integer.parseInt(rs.getString("idUsuario")));
                usuario.setEmail(rs.getString("email"));
                usuario.setCelular(Long.parseLong(rs.getString("celular")));
                usuario.setPerfil(PerfilDeAcesso.valueOf(rs.getString("perfil")));
                
                funcionario.setUsuario(usuario);
            }

        } catch (Exception ex) {

        } finally {

            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public void buscar(Funcionario funcionario) {

        try {
            //Conexao
            conexao = ConectaBanco.getConexao();
            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(BUSCAR_ID);

            pstmt.setInt(1, funcionario.getIdFuncionario());
            //executa
            ResultSet rs = pstmt.executeQuery();

            // como a query ira retornar somente um registro, faremos o NEXT
            while (rs.next()) {
                funcionario.setIdFuncionario(Integer.parseInt(rs.getString("idFunc")));
                funcionario.setIdPessoa(Integer.parseInt(rs.getString("idPessoa")));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setDataNascimento(rs.getDate("dataNascimento"));
            }
        } catch (Exception e) {

            //
        } finally {

            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public boolean alterar(Funcionario funcionario) {

        try {

            conexao = ConectaBanco.getConexao();

            PreparedStatement pstmt = conexao.prepareStatement(UPDATE);

            pstmt.setString(1, funcionario.getNome());
            pstmt.setInt(2, funcionario.getIdFuncionario());

            pstmt.execute();
            return true;

        } catch (Exception ex) {

            return false;

        } finally {

            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public boolean excluir(Funcionario funcionario) {

        try {

            conexao = ConectaBanco.getConexao();

            PreparedStatement pstmt = conexao.prepareStatement(DELETE);

            pstmt.setInt(1, funcionario.getIdFuncionario());

            pstmt.execute();

            return true;

        } catch (Exception ex) {

            return false;

        } finally {

            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public boolean cadastrar(Funcionario funcionario) {

        try {

            conexao = ConectaBanco.getConexao();

            PreparedStatement pstmt = conexao.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, funcionario.getNome());

            pstmt.execute();

            final ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                funcionario.setIdFuncionario(rs.getInt("id"));
            }

            return true;

        } catch (Exception ex) {

            return false;

        } finally {

            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
};
