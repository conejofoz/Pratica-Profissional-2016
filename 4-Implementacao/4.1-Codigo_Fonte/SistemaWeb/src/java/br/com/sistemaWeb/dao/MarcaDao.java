package br.com.sistemaWeb.dao;

import br.com.sistemaWeb.classes.FabricaConexao;
import br.com.sistemaWeb.classes.Marca;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Silvio Coelho
 */
public class MarcaDao implements Serializable{
    private static final long serialVersionUID = 1L;

    PreparedStatement pstm;
    ResultSet rs;
    private Connection conexao;

    public void salvar(Marca marca) throws Exception {
        try {
            String sql = "INSERT INTO marcas(nomemarca) VALUES(?)";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, marca.getNomeMarca());
            
            pstm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
    }

    public void apagar(Marca marca) throws SQLException {
        String sql = "DELETE FROM marcas WHERE id = ?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setInt(1, marca.getId());
        pstm.executeUpdate();
    }

    public void atualizar(Marca marca) throws SQLException {
        String sql = "UPDATE marcas SET nomemarca=? WHERE id =?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setString(1, marca.getNomeMarca());
        pstm.setInt(4, marca.getId());
        pstm.executeUpdate();
    }

    public List<Marca> todosMarcaes() throws SQLException {
        List<Marca> listaMarca = new ArrayList<Marca>();
        String sql = "SELECT * FROM marcas order by nomemarca";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        rs = pstm.executeQuery();
        while (rs.next()) {
            Marca tempMarca = new Marca();
            tempMarca.setId(rs.getInt("id"));
            tempMarca.setNomeMarca(rs.getString("nomeMarca"));
            listaMarca.add(tempMarca);
        }
        return listaMarca;
    }

    
    public List<Marca> consultaMarcaes(String nome) throws SQLException {
        List<Marca> listaMarca = new ArrayList<Marca>();

        String sql = "SELECT * FROM marcas WHERE nomemarca LIKE ?";

        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setString(1, "%" + nome + "%");
        rs = pstm.executeQuery();

        while (rs.next()) {
            Marca tempMarca = new Marca();
            tempMarca.setId(rs.getInt("id"));
            tempMarca.setNomeMarca(rs.getString("nomemarca"));
            listaMarca.add(tempMarca);
            System.out.println(tempMarca.getNomeMarca());
        }
        System.out.println("aaa");
        return listaMarca;
    }
    
    
    public Marca buscaPorID(Marca marca) throws Exception {
        Marca tempMarca = null;
        //ResultSet rs;
        try {
            String sql = "select * from marcas where id=?";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, marca.getId());
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempMarca = new Marca();
                tempMarca.setId(rs.getInt("id"));
                tempMarca.setNomeMarca(rs.getString("nomeMarca"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        return tempMarca;
    }

    public boolean buscaPorNome(Marca marcaParam) throws Exception {
        boolean achou = false;
        //ResultSet rs;
        try {
            String sql = "select id, nomemarca from marcas where nomemarca=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, marcaParam.getNomeMarca().trim());
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

    public Marca buscaPorCodigo(int id) throws Exception {
        System.out.println("entrou no buscaESTAD por sigla");
        Marca tempMarca = null;
        try {
            String sql = "select *  from marcas where id=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempMarca = new Marca();
                tempMarca.setId(rs.getInt("id"));
                tempMarca.setNomeMarca(rs.getString("nomemarca"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        if (tempMarca == null) {
            System.out.println("objeto estado null");
        } else {
            System.out.println("objeto pais :" + tempMarca.getNomeMarca());
        }
        return tempMarca;
    }

}
