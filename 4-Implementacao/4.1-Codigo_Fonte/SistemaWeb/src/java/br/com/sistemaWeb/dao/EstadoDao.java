package br.com.sistemaWeb.dao;

import br.com.sistemaWeb.classes.FabricaConexao;
import br.com.sistemaWeb.classes.Estado;
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
public class EstadoDao {

    PreparedStatement pstm;
    ResultSet rs;
    private Connection conexao;

    public void salvar(Estado estado) throws Exception {
        try {
            String sql = "INSERT INTO estados(siglaEstado, nomeEstado, idPais) VALUES(?,?, ?) ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, estado.getSiglaEstado());
            pstm.setString(2, estado.getNomeEstado());
            pstm.setInt(3, estado.getPais().getId());
            pstm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
    }

    public void apagar(Estado estado) throws SQLException {
        String sql = "DELETE FROM estados WHERE id = ?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setInt(1, estado.getId());
        pstm.executeUpdate();
    }

    public void atualizar(Estado estado) throws SQLException {
        String sql = "UPDATE estados SET siglaEstado=?, nomeEstado=?, idPais=? WHERE id =?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setString(1, estado.getSiglaEstado());
        pstm.setString(2, estado.getNomeEstado());
        pstm.setInt(3, estado.getPais().getId());
        pstm.setInt(4, estado.getId());
        pstm.executeUpdate();
    }

    public List<Estado> todosEstadoes() throws SQLException {
        List<Estado> listaEstado = new ArrayList<Estado>();

        String sql = "SELECT E.id, E.siglaestado, E.nomeestado, E.idpais, P.nomepais FROM estados E, paises P WHERE E.idpais=P.id order by E.nomeestado";

        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        rs = pstm.executeQuery();

        while (rs.next()) {
            Estado tempEstado = new Estado();
            tempEstado.setId(rs.getInt("id"));
            tempEstado.setSiglaEstado(rs.getString("siglaEstado"));
            tempEstado.setNomeEstado(rs.getString("nomeEstado"));
            tempEstado.getPais().setId(rs.getInt("idPais"));
            tempEstado.getPais().setNomePais(rs.getString("nomepais"));
            listaEstado.add(tempEstado);
        }
        conexao.close();
        return listaEstado;
    }

    public Estado buscaPorID(Estado per) throws Exception {
        Estado tempEstado = null;
        //ResultSet rs;
        try {
            String sql = "select E.*, P.* from estados E, paises P where E.id=? AND E.idpais=P.id ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, per.getId());
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempEstado = new Estado();
                tempEstado.setId(rs.getInt("id"));
                tempEstado.setSiglaEstado(rs.getString("siglaEstado"));
                tempEstado.setNomeEstado(rs.getString("nomeEstado"));
                tempEstado.getPais().setId(rs.getInt("idPais"));
                tempEstado.getPais().setNomePais(rs.getString("nomepais"));
                tempEstado.getPais().setSiglaPais(rs.getString("siglapais"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        return tempEstado;
    }

    public boolean buscaPorNome(Estado estadoParam) throws Exception {
        boolean achou = false;
        //ResultSet rs;
        try {
            String sql = "select id, nomeEstado, siglaestado from estados where nomeEstado=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, estadoParam.getNomeEstado().trim());
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
    
    
    
      public Estado buscaPorSigla(String sigla) throws Exception {
        System.out.println("entrou no buscaESTAD por sigla");
        Estado tempEstado = null;

        try {
            String sql = "select *  from estados where siglaestado=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, sigla);
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempEstado = new Estado();
                tempEstado.setId(rs.getInt("id"));
                tempEstado.setSiglaEstado(rs.getString("siglaEstado"));
                tempEstado.setNomeEstado(rs.getString("nomeEstado"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
      if(tempEstado==null){
            System.out.println("objeto estado null");
        } else {
            System.out.println("objeto pais :" + tempEstado.getNomeEstado());
        }
        
        return tempEstado;
    }

}
