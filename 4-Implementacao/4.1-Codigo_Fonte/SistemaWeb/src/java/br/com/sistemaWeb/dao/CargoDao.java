package br.com.sistemaWeb.dao;

import br.com.sistemaWeb.classes.FabricaConexao;
import br.com.sistemaWeb.classes.Cargo;
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
public class CargoDao {

    PreparedStatement pstm;
    ResultSet rs;
    private Connection conexao;

    public void salvar(Cargo cargo) throws Exception {
        try {
            String sql = "INSERT INTO cargos(nomecargo) VALUES(?) ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, cargo.getNomeCargo());

            pstm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
    }

    public void apagar(Cargo cargo) throws SQLException {
        String sql = "DELETE FROM cargos WHERE id = ?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setInt(1, cargo.getId());
        pstm.executeUpdate();
    }

    public void atualizar(Cargo cargo) throws SQLException {
        String sql = "UPDATE cargos SET nomecargo=? WHERE id =?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setString(1, cargo.getNomeCargo());

        pstm.setInt(2, cargo.getId());
        pstm.executeUpdate();
    }

    public List<Cargo> todosCargos() throws SQLException {
        List<Cargo> listaCargo = new ArrayList<Cargo>();

        String sql = "SELECT id, nomecargo FROM cargos order by nomeCargo";

        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        rs = pstm.executeQuery();

        while (rs.next()) {
            Cargo tempCargo = new Cargo();
            tempCargo.setId(rs.getInt("id"));
            tempCargo.setNomeCargo(rs.getString("nomeCargo"));

            listaCargo.add(tempCargo);
        }
        return listaCargo;
    }

    public Cargo buscaPorID(Cargo per) throws Exception {
        Cargo tempCargo = null;
        //ResultSet rs;
        try {
            String sql = "select id, nomecargo from cargos where id=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, per.getId());
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempCargo = new Cargo();
                tempCargo.setId(rs.getInt("id"));
                tempCargo.setNomeCargo(rs.getString("nomecargo"));
                System.out.println("nome do cargo no banco "+tempCargo.getNomeCargo());
            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        return tempCargo;
    }

    public boolean buscaPorNome(Cargo cargoParam) throws Exception {
        boolean achou = false;
        //ResultSet rs;
        try {
            String sql = "select id, nomeCargo from cargos where nomeCargo=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, cargoParam.getNomeCargo().trim());
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

    public Cargo buscaPorCodigo(int id) throws Exception {
        System.out.println("entrou no buscaESTAD por sigla");
        Cargo tempCargo = null;

        try {
            String sql = "select *  from cargos where id=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempCargo = new Cargo();
                tempCargo.setId(rs.getInt("id"));
                tempCargo.setNomeCargo(rs.getString("nomeCargo"));

            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        if (tempCargo == null) {
            System.out.println("objeto estado null");
        } else {
            System.out.println("objeto pais :" + tempCargo.getNomeCargo());
        }

        return tempCargo;
    }

}
