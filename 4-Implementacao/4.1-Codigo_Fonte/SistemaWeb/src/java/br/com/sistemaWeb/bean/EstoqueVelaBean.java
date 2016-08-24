/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemaWeb.bean;

import br.com.sistemaWeb.classes.EstoqueVela;
import br.com.sistemaWeb.dao.EstoqueVelaDao;
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
public class EstoqueVelaBean implements Serializable{
    private static final long serialVersionUID = 1L;
    private EstoqueVela estoqueVela = new EstoqueVela();
    private List<EstoqueVela> listaEstoqueVela;
    private EstoqueVelaDao eDao;
//    private LogEventoDAO eDaoLog;
    private String accion;
    private EstoqueVela estoqueVelaSelecionado;
  //  private LogEvento logEvento = new LogEvento();
    
    //paga o log
   // private UsuarioBean usuarioBean = new UsuarioBean();

    public EstoqueVela getEstoqueVelaSelecionado() {
        return estoqueVelaSelecionado;
    }

    public void setEstoqueVelaSelecionado(EstoqueVela estoqueVelaSelecionado) {
        this.estoqueVelaSelecionado = estoqueVelaSelecionado;
    }

     
    
    

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public EstoqueVela getEstoqueVela() {
        return estoqueVela;
    }

    public void setEstoqueVela(EstoqueVela estoqueVela) {
        this.estoqueVela = estoqueVela;
    }

    public List<EstoqueVela> getListaEstoqueVela() {
        return listaEstoqueVela;
    }

    public void setListaEstoqueVela(List<EstoqueVela> listaEstoqueVela) {
        this.listaEstoqueVela = listaEstoqueVela;
    }

    public EstoqueVelaDao geteDao() {
        return eDao;
    }

    public void seteDao(EstoqueVelaDao eDao) {
        this.eDao = eDao;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

  

    
    public void apagar() {
            eDao = new EstoqueVelaDao();
            try {
                eDao.apagar(estoqueVelaSelecionado);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Eliminado com sucesso"));
            } catch (SQLException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Erro ao eliminar"));
            }
    }
    
    
   
    public void salvar() {
        if (estoqueVela != null) {
            eDao = new EstoqueVelaDao();
            
            try {
                try {
                    if (!eDao.buscaPorNome(estoqueVela)) {
                        //carregarLog();
                        eDao.salvar(estoqueVela);
                        //eDaoLog.salvar(logEvento);
                        System.out.println("passou no salvar");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "EstoqueVela ja existe "));
                    }
                } catch (SQLException ex1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex1.getMessage()));
                }
            } catch (Exception ex2) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex2.getMessage()));
            }
            estoqueVela = new EstoqueVela();
        }
    }

    
    public void salvarAntigo() throws Exception {
        if (estoqueVela != null) {
            eDao = new EstoqueVelaDao();
            try {
                
                eDao.salvar(estoqueVela);
                System.out.println("passou no salvar");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
            } catch (SQLException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
            }
            estoqueVela = new EstoqueVela();
        }
    }
    
    
    public void atualizar() {
        eDao = new EstoqueVelaDao();
        try {
            eDao.atualizar(estoqueVela);
            System.out.println("passou no atualizar");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Alterado com sucesso "));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
        }

    }

    public void listar() throws Exception {
        EstoqueVelaDao dao;
        try {
            dao = new EstoqueVelaDao();
            listaEstoqueVela = dao.todosEstoqueVela();
        } catch (Exception e) {
            throw e;
        }
    }

    public void buscaPorID(EstoqueVela per) throws Exception {
        EstoqueVelaDao dao;
        EstoqueVela temp;
        try {
            dao = new EstoqueVelaDao();
            temp = dao.buscaPorID(per);
            if (temp != null) {
                this.estoqueVela = temp;
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
        this.estoqueVela.setId(0);
  //      this.estoqueVela.setNomeEstoqueVela("");
   
    }
    
    public void obterEstoqueVela(EstoqueVela xEstoqueVela){
        this.accion = "Modificar";
        this.estoqueVela = xEstoqueVela;
    }
    /*  
    public void carregarLog() {
        eDaoLog = new LogEventoDAO();
        try {
            logEvento.setNomeUsuario(usuarioBean.verificarUsuarioLogado().getNome().trim());
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao ao buscar o nome do estoqueVela para o log", "Erro: " + ex.getMessage()));
        }
        logEvento.setDescricao(logEvento.getNomeUsuario() + " inseriu um estoqueVela no sistema");
        logEvento.setData(new Date());
        logEvento.setHora(new Date());
        
    }*/
    
    
public void selecionar(EstoqueVela var_estoqueVela){
    RequestContext.getCurrentInstance().closeDialog(var_estoqueVela);
}    
    

   public void abrirDialogoEstoqueVela(){
       Map<String, Object> opcoes = new HashMap<>();
       opcoes.put("modal", true);
       opcoes.put("resizable", false);
       opcoes.put("contentHeight", 470);
       opcoes.put("contentWidth", 1000);
       RequestContext.getCurrentInstance().openDialog("consultaEstoqueVelas", opcoes, null);
       System.out.println("passou");
   } 
   
   public void handleKeyEvent() {
//       estoqueVela.setNomeEstoqueVela(estoqueVela.getNomeEstoqueVela().toUpperCase());
    }
   
  
   
   public void zerarEstoqueVela(){
       estoqueVela = new EstoqueVela();
   }
   
   public EstoqueVela buscaPorID2(EstoqueVela per) throws Exception {
        EstoqueVelaDao dao;
        EstoqueVela temp;
        try {
            dao = new EstoqueVelaDao();
            temp = dao.buscaPorID(per);
            if (temp != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "EstoqueVela encontrado "));
                //this.estoqueVela = temp;
               // this.accion = "Modificar";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "EstoqueVela n'ao existe "));
            }
        } catch (Exception e) {
            throw e;
        }
        return temp;
    }
    
}
