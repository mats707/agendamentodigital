package dao;

import dao.interfaces.IPessoaDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelos.Pessoa;
import modelos.Cliente;
import modelos.Usuario;
import util.ConectaBanco;

public class PessoaDAO implements IPessoaDAO {

    private static final String LISTAR = "SELECT id, nome, dataNascimento, usuario FROM sistema.pessoa ORDER BY id;";
    private static final String BUSCAR = "SELECT * FROM sistema.pessoa WHERE nome ilike ?;";
    private static final String BUSCAR_USUARIO = "SELECT id, nome, dataNascimento, usuario FROM sistema.pessoa WHERE usuario=? ORDER BY id;";
    private static final String INSERT
            = "INSERT INTO sistema.pessoa (id, nome, dataNascimento, endereco, sexo, estadoCivil, qtdFilhos, profissao, escolaridade)\n"
            + "  VALUES (NEXTVAL('sqn_pessoa'),\n"
            + "		?,\n"
            + "		?,\n"
            + "		(SELECT id FROM endereco WHERE id = ?),\n"
            + "		(SELECT id FROM sexo WHERE id = ?),\n"
            + "		(SELECT id FROM estadoCivil WHERE id = ?),\n"
            + "		?,\n"
            + "		(SELECT id FROM profissao WHERE id = ?),\n"
            + "		(SELECT id FROM escolaridade WHERE id = ?)\n"
            + "  );";
    private static final String DELETE = "DELETE FROM pessoa WHERE id=?;";
    private static final String UPDATE = "UPDATE pessoa SET nome=? WHERE id=?;";

    private Connection conexao;

    @Override
    public ArrayList<Pessoa> listar() {

        //cria uma array de Obj Tipo
        ArrayList<Pessoa> listaPessoa = new ArrayList<Pessoa>();

        try {

            //Conexao
            conexao = ConectaBanco.getConexao();

            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(LISTAR);

            //executa
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                //a cada loop
                Pessoa novoPessoa = new Pessoa();
                novoPessoa.setIdPessoa(Integer.parseInt(rs.getString("id")));
                novoPessoa.setNome(rs.getString("nome"));
                novoPessoa.setDataNascimento(rs.getDate("dataNascimento"));

                Usuario usuario = new Usuario();
                usuario.setIdUsuario(Integer.parseInt(rs.getString("usuario")));
                novoPessoa.setUsuario(usuario);

                //add na lista
                listaPessoa.add(novoPessoa);
            }
            return listaPessoa;

        } catch (Exception ex) {

            return listaPessoa;

        } finally {

            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public void buscar_usuario(Cliente cliente) {

        try {

            //Conexao
            conexao = ConectaBanco.getConexao();

            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(BUSCAR_USUARIO);
            pstmt.setInt(1, cliente.getUsuario().getIdUsuario());
            
            //Resetando Usuario
            Usuario usuarioAntigo = cliente.getUsuario();
            usuarioAntigo.setIdUsuario(null);
            cliente.setUsuario(usuarioAntigo);

            //executa
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                cliente.setIdPessoa(Integer.parseInt(rs.getString("id")));
                cliente.setNome(rs.getString("nome"));
                cliente.setDataNascimento(rs.getDate("dataNascimento"));

                Usuario usuario = new Usuario();
                usuario.setIdUsuario(Integer.parseInt(rs.getString("usuario")));
                cliente.setUsuario(usuario);
            }

        } catch (Exception ex) {

        } finally {

            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public void buscar(Pessoa pessoa) {

        try {
            //Conexao
            conexao = ConectaBanco.getConexao();
            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(BUSCAR);

            pstmt.setString(1, pessoa.getNome());
            //executa
            ResultSet rs = pstmt.executeQuery();

            // como a query ira retornar somente um registro, faremos o NEXT
            while (rs.next()) {
                pessoa.setIdPessoa(rs.getInt("id"));
            }
        } catch (Exception e) {

            //
        } finally {

            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public boolean alterar(Cliente cliente) {

        try {

            conexao = ConectaBanco.getConexao();

            PreparedStatement pstmt = conexao.prepareStatement(UPDATE);

            pstmt.setString(1, cliente.getNome());
            pstmt.setInt(2, cliente.getIdPessoa());

            pstmt.execute();
            return true;

        } catch (Exception ex) {

            return false;

        } finally {

            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public boolean excluir(Pessoa pessoa) {

        try {

            conexao = ConectaBanco.getConexao();

            PreparedStatement pstmt = conexao.prepareStatement(DELETE);

            pstmt.setInt(1, pessoa.getIdPessoa());

            pstmt.execute();

            return true;

        } catch (Exception ex) {

            return false;

        } finally {

            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public boolean cadastrar(Pessoa pessoa) {

        try {

            conexao = ConectaBanco.getConexao();

            PreparedStatement pstmt = conexao.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, pessoa.getNome());

            pstmt.execute();

            final ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                pessoa.setIdPessoa(rs.getInt("id"));
            }

            return true;

        } catch (Exception ex) {

            return false;

        } finally {

            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
};
