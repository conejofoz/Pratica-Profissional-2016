package br.com.sistemaWeb.dao;

import br.com.sistemaWeb.classes.ItensCompra;
import br.com.sistemaWeb.classes.FabricaConexao;
import br.com.sistemaWeb.classes.Compra;
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
public class CompraDao {

    PreparedStatement pstm;
    ResultSet rs;
    private Connection conexao;

    public void salvar(Compra compra, List<ItensCompra> listaDetalhe) throws Exception {
        /*for (ItensCompra det : listaDetalhe) {
            //System.out.println(det.getProduto().getId() + " " + det.getProduto().getNomeProduto() + " " + det.getQuantidade() + " " + det.getPreco());
        }
        */
        try {
            String sql = "INSERT INTO compras(nota, idfornecedor, serie, modelo, total, totalnota, emissao) VALUES(?,?,?,?,?,?,?) ";
            conexao = FabricaConexao.conectar();
            this.conexao.setAutoCommit(false);
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, compra.getNota());
            pstm.setInt(2, compra.getFornecedor().getId());
            pstm.setString(3, compra.getSerie());
            pstm.setString(4, compra.getModelo());
            pstm.setDouble(5, compra.getTotalProdutos());
            pstm.setDouble(6, compra.getTotalNota());
            pstm.setDate(7, new java.sql.Date(compra.getEmissao().getTime()));
            pstm.executeUpdate();
            pstm.close();

            for (ItensCompra det : listaDetalhe) {
                String sqlDetalhe = "INSERT INTO itenscompra (nota, idfornecedor, serie, modelo, idproduto, quantidade, preco) values(?, ?, ?, ?, ?, ?, ?)";
                pstm = conexao.prepareStatement(sqlDetalhe);
                pstm.setInt(1, compra.getNota());
                pstm.setInt(2, compra.getFornecedor().getId());
                pstm.setString(3, compra.getSerie());
                pstm.setString(4, compra.getModelo());
                pstm.setInt(5, det.getProduto().getId());
                pstm.setDouble(6, det.getQuantidade());
                pstm.setDouble(7, det.getPreco());
                pstm.executeUpdate();
                pstm.close();
                //System.out.println(det.getProduto().getId() + " " + det.getProduto().getNomeProduto() + " " + det.getQuantidade() + " " + det.getPreco());
            }

            for (ItensCompra det : listaDetalhe) {
                String sqlAlta = "UPDATE produtos SET estoqueatual = estoqueatual + ? WHERE id = ?";
                pstm = conexao.prepareStatement(sqlAlta);
                pstm.setDouble(1, det.getQuantidade());
                pstm.setInt(2, det.getProduto().getId());
                pstm.executeUpdate();
                pstm.close();
                //System.out.println(det.getProduto().getId() + " " + det.getProduto().getNomeProduto() + " " + det.getQuantidade() + " " + det.getPreco());
            }

            this.conexao.commit();
        } catch (Exception e) {
            this.conexao.rollback();
            throw e;
        } finally {
            conexao.close();
        }
    }

    public void apagar(Compra compra) throws SQLException {
        String sql = "DELETE FROM compras WHERE nota = ? AND idfornecedor = ? AND serie = ? AND modelo = ? ";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setInt(1, compra.getNota());
        pstm.setInt(2, compra.getFornecedor().getId());
        pstm.setString(3, compra.getSerie());
        pstm.setString(4, compra.getModelo());
        pstm.executeUpdate();
    }

    /* public void atualizar(Compra compra) throws SQLException {
     String sql = "UPDATE compras SET nomecompra=?, preco=? WHERE id =?";
     conexao = FabricaConexao.conectar();
     pstm = conexao.prepareStatement(sql);
     pstm.setDate(1, new java.sql.Date(compra.getData().getTime()));
     pstm.setDouble(2, compra.getTotal());
     pstm.setInt(3, compra.getFornecedor().getId());
     pstm.executeUpdate();
     }
     */
    public List<Compra> todosCompras() throws SQLException {
        List<Compra> listaCompra = new ArrayList<Compra>();

        String sql = "SELECT C.*, F.nomefornecedor FROM compras C, fornecedores F WHERE C.idfornecedor = F.id order by emissao";

        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        rs = pstm.executeQuery();

        while (rs.next()) {
            Compra tempCompra = new Compra();
            tempCompra.setNota(rs.getInt("nota"));
            tempCompra.setModelo(rs.getString("modelo"));
            tempCompra.setSerie(rs.getString("serie"));
            tempCompra.setTotalProdutos(rs.getDouble("total"));
            tempCompra.setTotalNota(rs.getDouble("totalnota"));
            tempCompra.setEmissao(rs.getDate("emissao"));
            tempCompra.getFornecedor().setId(rs.getInt("idfornecedor"));
            tempCompra.getFornecedor().setNomeFornecedor(rs.getString("nomefornecedor"));
            listaCompra.add(tempCompra);
        }
        return listaCompra;
    }
    
    
    public List<ItensCompra> todosItensCompra(Compra compra) throws SQLException {
        List<ItensCompra> listaItensCompra = new ArrayList<ItensCompra>();

        String sql = "SELECT I.*, P.id, P.nomeproduto FROM itenscompra I, produtos P WHERE I.nota = ? AND I.idfornecedor = ? AND I.serie = ? AND I.modelo = ? AND I.idproduto=P.id ";

        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setInt(1, compra.getNota());
        pstm.setInt(2, compra.getFornecedor().getId());
        pstm.setString(3, compra.getSerie());
        pstm.setString(4, compra.getModelo());
        rs = pstm.executeQuery();

        while (rs.next()) {
            ItensCompra tempItensCompra = new ItensCompra();
            tempItensCompra.getProduto().setId(rs.getInt("idproduto"));
            tempItensCompra.getProduto().setNomeProduto(rs.getString("nomeproduto"));
            tempItensCompra.setQuantidade(rs.getDouble("quantidade"));
            tempItensCompra.setPreco(rs.getDouble("preco"));
            listaItensCompra.add(tempItensCompra);
        }
        return listaItensCompra;
    }

    public Compra buscaPorID(Compra compra) throws Exception {
        Compra tempCompra = null;
        //ResultSet rs;
        try {
            String sql = "select *  from compras WHERE nota = ? AND idfornecedor = ? AND serie = ? AND modelo = ?  ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, compra.getNota());
            pstm.setInt(2, compra.getFornecedor().getId());
            pstm.setString(3, compra.getSerie());
            pstm.setString(4, compra.getModelo());
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempCompra = new Compra();
                tempCompra.setNota(rs.getInt("nota"));
                tempCompra.setModelo(rs.getString("modelo"));
                tempCompra.setSerie(rs.getString("serie"));
                tempCompra.setTotalProdutos(rs.getDouble("total"));
                tempCompra.setEmissao(rs.getDate("emissao"));
                tempCompra.getFornecedor().setId(rs.getInt("idfornecedor"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        return tempCompra;
    }

}
