/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemaWeb.bean;

import br.com.sistemaWeb.classes.ContaCaixa;
import br.com.sistemaWeb.dao.ContaCaixaDao;
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
public class ContaCaixaBean implements Serializable{
    private static final long serialVersionUID = 1L;
    private ContaCaixa contaCaixa = new ContaCaixa();
    private List<ContaCaixa> listaContaCaixa;
    private ContaCaixaDao eDao;
//    private LogEventoDAO eDaoLog;
    private String accion;
    private ContaCaixa contaCaixaSelecionado;
  //  private LogEvento logEvento = new LogEvento();
    
    //paga o log
   // private UsuarioBean usuarioBean = new UsuarioBean();

    public ContaCaixa getContaCaixaSelecionado() {
        return contaCaixaSelecionado;
    }

    public void setContaCaixaSelecionado(ContaCaixa contaCaixaSelecionado) {
        this.contaCaixaSelecionado = contaCaixaSelecionado;
    }

     
    
    

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public ContaCaixa getContaCaixa() {
        return contaCaixa;
    }

    public void setContaCaixa(ContaCaixa contaCaixa) {
        this.contaCaixa = contaCaixa;
    }

    public List<ContaCaixa> getListaContaCaixa() {
        return listaContaCaixa;
    }

    public void setListaContaCaixa(List<ContaCaixa> listaContaCaixa) {
        this.listaContaCaixa = listaContaCaixa;
    }

    public ContaCaixaDao geteDao() {
        return eDao;
    }

    public void seteDao(ContaCaixaDao eDao) {
        this.eDao = eDao;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

  

    
    public void apagar() {
            eDao = new ContaCaixaDao();
            try {
                eDao.apagar(contaCaixaSelecionado);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Eliminado com sucesso"));
            } catch (SQLException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Erro ao eliminar"));
            }
    }
    
    
   
    public void salvar() {
        if (contaCaixa != null) {
            eDao = new ContaCaixaDao();
            
            try {
                try {
                    if (!eDao.buscaPorNome(contaCaixa)) {
                        //carregarLog();
                        eDao.salvar(contaCaixa);
                        //eDaoLog.salvar(logEvento);
                        System.out.println("passou no salvar");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "ContaCaixa ja existe "));
                    }
                } catch (SQLException ex1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex1.getMessage()));
                }
            } catch (Exception ex2) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex2.getMessage()));
            }
            contaCaixa = new ContaCaixa();
        }
    }

    
    public void salvarAntigo() throws Exception {
        if (contaCaixa != null) {
            eDao = new ContaCaixaDao();
            try {
                
                eDao.salvar(contaCaixa);
                System.out.println("passou no salvar");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
            } catch (SQLException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
            }
            contaCaixa = new ContaCaixa();
        }
    }
    
    
    public void atualizar() {
        eDao = new ContaCaixaDao();
        try {
            eDao.atualizar(contaCaixa);
            System.out.println("passou no atualizar");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Alterado com sucesso "));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
        }

    }

    public void listar() throws Exception {
        ContaCaixaDao dao;
        try {
            dao = new ContaCaixaDao();
            listaContaCaixa = dao.todosContaCaixas();
        } catch (Exception e) {
            throw e;
        }
    }

    public void buscaPorID(ContaCaixa per) throws Exception {
        ContaCaixaDao dao;
        ContaCaixa temp;
        try {
            dao = new ContaCaixaDao();
            temp = dao.buscaPorID(per);
            if (temp != null) {
                this.contaCaixa = temp;
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
        this.contaCaixa.setId(0);
        this.contaCaixa.setNomeContaCaixa("");
   
    }
    
    public void obterContaCaixa(ContaCaixa xContaCaixa){
        this.accion = "Modificar";
        this.contaCaixa = xContaCaixa;
    }
    /*  
    public void carregarLog() {
        eDaoLog = new LogEventoDAO();
        try {
            logEvento.setNomeUsuario(usuarioBean.verificarUsuarioLogado().getNome().trim());
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao ao buscar o nome do contaCaixa para o log", "Erro: " + ex.getMessage()));
        }
        logEvento.setDescricao(logEvento.getNomeUsuario() + " inseriu um contaCaixa no sistema");
        logEvento.setData(new Date());
        logEvento.setHora(new Date());
        
    }*/
    
    
public void selecionar(ContaCaixa var_contaCaixa){
    RequestContext.getCurrentInstance().closeDialog(var_contaCaixa);
}    
    

   public void abrirDialogoContaCaixa(){
       Map<String, Object> opcoes = new HashMap<>();
       opcoes.put("modal", true);
       opcoes.put("resizable", false);
       opcoes.put("contentHeight", 470);
       opcoes.put("contentWidth", 1000);
       RequestContext.getCurrentInstance().openDialog("consultaContaCaixas", opcoes, null);
       System.out.println("passou");
   } 
   
   public void handleKeyEvent() {
       contaCaixa.setNomeContaCaixa(contaCaixa.getNomeContaCaixa().toUpperCase());
    }
   
  
   
   public void zerarContaCaixa(){
       contaCaixa = new ContaCaixa();
   }
    
}
