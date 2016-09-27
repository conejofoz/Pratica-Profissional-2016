package br.com.sistemaWeb.dao;

import br.com.sistemaWeb.classes.FabricaConexao;
import br.com.sistemaWeb.classes.Cst;
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
public class CstDao implements Serializable{
    private static final long serialVersionUID = 1L;

    PreparedStatement pstm;
    ResultSet rs;
    private Connection conexao;

    public void salvar(Cst cst) throws Exception {
        try {
            String sql = "INSERT INTO cst(id, nomecst) VALUES(?,?)";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, cst.getId());
            pstm.setString(2, cst.getNomeCst());
            pstm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
    }

    public void apagar(Cst cst) throws SQLException {
        String sql = "DELETE FROM cst WHERE id = ?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setString(1, cst.getId());
        pstm.executeUpdate();
    }

    public void atualizar(Cst cst) throws SQLException {
        String sql = "UPDATE cst SET nomecst=? WHERE id =?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setString(1, cst.getNomeCst());
        pstm.setString(4, cst.getId());
        pstm.executeUpdate();
    }

    public List<Cst> todosCstes() throws SQLException {
        List<Cst> listaCst = new ArrayList<Cst>();
        String sql = "SELECT * FROM cst order by nomecst";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        rs = pstm.executeQuery();
        while (rs.next()) {
            Cst tempCst = new Cst();
            tempCst.setId(rs.getString("id"));
            tempCst.setNomeCst(rs.getString("nomeCst"));
            listaCst.add(tempCst);
        }
        return listaCst;
    }

    
    public List<Cst> consultaCstes(String nome) throws SQLException {
        List<Cst> listaCst = new ArrayList<Cst>();

        String sql = "SELECT * FROM cst WHERE nomecst LIKE ?";

        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setString(1, "%" + nome + "%");
        rs = pstm.executeQuery();

        while (rs.next()) {
            Cst tempCst = new Cst();
            tempCst.setId(rs.getString("id"));
            tempCst.setNomeCst(rs.getString("nomecst"));
            listaCst.add(tempCst);
            System.out.println(tempCst.getNomeCst());
        }
        System.out.println("aaa");
        return listaCst;
    }
    
    
    public Cst buscaPorID(Cst cst) throws Exception {
        Cst tempCst = null;
        //ResultSet rs;
        try {
            String sql = "select * from cst where id=?";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, cst.getId());
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempCst = new Cst();
                tempCst.setId(rs.getString("id"));
                tempCst.setNomeCst(rs.getString("nomeCst"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        return tempCst;
    }

    public boolean buscaPorNome(Cst cstParam) throws Exception {
        boolean achou = false;
        //ResultSet rs;
        try {
            String sql = "select id, nomecst from cst where nomecst=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, cstParam.getNomeCst().trim());
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

    public Cst buscaPorCodigo(int id) throws Exception {
        System.out.println("entrou no buscaESTAD por sigla");
        Cst tempCst = null;
        try {
            String sql = "select *  from cst where id=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempCst = new Cst();
                tempCst.setId(rs.getString("id"));
                tempCst.setNomeCst(rs.getString("nomecst"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        if (tempCst == null) {
            System.out.println("objeto estado null");
        } else {
            System.out.println("objeto pais :" + tempCst.getNomeCst());
        }
        return tempCst;
    }

}
