package br.com.sistemaWeb.dao;

import br.com.sistemaWeb.classes.FabricaConexao;
import br.com.sistemaWeb.classes.Funcionario;
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
public class FuncionarioDao {

    PreparedStatement pstm;
    ResultSet rs;
    private Connection conexao;

    public void salvar(Funcionario funcionario) throws Exception {
        try {
            String sql = "INSERT INTO funcionarios(nomefuncionario, idcargo, email) VALUES(?,?, ?) ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, funcionario.getNomeFuncionario());
            pstm.setInt(2, funcionario.getCargo().getId());
            pstm.setString(3, funcionario.getEmail());
            pstm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
    }

    public void apagar(Funcionario funcionario) throws SQLException {
        String sql = "DELETE FROM funcionarios WHERE id = ?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setInt(1, funcionario.getId());
        pstm.executeUpdate();
    }

    public void atualizar(Funcionario funcionario) throws SQLException {
        String sql = "UPDATE funcionarios SET nomefuncionario=?, idcargo=?, email=? WHERE id =?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setString(1, funcionario.getNomeFuncionario());
        pstm.setInt(2, funcionario.getCargo().getId());
        pstm.setString(3, funcionario.getEmail());
        pstm.setInt(4, funcionario.getId());
        pstm.executeUpdate();
    }

    public List<Funcionario> todosFuncionarios() throws SQLException {
        List<Funcionario> listaFuncionario = new ArrayList<Funcionario>();

        String sql = "SELECT F.id, F.nomefuncionario, F.idcargo, C.nomecargo FROM funcionarios F, cargos C WHERE F.idcargo=C.id order by F.nomeFuncionario";

        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        rs = pstm.executeQuery();

        
        while (rs.next()) {
            Funcionario tempFuncionario = new Funcionario();
            tempFuncionario.setId(rs.getInt("id"));
            tempFuncionario.setNomeFuncionario(rs.getString("nomeFuncionario"));
            tempFuncionario.getCargo().setId(rs.getInt("idcargo"));
            tempFuncionario.getCargo().setNomeCargo(rs.getString("nomecargo"));
            listaFuncionario.add(tempFuncionario);
        }
        conexao.close();
        return listaFuncionario;
    }

    public Funcionario buscaPorID(Funcionario per) throws Exception {
        Funcionario tempFuncionario = null;
        //ResultSet rs;
        try {
            //String sql = "select *  from funcionarios where id=? ";
            String sql = "SELECT F.id, F.nomefuncionario, F.idcargo, F.email, C.nomecargo FROM funcionarios F, cargos C WHERE F.id=? and F.idcargo=C.id";

            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, per.getId());
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempFuncionario = new Funcionario();
                tempFuncionario.setId(rs.getInt("id"));
                tempFuncionario.setNomeFuncionario(rs.getString("nomeFuncionario"));
                tempFuncionario.getCargo().setId(rs.getInt("idcargo"));
                tempFuncionario.getCargo().setNomeCargo(rs.getString("nomecargo"));
                tempFuncionario.setEmail(rs.getString("email"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        return tempFuncionario;
    }

    public boolean buscaPorNome(Funcionario funcionarioParam) throws Exception {
        boolean achou = false;
        //ResultSet rs;
        try {
            String sql = "select id, nomeFuncionario from funcionarios where nomeFuncionario=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, funcionarioParam.getNomeFuncionario().trim());
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

    public Funcionario buscaPorCodigo(int id) throws Exception {
        System.out.println("entrou no buscaESTAD por sigla");
        Funcionario tempFuncionario = null;

        try {
            String sql = "select *  from funcionarios where id=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempFuncionario = new Funcionario();
                tempFuncionario.setId(rs.getInt("id"));
                tempFuncionario.setNomeFuncionario(rs.getString("nomeFuncionario"));
                tempFuncionario.getCargo().setId(rs.getInt("idcargo"));
                tempFuncionario.getCargo().setNomeCargo("nomecargo");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        if (tempFuncionario == null) {
            System.out.println("objeto estado null");
        } else {
            System.out.println("objeto pais :" + tempFuncionario.getNomeFuncionario());
        }

        return tempFuncionario;
    }

}
