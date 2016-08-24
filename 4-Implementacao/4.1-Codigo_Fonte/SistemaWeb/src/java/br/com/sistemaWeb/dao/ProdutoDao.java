package br.com.sistemaWeb.dao;

import br.com.sistemaWeb.classes.FabricaConexao;
import br.com.sistemaWeb.classes.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Silvio Coelho
 */
public class ProdutoDao {

    PreparedStatement pstm;
    ResultSet rs;
    private Connection conexao;

    public void salvar(Produto produto) throws Exception {
        // produto.setEstoqueAtual(0); //não tava salvando estoque atual com zero
        try {
            String sql = "INSERT INTO produtos("
                    + "nomeproduto, "
                    + "codigobarras, "
                    + "codigodofornecedor, "
                    + "quantidadeporcaixa, "
                    + "estoqueminimo, "
                    + "estoquemaximo, "
                    + "pesoneto, "
                    + "pesobruto, "
                    + "tamanho, "
                    + "valorcompra, "
                    + "icms, "
                    + "iss, "
                    + "ipi, "
                    + "precocusto, "
                    + "valoricms, "
                    + "valorIss, "
                    + "valoripi, "
                    + "precomedio, "
                    + "precovarejo, "
                    + "precoatacado, "
                    + "siglaunidade, "
                    + "idgrupo, "
                    + "idfornecedor, "
                    + "idmarca, "
                    + "idncm, "
                    + "idcst, "
                    + "idcfop, "
                    + "datacadastro, "
                    + "horacadastro, "
                    + "estoqueatual)"
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
            System.out.println(sql);
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, produto.getNomeProduto());
            pstm.setString(2, produto.getCodigoBarras());
            pstm.setString(3, produto.getCodigoDoFornecedor());
            pstm.setDouble(4, produto.getQuantidadePorCaixa());
            pstm.setDouble(5, produto.getEstoqueMinimo());
            pstm.setDouble(6, produto.getEstoqueMaximo());
            pstm.setDouble(7, produto.getPesoNeto());
            pstm.setDouble(8, produto.getPesoBruto());
            pstm.setString(9, produto.getTamanho());
            pstm.setDouble(10, produto.getValorCompra());
            pstm.setDouble(11, produto.getIcms());
            pstm.setDouble(12, produto.getIss());
            pstm.setDouble(13, produto.getIpi());
            pstm.setDouble(14, produto.getPrecoCusto());
            pstm.setDouble(15, produto.getValorIcms());
            pstm.setDouble(16, produto.getValorIss());
            pstm.setDouble(17, produto.getValorIpi());
            pstm.setDouble(18, produto.getPrecoMedio());
            pstm.setDouble(19, produto.getPrecoVarejo());
            pstm.setDouble(20, produto.getPrecoAtacado());
            pstm.setString(21, produto.getUnidade().getSiglaUnidade());
            pstm.setInt(22, produto.getGrupo().getId());
            pstm.setInt(23, produto.getFornecedor().getId());
            pstm.setInt(24, produto.getMarca().getId());
            pstm.setString(25, produto.getNcm().getId());
            pstm.setString(26, produto.getCst().getId());
            pstm.setString(27, produto.getCfop().getId());
            Date dataAtual = new Date();
            pstm.setDate(28, new java.sql.Date(dataAtual.getTime()));
            pstm.setTime(29, new java.sql.Time(dataAtual.getTime()));
            pstm.setDouble(30, produto.getEstoqueAtual());
            pstm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
    }

    public void apagar(Produto produto) throws SQLException {
        String sql = "DELETE FROM produtos WHERE id = ?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setInt(1, produto.getId());
        pstm.executeUpdate();
    }

    public void atualizar(Produto produto) throws SQLException {
        String sql = "UPDATE produtos SET nomeproduto=?, precovarejo=?, idgrupo=? WHERE id =?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setString(1, produto.getNomeProduto());
        pstm.setDouble(2, produto.getPrecoVarejo());
        pstm.setInt(3, produto.getGrupo().getId());
        pstm.setInt(4, produto.getId());
        pstm.executeUpdate();
    }

    public List<Produto> todosProdutos() throws SQLException {
        List<Produto> listaProduto = new ArrayList<Produto>();

        String sql = "SELECT id, nomeproduto, precovarejo, estoqueatual, idgrupo FROM produtos order by nomeproduto";

        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        rs = pstm.executeQuery();

        while (rs.next()) {
            Produto tempProduto = new Produto();
            tempProduto.setId(rs.getInt("id"));
            tempProduto.setNomeProduto(rs.getString("nomeProduto"));
            tempProduto.setPrecoVarejo(rs.getDouble("precovarejo"));
            tempProduto.setEstoqueAtual(rs.getDouble("estoqueatual"));
            tempProduto.getGrupo().setId(rs.getInt("idgrupo"));
            listaProduto.add(tempProduto);
        }
        conexao.close();
        return listaProduto;
    }

    public Produto buscaPorID(Produto per) throws Exception {
        Produto tempProduto = null;
        //ResultSet rs;
        try {
            //String sql = "select P.id, P.nomeproduto, P.precovarejo, P.estoqueatual, P.idgrupo, G.id, G.nomegrupo from produtos P, grupos G where P.id=? and P.idgrupo=G.id";
            ////String sql = "SELECT F.id, F.nomefuncionario, F.idcargo, F.email, C.nomecargo FROM funcionarios F, cargos C WHERE F.id=? and F.idcargo=C.id";
            String sqlProduto = "select * from produtos where id=?";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sqlProduto);
            pstm.setInt(1, per.getId());
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempProduto = new Produto();
                tempProduto.setId(rs.getInt("id"));
                tempProduto.setNomeProduto(rs.getString("nomeProduto"));
                tempProduto.setCodigoBarras(rs.getString("codigobarras"));
                tempProduto.setCodigoDoFornecedor(rs.getString("codigodofornecedor"));
                tempProduto.setQuantidadePorCaixa(rs.getDouble("quantidadeporcaixa"));
                tempProduto.setEstoqueMinimo(rs.getDouble("estoqueminimo"));
                tempProduto.setEstoqueMaximo(rs.getDouble("estoquemaximo"));
                tempProduto.setPesoNeto(rs.getDouble("pesoneto"));
                tempProduto.setPesoBruto(rs.getDouble("pesobruto"));
                tempProduto.setTamanho(rs.getString("tamanho"));
                tempProduto.setValorCompra(rs.getDouble("valorcompra"));
                tempProduto.setIcms(rs.getDouble("icms"));
                tempProduto.setIss(rs.getDouble("iss"));
                tempProduto.setIpi(rs.getDouble("ipi"));
                tempProduto.setPrecoCusto(rs.getDouble("precocusto"));
                tempProduto.setValorIcms(rs.getDouble("valoricms"));
                tempProduto.setValorIss(rs.getDouble("valoriss"));
                tempProduto.setValorIpi(rs.getDouble("valoripi"));
                tempProduto.setPrecoMedio(rs.getDouble("precomedio"));
                tempProduto.setPrecoVarejo(rs.getDouble("precovarejo"));
                tempProduto.setPrecoAtacado(rs.getDouble("precoatacado"));
                tempProduto.setPrecoVarejo(rs.getDouble("precovarejo"));
                tempProduto.setEstoqueAtual(rs.getDouble("estoqueatual"));
                tempProduto.getMarca().setId(rs.getInt("idmarca"));
                tempProduto.getFornecedor().setId(rs.getInt("idfornecedor"));
                tempProduto.getGrupo().setId(rs.getInt("idgrupo"));
                tempProduto.getUnidade().setSiglaUnidade(rs.getString("siglaunidade"));
                tempProduto.getNcm().setId(rs.getString("idncm"));
                tempProduto.getCst().setId(rs.getString("idcst"));
                tempProduto.getCfop().setId(rs.getString("idcfop"));
                tempProduto.setDataCadastro(rs.getDate("datacadastro"));
                tempProduto.setHoraCadastro(rs.getTime("horacadastro"));
            }
            
            String sqlMarca = "select * from marcas where id=?";
            pstm = conexao.prepareStatement(sqlMarca);
            pstm.setInt(1, tempProduto.getMarca().getId());
            rs = pstm.executeQuery();
            while (rs.next()) {
                tempProduto.getMarca().setNomeMarca(rs.getString("nomemarca"));
            }
            
            String sqlFornecedor = "select * from fornecedores where id=?";
            pstm = conexao.prepareStatement(sqlFornecedor);
            pstm.setInt(1, tempProduto.getFornecedor().getId());
            rs = pstm.executeQuery();
            while (rs.next()) {
                tempProduto.getFornecedor().setNomeFornecedor(rs.getString("nomefornecedor"));
            }

            String sqlGrupo = "select * from grupos where id=?";
            pstm = conexao.prepareStatement(sqlGrupo);
            pstm.setInt(1, tempProduto.getGrupo().getId());
            rs = pstm.executeQuery();
            while (rs.next()) {
                tempProduto.getGrupo().setNomeGrupo(rs.getString("nomegrupo"));
            }
            
            String sqlUnidade = "select * from unidades where siglaunidade=?";
            pstm = conexao.prepareStatement(sqlUnidade);
            pstm.setString(1, tempProduto.getUnidade().getSiglaUnidade());
            rs = pstm.executeQuery();
            while (rs.next()) {
                tempProduto.getUnidade().setNomeUnidade(rs.getString("nomeunidade"));
            }
            
            String sqlNcm = "select * from ncm where id=?";
            pstm = conexao.prepareStatement(sqlNcm);
            pstm.setString(1, tempProduto.getNcm().getId());
            rs = pstm.executeQuery();
            while (rs.next()) {
                tempProduto.getNcm().setNomeNcm(rs.getString("nomencm"));
            }
            
            String sqlCst = "select * from cst where id=?";
            pstm = conexao.prepareStatement(sqlCst);
            pstm.setString(1, tempProduto.getCst().getId());
            rs = pstm.executeQuery();
            while (rs.next()) {
                tempProduto.getCst().setNomeCst(rs.getString("nomecst"));
            }
            
            String sqlCfop = "select * from cfop where id=?";
            pstm = conexao.prepareStatement(sqlCfop);
            pstm.setString(1, tempProduto.getCfop().getId());
            rs = pstm.executeQuery();
            while (rs.next()) {
                tempProduto.getCfop().setNomeCfop(rs.getString("nomecfop"));
            }
            

        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        return tempProduto;
    }

    public boolean buscaPorNome(Produto produtoParam) throws Exception {
        boolean achou = false;
        //ResultSet rs;
        try {
            String sql = "select id, nomeProduto from produtos where nomeProduto=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, produtoParam.getNomeProduto().trim());
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

    public Produto buscaPorCodigo(int id) throws Exception {
        System.out.println("entrou no buscaESTAD por sigla");
        Produto tempProduto = null;

        try {
            String sql = "select *  from produtos where id=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempProduto = new Produto();
                tempProduto.setId(rs.getInt("id"));
                tempProduto.setNomeProduto(rs.getString("nomeproduto"));
                tempProduto.setPrecoVarejo(rs.getDouble("precovarejo"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        if (tempProduto == null) {
            System.out.println("objeto estado null");
        } else {
            System.out.println("objeto pais :" + tempProduto.getNomeProduto());
        }

        return tempProduto;
    }

    public List<Produto> consultaProdutos(String nome) throws SQLException {
        List<Produto> listaProduto = new ArrayList<Produto>();

        String sql = "SELECT * FROM produtos WHERE nomeproduto LIKE ? order by nomeproduto";

        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setString(1, "%" + nome + "%");
        rs = pstm.executeQuery();

        while (rs.next()) {
            Produto tempProduto = new Produto();
            tempProduto.setId(rs.getInt("id"));
            tempProduto.setNomeProduto(rs.getString("nomeproduto"));
            tempProduto.setPrecoVarejo(rs.getDouble("precovarejo"));
            tempProduto.setEstoqueAtual(rs.getDouble("estoqueatual"));
            tempProduto.setCodigoBarras(rs.getString("codigobarras"));
            listaProduto.add(tempProduto);
            System.out.println(tempProduto.getNomeProduto());
        }
        System.out.println("aaa");
        conexao.close();
        return listaProduto;
    }

}
