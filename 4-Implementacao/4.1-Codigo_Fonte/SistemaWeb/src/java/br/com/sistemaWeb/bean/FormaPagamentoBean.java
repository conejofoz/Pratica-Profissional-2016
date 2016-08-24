/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemaWeb.bean;

import br.com.sistemaWeb.classes.FormaPagamento;
import br.com.sistemaWeb.dao.FormaPagamentoDao;
import java.io.Serializable;
import java.sql.SQLException;
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
public class FormaPagamentoBean implements Serializable{
    private static final long serialVersionUID = 1L;
    private FormaPagamento formaPagamento = new FormaPagamento();
    private List<FormaPagamento> listaFormaPagamento;
    private FormaPagamentoDao eDao;
//    private LogEventoDAO eDaoLog;
    private String accion;
    private FormaPagamento formaPagamentoSelecionado;
  //  private LogEvento logEvento = new LogEvento();
    
    //paga o log
   // private UsuarioBean usuarioBean = new UsuarioBean();

    public FormaPagamento getFormaPagamentoSelecionado() {
        return formaPagamentoSelecionado;
    }

    public void setFormaPagamentoSelecionado(FormaPagamento formaPagamentoSelecionado) {
        this.formaPagamentoSelecionado = formaPagamentoSelecionado;
    }

     
    
    

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public List<FormaPagamento> getListaFormaPagamento() {
        return listaFormaPagamento;
    }

    public void setListaFormaPagamento(List<FormaPagamento> listaFormaPagamento) {
        this.listaFormaPagamento = listaFormaPagamento;
    }

    public FormaPagamentoDao geteDao() {
        return eDao;
    }

    public void seteDao(FormaPagamentoDao eDao) {
        this.eDao = eDao;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

  

    
    public void apagar() {
            eDao = new FormaPagamentoDao();
            try {
                eDao.apagar(formaPagamentoSelecionado);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Eliminado com sucesso"));
            } catch (SQLException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Erro ao eliminar"));
            }
    }
    
    
   
    public void salvar() {
        if (formaPagamento != null) {
            eDao = new FormaPagamentoDao();
            
            try {
                try {
                    if (!eDao.buscaPorNome(formaPagamento)) {
                        //carregarLog();
                        eDao.salvar(formaPagamento);
                        //eDaoLog.salvar(logEvento);
                        System.out.println("passou no salvar");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "FormaPagamento ja existe "));
                    }
                } catch (SQLException ex1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex1.getMessage()));
                }
            } catch (Exception ex2) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex2.getMessage()));
            }
            formaPagamento = new FormaPagamento();
        }
    }

    
    public void salvarAntigo() throws Exception {
        if (formaPagamento != null) {
            eDao = new FormaPagamentoDao();
            try {
                
                eDao.salvar(formaPagamento);
                System.out.println("passou no salvar");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
            } catch (SQLException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
            }
            formaPagamento = new FormaPagamento();
        }
    }
    
    
    public void atualizar() {
        eDao = new FormaPagamentoDao();
        try {
            eDao.atualizar(formaPagamento);
            System.out.println("passou no atualizar");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Alterado com sucesso "));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
        }

    }

    public void listar() throws Exception {
        FormaPagamentoDao dao;
        try {
            dao = new FormaPagamentoDao();
            listaFormaPagamento = dao.todosFormaPagamentos();
        } catch (Exception e) {
            throw e;
        }
    }

    public void buscaPorID(FormaPagamento per) throws Exception {
        FormaPagamentoDao dao;
        FormaPagamento temp;
        try {
            dao = new FormaPagamentoDao();
            temp = dao.buscaPorID(per);
            if (temp != null) {
                this.formaPagamento = temp;
                this.accion = "Modificar";
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void operar(ActionEvent actionEvent) throws Exception {
        System.out.println("entrou no operar");
        System.out.println("valor do accion" + this.accion);
        RequestContext context = RequestContext.getCurrentInstance();
        boolean fecharDialogo = false;
        switch (accion) {
            case "Registrar":
                try{
                   this.salvar();
                   this.limpiar();
                   fecharDialogo = true;
                   System.out.println(fecharDialogo);
                }catch(Exception e){
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
        this.formaPagamento.setId(0);
        this.formaPagamento.setNomeFormaPagamento("");
    }
    
    public void obterFormaPagamento(FormaPagamento xFormaPagamento){
        this.accion = "Modificar";
        this.formaPagamento = xFormaPagamento;
    }
    /*  
    public void carregarLog() {
        eDaoLog = new LogEventoDAO();
        try {
            logEvento.setNomeUsuario(usuarioBean.verificarUsuarioLogado().getNome().trim());
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao ao buscar o nome do formaPagamento para o log", "Erro: " + ex.getMessage()));
        }
        logEvento.setDescricao(logEvento.getNomeUsuario() + " inseriu um formaPagamento no sistema");
        logEvento.setData(new Date());
        logEvento.setHora(new Date());
        
    }*/
    
    
public void selecionar(FormaPagamento var_formaPagamento){
    RequestContext.getCurrentInstance().closeDialog(var_formaPagamento);
}    
    

   public void abrirDialogoFormaPagamento(){
       Map<String, Object> opcoes = new HashMap<>();
       opcoes.put("modal", true);
       opcoes.put("resizable", false);
       opcoes.put("contentHeight", 470);
       opcoes.put("contentWidth", 1000);
       RequestContext.getCurrentInstance().openDialog("consultaFormaPagamentoes", opcoes, null);
       System.out.println("passou");
   } 
   
   public void iniciarObjeto(){
       formaPagamento = new FormaPagamento();
   }
   
   public void zerarFormaPagamento(){
       formaPagamento = new FormaPagamento();
   }
    
}
