package br.com.sistemaWeb.dao;

import br.com.sistemaWeb.classes.DetalheVenda;
import br.com.sistemaWeb.classes.FabricaConexao;
import br.com.sistemaWeb.classes.Produto;
import br.com.sistemaWeb.classes.Venda;
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
public class VendaDao {

    PreparedStatement pstm;
    ResultSet rs;
    private Connection conexao;
    private int vendaNova;

    public void salvar(Venda venda, List<DetalheVenda> listaDetalhe) throws Exception {
        for (DetalheVenda det : listaDetalhe) {
            System.out.println(det.getProduto().getId() + " " + det.getProduto().getNomeProduto() + " " + det.getQuantidade() + " " + det.getPreco());
        }
        try {
            String sql = "INSERT INTO vendas(data, total, idcliente) VALUES(?,?,?) ";
            conexao = FabricaConexao.conectar();
            this.conexao.setAutoCommit(false);
            pstm = conexao.prepareStatement(sql);
            pstm.setDate(1, new java.sql.Date(venda.getData().getTime()));
            pstm.setDouble(2, venda.getTotal());
            pstm.setInt(3, venda.getCliente().getId());
            pstm.executeUpdate();
            pstm.close();

            String sqlVendaGerada = "SELECT MAX(id) FROM vendas";
            pstm = conexao.prepareStatement(sqlVendaGerada);
            rs = pstm.executeQuery();
            while (rs.next()) {
                vendaNova = rs.getInt(1);
            }

            for (DetalheVenda det : listaDetalhe) {
                String sqlDetalhe = "INSERT INTO detalheVendas (idvenda, idproduto, quantidade, preco) values(?, ?, ?, ?)";
                pstm = conexao.prepareStatement(sqlDetalhe);
                pstm.setInt(1, vendaNova);
                pstm.setInt(2, det.getProduto().getId());
                pstm.setDouble(3, det.getQuantidade());
                pstm.setDouble(4, det.getPreco());
                pstm.executeUpdate();
                pstm.close();
                System.out.println(det.getProduto().getId() + " " + det.getProduto().getNomeProduto() + " " + det.getQuantidade() + " " + det.getPreco());
            }
            
            for (DetalheVenda det : listaDetalhe) {
                String sqlBaixa = "UPDATE produtos SET estoqueatual = estoqueatual - ? WHERE id = ?";
                pstm = conexao.prepareStatement(sqlBaixa);
                pstm.setDouble(1, det.getQuantidade());
                pstm.setInt(2, det.getProduto().getId());
                pstm.executeUpdate();
                pstm.close();
                System.out.println(det.getProduto().getId() + " " + det.getProduto().getNomeProduto() + " " + det.getQuantidade() + " " + det.getPreco());
            }
            
            this.conexao.commit();
        } catch (Exception e) {
            this.conexao.rollback();
            throw e;
        } finally {
            conexao.close();
        }
    }

    public void apagar(Venda venda) throws SQLException {
        String sql = "DELETE FROM vendas WHERE id = ?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setInt(1, venda.getId());
        pstm.executeUpdate();
    }

    public void atualizar(Venda venda) throws SQLException {
        String sql = "UPDATE vendas SET nomevenda=?, preco=? WHERE id =?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setDate(1, new java.sql.Date(venda.getData().getTime()));
        pstm.setDouble(2, venda.getTotal());
        pstm.setInt(3, venda.getCliente().getId());
        pstm.executeUpdate();
    }

    public List<Venda> todosVendas() throws SQLException {
        List<Venda> listaVenda = new ArrayList<Venda>();

        String sql = "SELECT * FROM vendas order by data";

        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        rs = pstm.executeQuery();

        while (rs.next()) {
            Venda tempVenda = new Venda();
            tempVenda.setId(rs.getInt("id"));
            tempVenda.setData(rs.getDate("data"));
            tempVenda.setTotal(rs.getDouble("total"));
            listaVenda.add(tempVenda);
        }
        conexao.close();
        return listaVenda;
    }

    public Venda buscaPorID(Venda per) throws Exception {
        Venda tempVenda = null;
        //ResultSet rs;
        try {
            String sql = "select *  from vendas where id=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, per.getId());
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempVenda = new Venda();
                tempVenda.setId(rs.getInt("id"));
                tempVenda.setData(rs.getDate("data"));
                tempVenda.setTotal(rs.getDouble("total"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        return tempVenda;
    }

}
