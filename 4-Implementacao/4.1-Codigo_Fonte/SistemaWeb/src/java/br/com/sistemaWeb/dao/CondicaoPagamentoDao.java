package br.com.sistemaWeb.dao;

import br.com.sistemaWeb.classes.FabricaConexao;
import br.com.sistemaWeb.classes.CondicaoPagamento;
import br.com.sistemaWeb.classes.Parcelas;
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
public class CondicaoPagamentoDao {

    PreparedStatement pstm;
    ResultSet rs;
    private Connection conexao;
    private int parcelaNova;

    public void salvar(CondicaoPagamento condicaoPagamento, List<Parcelas> listaParcelas) throws Exception {
        try {
            String sql = "INSERT INTO condicoesPagamento(nomecondicaoPagamento) VALUES(?) ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, condicaoPagamento.getNomeCondicaoPagamento());
            pstm.executeUpdate();
            pstm.close();
            
            String sqlIdCondicao = "SELECT MAX(id) FROM condicoespagamento";
            pstm = conexao.prepareStatement(sqlIdCondicao);
            rs = pstm.executeQuery();
            while (rs.next()) {
                parcelaNova = rs.getInt(1);
            }

            for (Parcelas det : listaParcelas) {
                String sqlDetalhe = "INSERT INTO parcelas (idcondicaopagamento, numeroparcela, dias, porcentagem, idformapagamento) values(?, ?, ?, ?, ?)";
                pstm = conexao.prepareStatement(sqlDetalhe);
                pstm.setInt(1, parcelaNova);
                pstm.setInt(2, det.getNumero());
                pstm.setDouble(3, det.getDias());
                pstm.setDouble(4, det.getPorcentagem());
                pstm.setInt(5, det.getFormaPagamento().getId());
                pstm.executeUpdate();
                pstm.close();
            }
            
            
            
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
    }

    public void apagar(CondicaoPagamento condicaoPagamento) throws SQLException {
        String sql = "DELETE FROM condicoesPagamento WHERE id = ?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setInt(1, condicaoPagamento.getId());
        pstm.executeUpdate();
    }

    public void atualizar(CondicaoPagamento condicaoPagamento) throws SQLException {
        String sql = "UPDATE condicoesPagamento SET nomecondicaoPagamento=? WHERE id =?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setString(1, condicaoPagamento.getNomeCondicaoPagamento());

        pstm.setInt(2, condicaoPagamento.getId());
        pstm.executeUpdate();
    }

    public List<CondicaoPagamento> todosCondicaoPagamentos() throws SQLException {
        List<CondicaoPagamento> listaCondicaoPagamento = new ArrayList<CondicaoPagamento>();

        String sql = "SELECT id, nomecondicaoPagamento FROM condicoesPagamento order by nomeCondicaoPagamento";
        try{
            
        
            
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        rs = pstm.executeQuery();
        }catch(SQLException ex){
           throw ex; 
        }
        while (rs.next()) {
            CondicaoPagamento tempCondicaoPagamento = new CondicaoPagamento();
            tempCondicaoPagamento.setId(rs.getInt("id"));
            tempCondicaoPagamento.setNomeCondicaoPagamento(rs.getString("nomeCondicaoPagamento"));


            listaCondicaoPagamento.add(tempCondicaoPagamento);
        }
        conexao.close();
        return listaCondicaoPagamento;
    }

    public CondicaoPagamento buscaPorID(CondicaoPagamento per) throws Exception {
        CondicaoPagamento tempCondicaoPagamento = null;
        //ResultSet rs;
        try {
            String sql = "select *  from condicoesPagamento where id=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, per.getId());
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempCondicaoPagamento = new CondicaoPagamento();
                tempCondicaoPagamento.setId(rs.getInt("id"));
                tempCondicaoPagamento.setNomeCondicaoPagamento(rs.getString("nomeCondicaoPagamento"));
                tempCondicaoPagamento.setQuantidadeParcelas(rs.getInt("qtdparcelas"));
                

            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        return tempCondicaoPagamento;
    }

    public boolean buscaPorNome(CondicaoPagamento condicaoPagamentoParam) throws Exception {
        boolean achou = false;
        //ResultSet rs;
        try {
            String sql = "select id, nomeCondicaoPagamento from condicoesPagamento where nomeCondicaoPagamento=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, condicaoPagamentoParam.getNomeCondicaoPagamento().trim());
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

    public CondicaoPagamento buscaPorCodigo(int id) throws Exception {
        System.out.println("entrou no buscaESTAD por sigla");
        CondicaoPagamento tempCondicaoPagamento = null;

        try {
            String sql = "select *  from condicoesPagamento where id=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempCondicaoPagamento = new CondicaoPagamento();
                tempCondicaoPagamento.setId(rs.getInt("id"));
                tempCondicaoPagamento.setNomeCondicaoPagamento(rs.getString("nomeCondicaoPagamento"));

            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        if (tempCondicaoPagamento == null) {
            System.out.println("objeto estado null");
        } else {
            System.out.println("objeto pais :" + tempCondicaoPagamento.getNomeCondicaoPagamento());
        }
        return tempCondicaoPagamento;
    }

}
