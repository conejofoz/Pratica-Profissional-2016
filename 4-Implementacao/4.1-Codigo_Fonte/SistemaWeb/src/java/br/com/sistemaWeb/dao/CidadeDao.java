package br.com.sistemaWeb.dao;

import br.com.sistemaWeb.classes.FabricaConexao;
import br.com.sistemaWeb.classes.Cidade;
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
public class CidadeDao {

    PreparedStatement pstm;
    ResultSet rs;
    private Connection conexao;
    

    public void salvar(Cidade cidade) throws Exception {
        try {
            String sql = "INSERT INTO cidades(nomeCidade, idEstado) VALUES(?, ?) ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, cidade.getNomeCidade());
            pstm.setInt(2, cidade.getEstado().getId());
            pstm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
    }

    
    public void apagar(Cidade cidade) throws SQLException {
        String sql = "DELETE FROM cidades WHERE id = ?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setInt(1, cidade.getId());
        pstm.executeUpdate();
    }

    public void atualizar(Cidade cidade) throws SQLException {
        String sql = "UPDATE cidades SET nomeCidade=?, idEstado=? WHERE id =?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setString(1, cidade.getNomeCidade());
        pstm.setInt(2, cidade.getEstado().getId());
        pstm.setInt(3, cidade.getId());
        pstm.executeUpdate();
    }

    public List<Cidade> todosCidadees() throws SQLException {
        List<Cidade> listaCidade = new ArrayList<Cidade>();

        String sql = "SELECT C.id, C.nomecidade, C.idestado, E.nomeestado, E.siglaestado FROM cidades C, estados E WHERE C.idestado=E.id order by C.nomecidade";

        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        rs = pstm.executeQuery();

        while (rs.next()) {
            Cidade tempCidade = new Cidade();
            tempCidade.setId(rs.getInt("id"));
            tempCidade.setNomeCidade(rs.getString("nomeCidade"));
            tempCidade.getEstado().setId(rs.getInt("idEstado"));
            tempCidade.getEstado().setNomeEstado(rs.getString("nomeestado"));
            tempCidade.getEstado().setSiglaEstado(rs.getString("siglaestado"));
            listaCidade.add(tempCidade);
        }
        return listaCidade;
    }

    public Cidade buscaPorID(Cidade per) throws Exception {
        Cidade tempCidade = null;
        //ResultSet rs;
        try {
            String sql = "select C.*, E.* from cidades C, estados E where C.id=? AND C.idestado=E.id ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, per.getId());
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempCidade = new Cidade();
                tempCidade.setId(rs.getInt("id"));
                tempCidade.setNomeCidade(rs.getString("nomeCidade"));
                tempCidade.getEstado().setId(rs.getInt("idEstado"));
                tempCidade.getEstado().setSiglaEstado(rs.getString("siglaestado"));
                tempCidade.getEstado().setNomeEstado(rs.getString("nomeestado"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        return tempCidade;
    }

    
    
    public boolean buscaPorNome(Cidade cidadeParam) throws Exception {
        boolean achou = false;
        //ResultSet rs;
        try {
            String sql = "select id, nomeCidade from cidades where nomeCidade=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, cidadeParam.getNomeCidade().trim());
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
    
    
    public Cidade buscaPorNome2(Cidade per) throws Exception {
        Cidade tempCidade = null;
        try {
            String sql = "select C.*, E.* from cidades C, estados E where C.nomecidade=? AND C.idestado=E.id ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, per.getNomeCidade().toUpperCase());
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempCidade = new Cidade();
                tempCidade.setId(rs.getInt("id"));
                tempCidade.setNomeCidade(rs.getString("nomeCidade"));
                tempCidade.getEstado().setId(rs.getInt("idEstado"));
                tempCidade.getEstado().setSiglaEstado(rs.getString("siglaestado"));
                tempCidade.getEstado().setNomeEstado(rs.getString("nomeestado"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        return tempCidade;
    }

}
