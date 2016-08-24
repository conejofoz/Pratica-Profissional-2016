/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemaWeb.bean;

import br.com.sistemaWeb.classes.Cargo;
import br.com.sistemaWeb.dao.CargoDao;
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
public class CargoBean implements Serializable{
    private static final long serialVersionUID = 1L;
    private Cargo cargo = new Cargo();
    private List<Cargo> listaCargo;
    private CargoDao eDao;
//    private LogEventoDAO eDaoLog;
    private String accion;
    private Cargo cargoSelecionado;
  //  private LogEvento logEvento = new LogEvento();
    
    //paga o log
   // private UsuarioBean usuarioBean = new UsuarioBean();

    public Cargo getCargoSelecionado() {
        return cargoSelecionado;
    }

    public void setCargoSelecionado(Cargo cargoSelecionado) {
        this.cargoSelecionado = cargoSelecionado;
    }

     
    
    

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public List<Cargo> getListaCargo() {
        return listaCargo;
    }

    public void setListaCargo(List<Cargo> listaCargo) {
        this.listaCargo = listaCargo;
    }

    public CargoDao geteDao() {
        return eDao;
    }

    public void seteDao(CargoDao eDao) {
        this.eDao = eDao;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

  

    
    public void apagar() {
            eDao = new CargoDao();
            try {
                eDao.apagar(cargoSelecionado);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Eliminado com sucesso"));
            } catch (SQLException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Erro ao eliminar"));
            }
    }
    
    
   
    public void salvar() {
        if (cargo != null) {
            eDao = new CargoDao();
            
            try {
                try {
                    if (!eDao.buscaPorNome(cargo)) {
                        //carregarLog();
                        eDao.salvar(cargo);
                        //eDaoLog.salvar(logEvento);
                        System.out.println("passou no salvar");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Cargo ja existe "));
                    }
                } catch (SQLException ex1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex1.getMessage()));
                }
            } catch (Exception ex2) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex2.getMessage()));
            }
            cargo = new Cargo();
        }
    }

    
    public void salvarAntigo() throws Exception {
        if (cargo != null) {
            eDao = new CargoDao();
            try {
                
                eDao.salvar(cargo);
                System.out.println("passou no salvar");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
            } catch (SQLException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
            }
            cargo = new Cargo();
        }
    }
    
    
    public void atualizar() {
        eDao = new CargoDao();
        try {
            eDao.atualizar(cargo);
            System.out.println("passou no atualizar");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Alterado com sucesso "));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
        }

    }

    public void listar() throws Exception {
        CargoDao dao;
        try {
            dao = new CargoDao();
            listaCargo = dao.todosCargos();
        } catch (Exception e) {
            throw e;
        }
    }

    public void buscaPorID(Cargo per) throws Exception {
        CargoDao dao;
        Cargo temp;
        try {
            dao = new CargoDao();
            temp = dao.buscaPorID(per);
            if (temp != null) {
                this.cargo = temp;
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
        this.cargo.setId(0);
        this.cargo.setNomeCargo("");
   
    }
    
    public void obterCargo(Cargo xCargo){
        this.accion = "Modificar";
        this.cargo = xCargo;
    }
    /*  
    public void carregarLog() {
        eDaoLog = new LogEventoDAO();
        try {
            logEvento.setNomeUsuario(usuarioBean.verificarUsuarioLogado().getNome().trim());
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao ao buscar o nome do cargo para o log", "Erro: " + ex.getMessage()));
        }
        logEvento.setDescricao(logEvento.getNomeUsuario() + " inseriu um cargo no sistema");
        logEvento.setData(new Date());
        logEvento.setHora(new Date());
        
    }*/
    
    
public void selecionar(Cargo var_cargo){
    RequestContext.getCurrentInstance().closeDialog(var_cargo);
}    
    

   public void abrirDialogoCargo(){
       Map<String, Object> opcoes = new HashMap<>();
       opcoes.put("modal", true);
       opcoes.put("resizable", false);
       opcoes.put("contentHeight", 470);
       opcoes.put("contentWidth", 1000);
       RequestContext.getCurrentInstance().openDialog("consultaCargos", opcoes, null);
       System.out.println("passou");
   } 
   
   public void handleKeyEvent() {
       cargo.setNomeCargo(cargo.getNomeCargo().toUpperCase());
    }
   
  
   
   public void zerarCargo(){
       cargo = new Cargo();
   }
   
   public Cargo buscaPorID2(Cargo per) throws Exception {
        CargoDao dao;
        Cargo temp;
        try {
            dao = new CargoDao();
            temp = dao.buscaPorID(per);
            if (temp != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Cargo encontrado "));
                //this.cargo = temp;
               // this.accion = "Modificar";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Cargo n'ao existe "));
            }
        } catch (Exception e) {
            throw e;
        }
        return temp;
    }
    
}
