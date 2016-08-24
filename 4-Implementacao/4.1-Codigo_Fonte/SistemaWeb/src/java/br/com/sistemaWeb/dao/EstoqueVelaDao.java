package br.com.sistemaWeb.dao;

import br.com.sistemaWeb.classes.FabricaConexao;
import br.com.sistemaWeb.classes.EstoqueVela;
import br.com.sistemaWeb.classes.EstoqueVela;
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
public class EstoqueVelaDao {

    PreparedStatement pstm;
    ResultSet rs;
    private Connection conexao;
    private Connection conexaoF;

    public void salvar(EstoqueVela estoqueVela) throws Exception {
        try {
            String sql = "INSERT INTO estoqueVelas(nomeestoqueVela) VALUES(?) ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
  //          pstm.setString(1, estoqueVela.getNomeEstoqueVela());

            pstm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
    }

    public void apagar(EstoqueVela estoqueVela) throws SQLException {
        String sql = "DELETE FROM estoqueVelas WHERE id = ?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setInt(1, estoqueVela.getId());
        pstm.executeUpdate();
    }

    public void atualizar(EstoqueVela estoqueVela) throws SQLException {
        String sql = "UPDATE estoqueVelas SET nomeestoqueVela=? WHERE id =?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
       // pstm.setString(1, estoqueVela.getNomeEstoqueVela());

        pstm.setInt(2, estoqueVela.getId());
        pstm.executeUpdate();
    }

    public List<EstoqueVela> todosEstoqueVela() throws SQLException {
        List<EstoqueVela> listaEstoqueVela = new ArrayList<EstoqueVela>();

        String sql = "SELECT cp_codpro, cp_descridp, cp_deposito1, cp_codgru FROM produtos WHERE cp_codgru = ? order by cp_descridp";

        conexao = FabricaConexao.conectarF();
        pstm = conexao.prepareStatement(sql);
        pstm.setString(1, "9157");
        rs = pstm.executeQuery();

        while (rs.next()) {
            EstoqueVela tempEstoqueVela = new EstoqueVela();
            tempEstoqueVela.setId(rs.getInt("cp_codpro"));
            tempEstoqueVela.setDescricao(rs.getString("cp_descridp"));
            tempEstoqueVela.setQuantidade(rs.getDouble("cp_deposito1"));

            listaEstoqueVela.add(tempEstoqueVela);
            System.out.println(rs.getString("cp_descridp"));
        }
        conexao.close();
        return listaEstoqueVela;
    }

    public EstoqueVela buscaPorID(EstoqueVela per) throws Exception {
        EstoqueVela tempEstoqueVela = null;
        //ResultSet rs;
        try {
            String sql = "select id, nomeestoqueVela from estoqueVelas where id=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, per.getId());
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempEstoqueVela = new EstoqueVela();
                tempEstoqueVela.setId(rs.getInt("id"));
   //             tempEstoqueVela.setNomeEstoqueVela(rs.getString("nomeestoqueVela"));
     //          System.out.println("nome do estoqueVela no banco "+tempEstoqueVela.getNomeEstoqueVela());
            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        return tempEstoqueVela;
    }

    public boolean buscaPorNome(EstoqueVela estoqueVelaParam) throws Exception {
        boolean achou = false;
        //ResultSet rs;
        try {
            String sql = "select id, nomeEstoqueVela from estoqueVelas where nomeEstoqueVela=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
     //       pstm.setString(1, estoqueVelaParam.getNomeEstoqueVela().trim());
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

    public EstoqueVela buscaPorCodigo(int id) throws Exception {
        System.out.println("entrou no buscaESTAD por sigla");
        EstoqueVela tempEstoqueVela = null;

        try {
            String sql = "select *  from estoqueVelas where id=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempEstoqueVela = new EstoqueVela();
                tempEstoqueVela.setId(rs.getInt("id"));
   //             tempEstoqueVela.setNomeEstoqueVela(rs.getString("nomeEstoqueVela"));

            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        if (tempEstoqueVela == null) {
            System.out.println("objeto estado null");
        } else {
   //         System.out.println("objeto pais :" + tempEstoqueVela.getNomeEstoqueVela());
        }

        return tempEstoqueVela;
    }

}
