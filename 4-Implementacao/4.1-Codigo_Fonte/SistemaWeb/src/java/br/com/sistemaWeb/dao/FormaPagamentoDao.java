package br.com.sistemaWeb.dao;

import br.com.sistemaWeb.classes.FabricaConexao;
import br.com.sistemaWeb.classes.FormaPagamento;
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
public class FormaPagamentoDao {

    PreparedStatement pstm;
    ResultSet rs;
    private Connection conexao;

    public void salvar(FormaPagamento formaPagamento) throws Exception {
        try {
            String sql = "INSERT INTO formasPagamento(nomeformaPagamento) VALUES(?) ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, formaPagamento.getNomeFormaPagamento());
   

            pstm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
    }

    public void apagar(FormaPagamento formaPagamento) throws SQLException {
        String sql = "DELETE FROM formasPagamento WHERE id = ?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setInt(1, formaPagamento.getId());
        pstm.executeUpdate();
    }

    public void atualizar(FormaPagamento formaPagamento) throws SQLException {
        String sql = "UPDATE formasPagamento SET nomeformaPagamento=? WHERE id =?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setString(1, formaPagamento.getNomeFormaPagamento());

        pstm.setInt(2, formaPagamento.getId());
        pstm.executeUpdate();
    }

    public List<FormaPagamento> todosFormaPagamentos() throws SQLException {
        List<FormaPagamento> listaFormaPagamento = new ArrayList<FormaPagamento>();

        String sql = "SELECT id, nomeformaPagamento FROM formasPagamento order by nomeFormaPagamento";
        try{
            
        
            
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        rs = pstm.executeQuery();
        }catch(SQLException ex){
           throw ex; 
        }
        while (rs.next()) {
            FormaPagamento tempFormaPagamento = new FormaPagamento();
            tempFormaPagamento.setId(rs.getInt("id"));
            tempFormaPagamento.setNomeFormaPagamento(rs.getString("nomeFormaPagamento"));


            listaFormaPagamento.add(tempFormaPagamento);
        }
        conexao.close();
        return listaFormaPagamento;
    }

    public FormaPagamento buscaPorID(FormaPagamento per) throws Exception {
        FormaPagamento tempFormaPagamento = null;
        //ResultSet rs;
        try {
            String sql = "select *  from formasPagamento where id=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, per.getId());
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempFormaPagamento = new FormaPagamento();
                tempFormaPagamento.setId(rs.getInt("id"));
                tempFormaPagamento.setNomeFormaPagamento(rs.getString("nomeFormaPagamento"));
                

            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        return tempFormaPagamento;
    }

    public boolean buscaPorNome(FormaPagamento formaPagamentoParam) throws Exception {
        boolean achou = false;
        //ResultSet rs;
        try {
            String sql = "select id, nomeFormaPagamento from formasPagamento where nomeFormaPagamento=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, formaPagamentoParam.getNomeFormaPagamento().trim());
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

    public FormaPagamento buscaPorCodigo(int id) throws Exception {
        System.out.println("entrou no buscaESTAD por sigla");
        FormaPagamento tempFormaPagamento = null;

        try {
            String sql = "select *  from formasPagamento where id=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempFormaPagamento = new FormaPagamento();
                tempFormaPagamento.setId(rs.getInt("id"));
                tempFormaPagamento.setNomeFormaPagamento(rs.getString("nomeFormaPagamento"));

            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        if (tempFormaPagamento == null) {
            System.out.println("objeto estado null");
        } else {
            System.out.println("objeto pais :" + tempFormaPagamento.getNomeFormaPagamento());
        }
        return tempFormaPagamento;
    }

}
