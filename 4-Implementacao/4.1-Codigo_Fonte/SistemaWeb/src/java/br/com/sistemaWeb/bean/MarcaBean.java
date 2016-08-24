/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemaWeb.bean;

import br.com.sistemaWeb.classes.Marca;
import br.com.sistemaWeb.dao.MarcaDao;
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
public class MarcaBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Marca marca = new Marca();
    private List<Marca> listaMarca;
    private MarcaDao eDao;
//    private LogEventoDAO eDaoLog;
    private String accion;
    private Marca marcaSelecionado;
    private String consultaNome;

    public String getConsultaNome() {
        return consultaNome;
    }

    public void setConsultaNome(String consultaNome) {
        this.consultaNome = consultaNome;
    }
    
    
    
  
    
  //  private LogEvento logEvento = new LogEvento();

    //paga o log
    // private UsuarioBean usuarioBean = new UsuarioBean();

  

   
 
    public Marca getMarcaSelecionado() {
        return marcaSelecionado;
    }

    public void setMarcaSelecionado(Marca marcaSelecionado) {
        this.marcaSelecionado = marcaSelecionado;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public List<Marca> getListaMarca() {
        return listaMarca;
    }

    public void setListaMarca(List<Marca> listaMarca) {
        this.listaMarca = listaMarca;
    }

    public MarcaDao geteDao() {
        return eDao;
    }

    public void seteDao(MarcaDao eDao) {
        this.eDao = eDao;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public void apagar() {
        eDao = new MarcaDao();
        try {
            eDao.apagar(marcaSelecionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Eliminado com sucesso"));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Erro ao eliminar"));
        }
    }

    public boolean salvar() throws Exception{
    boolean retorno = false;    
    System.out.println("entrou no salbarBean");
        if (marca != null) {
            eDao = new MarcaDao();
            try {
                try {
                    if (!eDao.buscaPorNome(marca)) {
                        //carregarLog();
                        eDao.salvar(marca);
                        //eDaoLog.salvar(logEvento);
                        System.out.println("passou no salvar");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
                        retorno = true;
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Marca ja existe "));
                        retorno = false;
                    }
                } catch (SQLException ex1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex1.getMessage()));
                    retorno = false;
                }
            } catch (Exception ex2) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar marca*", "Erro: " + ex2.getMessage()));
                retorno = false;
                throw ex2;
            }
            marca = new Marca();
        }
        return retorno;
    }


    public void atualizar() {
        eDao = new MarcaDao();
        try {
            eDao.atualizar(marca);
            System.out.println("passou no atualizar");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Alterado com sucesso "));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
        }

    }

    public void listar() throws Exception {
        MarcaDao dao;
        try {
            dao = new MarcaDao();
            listaMarca = dao.todosMarcaes();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void consultar() throws Exception{
        System.out.println("consultar");
        MarcaDao dao;
        dao = new MarcaDao();
        try {
            listaMarca = dao.consultaMarcaes(this.consultaNome);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao consultar ", "Erro: " + ex.getMessage()));
            throw ex;
        }
    }

    public void buscaPorID(Marca per) throws Exception {
        MarcaDao dao;
        Marca temp;
        try {
            dao = new MarcaDao();
            temp = dao.buscaPorID(per);
            if (temp != null) {
                this.marca = temp;
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
                try {
                    if(this.salvar()){
                       this.limpiar();
                       fecharDialogo = true;
                    } else{
                        System.out.println("erro booooooo");
                    };
                    System.out.println("valor do fechardialogo" + fecharDialogo);
                } catch (Exception e) {
                    System.out.println("passou no erro do operar");

                }

                break;
            case "Modificar":
                try{
                this.atualizar();
                this.limpiar();
                fecharDialogo = true;
                break;
                }catch(Exception ex1){
                    System.out.println("Erro ao atualizar marca");
                }
        }
        context.addCallbackParam("fecharDialogo", fecharDialogo);
    }

    public void limpiar() {
       // this.marca.setId(0);
        this.marca.setNomeMarca("");

    }

    public void obterMarca(Marca xMarca) {
        this.accion = "Modificar";
        this.marca = xMarca;
    }
    /*  
     public void carregarLog() {
     eDaoLog = new LogEventoDAO();
     try {
     logEvento.setNomeUsuario(usuarioBean.verificarUsuarioLogado().getNome().trim());
     } catch (IOException ex) {
     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao ao buscar o nome do marca para o log", "Erro: " + ex.getMessage()));
     }
     logEvento.setDescricao(logEvento.getNomeUsuario() + " inseriu um marca no sistema");
     logEvento.setData(new Date());
     logEvento.setHora(new Date());
        
     }*/

    public void selecionar(Marca var_marca) {
        if (var_marca!=null){
            System.out.println("marca chegou no selecionar");
        } else {
            System.out.println("marca não chegou no selecionar");
        }
        
        try{
        RequestContext.getCurrentInstance().closeDialog(var_marca);
        } catch(Exception e){
            System.out.println("erro ao selecionar marca");
        }
    }

    public void abrirDialogoMarca() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", false);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 300);
        opcoes.put("contentWidth", 400);
        //opcoes.put("widgetVar", "wconsultaMarcas");
        RequestContext.getCurrentInstance().openDialog("consultaMarcas", opcoes, null);
        System.out.println("passou no dialogo do marca");
    }
    
    public void abrirDialogoMarca2() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 470);
        opcoes.put("contentWidth", 1000);
        //opcoes.put("widgetVar", "wdialogoUUU");
        RequestContext.getCurrentInstance().openDialog("consultaMarcas2", opcoes, null);
        System.out.println("passou no dialogo do marca2");
    }
    
    public void abrirDialogoMarca3() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 470);
        opcoes.put("contentWidth", 1000);
        //opcoes.put("widgetVar", "wdialogoUUU");
        RequestContext.getCurrentInstance().openDialog("wdialogoCadastroMarcaesxxx", opcoes, null);
        System.out.println("passou no dialogo do marca2");
    }

    public void handleKeyEvent() {
        marca.setNomeMarca(marca.getNomeMarca().toUpperCase());
    }
  

   

    public void zerarMarca() {
        marca = new Marca();
    }
    
 public void nãoFazNada(){
     System.out.println("não faz nada");
 }

    
}
