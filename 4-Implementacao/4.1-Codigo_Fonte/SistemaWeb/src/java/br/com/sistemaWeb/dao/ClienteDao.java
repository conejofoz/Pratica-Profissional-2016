package br.com.sistemaWeb.dao;

import br.com.sistemaWeb.classes.FabricaConexao;
import br.com.sistemaWeb.classes.Cliente;
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
public class ClienteDao {

    PreparedStatement pstm;
    ResultSet rs;
    private Connection conexao;

    public void salvar(Cliente cliente) throws Exception {
        try {
            String sql = "INSERT INTO clientes(nomecliente, tipopessoa, sexo, cep, idcidade, endereco, numero, complemento, bairro, telefone, telefonecomercial, celular, fax, email, site, documento, cpf ";
            if(cliente.getDataNascimento()!=null){
                sql = sql + ", datanascimento";
            }
            sql = sql + ", cnpj) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
            if(cliente.getDataNascimento()!=null){
                sql = sql + ",?"; //data nascimento
            }
            sql = sql + ",?) ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, cliente.getNomeCliente());
            pstm.setString(2, cliente.getTipoPessoa());
            pstm.setString(3, cliente.getSexo());
            pstm.setString(4, cliente.getCep());
            pstm.setInt(5, cliente.getCidade().getId());
            pstm.setString(6, cliente.getEndereco());
            pstm.setString(7, cliente.getNumero());
            pstm.setString(8, cliente.getComplemento());
            
            pstm.setString(9, cliente.getBairro());
            pstm.setString(10, cliente.getTelefone());
            pstm.setString(11, cliente.getTelefoneComercial());
            pstm.setString(12, cliente.getCelular());
            pstm.setString(13, cliente.getFax());
            pstm.setString(14, cliente.getEmail());
            pstm.setString(15, cliente.getSite());
            pstm.setString(16, cliente.getDocumento());
            pstm.setString(17, cliente.getCpf());
            int nParamSql = 18;
            if(cliente.getDataNascimento()!=null){
               pstm.setDate(nParamSql, new java.sql.Date(cliente.getDataNascimento().getTime()));
               nParamSql++;
            }
            pstm.setString(nParamSql, cliente.getCnpj());
            
            pstm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
    }

    public void apagar(Cliente cliente) throws SQLException {
        String sql = "DELETE FROM clientes WHERE id = ?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setInt(1, cliente.getId());
        pstm.executeUpdate();
    }

    public void atualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE clientes SET nomecliente=?, telefone=?, email=? WHERE id =?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setString(1, cliente.getNomeCliente());
        pstm.setString(2, cliente.getTelefone());
        pstm.setString(3, cliente.getEmail());
        pstm.setInt(4, cliente.getId());
        pstm.executeUpdate();
    }

    public List<Cliente> todosClientes() throws SQLException {
        List<Cliente> listaCliente = new ArrayList<Cliente>();

        String sql = "SELECT id, nomecliente, telefone, email FROM clientes order by nomeCliente";

        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        rs = pstm.executeQuery();

        while (rs.next()) {
            Cliente tempCliente = new Cliente();
            tempCliente.setId(rs.getInt("id"));
            tempCliente.setNomeCliente(rs.getString("nomeCliente"));
            tempCliente.setTelefone(rs.getString("telefone"));
            tempCliente.setEmail(rs.getString("email"));
            
            listaCliente.add(tempCliente);
            
        }
        
        
        return listaCliente;
    }

    
    public List<Cliente> consultaClientes(String nome) throws SQLException {
        List<Cliente> listaCliente = new ArrayList<Cliente>();

        String sql = "SELECT id, nomecliente, telefone, email FROM clientes WHERE nomecliente LIKE ? order by nomeCliente";

        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setString(1, "%" + nome + "%");
        rs = pstm.executeQuery();

        while (rs.next()) {
            Cliente tempCliente = new Cliente();
            tempCliente.setId(rs.getInt("id"));
            tempCliente.setNomeCliente(rs.getString("nomeCliente"));
            tempCliente.setTelefone(rs.getString("telefone"));
            tempCliente.setEmail(rs.getString("email"));
            listaCliente.add(tempCliente);
            System.out.println(tempCliente.getNomeCliente());
        }
        System.out.println("aaa");
        return listaCliente;
    }
    
    
    public Cliente buscaPorID(Cliente per) throws Exception {
        Cliente tempCliente = null;
        //ResultSet rs;
        try {
            String sql = "select *  from clientes where id=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, per.getId());
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempCliente = new Cliente();
                tempCliente.setId(rs.getInt("id"));
                tempCliente.setNomeCliente(rs.getString("nomeCliente"));
                tempCliente.setTipoPessoa(rs.getString("tipopessoa"));
                tempCliente.setSexo(rs.getString("sexo"));
                tempCliente.setCep(rs.getString("cep"));
                tempCliente.getCidade().setId(rs.getInt("idcidade"));
                tempCliente.setEndereco(rs.getString("endereco"));
                tempCliente.setNumero(rs.getString("numero"));
                tempCliente.setComplemento(rs.getString("complemento"));
                tempCliente.setBairro(rs.getString("bairro"));
                tempCliente.setTelefone(rs.getString("telefone"));
                tempCliente.setTelefoneComercial(rs.getString("telefonecomercial"));
                tempCliente.setCelular(rs.getString("celular"));
                tempCliente.setFax(rs.getString("fax"));
                tempCliente.setEmail(rs.getString("email"));
                tempCliente.setSite(rs.getString("site"));
                tempCliente.setDocumento(rs.getString("documento"));
                tempCliente.setCpf(rs.getString("cpf"));
                tempCliente.setCnpj(rs.getString("cnpj"));
                tempCliente.setDataNascimento(rs.getDate("datanascimento"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        return tempCliente;
    }

    public boolean buscaPorNome(Cliente clienteParam) throws Exception {
        boolean achou = false;
        //ResultSet rs;
        try {
            String sql = "select id, nomeCliente from clientes where nomeCliente=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, clienteParam.getNomeCliente().trim());
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

    public Cliente buscaPorCodigo(int id) throws Exception {
        System.out.println("entrou no buscaESTAD por sigla");
        Cliente tempCliente = null;

        try {
            String sql = "select *  from clientes where id=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempCliente = new Cliente();
                tempCliente.setId(rs.getInt("id"));
                tempCliente.setNomeCliente(rs.getString("nomeCliente"));
                tempCliente.setTelefone(rs.getString("telefone"));
                tempCliente.setEmail(rs.getString("email"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        if (tempCliente == null) {
            System.out.println("objeto estado null");
        } else {
            System.out.println("objeto pais :" + tempCliente.getNomeCliente());
        }

        return tempCliente;
    }

}
