/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemaWeb.bean;

import br.com.sistemaWeb.classes.Grupo;
import br.com.sistemaWeb.dao.GrupoDao;
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
import javax.persistence.PersistenceException;
import org.primefaces.context.RequestContext;

/**
 *
 * @author geral
 */
@ManagedBean
@ViewScoped
public class GrupoBean implements Serializable{
    private static final long serialVersionUID = 1L;
    private Grupo grupo;// = new Grupo();
    private List<Grupo> listaGrupo;
    private GrupoDao eDao;
//    private LogEventoDAO eDaoLog;
    private String accion;
    private Grupo grupoSelecionado;
  //  private LogEvento logEvento = new LogEvento();
    
    //paga o log
   // private UsuarioBean usuarioBean = new UsuarioBean();

    public Grupo getGrupoSelecionado() {
        return grupoSelecionado;
    }

    public void setGrupoSelecionado(Grupo grupoSelecionado) {
        this.grupoSelecionado = grupoSelecionado;
    }

     
    
    

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public List<Grupo> getListaGrupo() {
        return listaGrupo;
    }

    public void setListaGrupo(List<Grupo> listaGrupo) {
        this.listaGrupo = listaGrupo;
    }

    public GrupoDao geteDao() {
        return eDao;
    }

    public void seteDao(GrupoDao eDao) {
        this.eDao = eDao;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

  

    
    public void apagar() {
            eDao = new GrupoDao();
            try {
                eDao.apagar(grupoSelecionado);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Eliminado com sucesso"));
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Erro ao eliminar"));
            }
    }
    
    
   
    public boolean salvar() {
        boolean retorno = false;
        if (grupo != null) {
            eDao = new GrupoDao();
            
            //try {
                try {
                    ////if (!eDao.buscaPorNome(grupo)) {
                        //carregarLog();
                        eDao.salvarH(grupo);
                        retorno = true;
                        //eDaoLog.salvar(logEvento);
                        System.out.println("passou no salvar");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
                    ////} else {
                    ////    retorno = false;
                    ////    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Grupo ja existe "));
                    ////}
                //} catch (PersistenceException pe){
                //    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Já existe", "Erro: " + pe.getMessage()));
                //    return false; 
                } catch (Exception ex1) {
                    retorno = false;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar Grupo", "Erro : " + ex1.getMessage()));
                }
           // } catch (Exception ex2) {
           //     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex2.getMessage()));
           // }
            grupo = new Grupo();
        }
        return retorno;
    }

    
    public void salvarAntigo() throws Exception {
        if (grupo != null) {
            eDao = new GrupoDao();
            try {
                
                eDao.salvar(grupo);
                System.out.println("passou no salvar");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
            } catch (SQLException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
            }
            grupo = new Grupo();
        }
    }
    
    
    public void atualizar() {
        eDao = new GrupoDao();
        try {
            eDao.atualizarH(grupo);
            System.out.println("passou no atualizar");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Alterado com sucesso "));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
        }

    }

    public void listar() throws Exception {
        GrupoDao dao;
        try {
            dao = new GrupoDao();
            listaGrupo = dao.todosGrupos();
        } catch (Exception e) {
            throw e;
        }
    }

    public void buscaPorID(Grupo per) throws Exception {
        GrupoDao dao;
        Grupo temp;
        try {
            dao = new GrupoDao();
            temp = dao.buscaPorID(per);
            if (temp != null) {
                this.grupo = temp;
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
                   if (this.salvar()){
                   this.limpiar();
                   fecharDialogo = true;
                   System.out.println(fecharDialogo);
                   }
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
        this.grupo.setId(0);
        this.grupo.setNomeGrupo("");
   
    }
    
    public void obterGrupo(Grupo xGrupo){
        this.accion = "Modificar";
        this.grupo = xGrupo;
    }
    /*  
    public void carregarLog() {
        eDaoLog = new LogEventoDAO();
        try {
            logEvento.setNomeUsuario(usuarioBean.verificarUsuarioLogado().getNome().trim());
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao ao buscar o nome do grupo para o log", "Erro: " + ex.getMessage()));
        }
        logEvento.setDescricao(logEvento.getNomeUsuario() + " inseriu um grupo no sistema");
        logEvento.setData(new Date());
        logEvento.setHora(new Date());
        
    }*/
    
    
public void selecionar(Grupo var_grupo){
    RequestContext.getCurrentInstance().closeDialog(var_grupo);
}    
    

   public void abrirDialogoGrupo(){
       Map<String, Object> opcoes = new HashMap<>();
       opcoes.put("modal", true);
       opcoes.put("resizable", false);
       opcoes.put("contentHeight", 470);
       opcoes.put("contentWidth", 1000);
       RequestContext.getCurrentInstance().openDialog("consultaGrupos", opcoes, null);
       System.out.println("passou");
   } 
   
   public void handleKeyEvent() {
       grupo.setNomeGrupo(grupo.getNomeGrupo().toUpperCase());
    }
   
  
   
   public void zerarGrupo(){
       grupo = new Grupo();
   }
    
}
