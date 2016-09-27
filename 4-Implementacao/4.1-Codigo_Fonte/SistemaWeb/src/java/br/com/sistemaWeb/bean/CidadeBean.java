/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemaWeb.bean;

import br.com.sistemaWeb.classes.Cidade;
import br.com.sistemaWeb.classes.Estado;
import br.com.sistemaWeb.dao.CidadeDao;
import br.com.sistemaWeb.dao.EstadoDao;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author geral
 */
@ManagedBean
@ViewScoped
public class CidadeBean implements Serializable{
    private static final long serialVersionUID = 1L;
    private Cidade cidade = new Cidade();
    private List<Cidade> listaCidade;
    private CidadeDao eDao;
//    private LogEventoDAO eDaoLog;
    private String accion;
    private Cidade cidadeSelecionado;
  //  private LogEvento logEvento = new LogEvento();
    
    //paga o log
   // private UsuarioBean usuarioBean = new UsuarioBean();


    
    //private Estado estado = new Estado();

  //  public Estado getEstado() {
  //      return estado;
  //  }

   // public void setEstado(Estado estado) {
   //     this.estado = estado;
   // }
    
    

    public Cidade getCidadeSelecionado() {
        return cidadeSelecionado;
    }

    public void setCidadeSelecionado(Cidade cidadeSelecionado) {
        this.cidadeSelecionado = cidadeSelecionado;
    }

     
    
    

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public List<Cidade> getListaCidade() {
        return listaCidade;
    }

    public void setListaCidade(List<Cidade> listaCidade) {
        this.listaCidade = listaCidade;
    }

    public CidadeDao geteDao() {
        return eDao;
    }

    public void seteDao(CidadeDao eDao) {
        this.eDao = eDao;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

  

    
    public void apagar() {
            eDao = new CidadeDao();
            try {
                eDao.apagar(cidadeSelecionado);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Eliminado com sucesso"));
            } catch (SQLException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Erro ao eliminar"));
            }
    }
    
    
   
    public void salvar() {
        if (cidade != null) {
            eDao = new CidadeDao();
            
            try {
                try {
                    if (!eDao.buscaPorNome(cidade)) {
                        //carregarLog();
                        eDao.salvar(cidade);
                        //eDaoLog.salvar(logEvento);
                        System.out.println("passou no salvar");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Cidade ja existe "));
                    }
                } catch (SQLException ex1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex1.getMessage()));
                }
            } catch (Exception ex2) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex2.getMessage()));
            }
            cidade = new Cidade();
        }
    }

    
    public void salvarAntigo() throws Exception {
        if (cidade != null) {
            eDao = new CidadeDao();
            try {
                
                eDao.salvar(cidade);
                System.out.println("passou no salvar");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
            } catch (SQLException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
            }
            cidade = new Cidade();
        }
    }
    
    
    public void atualizar() {
        eDao = new CidadeDao();
        try {
            eDao.atualizar(cidade);
            System.out.println("passou no atualizar");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Alterado com sucesso "));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
        }

    }

    public void listar() throws Exception {
        CidadeDao dao;
        try {
            dao = new CidadeDao();
            listaCidade = dao.todosCidadees();
        } catch (Exception e) {
            throw e;
        }
    }

    public void buscaPorID(Cidade per) throws Exception {
        CidadeDao dao;
        Cidade temp;
        try {
            dao = new CidadeDao();
            temp = dao.buscaPorID(per);
            if (temp != null) {
                this.cidade = temp;
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
        this.cidade.setId(0);
        this.cidade.setNomeCidade("");
    }
    
    public void obterCidade(Cidade xCidade){
        this.accion = "Modificar";
        this.cidade = xCidade;
    }
    /*  
    public void carregarLog() {
        eDaoLog = new LogEventoDAO();
        try {
            logEvento.setNomeUsuario(usuarioBean.verificarUsuarioLogado().getNome().trim());
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao ao buscar o nome do cidade para o log", "Erro: " + ex.getMessage()));
        }
        logEvento.setDescricao(logEvento.getNomeUsuario() + " inseriu um cidade no sistema");
        logEvento.setData(new Date());
        logEvento.setHora(new Date());
        
    }*/
    
    public void buscaEstadoBySigla(){
        System.out.println("entrou no busca estado por sigla do cidadebean");
        EstadoDao xdaoEstado;
        Estado temp;
        String sigla = this.cidade.getEstado().getSiglaEstado();
        System.out.println(sigla);
        try {
            xdaoEstado = new EstadoDao();
            temp = xdaoEstado.buscaPorSigla(sigla);
            if (temp != null) {
                this.cidade.setEstado(temp);
                System.out.println("achou o estado " + this.cidade.getEstado().getNomeEstado());
                //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ACHOU O ESTADO", "Erro: " ));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao PROCURAR ESTADO", "Erro: " ));
               System.out.println(" nao achou o estado " ); 
            }
        } catch (Exception e) {
            //throw e;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO NO TRY BUSCA ESTADO", "Erro: " + e.getMessage() ));
        }

    }
    


   public void setarEstadoSelecionado(SelectEvent event){
      Estado estadoNovo = (Estado) event.getObject();
      estadoNovo.getClass().getName(); //esta linha maldida me fez patiar 2 dias
      this.cidade.setEstado(estadoNovo);
      //this.setEstado(estadoNovo);
   }
   
   public void listavalor(){
       System.out.println(" valor do id do estado: " + cidade.getEstado().getId());
       System.out.println(" valor do id nome estado:" + cidade.getEstado().getNomeEstado());
   }
   
   public void iniciarObjeto(){
       cidade = new Cidade();
   }
   
     public void abrirDialogoCidade(){
       Map<String, Object> opcoes = new HashMap<>();
       opcoes.put("modal", true);
       opcoes.put("resizable", false);
       opcoes.put("contentHeight", 470);
       opcoes.put("contentWidth", 1000);
       RequestContext.getCurrentInstance().openDialog("consultaCidades", opcoes, null);
       System.out.println("passou");
   } 
     
     
     public void selecionar(Cidade cidade){
    RequestContext.getCurrentInstance().closeDialog(cidade);
}    
   
      
}
