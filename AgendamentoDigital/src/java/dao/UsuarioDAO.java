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

    private static final String CADASTRA_NOVO_USUARIO = "INSERT INTO sistema.usuario (id, email, senha, celular, perfil) VALUES (nextval('sistema.sqn_usuario'),?,?,?,(select id from sistema.perfilacesso where nome ilike ?));";
    private static final String ALTERA_USUARIO = "UPDATE sistema.usuario\n"
            + "SET email = ?,"
            + "senha = ?,\n"
            + "celular = ?,\n"
            + "perfil = (select id from sistema.perfilacesso where lower(nome) ilike lower(?))\n"
            + "WHERE id = ?";
    private static final String AUTENTICA_USUARIO = "SELECT u.id, u.email, u.senha, u.celular, p.nome as perfil FROM sistema.usuario u INNER JOIN sistema.perfilacesso p "
            + "ON u.perfil = p.id WHERE u.email=?";
    private static final String BUSCAR = "SELECT u.id, u.email, u.celular, p.nome as perfil FROM sistema.usuario u INNER JOIN sistema.perfilacesso p "
            + "ON u.perfil = p.id WHERE u.email=?";
    private static final String BUSCAR_ID = "SELECT u.id, u.email, u.celular, p.nome as perfil FROM sistema.usuario u INNER JOIN sistema.perfilacesso p "
            + "ON u.perfil = p.id WHERE u.id=?";
    private static final String LISTAR = "SELECT u.id, u.email, u.celular, p.nome as perfil FROM sistema.usuario u INNER JOIN sistema.perfilacesso p "
            + "ON u.perfil = p.id ORDER BY u.email;";
    private static final String DELETAR = "DELETE FROM sistema.usuario WHERE id = ?";
    private static final String BUSCA_COMPLETA = "SELECT u.id, u.email, u.celular, p.nome as perfil FROM sistema.usuario u INNER JOIN sistema.perfilacesso p "
            + "ON u.perfil = p.id WHERE u.id=? AND u.email=? AND u.celular=? AND p.nome=?";

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
            rsUsuario = pstmt.executeQuery();
            if (rsUsuario.next()) {
                usuarioAutenticado = new Usuario();
                usuarioAutenticado.setIdUsuario(rsUsuario.getInt("id"));
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
                novoUsuario.setIdUsuario(Integer.parseInt(rs.getString("id")));
                novoUsuario.setEmail(rs.getString("email"));
                novoUsuario.setCelular(Long.parseLong(rs.getString("celular")));
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
                usuario.setIdUsuario(Integer.parseInt(rs.getString("id")));
                usuario.setEmail(rs.getString("email"));
                usuario.setCelular(Long.parseLong(rs.getString("celular")));
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
    
    public void buscarId(Usuario usuario) {

        try {

            //Conexao
            conexao = ConectaBanco.getConexao();

            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(BUSCAR_ID);

            pstmt.setInt(1, usuario.getIdUsuario());

            usuario.setEmail(null);

            //executa
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                usuario.setIdUsuario(Integer.parseInt(rs.getString("id")));
                usuario.setEmail(rs.getString("email"));
                usuario.setCelular(Long.parseLong(rs.getString("celular")));
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
    public Usuario buscaCompleta(Usuario usuario) {

        try {

            //Conexao
            conexao = ConectaBanco.getConexao();

            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(BUSCA_COMPLETA);

            pstmt.setInt(1, usuario.getIdUsuario());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setLong(3, usuario.getCelular());
            pstmt.setString(4, usuario.getPerfil().name());

            usuario.setIdUsuario(null);
            usuario.setEmail(null);
            usuario.setCelular(null);
            usuario.setPerfil(null);

            //executa
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                usuario.setIdUsuario(Integer.parseInt(rs.getString("id")));
                usuario.setEmail(rs.getString("email"));
                usuario.setCelular(Long.parseLong(rs.getString("celular")));
                usuario.setPerfil(PerfilDeAcesso.valueOf(rs.getString("perfil")));
            }
            
            return usuario;

        } catch (Exception ex) {
            
            return usuario;

        } finally {

            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public String alterarUsuario(Usuario usuario) {

        String sqlReturnCode = "0";

        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(ALTERA_USUARIO, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, usuario.getEmail());
            pstmt.setString(2, usuario.getSenha());
            pstmt.setLong(3, usuario.getCelular());
            pstmt.setString(4, usuario.getPerfil().toString());
            pstmt.setInt(5, usuario.getIdUsuario());
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
    public String deletar(Usuario usuario) {

        String sqlReturnCode = "0";

        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(DELETAR);
            pstmt.setInt(1, usuario.getIdUsuario());
            pstmt.execute();

            return sqlReturnCode;

        } catch (SQLException sqlErro) {
            sqlReturnCode = sqlErro.getSQLState();
            if (sqlReturnCode.equalsIgnoreCase("23503")) { //Significa que violou uma unique constraint
                return sqlErro.getMessage().split("\n")[0];
            } else {
                return sqlReturnCode;
            }
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
