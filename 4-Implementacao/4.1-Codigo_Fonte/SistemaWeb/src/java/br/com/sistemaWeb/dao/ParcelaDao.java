package br.com.sistemaWeb.dao;

import br.com.sistemaWeb.classes.Compra;
import br.com.sistemaWeb.classes.CondicaoPagamento;
import br.com.sistemaWeb.classes.FabricaConexao;
import br.com.sistemaWeb.classes.Parcelas;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Silvio Coelho
 */
public class ParcelaDao {

    PreparedStatement pstm;
    ResultSet rs;
    private Connection conexao;

    public void salvar(Parcelas parcelas) throws Exception {
        try {
            String sql = "INSERT INTO parcelas(nomeparcela) VALUES(?) ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            //  pstm.setString(1, parcelas.getNomeParcela());

            pstm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
    }

    public void apagar(Parcelas parcelas) throws SQLException {
        String sql = "DELETE FROM parcelas WHERE id = ?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setInt(1, parcelas.getNumero());
        pstm.executeUpdate();
    }

    public void atualizar(Parcelas parcelas) throws SQLException {
        String sql = "UPDATE parcelas SET nomeparcela=? WHERE id =?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        //pstm.setString(1, parcelas.getNomeParcela());

        //pstm.setInt(2, parcela.getId());
        pstm.executeUpdate();
    }

    public List<Parcelas> todosParcelas(Compra compra) throws SQLException {
        List<Parcelas> listaParcelas = new ArrayList<Parcelas>();
        String sql = "SELECT * FROM parcelas where idcondicaopagamento = ?";
        try {
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, compra.getCondicaoPagamento().getId());
            rs = pstm.executeQuery();
        } catch (SQLException ex) {
            throw ex;
        }
        double totalParcialParcelas = 0;
        int contador = 1;
        System.out.println("quantidade de parcelas desta condição" + compra.getCondicaoPagamento().getQuantidadeParcelas());
        while (rs.next()) {
            Parcelas tempParcelas = new Parcelas();
            tempParcelas.setDocumento(compra.getNota());
            tempParcelas.setNumero(rs.getInt("numeroparcela"));
            tempParcelas.getCondicaoPagamento().setId(999);

            tempParcelas.getCondicaoPagamento().setId(rs.getInt("idcondicaopagamento"));
            tempParcelas.getFormaPagamento().setId(rs.getInt("idformapagamento"));
            tempParcelas.setPorcentagem(rs.getDouble("porcentagem"));
            tempParcelas.setDias(rs.getInt("dias"));
            //tempParcelas.setValorParcela(rs.getDouble("valorparcela")); //não tem esse campo na tabela parcelas é só atributo
            if (contador == compra.getCondicaoPagamento().getQuantidadeParcelas()) {
                tempParcelas.setValorParcela(compra.getTotalNota() - totalParcialParcelas);
                System.out.println("contador do if " + contador);
            } else {
                tempParcelas.setValorParcela(compra.getTotalNota() * rs.getDouble("porcentagem") / 100);
                BigDecimal bd = new BigDecimal(tempParcelas.getValorParcela()).setScale(2, RoundingMode.HALF_EVEN);
                tempParcelas.setValorParcela(bd.doubleValue());
                totalParcialParcelas = totalParcialParcelas + tempParcelas.getValorParcela();
                System.out.println("contador do else " + contador + " numero da parcela " +tempParcelas.getNumero());
            }
            if (compra.getEmissao() != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(compra.getEmissao()); // Objeto Date() do usuário
                cal.add(cal.DAY_OF_MONTH, +rs.getInt("dias"));
                tempParcelas.setVencimento(cal.getTime());
            }
            calculaVencimento(compra);
            listaParcelas.add(tempParcelas);
            contador = contador + 1;
        }
        conexao.close();
        return listaParcelas;
    }

    public void calculaVencimento(Compra compra) {
        if (compra.getEmissao() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(compra.getEmissao()); // Objeto Date() do usuário
            //cal.add(cal.DAY_OF_MONTH, +this.dias);
            // this.
            // this.parcela.setVencimento(cal.getTime());
        }

    }

    public Parcelas buscaPorID(Parcelas per) throws Exception {
        Parcelas tempParcela = null;
        //ResultSet rs;
        try {
            String sql = "select *  from parcelas where id=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, per.getNumero());
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempParcela = new Parcelas();
                tempParcela.setNumero(rs.getInt("id"));
                //tempParcela.setNomeParcela(rs.getString("nomeParcela"));

            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        return tempParcela;
    }

    public boolean buscaPorNome(Parcelas parcelaParam) throws Exception {
        boolean achou = false;
        //ResultSet rs;
        try {
            String sql = "select id, nomeParcela from parcelas where nomeParcela=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            //pstm.setString(1, parcelaParam.getNomeParcela().trim());
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

    public Parcelas buscaPorCodigo(int id) throws Exception {
        System.out.println("entrou no buscaESTAD por sigla");
        Parcelas tempParcela = null;

        try {
            String sql = "select *  from parcelas where id=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempParcela = new Parcelas();
                //tempParcela.setId(rs.getInt("id"));
                //tempParcela.setNomeParcela(rs.getString("nomeParcela"));

            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        if (tempParcela == null) {
            System.out.println("objeto estado null");
        } else {
            //System.out.println("objeto pais :" + tempParcela.getNomeParcela());
        }
        return tempParcela;
    }

}
