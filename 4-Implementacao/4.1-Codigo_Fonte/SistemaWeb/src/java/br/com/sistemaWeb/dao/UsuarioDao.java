package br.com.sistemaWeb.dao;

import br.com.sistemaWeb.classes.FabricaConexao;
import br.com.sistemaWeb.classes.Usuario;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Silvio Coelho
 */
public class UsuarioDao implements Serializable{
    private static final long serialVersionUID = 1L;

    PreparedStatement pstm;
    ResultSet rs;
    private Connection conexao;
   // private EntityManager em;

    public void salvar(Usuario usuario) throws Exception {
        try {
            String sql = "INSERT INTO usuarios(nomeusuario, senha) VALUES(?,?)";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, usuario.getNomeUsuario());
            pstm.setString(2, usuario.getSenha());
            
            pstm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
    }

    public void apagar(Usuario usuario) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setInt(1, usuario.getId());
        pstm.executeUpdate();
    }

    public void atualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nomeusuario=? WHERE id =?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setString(1, usuario.getNomeUsuario());
        pstm.setInt(4, usuario.getId());
        pstm.executeUpdate();
    }

    public List<Usuario> todosUsuarios() throws SQLException {
        List<Usuario> listaUsuario = new ArrayList<Usuario>();
        String sql = "SELECT * FROM usuarios order by nomeusuario";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        rs = pstm.executeQuery();
        while (rs.next()) {
            Usuario tempUsuario = new Usuario();
            tempUsuario.setId(rs.getInt("id"));
            tempUsuario.setNomeUsuario(rs.getString("nomeUsuario"));
            listaUsuario.add(tempUsuario);
        }
        return listaUsuario;
    }

    
    public List<Usuario> consultaUsuarios(String nome) throws SQLException {
        List<Usuario> listaUsuario = new ArrayList<Usuario>();

        String sql = "SELECT * FROM usuarios WHERE nomeusuario LIKE ?";

        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setString(1, "%" + nome + "%");
        rs = pstm.executeQuery();

        while (rs.next()) {
            Usuario tempUsuario = new Usuario();
            tempUsuario.setId(rs.getInt("id"));
            tempUsuario.setNomeUsuario(rs.getString("nomeusuario"));
            listaUsuario.add(tempUsuario);
            System.out.println(tempUsuario.getNomeUsuario());
        }
        System.out.println("aaa");
        return listaUsuario;
    }
    
    
    public Usuario buscaPorID(Usuario usuario) throws Exception {
        Usuario tempUsuario = null;
        //ResultSet rs;
        try {
            String sql = "select * from usuarios where id=?";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, usuario.getId());
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempUsuario = new Usuario();
                tempUsuario.setId(rs.getInt("id"));
                tempUsuario.setNomeUsuario(rs.getString("nomeUsuario"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        return tempUsuario;
    }

    public boolean buscaPorNome(Usuario usuarioParam) throws Exception {
        boolean achou = false;
        //ResultSet rs;
        try {
            String sql = "select id, nomeusuario from usuarios where nomeusuario=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, usuarioParam.getNomeUsuario().trim());
            rs = pstm.executeQuery();
            while (rs.next()) {
                achou = true;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        return achou;
    }

    public Usuario buscaPorCodigo(int id) throws Exception {
        System.out.println("entrou no buscaESTAD por sigla");
        Usuario tempUsuario = null;
        try {
            String sql = "select *  from usuarios where id=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempUsuario = new Usuario();
                tempUsuario.setId(rs.getInt("id"));
                tempUsuario.setNomeUsuario(rs.getString("nomeusuario"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        if (tempUsuario == null) {
            System.out.println("objeto estado null");
        } else {
            System.out.println("objeto pais :" + tempUsuario.getNomeUsuario());
        }
        return tempUsuario;
    }
    
    public List<Usuario> buscarTodos(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemploPU");
        EntityManager em = emf.createEntityManager();
        return em.createQuery("from Usuarios").getResultList();
    }

}
