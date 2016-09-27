package br.com.sistemaWeb.dao;

import br.com.sistemaWeb.classes.FabricaConexao;
import br.com.sistemaWeb.classes.Cfop;
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
public class CfopDao implements Serializable{
    private static final long serialVersionUID = 1L;

    PreparedStatement pstm;
    ResultSet rs;
    private Connection conexao;

    public void salvar(Cfop cfop) throws Exception {
        try {
            String sql = "INSERT INTO cfop(id, nomecfop) VALUES(?,?)";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, cfop.getId());
            pstm.setString(2, cfop.getNomeCfop());
            pstm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
    }

    public void apagar(Cfop cfop) throws SQLException {
        String sql = "DELETE FROM cfop WHERE id = ?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setString(1, cfop.getId());
        pstm.executeUpdate();
    }

    public void atualizar(Cfop cfop) throws SQLException {
        String sql = "UPDATE cfop SET nomecfop=? WHERE id =?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setString(1, cfop.getNomeCfop());
        pstm.setString(4, cfop.getId());
        pstm.executeUpdate();
    }

    public List<Cfop> todosCfopes() throws SQLException {
        List<Cfop> listaCfop = new ArrayList<Cfop>();
        String sql = "SELECT * FROM cfop order by nomecfop";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        rs = pstm.executeQuery();
        while (rs.next()) {
            Cfop tempCfop = new Cfop();
            tempCfop.setId(rs.getString("id"));
            tempCfop.setNomeCfop(rs.getString("nomeCfop"));
            listaCfop.add(tempCfop);
        }
        return listaCfop;
    }

    
    public List<Cfop> consultaCfopes(String nome) throws SQLException {
        List<Cfop> listaCfop = new ArrayList<Cfop>();

        String sql = "SELECT * FROM cfop WHERE nomecfop LIKE ?";

        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setString(1, "%" + nome + "%");
        rs = pstm.executeQuery();

        while (rs.next()) {
            Cfop tempCfop = new Cfop();
            tempCfop.setId(rs.getString("id"));
            tempCfop.setNomeCfop(rs.getString("nomecfop"));
            listaCfop.add(tempCfop);
            System.out.println(tempCfop.getNomeCfop());
        }
        System.out.println("aaa");
        return listaCfop;
    }
    
    
    public Cfop buscaPorID(Cfop cfop) throws Exception {
        Cfop tempCfop = null;
        //ResultSet rs;
        try {
            String sql = "select * from cfop where id=?";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, cfop.getId());
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempCfop = new Cfop();
                tempCfop.setId(rs.getString("id"));
                tempCfop.setNomeCfop(rs.getString("nomeCfop"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        return tempCfop;
    }

    public boolean buscaPorNome(Cfop cfopParam) throws Exception {
        boolean achou = false;
        //ResultSet rs;
        try {
            String sql = "select id, nomecfop from cfop where nomecfop=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, cfopParam.getNomeCfop().trim());
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

    public Cfop buscaPorCodigo(int id) throws Exception {
        System.out.println("entrou no buscaESTAD por sigla");
        Cfop tempCfop = null;
        try {
            String sql = "select *  from cfop where id=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempCfop = new Cfop();
                tempCfop.setId(rs.getString("id"));
                tempCfop.setNomeCfop(rs.getString("nomecfop"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        if (tempCfop == null) {
            System.out.println("objeto estado null");
        } else {
            System.out.println("objeto pais :" + tempCfop.getNomeCfop());
        }
        return tempCfop;
    }

}
