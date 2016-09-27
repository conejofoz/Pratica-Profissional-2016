package br.com.sistemaWeb.dao;

import br.com.sistemaWeb.classes.FabricaConexao;
import br.com.sistemaWeb.classes.Fornecedor;
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
public class FornecedorDao {

    PreparedStatement pstm;
    ResultSet rs;
    private Connection conexao;

    public void salvar(Fornecedor fornecedor) throws Exception {
        try {
            String sql = "INSERT INTO fornecedores(nomefornecedor, tipopessoa, sexo, cep, idcidade, endereco, numero, complemento, bairro, telefone, telefonecomercial, celular, fax, email, site, documento, cpf ";
            if(fornecedor.getDataNascimento()!=null){
                sql = sql + ", datanascimento";
            }
            sql = sql + ", cnpj) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
            if(fornecedor.getDataNascimento()!=null){
                sql = sql + ",?"; //data nascimento
            }
            sql = sql + ",?) ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, fornecedor.getNomeFornecedor());
            pstm.setString(2, fornecedor.getTipoPessoa());
            pstm.setString(3, fornecedor.getSexo());
            pstm.setString(4, fornecedor.getCep());
            pstm.setInt(5, fornecedor.getCidade().getId());
            pstm.setString(6, fornecedor.getEndereco());
            pstm.setString(7, fornecedor.getNumero());
            pstm.setString(8, fornecedor.getComplemento());
            
            pstm.setString(9, fornecedor.getBairro());
            pstm.setString(10, fornecedor.getTelefone());
            pstm.setString(11, fornecedor.getTelefoneComercial());
            pstm.setString(12, fornecedor.getCelular());
            pstm.setString(13, fornecedor.getFax());
            pstm.setString(14, fornecedor.getEmail());
            pstm.setString(15, fornecedor.getSite());
            pstm.setString(16, fornecedor.getDocumento());
            pstm.setString(17, fornecedor.getCpf());
            int nParamSql = 18;
            if(fornecedor.getDataNascimento()!=null){
               pstm.setDate(nParamSql, new java.sql.Date(fornecedor.getDataNascimento().getTime()));
               nParamSql++;
            }
            pstm.setString(nParamSql, fornecedor.getCnpj());
            
            pstm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
    }

    public void apagar(Fornecedor fornecedor) throws SQLException {
        String sql = "DELETE FROM fornecedor WHERE id = ?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setInt(1, fornecedor.getId());
        pstm.executeUpdate();
    }

    public void atualizar(Fornecedor fornecedor) throws SQLException {
        String sql = "UPDATE fornecedor SET nomefornecedor=?, telefone=?, email=? WHERE id =?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setString(1, fornecedor.getNomeFornecedor());
        pstm.setString(2, fornecedor.getTelefone());
        pstm.setString(3, fornecedor.getEmail());
        pstm.setInt(4, fornecedor.getId());
        pstm.executeUpdate();
    }

    public List<Fornecedor> todosFornecedores() throws SQLException {
        List<Fornecedor> listaFornecedor = new ArrayList<Fornecedor>();

        String sql = "SELECT id, nomefornecedor, telefone, email FROM fornecedor order by nomeFornecedor";

        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        rs = pstm.executeQuery();

        while (rs.next()) {
            Fornecedor tempFornecedor = new Fornecedor();
            tempFornecedor.setId(rs.getInt("id"));
            tempFornecedor.setNomeFornecedor(rs.getString("nomeFornecedor"));
            tempFornecedor.setTelefone(rs.getString("telefone"));
            tempFornecedor.setEmail(rs.getString("email"));
            
            listaFornecedor.add(tempFornecedor);
            
        }
        
        
        return listaFornecedor;
    }

    
    public List<Fornecedor> consultaFornecedores(String nome) throws SQLException {
        List<Fornecedor> listaFornecedor = new ArrayList<Fornecedor>();

        String sql = "SELECT id, nomefornecedor, telefone, email FROM fornecedores WHERE nomefornecedor LIKE ? order by nomeFornecedor";

        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setString(1, "%" + nome + "%");
        rs = pstm.executeQuery();

        while (rs.next()) {
            Fornecedor tempFornecedor = new Fornecedor();
            tempFornecedor.setId(rs.getInt("id"));
            tempFornecedor.setNomeFornecedor(rs.getString("nomeFornecedor"));
            tempFornecedor.setTelefone(rs.getString("telefone"));
            tempFornecedor.setEmail(rs.getString("email"));
            listaFornecedor.add(tempFornecedor);
            System.out.println(tempFornecedor.getNomeFornecedor());
        }
        System.out.println("aaa");
        return listaFornecedor;
    }
    
    
    public Fornecedor buscaPorID(Fornecedor per) throws Exception {
        Fornecedor tempFornecedor = null;
        //ResultSet rs;
        try {
            String sql = "select *  from fornecedores where id=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, per.getId());
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempFornecedor = new Fornecedor();
                tempFornecedor.setId(rs.getInt("id"));
                tempFornecedor.setNomeFornecedor(rs.getString("nomeFornecedor"));
                tempFornecedor.setTipoPessoa(rs.getString("tipopessoa"));
                tempFornecedor.setSexo(rs.getString("sexo"));
                tempFornecedor.setCep(rs.getString("cep"));
                tempFornecedor.getCidade().setId(rs.getInt("idcidade"));
                tempFornecedor.setEndereco(rs.getString("endereco"));
                tempFornecedor.setNumero(rs.getString("numero"));
                tempFornecedor.setComplemento(rs.getString("complemento"));
                tempFornecedor.setBairro(rs.getString("bairro"));
                tempFornecedor.setTelefone(rs.getString("telefone"));
                tempFornecedor.setTelefoneComercial(rs.getString("telefonecomercial"));
                tempFornecedor.setCelular(rs.getString("celular"));
                tempFornecedor.setFax(rs.getString("fax"));
                tempFornecedor.setEmail(rs.getString("email"));
                tempFornecedor.setSite(rs.getString("site"));
                tempFornecedor.setDocumento(rs.getString("documento"));
                tempFornecedor.setCpf(rs.getString("cpf"));
                tempFornecedor.setCnpj(rs.getString("cnpj"));
                tempFornecedor.setDataNascimento(rs.getDate("datanascimento"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        return tempFornecedor;
    }

    public boolean buscaPorNome(Fornecedor fornecedorParam) throws Exception {
        boolean achou = false;
        //ResultSet rs;
        try {
            String sql = "select id, nomeFornecedor from fornecedores where nomeFornecedor=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, fornecedorParam.getNomeFornecedor().trim());
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

    public Fornecedor buscaPorCodigo(int id) throws Exception {
        System.out.println("entrou no buscaESTAD por sigla");
        Fornecedor tempFornecedor = null;

        try {
            String sql = "select *  from fornecedores where id=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempFornecedor = new Fornecedor();
                tempFornecedor.setId(rs.getInt("id"));
                tempFornecedor.setNomeFornecedor(rs.getString("nomeFornecedor"));
                tempFornecedor.setTelefone(rs.getString("telefone"));
                tempFornecedor.setEmail(rs.getString("email"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        if (tempFornecedor == null) {
            System.out.println("objeto estado null");
        } else {
            System.out.println("objeto pais :" + tempFornecedor.getNomeFornecedor());
        }

        return tempFornecedor;
    }

}
