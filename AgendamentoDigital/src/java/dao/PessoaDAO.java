package dao;

import dao.interfaces.IPessoaDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    private static final String BUSCAR = "SELECT id, nome, dataNascimento, usuario FROM sistema.pessoa WHERE id= ?;";
    //private static final String BUSCAR_NOME = "SELECT * FROM sistema.pessoa WHERE id ilike ?;";
    private static final String BUSCAR_USUARIO = "SELECT id, nome, dataNascimento, usuario FROM sistema.pessoa WHERE usuario=? ORDER BY id;";
    private static final String CADASTRAR = "INSERT INTO sistema.pessoa (id, nome, dataNascimento, usuario) VALUES (NEXTVAL('sistema.sqn_pessoa'),?,?::DATE,(SELECT id FROM sistema.usuario WHERE id = ?));";
    private static final String DELETE = "DELETE FROM sistema.pessoa WHERE id=?;";
    private static final String UPDATE = "UPDATE sistema.pessoa SET nome=?, datanascimento=?::DATE WHERE id=?;";

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

//    @Override
//    public void buscar(Pessoa pessoa) {
//
//        try {
//            //Conexao
//            conexao = ConectaBanco.getConexao();
//            //cria comando SQL
//            PreparedStatement pstmt = conexao.prepareStatement(BUSCAR);
//
//            pstmt.setString(1, pessoa.getNome());
//            //executa
//            ResultSet rs = pstmt.executeQuery();
//
//            // como a query ira retornar somente um registro, faremos o NEXT
//            while (rs.next()) {
//                pessoa.setIdPessoa(rs.getInt("id"));
//            }
//        } catch (Exception e) {
//
//            //
//        } finally {
//
//            try {
//                conexao.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
    
    @Override
    public void buscar(Pessoa pessoa) {

        try {
            //Conexao
            conexao = ConectaBanco.getConexao();
            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(BUSCAR);

            pstmt.setInt(1, pessoa.getIdPessoa());
            //executa
            ResultSet rs = pstmt.executeQuery();
            
            // como a query ira retornar somente um registro, faremos o NEXT
            while (rs.next()) {
                pessoa.setIdPessoa(rs.getInt("id"));
                pessoa.setNome(rs.getString("nome"));
                pessoa.setDataNascimento(rs.getDate("dataNascimento"));                
                pessoa.setUsuario(new Usuario(Integer.parseInt(rs.getString("usuario"))));
                
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
    public String alterar(Pessoa pessoa) {
        
        String sqlReturnCode = "0";
        
        String patternDataNascimento = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(patternDataNascimento);

        try {

            conexao = ConectaBanco.getConexao();

            PreparedStatement pstmt = conexao.prepareStatement(UPDATE);

            pstmt.setString(1, pessoa.getNome());
            pstmt.setString(2, df.format(pessoa.getDataNascimento()));
            pstmt.setInt(3, pessoa.getIdPessoa());

            pstmt.execute();

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
    public boolean deletar(Pessoa pessoa) {

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
    public String cadastrar(Pessoa pessoa) {
        
        String sqlReturnCode = "0";
        
        String patternDataNascimento = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(patternDataNascimento);

        try {

            conexao = ConectaBanco.getConexao();

            PreparedStatement pstmt = conexao.prepareStatement(CADASTRAR, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, pessoa.getNome());
            pstmt.setString(2, df.format(pessoa.getDataNascimento()));
            pstmt.setInt(3, pessoa.getUsuario().getIdUsuario());

            pstmt.execute();

            final ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                pessoa.setIdPessoa(rs.getInt("id"));
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
};
