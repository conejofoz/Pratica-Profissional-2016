/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemaWeb.bean;

import br.com.sistemaWeb.classes.Cargo;
import br.com.sistemaWeb.classes.Funcionario;
import br.com.sistemaWeb.dao.CargoDao;
import br.com.sistemaWeb.dao.FuncionarioDao;
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
import org.primefaces.event.SelectEvent;

/**
 *
 * @author geral
 */
@ManagedBean
@ViewScoped
public class FuncionarioBean implements Serializable{
    private static final long serialVersionUID = 1L;
    private Funcionario funcionario = new Funcionario();
    private List<Funcionario> listaFuncionario;
    private FuncionarioDao eDao;
//    private LogEventoDAO eDaoLog;
    private String accion;
    private Funcionario funcionarioSelecionado;
  //  private LogEvento logEvento = new LogEvento();
    
    //paga o log
   // private UsuarioBean usuarioBean = new UsuarioBean();
    private Cargo cargo = new Cargo();

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
    
    

    public Funcionario getFuncionarioSelecionado() {
        return funcionarioSelecionado;
    }

    public void setFuncionarioSelecionado(Funcionario funcionarioSelecionado) {
        this.funcionarioSelecionado = funcionarioSelecionado;
    }

     
    
    

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public List<Funcionario> getListaFuncionario() {
        return listaFuncionario;
    }

    public void setListaFuncionario(List<Funcionario> listaFuncionario) {
        this.listaFuncionario = listaFuncionario;
    }

    public FuncionarioDao geteDao() {
        return eDao;
    }

    public void seteDao(FuncionarioDao eDao) {
        this.eDao = eDao;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

  

    
    public void apagar() {
            eDao = new FuncionarioDao();
            try {
                eDao.apagar(funcionarioSelecionado);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Eliminado com sucesso"));
            } catch (SQLException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Erro ao eliminar"));
            }
    }
    
    
   
    public void salvar() {
        if (funcionario != null) {
            eDao = new FuncionarioDao();
            
            try {
                try {
                    if (!eDao.buscaPorNome(funcionario)) {
                        //carregarLog();
                        eDao.salvar(funcionario);
                        //eDaoLog.salvar(logEvento);
                        System.out.println("passou no salvar");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Funcionario ja existe "));
                    }
                } catch (SQLException ex1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex1.getMessage()));
                }
            } catch (Exception ex2) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex2.getMessage()));
            }
            funcionario = new Funcionario();
        }
    }

    
    public void salvarAntigo() throws Exception {
        if (funcionario != null) {
            eDao = new FuncionarioDao();
            try {
                
                eDao.salvar(funcionario);
                System.out.println("passou no salvar");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
            } catch (SQLException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
            }
            funcionario = new Funcionario();
        }
    }
    
    
    public void atualizar() {
        eDao = new FuncionarioDao();
        try {
            eDao.atualizar(funcionario);
            System.out.println("passou no atualizar");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Alterado com sucesso "));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
        }

    }

    public void listar() throws Exception {
        FuncionarioDao dao;
        try {
            dao = new FuncionarioDao();
            listaFuncionario = dao.todosFuncionarios();
        } catch (Exception e) {
            throw e;
        }
    }

    public void buscaPorID(Funcionario per) throws Exception {
        FuncionarioDao dao;
        Funcionario temp;
        try {
            dao = new FuncionarioDao();
            temp = dao.buscaPorID(per);
            if (temp != null) {
                this.funcionario = temp;
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
        this.funcionario.setId(0);
        this.funcionario.setNomeFuncionario("");
        
    }
    
    public void obterFuncionario(Funcionario xFuncionario){
        this.accion = "Modificar";
        this.funcionario = xFuncionario;
    }
    /*  
    public void carregarLog() {
        eDaoLog = new LogEventoDAO();
        try {
            logEvento.setNomeUsuario(usuarioBean.verificarUsuarioLogado().getNome().trim());
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao ao buscar o nome do funcionario para o log", "Erro: " + ex.getMessage()));
        }
        logEvento.setDescricao(logEvento.getNomeUsuario() + " inseriu um funcionario no sistema");
        logEvento.setData(new Date());
        logEvento.setHora(new Date());
        
    }*/
    
    
public void selecionar(Funcionario var_funcionario){
    RequestContext.getCurrentInstance().closeDialog(var_funcionario);
}    
    

   public void abrirDialogoFuncionario(){
       Map<String, Object> opcoes = new HashMap<>();
       opcoes.put("modal", true);
       opcoes.put("resizable", false);
       opcoes.put("contentHeight", 470);
       opcoes.put("contentWidth", 1000);
       RequestContext.getCurrentInstance().openDialog("consultaFuncionarios", opcoes, null);
       System.out.println("passou");
   } 
   
   public void handleKeyEvent() {
       funcionario.setNomeFuncionario(funcionario.getNomeFuncionario().toUpperCase());
    }
   
    public void handleKeyEventEmail() {
       funcionario.setEmail(funcionario.getEmail().toLowerCase());
    }
  
   
   public void zerarFuncionario(){
       funcionario = new Funcionario();
   }
   
   
   public Cargo buscaPorID2(Cargo per) throws Exception {
        CargoDao dao;
        Cargo temp;
        try {
            dao = new CargoDao();
            temp = dao.buscaPorID(per);
            if (temp != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Cargo encontrado "));
                this.funcionario.setCargo(temp);
               // this.accion = "Modificar";
                System.out.println("codigo do cargo: " + funcionario.getCargo().getId());
                System.out.println("Nome do cargo  : " + funcionario.getCargo().getNomeCargo());
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Cargo n'ao existe "));
            }
        } catch (Exception e) {
            throw e;
        }
        return temp;
    }
   
   
   public void setarCargoSelecionado(SelectEvent event){
      Cargo cargoNovo = (Cargo) event.getObject();
      cargoNovo.getClass().getName(); //esta linha maldida me fez patiar 2 dias
      funcionario.setCargo(cargoNovo); 
      setCargo(cargoNovo);
   }
    
}
