package dao;

import dao.interfaces.IUsuarioDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelos.PerfilDeAcesso;
import modelos.Usuario;
import util.ConectaBanco;
import util.geraHash;

public class UsuarioDAO implements IUsuarioDAO {

    private static final String CADASTRA_NOVO_USUARIO = "INSERT INTO usuario (id, email, senha, celular, perfil) VALUES (nextval('sqn_usuario'),?,?,?,(select id from PerfilAcesso where nome ilike ?));";
    private static final String AUTENTICA_USUARIO = "SELECT u.email, u.senha, u.celular, p.nome as perfil FROM usuario u INNER JOIN perfilAcesso p "
            + "ON u.perfil = p.id WHERE u.email=? AND u.senha=?";
    private static final String BUSCAR = "SELECT u.email, p.nome as perfil FROM usuario u INNER JOIN perfilAcesso p "
            + "ON u.perfil = p.id WHERE u.email=?";
    private static final String LISTAR = "SELECT u.email, p.nome as perfil FROM usuario u INNER JOIN perfilAcesso p "
            + "ON u.perfil = p.id ORDER BY u.email;";

    private Connection conexao;

    @Override
    public String cadastraNovoUsuario(Usuario usuario) {

        String sqlReturnCode = "0";
        
        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CADASTRA_NOVO_USUARIO, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, usuario.getEmail());
            pstmt.setString(2, usuario.getSenha());
            pstmt.setLong(3, usuario.getCelular());
            pstmt.setString(4, usuario.getPerfil().toString());
            pstmt.execute();

            final ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                usuario.setIdUsuario(rs.getInt("id"));
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
    public Usuario autenticaUsuario(Usuario usuario) {
        Usuario usuarioAutenticado = null;
        PreparedStatement pstmt = null;
        ResultSet rsUsuario = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(AUTENTICA_USUARIO);
            pstmt.setString(1, usuario.getEmail());
            pstmt.setString(2, usuario.getSenha());
            rsUsuario = pstmt.executeQuery();
            if (rsUsuario.next()) {
                usuarioAutenticado = new Usuario();
                usuarioAutenticado.setEmail(rsUsuario.getString("email"));
                usuarioAutenticado.setSenha(rsUsuario.getString("senha"));
                usuarioAutenticado.setCelular(rsUsuario.getLong("celular"));
                usuarioAutenticado.setPerfil(PerfilDeAcesso.valueOf(rsUsuario.getString("perfil")));
            }
        } catch (SQLException sqlErro) {
            throw new RuntimeException(sqlErro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        return usuarioAutenticado;
    }

    @Override
    public ArrayList<Usuario> listar() {

        ArrayList<Usuario> listaUsuario = new ArrayList<Usuario>();

        try {

            //Conexao
            conexao = ConectaBanco.getConexao();
            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(LISTAR);
            //executa
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Usuario novoUsuario = new Usuario();
                novoUsuario.setEmail(rs.getString("email"));
                novoUsuario.setPerfil(PerfilDeAcesso.valueOf(rs.getString("perfil")));

                //add na lista
                listaUsuario.add(novoUsuario);
            }
            return listaUsuario;

        } catch (Exception ex) {
            
            return listaUsuario;

        } finally {

            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public void buscar(Usuario usuario) {

        try {

            //Conexao
            conexao = ConectaBanco.getConexao();

            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(BUSCAR);

            pstmt.setString(1, usuario.getEmail());

            usuario.setEmail(null);

            //executa
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                usuario.setEmail(rs.getString("email"));
                usuario.setPerfil(PerfilDeAcesso.valueOf(rs.getString("perfil")));
            }

        } catch (Exception ex) {

        } finally {

            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public boolean alterar(Usuario sexo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean excluir(Usuario sexo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
};
