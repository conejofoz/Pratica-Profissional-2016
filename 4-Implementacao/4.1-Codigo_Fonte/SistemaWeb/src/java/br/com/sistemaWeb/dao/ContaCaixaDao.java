package br.com.sistemaWeb.dao;

import br.com.sistemaWeb.classes.FabricaConexao;
import br.com.sistemaWeb.classes.ContaCaixa;
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
public class ContaCaixaDao {

    PreparedStatement pstm;
    ResultSet rs;
    private Connection conexao;

    public void salvar(ContaCaixa contaCaixa) throws Exception {
        try {
            String sql = "INSERT INTO contasCaixa(nomecontaCaixa) VALUES(?) ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, contaCaixa.getNomeContaCaixa());

            pstm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
    }

    public void apagar(ContaCaixa contaCaixa) throws SQLException {
        String sql = "DELETE FROM contasCaixa WHERE id = ?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setInt(1, contaCaixa.getId());
        pstm.executeUpdate();
    }

    public void atualizar(ContaCaixa contaCaixa) throws SQLException {
        String sql = "UPDATE contasCaixa SET nomecontaCaixa=? WHERE id =?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setString(1, contaCaixa.getNomeContaCaixa());

        pstm.setInt(2, contaCaixa.getId());
        pstm.executeUpdate();
    }

    public List<ContaCaixa> todosContaCaixas() throws SQLException {
        List<ContaCaixa> listaContaCaixa = new ArrayList<ContaCaixa>();

        String sql = "SELECT id, nomecontaCaixa FROM contasCaixa order by nomeContaCaixa";
        try{
            
        
            
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        rs = pstm.executeQuery();
        }catch(SQLException ex){
           throw ex; 
        }
        while (rs.next()) {
            ContaCaixa tempContaCaixa = new ContaCaixa();
            tempContaCaixa.setId(rs.getInt("id"));
            tempContaCaixa.setNomeContaCaixa(rs.getString("nomeContaCaixa"));

            listaContaCaixa.add(tempContaCaixa);
        }
        return listaContaCaixa;
    }

    public ContaCaixa buscaPorID(ContaCaixa per) throws Exception {
        ContaCaixa tempContaCaixa = null;
        //ResultSet rs;
        try {
            String sql = "select *  from contasCaixa where id=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, per.getId());
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempContaCaixa = new ContaCaixa();
                tempContaCaixa.setId(rs.getInt("id"));
                tempContaCaixa.setNomeContaCaixa(rs.getString("nomeContaCaixa"));

            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        return tempContaCaixa;
    }

    public boolean buscaPorNome(ContaCaixa contaCaixaParam) throws Exception {
        boolean achou = false;
        //ResultSet rs;
        try {
            String sql = "select id, nomeContaCaixa from contasCaixa where nomeContaCaixa=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, contaCaixaParam.getNomeContaCaixa().trim());
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

    public ContaCaixa buscaPorCodigo(int id) throws Exception {
        System.out.println("entrou no buscaESTAD por sigla");
        ContaCaixa tempContaCaixa = null;

        try {
            String sql = "select *  from contasCaixa where id=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempContaCaixa = new ContaCaixa();
                tempContaCaixa.setId(rs.getInt("id"));
                tempContaCaixa.setNomeContaCaixa(rs.getString("nomeContaCaixa"));

            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        if (tempContaCaixa == null) {
            System.out.println("objeto estado null");
        } else {
            System.out.println("objeto pais :" + tempContaCaixa.getNomeContaCaixa());
        }
        return tempContaCaixa;
    }

}
