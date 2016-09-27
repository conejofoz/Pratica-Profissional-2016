package br.com.sistemaWeb.dao;

import br.com.sistemaWeb.classes.FabricaConexao;
import br.com.sistemaWeb.classes.Moeda;
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
public class MoedaDao {

    PreparedStatement pstm;
    ResultSet rs;
    private Connection conexao;

    public void salvar(Moeda moeda) throws Exception {
        try {
            String sql = "INSERT INTO moedas(nomemoeda, siglamoeda) VALUES(?,?) ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, moeda.getNomeMoeda());
            pstm.setString(2, moeda.getSiglaMoeda());

            pstm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
    }

    public void apagar(Moeda moeda) throws SQLException {
        String sql = "DELETE FROM moedas WHERE id = ?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setInt(1, moeda.getId());
        pstm.executeUpdate();
    }

    public void atualizar(Moeda moeda) throws SQLException {
        String sql = "UPDATE moedas SET nomemoeda=?, siglamoeda=? WHERE id =?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setString(1, moeda.getNomeMoeda());
        pstm.setString(2, moeda.getSiglaMoeda());

        pstm.setInt(3, moeda.getId());
        pstm.executeUpdate();
    }

    public List<Moeda> todosMoedas() throws SQLException {
        List<Moeda> listaMoeda = new ArrayList<Moeda>();

        String sql = "SELECT id, nomemoeda, siglamoeda FROM moedas order by nomeMoeda";
        try{
            
        
            
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        rs = pstm.executeQuery();
        }catch(SQLException ex){
           throw ex; 
        }
        while (rs.next()) {
            Moeda tempMoeda = new Moeda();
            tempMoeda.setId(rs.getInt("id"));
            tempMoeda.setNomeMoeda(rs.getString("nomeMoeda"));
            tempMoeda.setSiglaMoeda(rs.getString("siglamoeda"));

            listaMoeda.add(tempMoeda);
        }
        return listaMoeda;
    }

    public Moeda buscaPorID(Moeda per) throws Exception {
        Moeda tempMoeda = null;
        //ResultSet rs;
        try {
            String sql = "select *  from moedas where id=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, per.getId());
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempMoeda = new Moeda();
                tempMoeda.setId(rs.getInt("id"));
                tempMoeda.setNomeMoeda(rs.getString("nomeMoeda"));
                tempMoeda.setSiglaMoeda(rs.getString("siglamoeda"));

            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        return tempMoeda;
    }

    public boolean buscaPorNome(Moeda moedaParam) throws Exception {
        boolean achou = false;
        //ResultSet rs;
        try {
            String sql = "select id, nomeMoeda from moedas where nomeMoeda=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, moedaParam.getNomeMoeda().trim());
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

    public Moeda buscaPorCodigo(int id) throws Exception {
        System.out.println("entrou no buscaESTAD por sigla");
        Moeda tempMoeda = null;

        try {
            String sql = "select *  from moedas where id=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempMoeda = new Moeda();
                tempMoeda.setId(rs.getInt("id"));
                tempMoeda.setNomeMoeda(rs.getString("nomeMoeda"));

            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        if (tempMoeda == null) {
            System.out.println("objeto estado null");
        } else {
            System.out.println("objeto pais :" + tempMoeda.getNomeMoeda());
        }
        return tempMoeda;
    }

}
