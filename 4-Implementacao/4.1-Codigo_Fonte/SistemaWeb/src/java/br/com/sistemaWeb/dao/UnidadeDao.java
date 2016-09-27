package br.com.sistemaWeb.dao;

import br.com.sistemaWeb.classes.FabricaConexao;
import br.com.sistemaWeb.classes.Unidade;
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
public class UnidadeDao implements Serializable{
    private static final long serialVersionUID = 1L;

    PreparedStatement pstm;
    ResultSet rs;
    private Connection conexao;

    public void salvar(Unidade unidade) throws Exception {
        try {
            String sql = "INSERT INTO unidades(siglaunidade, nomeunidade) VALUES(?,?)";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, unidade.getSiglaUnidade());
            pstm.setString(2, unidade.getNomeUnidade());
            pstm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
    }

    public void apagar(Unidade unidade) throws SQLException {
        String sql = "DELETE FROM unidades WHERE siglaunidade = ?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setString(1, unidade.getSiglaUnidade());
        pstm.executeUpdate();
    }

    public void atualizar(Unidade unidade) throws SQLException {
        String sql = "UPDATE unidades SET nomeunidade=? WHERE siglaunidade =?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setString(1, unidade.getNomeUnidade());
        pstm.setString(4, unidade.getSiglaUnidade());
        pstm.executeUpdate();
    }

    public List<Unidade> todosUnidadees() throws SQLException {
        List<Unidade> listaUnidade = new ArrayList<Unidade>();
        String sql = "SELECT * FROM unidades order by nomeunidade";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        rs = pstm.executeQuery();
        while (rs.next()) {
            Unidade tempUnidade = new Unidade();
            tempUnidade.setSiglaUnidade(rs.getString("siglaunidade"));
            tempUnidade.setNomeUnidade(rs.getString("nomeUnidade"));
            listaUnidade.add(tempUnidade);
        }
        return listaUnidade;
    }

    
    public List<Unidade> consultaUnidadees(String nome) throws SQLException {
        List<Unidade> listaUnidade = new ArrayList<Unidade>();

        String sql = "SELECT * FROM unidades WHERE nomeunidade LIKE ?";

        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setString(1, "%" + nome + "%");
        rs = pstm.executeQuery();

        while (rs.next()) {
            Unidade tempUnidade = new Unidade();
            tempUnidade.setSiglaUnidade(rs.getString("siglaunidade"));
            tempUnidade.setNomeUnidade(rs.getString("nomeunidade"));
            listaUnidade.add(tempUnidade);
            System.out.println(tempUnidade.getNomeUnidade());
        }
        System.out.println("aaa");
        return listaUnidade;
    }
    
    
    public Unidade buscaPorID(Unidade unidade) throws Exception {
        Unidade tempUnidade = null;
        //ResultSet rs;
        try {
            String sql = "select * from unidades where siglaunidade=?";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, unidade.getSiglaUnidade().toUpperCase());
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempUnidade = new Unidade();
                tempUnidade.setSiglaUnidade(rs.getString("siglaunidade"));
                tempUnidade.setNomeUnidade(rs.getString("nomeUnidade"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        return tempUnidade;
    }

    public boolean buscaPorNome(Unidade unidadeParam) throws Exception {
        boolean achou = false;
        //ResultSet rs;
        try {
            String sql = "select siglaunidade, nomeunidade from unidades where nomeunidade=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, unidadeParam.getNomeUnidade().trim());
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

    public Unidade buscaPorCodigo(int id) throws Exception {
        System.out.println("entrou no buscaESTAD por sigla");
        Unidade tempUnidade = null;
        try {
            String sql = "select *  from unidades where siglaunidade=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempUnidade = new Unidade();
                tempUnidade.setSiglaUnidade(rs.getString("siglaunidade"));
                tempUnidade.setNomeUnidade(rs.getString("nomeunidade"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        if (tempUnidade == null) {
            System.out.println("objeto estado null");
        } else {
            System.out.println("objeto pais :" + tempUnidade.getNomeUnidade());
        }
        return tempUnidade;
    }

}
