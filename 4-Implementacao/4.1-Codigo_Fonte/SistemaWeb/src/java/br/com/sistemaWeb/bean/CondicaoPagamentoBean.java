/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemaWeb.bean;

import br.com.sistemaWeb.classes.CondicaoPagamento;
import br.com.sistemaWeb.classes.FormaPagamento;
import br.com.sistemaWeb.classes.Parcelas;
import br.com.sistemaWeb.dao.CondicaoPagamentoDao;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author geral
 */
@ManagedBean
@ViewScoped
public class CondicaoPagamentoBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private CondicaoPagamento condicaoPagamento;
    private Parcelas parcela = new Parcelas();
    private FormaPagamento formaPagamento;
    private int numeroParcela; //numero da parcela a ser inserida na grade
    private int dias;
    private double porcentagemTotal; //porcentagem que ja foi inserida
    private double porcentagemRestante;
    private List<CondicaoPagamento> listaCondicaoPagamento;
    private List<Parcelas> listaParcelas;
    private CondicaoPagamentoDao eDao;
    private Date data;
//    private LogEventoDAO eDaoLog;
    private String accion;
    private CondicaoPagamento condicaoPagamentoSelecionado;
    private boolean mostrarFieldParcelas;

    public CondicaoPagamentoBean() {
        //this.mostrarFieldParcelas = false;
        this.formaPagamento = new FormaPagamento();
        this.listaParcelas = new ArrayList();
        this.condicaoPagamento = new CondicaoPagamento();
        this.porcentagemTotal = 0;
        this.porcentagemRestante = 0;
        this.numeroParcela = 1;
    }

    public boolean isMostrarFieldParcelas() {
        return mostrarFieldParcelas;
    }

    public void setMostrarFieldParcelas(boolean mostrarFieldParcelas) {
        this.mostrarFieldParcelas = mostrarFieldParcelas;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getPorcentagemRestante() {
        return porcentagemRestante;
    }

    public void setPorcentagemRestante(double porcentagemRestante) {
        this.porcentagemRestante = porcentagemRestante;
    }

    public double getPorcentagemTotal() {
        return porcentagemTotal;
    }

    public void setPorcentagemTotal(double porcentagemTotal) {
        this.porcentagemTotal = porcentagemTotal;
    }

    //paga o log
    // private UsuarioBean usuarioBean = new UsuarioBean();
    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public int getNumeroParcela() {
        return numeroParcela;
    }

    public void setNumeroParcela(int numeroParcela) {
        this.numeroParcela = numeroParcela;
    }

    public Parcelas getParcela() {
        return parcela;
    }

    public void setParcela(Parcelas parcela) {
        this.parcela = parcela;
    }

    public List<Parcelas> getListaParcelas() {
        return listaParcelas;
    }

    public void setListaParcelas(List<Parcelas> listaParcelas) {
        this.listaParcelas = listaParcelas;
    }

    public CondicaoPagamento getCondicaoPagamentoSelecionado() {
        return condicaoPagamentoSelecionado;
    }

    public void setCondicaoPagamentoSelecionado(CondicaoPagamento condicaoPagamentoSelecionado) {
        this.condicaoPagamentoSelecionado = condicaoPagamentoSelecionado;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public CondicaoPagamento getCondicaoPagamento() {
        return condicaoPagamento;
    }

    public void setCondicaoPagamento(CondicaoPagamento condicaoPagamento) {
        this.condicaoPagamento = condicaoPagamento;
    }

    public List<CondicaoPagamento> getListaCondicaoPagamento() {
        return listaCondicaoPagamento;
    }

    public void setListaCondicaoPagamento(List<CondicaoPagamento> listaCondicaoPagamento) {
        this.listaCondicaoPagamento = listaCondicaoPagamento;
    }

    public CondicaoPagamentoDao geteDao() {
        return eDao;
    }

    public void seteDao(CondicaoPagamentoDao eDao) {
        this.eDao = eDao;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public void apagar() {
        eDao = new CondicaoPagamentoDao();
        try {
            eDao.apagar(condicaoPagamentoSelecionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Eliminado com sucesso"));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Erro ao eliminar"));
        }
    }

    public void salvar() {
        if (condicaoPagamento != null) {
            eDao = new CondicaoPagamentoDao();

            try {
                try {
                    if (!eDao.buscaPorNome(condicaoPagamento)) {
                        //carregarLog();
                        eDao.salvar(condicaoPagamento, listaParcelas);
                        //eDaoLog.salvar(logEvento);
                        System.out.println("passou no salvar");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "CondicaoPagamento ja existe "));
                    }
                } catch (SQLException ex1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex1.getMessage()));
                }
            } catch (Exception ex2) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex2.getMessage()));
            }
            condicaoPagamento = new CondicaoPagamento();
            this.parcela = new Parcelas();
            this.listaParcelas.clear();
            this.numeroParcela = 1; //por causa disso não funcionava o bichinho q esconde na tela
            this.porcentagemTotal = 0;
        }
    }

    /*
     public void salvarAntigo() throws Exception {
     if (condicaoPagamento != null) {
     eDao = new CondicaoPagamentoDao();
     try {

     eDao.salvar(condicaoPagamento);
     System.out.println("passou no salvar");
     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
     } catch (SQLException ex) {
     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
     }
     condicaoPagamento = new CondicaoPagamento();
     }
     }*/
    public void atualizar() {
        eDao = new CondicaoPagamentoDao();
        try {
            eDao.atualizar(condicaoPagamento);
            System.out.println("passou no atualizar");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Alterado com sucesso "));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
        }

    }

    public void listar() throws Exception {
        CondicaoPagamentoDao dao;
        try {
            dao = new CondicaoPagamentoDao();
            listaCondicaoPagamento = dao.todosCondicaoPagamentos();
        } catch (Exception e) {
            throw e;
        }
    }

    public void buscaPorID(CondicaoPagamento per) throws Exception {
        CondicaoPagamentoDao dao;
        CondicaoPagamento temp;
        try {
            dao = new CondicaoPagamentoDao();
            temp = dao.buscaPorID(per);
            if (temp != null) {
                this.condicaoPagamento = temp;
                this.accion = "Modificar";
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void operar(ActionEvent actionEvent) throws Exception {
        if (!this.verificarPorcentagem()) {
            return;
        }

        System.out.println("entrou no operar");
        System.out.println("valor do accion" + this.accion);
        RequestContext context = RequestContext.getCurrentInstance();
        boolean fecharDialogo = false;
        switch (accion) {
            case "Registrar":
                try {
                    this.salvar();
                    this.limpiar();
                    fecharDialogo = true;
                    System.out.println(fecharDialogo);
                } catch (Exception e) {
                    System.out.println("passou no erro do operar");

                }

                break;
            case "Modificar":
                this.atualizar();
                this.limpiar();
                break;
        }
        context.addCallbackParam("fecharDialogo", fecharDialogo);
    }

    public void limpiar() {
        this.condicaoPagamento.setId(0);
        this.condicaoPagamento.setNomeCondicaoPagamento("");
    }

    public void obterCondicaoPagamento(CondicaoPagamento xCondicaoPagamento) {
        this.accion = "Modificar";
        this.condicaoPagamento = xCondicaoPagamento;
    }
    /*  
     public void carregarLog() {
     eDaoLog = new LogEventoDAO();
     try {
     logEvento.setNomeUsuario(usuarioBean.verificarUsuarioLogado().getNome().trim());
     } catch (IOException ex) {
     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao ao buscar o nome do condicaoPagamento para o log", "Erro: " + ex.getMessage()));
     }
     logEvento.setDescricao(logEvento.getNomeUsuario() + " inseriu um condicaoPagamento no sistema");
     logEvento.setData(new Date());
     logEvento.setHora(new Date());
        
     }*/

    public void selecionar(CondicaoPagamento var_condicaoPagamento) {
        RequestContext.getCurrentInstance().closeDialog(var_condicaoPagamento);
    }

    public void abrirDialogoCondicaoPagamento() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 470);
        opcoes.put("contentWidth", 1000);
        RequestContext.getCurrentInstance().openDialog("consultaCondicaoPagamentoes", opcoes, null);
        System.out.println("passou");
    }

    public void iniciarObjeto() {
        condicaoPagamento = new CondicaoPagamento();
    }

    public void zerarCondicaoPagamento() {
        condicaoPagamento = new CondicaoPagamento();
    }

    public void handleKeyEvent() {
        condicaoPagamento.setNomeCondicaoPagamento(condicaoPagamento.getNomeCondicaoPagamento().toUpperCase());
        //produto.setNomeProduto(produto.getNomeProduto().toUpperCase());
    }

    public void calculaVencimento() {
        if (this.data != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(this.data); // Objeto Date() do usuário
            cal.add(cal.DAY_OF_MONTH, +this.dias);
            this.parcela.setVencimento(cal.getTime());
        }

    }

    public void mostrarFieldseParcelas() {
        
        System.out.println("entrou no mostra parcela");
        System.out.println("numero " + this.numeroParcela);
        System.out.println("porcentagem " + this.porcentagemTotal);
        if (this.numeroParcela > this.condicaoPagamento.getQuantidadeParcelas()) {
            this.mostrarFieldParcelas = false;
            return;
        }

        if (this.porcentagemTotal == 100.00) {
            this.mostrarFieldParcelas = false;
            return;
        }

        this.mostrarFieldParcelas = true;
        System.out.println(this.mostrarFieldParcelas);
        System.out.println("saiu do mostra parcela");
    }
    
    

    public void calculaPorcentagemRestante() {
        this.porcentagemTotal = 0;
        for (Parcelas pa : listaParcelas) {
            this.porcentagemTotal = this.porcentagemTotal + pa.getPorcentagem();
        }

        this.porcentagemRestante = 100 - this.porcentagemTotal - this.parcela.getPorcentagem();
        BigDecimal bd = new BigDecimal(this.porcentagemRestante).setScale(2, RoundingMode.HALF_EVEN);
        this.porcentagemRestante = bd.doubleValue();

        if (this.numeroParcela == this.condicaoPagamento.getQuantidadeParcelas()) {
            this.parcela.setPorcentagem(this.porcentagemRestante);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", 
                    "Essa é a última parcela e foi setado o valor " + this.parcela.getPorcentagem()));
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", 
                    "valor formatado " + bd));
            
        }

        if (this.porcentagemRestante < 0) {
            this.parcela.setPorcentagem(0);
            this.calculaPorcentagemRestante();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Porcentagem não pode ser maior que 100%"));
        }
    }
    
    

    public boolean verificarPorcentagem() {
        this.porcentagemTotal = 0;
        for (Parcelas pa : listaParcelas) {
            this.porcentagemTotal = this.porcentagemTotal + pa.getPorcentagem();
        }
        //formatar porcentagem total com duas casas
        BigDecimal bd = new BigDecimal(this.porcentagemTotal).setScale(2, RoundingMode.HALF_EVEN);
        this.porcentagemTotal = bd.doubleValue();
        
        
        if (this.porcentagemTotal == 100.00) {
            return true;
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Porcentagem diferente 100%"));
        return false;
    }

    public void inserirNaGrid() {

        if (this.parcela.getPorcentagem() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Porcentagem não pode ser zero%"));
            return;
        }
        if (this.numeroParcela <= condicaoPagamento.getQuantidadeParcelas()) {
            Parcelas novaParcela = new Parcelas();
            novaParcela.setNumero(this.numeroParcela);
            novaParcela.setPorcentagem(parcela.getPorcentagem());
            novaParcela.setDias(this.dias);
            novaParcela.setVencimento(parcela.getVencimento());
            novaParcela.setFormaPagamento(this.parcela.getFormaPagamento());
            this.listaParcelas.add(novaParcela);
            //Mudar descricao
            if (this.numeroParcela == 1) {
                condicaoPagamento.setNomeCondicaoPagamento(condicaoPagamento.getNomeCondicaoPagamento().toUpperCase() + ' ' + this.dias);
            } else {
                condicaoPagamento.setNomeCondicaoPagamento(condicaoPagamento.getNomeCondicaoPagamento().toUpperCase() + '/' + this.dias);
            }

            parcela = new Parcelas();
            this.numeroParcela = this.numeroParcela + 1;
            
            //Mudar descricao
        }
        this.calculaDiasParcela();
        this.calculaPorcentagemRestante();
        this.mostrarFieldseParcelas();
    }

    public void calculaDiasParcela() {
        this.calculaVencimento();
        if (this.numeroParcela == 1) {
            this.dias = this.condicaoPagamento.getEntrada();
            System.out.println("dias para entrada: " + this.dias);
            System.out.println("dia calculado: " + this.dias);
            System.out.println("numero da parcela " + this.numeroParcela);
            System.out.println("intervalo de dias " + this.condicaoPagamento.getIntervaloDias());
        } else {
            this.dias = this.dias + this.condicaoPagamento.getIntervaloDias();
            System.out.println("dias para entrada: " + this.dias);
            System.out.println("dia calculado: " + this.dias);
            System.out.println("numero da parcela " + this.numeroParcela);
            System.out.println("intervalo de dias " + this.condicaoPagamento.getIntervaloDias());
        }
    }

}
