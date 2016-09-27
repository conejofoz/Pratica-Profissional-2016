package br.com.sistemaWeb.dao;

import br.com.sistemaWeb.classes.FabricaConexao;
import br.com.sistemaWeb.classes.Ncm;
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
public class NcmDao implements Serializable{
    private static final long serialVersionUID = 1L;

    PreparedStatement pstm;
    ResultSet rs;
    private Connection conexao;

    public void salvar(Ncm ncm) throws Exception {
        try {
            String sql = "INSERT INTO ncm(id, nomencm) VALUES(?,?)";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, ncm.getId());
            pstm.setString(2, ncm.getNomeNcm());
            pstm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
    }

    public void apagar(Ncm ncm) throws SQLException {
        String sql = "DELETE FROM ncm WHERE id = ?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setString(1, ncm.getId());
        pstm.executeUpdate();
    }

    public void atualizar(Ncm ncm) throws SQLException {
        String sql = "UPDATE ncm SET nomencm=? WHERE id =?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setString(1, ncm.getNomeNcm());
        pstm.setString(4, ncm.getId());
        pstm.executeUpdate();
    }

    public List<Ncm> todosNcmes() throws SQLException {
        List<Ncm> listaNcm = new ArrayList<Ncm>();
        String sql = "SELECT * FROM ncm order by nomencm";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        rs = pstm.executeQuery();
        while (rs.next()) {
            Ncm tempNcm = new Ncm();
            tempNcm.setId(rs.getString("id"));
            tempNcm.setNomeNcm(rs.getString("nomeNcm"));
            listaNcm.add(tempNcm);
        }
        return listaNcm;
    }

    
    public List<Ncm> consultaNcmes(String nome) throws SQLException {
        List<Ncm> listaNcm = new ArrayList<Ncm>();

        String sql = "SELECT * FROM ncm WHERE nomencm LIKE ?";

        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setString(1, "%" + nome + "%");
        rs = pstm.executeQuery();

        while (rs.next()) {
            Ncm tempNcm = new Ncm();
            tempNcm.setId(rs.getString("id"));
            tempNcm.setNomeNcm(rs.getString("nomencm"));
            listaNcm.add(tempNcm);
            System.out.println(tempNcm.getNomeNcm());
        }
        System.out.println("aaa");
        return listaNcm;
    }
    
    
    public Ncm buscaPorID(Ncm ncm) throws Exception {
        Ncm tempNcm = null;
        //ResultSet rs;
        try {
            String sql = "select * from ncm where id=?";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, ncm.getId());
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempNcm = new Ncm();
                tempNcm.setId(rs.getString("id"));
                tempNcm.setNomeNcm(rs.getString("nomeNcm"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        return tempNcm;
    }

    public boolean buscaPorNome(Ncm ncmParam) throws Exception {
        boolean achou = false;
        //ResultSet rs;
        try {
            String sql = "select id, nomencm from ncm where nomencm=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, ncmParam.getNomeNcm().trim());
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

    public Ncm buscaPorCodigo(int id) throws Exception {
        System.out.println("entrou no buscaESTAD por sigla");
        Ncm tempNcm = null;
        try {
            String sql = "select *  from ncm where id=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempNcm = new Ncm();
                tempNcm.setId(rs.getString("id"));
                tempNcm.setNomeNcm(rs.getString("nomencm"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        if (tempNcm == null) {
            System.out.println("objeto estado null");
        } else {
            System.out.println("objeto pais :" + tempNcm.getNomeNcm());
        }
        return tempNcm;
    }

}
