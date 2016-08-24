package br.com.sistemaWeb.dao;

import br.com.sistemaWeb.classes.FabricaConexao;
import br.com.sistemaWeb.classes.Transportadora;
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
public class TransportadoraDao {

    PreparedStatement pstm;
    ResultSet rs;
    private Connection conexao;

    public void salvar(Transportadora transportadora) throws Exception {
        try {
            String sql = "INSERT INTO transportadoras(nometransportadora, tipopessoa, sexo, cep, idcidade, endereco, numero, complemento, bairro, telefone, telefonecomercial, celular, fax, email, site, documento, cpf ";
            if(transportadora.getDataNascimento()!=null){
                sql = sql + ", datanascimento";
            }
            sql = sql + ", cnpj) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
            if(transportadora.getDataNascimento()!=null){
                sql = sql + ",?"; //data nascimento
            }
            sql = sql + ",?) ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, transportadora.getNomeTransportadora());
            pstm.setString(2, transportadora.getTipoPessoa());
            pstm.setString(3, transportadora.getSexo());
            pstm.setString(4, transportadora.getCep());
            pstm.setInt(5, transportadora.getCidade().getId());
            pstm.setString(6, transportadora.getEndereco());
            pstm.setString(7, transportadora.getNumero());
            pstm.setString(8, transportadora.getComplemento());
            
            pstm.setString(9, transportadora.getBairro());
            pstm.setString(10, transportadora.getTelefone());
            pstm.setString(11, transportadora.getTelefoneComercial());
            pstm.setString(12, transportadora.getCelular());
            pstm.setString(13, transportadora.getFax());
            pstm.setString(14, transportadora.getEmail());
            pstm.setString(15, transportadora.getSite());
            pstm.setString(16, transportadora.getDocumento());
            pstm.setString(17, transportadora.getCpf());
            int nParamSql = 18;
            if(transportadora.getDataNascimento()!=null){
               pstm.setDate(nParamSql, new java.sql.Date(transportadora.getDataNascimento().getTime()));
               nParamSql++;
            }
            pstm.setString(nParamSql, transportadora.getCnpj());
            
            pstm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
    }

    public void apagar(Transportadora transportadora) throws SQLException {
        String sql = "DELETE FROM transportadora WHERE id = ?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setInt(1, transportadora.getId());
        pstm.executeUpdate();
    }

    public void atualizar(Transportadora transportadora) throws SQLException {
        String sql = "UPDATE transportadoras SET nometransportadora=?, telefone=?, email=? WHERE id =?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setString(1, transportadora.getNomeTransportadora());
        pstm.setString(2, transportadora.getTelefone());
        pstm.setString(3, transportadora.getEmail());
        pstm.setInt(4, transportadora.getId());
        pstm.executeUpdate();
    }

    public List<Transportadora> todosTransportadoras() throws SQLException {
        List<Transportadora> listaTransportadora = new ArrayList<Transportadora>();

        String sql = "SELECT id, nometransportadora, telefone, email FROM transportadoras order by nomeTransportadora";

        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        rs = pstm.executeQuery();

        while (rs.next()) {
            Transportadora tempTransportadora = new Transportadora();
            tempTransportadora.setId(rs.getInt("id"));
            tempTransportadora.setNomeTransportadora(rs.getString("nomeTransportadora"));
            tempTransportadora.setTelefone(rs.getString("telefone"));
            tempTransportadora.setEmail(rs.getString("email"));
            
            listaTransportadora.add(tempTransportadora);
            
        }
        
        conexao.close();
        return listaTransportadora;
    }

    
    public List<Transportadora> consultaTransportadoras(String nome) throws SQLException {
        List<Transportadora> listaTransportadora = new ArrayList<Transportadora>();

        String sql = "SELECT id, nometransportadora, telefone, email FROM transportadoras WHERE nometransportadora LIKE ? order by nomeTransportadora";

        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setString(1, "%" + nome + "%");
        rs = pstm.executeQuery();

        while (rs.next()) {
            Transportadora tempTransportadora = new Transportadora();
            tempTransportadora.setId(rs.getInt("id"));
            tempTransportadora.setNomeTransportadora(rs.getString("nomeTransportadora"));
            tempTransportadora.setTelefone(rs.getString("telefone"));
            tempTransportadora.setEmail(rs.getString("email"));
            listaTransportadora.add(tempTransportadora);
            System.out.println(tempTransportadora.getNomeTransportadora());
        }
        System.out.println("aaa");
        conexao.close();
        return listaTransportadora;
    }
    
    
    public Transportadora buscaPorID(Transportadora transportadora) throws Exception {
        Transportadora tempTransportadora = null;
        //ResultSet rs;
        try {
            String sql = "select * from transportadoras where id=?";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, transportadora.getId());
            //pstm.setInt(2, transportadora.getCidade().getId());
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempTransportadora = new Transportadora();
                tempTransportadora.setId(rs.getInt("id"));
                tempTransportadora.setNomeTransportadora(rs.getString("nomeTransportadora"));
                tempTransportadora.setTipoPessoa(rs.getString("tipopessoa"));
                tempTransportadora.setSexo(rs.getString("sexo"));
                tempTransportadora.setCep(rs.getString("cep"));
                tempTransportadora.getCidade().setId(rs.getInt("idcidade"));
                tempTransportadora.getCidade().setNomeCidade("nomecidade");
                tempTransportadora.setEndereco(rs.getString("endereco"));
                tempTransportadora.setNumero(rs.getString("numero"));
                tempTransportadora.setComplemento(rs.getString("complemento"));
                tempTransportadora.setBairro(rs.getString("bairro"));
                tempTransportadora.setTelefone(rs.getString("telefone"));
                tempTransportadora.setTelefoneComercial(rs.getString("telefonecomercial"));
                tempTransportadora.setCelular(rs.getString("celular"));
                tempTransportadora.setFax(rs.getString("fax"));
                tempTransportadora.setEmail(rs.getString("email"));
                tempTransportadora.setSite(rs.getString("site"));
                tempTransportadora.setDocumento(rs.getString("documento"));
                tempTransportadora.setCpf(rs.getString("cpf"));
                tempTransportadora.setCnpj(rs.getString("cnpj"));
                tempTransportadora.setDataNascimento(rs.getDate("datanascimento"));
            }
            
            sql = "select *  from cidades where id=?";
            //conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, tempTransportadora.getCidade().getId());
            rs = pstm.executeQuery();
            while (rs.next()) {
                tempTransportadora.getCidade().setNomeCidade(rs.getString("nomecidade"));
                tempTransportadora.getCidade().getEstado().setId(rs.getInt("idestado"));
             }
            
            sql = "select *  from estados where id=?";
            //conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, tempTransportadora.getCidade().getEstado().getId());
            rs = pstm.executeQuery();
            while (rs.next()) {
                tempTransportadora.getCidade().getEstado().setNomeEstado(rs.getString("nomeestado"));
                tempTransportadora.getCidade().getEstado().setSiglaEstado(rs.getString("siglaestado"));
             }
            
            
            
            
           /* PreparedStatement pstm2;
            
            String sql2 = "select *  from cidades where id=? ";
            conexao2 = FabricaConexao.conectar();
            pstm2 = conexao2.prepareStatement(sql2);
            pstm2.setInt(1, tempTransportadora.getCidade().getId());
            rs2 = pstm2.executeQuery();
            //Cidade cidade = new Cidade();
            while (rs2.next()) {
                //cidade.setId(rs2.getInt("id"));
                //cidade.setNomeCidade(rs2.getString("nomecidade"));
                tempTransportadora.getCidade().setNomeCidade(rs2.getString("nomecidade"));
                tempTransportadora.getCidade().getEstado().setId(rs2.getInt("idestado"));
             }
            //tempTransportadora.setCidade(cidade);*/
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        return tempTransportadora;
    }

    public boolean buscaPorNome(Transportadora transportadoraParam) throws Exception {
        boolean achou = false;
        //ResultSet rs;
        try {
            String sql = "select id, nomeTransportadora from transportadoras where nomeTransportadora=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, transportadoraParam.getNomeTransportadora().trim());
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

    public Transportadora buscaPorCodigo(int id) throws Exception {
        System.out.println("entrou no buscaESTAD por sigla");
        Transportadora tempTransportadora = null;

        try {
            String sql = "select *  from transportadoras where id=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempTransportadora = new Transportadora();
                tempTransportadora.setId(rs.getInt("id"));
                tempTransportadora.setNomeTransportadora(rs.getString("nomeTransportadora"));
                tempTransportadora.setTelefone(rs.getString("telefone"));
                tempTransportadora.setEmail(rs.getString("email"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        if (tempTransportadora == null) {
            System.out.println("objeto estado null");
        } else {
            System.out.println("objeto pais :" + tempTransportadora.getNomeTransportadora());
        }

        return tempTransportadora;
    }

}
