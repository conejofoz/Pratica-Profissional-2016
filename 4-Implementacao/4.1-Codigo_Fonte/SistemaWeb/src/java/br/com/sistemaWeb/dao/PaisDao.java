package br.com.sistemaWeb.dao;

import br.com.sistemaWeb.classes.FabricaConexao;
import br.com.sistemaWeb.classes.Pais;
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
public class PaisDao {

    PreparedStatement pstm;
    ResultSet rs;
    private Connection conexao;
    

    public void salvar(Pais pais) throws Exception {
        try {
            String sql = "INSERT INTO paises(siglaPais, nomePais) VALUES(?,?) ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, pais.getSiglaPais());
            pstm.setString(2, pais.getNomePais());
            pstm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
    }

    
    public void apagar(Pais pais) throws SQLException {
        String sql = "DELETE FROM paises WHERE id = ?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setInt(1, pais.getId());
        pstm.executeUpdate();
    }

    public void atualizar(Pais pais) throws SQLException {
        String sql = "UPDATE paises SET siglaPais=?, nomePais=? WHERE id =?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setString(1, pais.getSiglaPais());
        pstm.setString(2, pais.getNomePais());
        pstm.setInt(3, pais.getId());
        pstm.executeUpdate();
    }

    public List<Pais> todosPaises() throws SQLException {
        List<Pais> listaPais = new ArrayList<Pais>();

        String sql = "SELECT id, siglaPais, nomePais FROM paises order by nomePais";

        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        rs = pstm.executeQuery();

        while (rs.next()) {
            Pais tempPais = new Pais();
            tempPais.setId(rs.getInt("id"));
            tempPais.setSiglaPais(rs.getString("siglaPais"));
            tempPais.setNomePais(rs.getString("nomePais"));
            listaPais.add(tempPais);
        }
        return listaPais;
    }

    public Pais buscaPorID(Pais per) throws Exception {
        Pais tempPais = null;
        //ResultSet rs;
        try {
            String sql = "select * from paises where id=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, per.getId());
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempPais = new Pais();
                tempPais.setId(rs.getInt("id"));
                tempPais.setSiglaPais(rs.getString("siglaPais"));
                tempPais.setNomePais(rs.getString("nomePais"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        return tempPais;
    }

    
    
    public boolean buscaPorNome(Pais paisParam) throws Exception {
        boolean achou = false;
        //ResultSet rs;
        try {
            String sql = "select id, nomePais from paises where nomePais=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, paisParam.getNomePais().trim());
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
    
    
    
        public Pais buscaPorSigla(String sigla) throws Exception {
            System.out.println("entrou no buscapais por sigla");
        Pais tempPais = null;
        
        try {
            String sql = "select *  from paises where siglapais=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, sigla);
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempPais = new Pais();
                tempPais.setId(rs.getInt("id"));
                tempPais.setSiglaPais(rs.getString("siglaPais"));
                tempPais.setNomePais(rs.getString("nomePais"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        
        if(tempPais==null){
            System.out.println("objeto pais null");
        } else {
            System.out.println("objeto pais :" + tempPais.getNomePais());
        }
        
        return tempPais;
    }

}
