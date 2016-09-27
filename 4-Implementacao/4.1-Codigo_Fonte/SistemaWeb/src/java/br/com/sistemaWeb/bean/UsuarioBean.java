/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemaWeb.bean;

import br.com.sistemaWeb.classes.Usuario;
import br.com.sistemaWeb.dao.UsuarioDao;
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
public class UsuarioBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Usuario usuario = new Usuario();
    private List<Usuario> listaUsuario;
    private UsuarioDao eDao;
//    private LogEventoDAO eDaoLog;
    private String accion;
    private Usuario usuarioSelecionado;
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

  

   
 
    public Usuario getUsuarioSelecionado() {
        return usuarioSelecionado;
    }

    public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
        this.usuarioSelecionado = usuarioSelecionado;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getListaUsuario() {
        return listaUsuario;
    }

    public void setListaUsuario(List<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    public UsuarioDao geteDao() {
        return eDao;
    }

    public void seteDao(UsuarioDao eDao) {
        this.eDao = eDao;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public void apagar() {
        eDao = new UsuarioDao();
        try {
            eDao.apagar(usuarioSelecionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Eliminado com sucesso"));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Erro ao eliminar"));
        }
    }

    public boolean salvar() throws Exception{
    boolean retorno = false;    
    System.out.println("entrou no salbarBean");
        if (usuario != null) {
            eDao = new UsuarioDao();
            try {
                try {
                    if (!eDao.buscaPorNome(usuario)) {
                        //carregarLog();
                        eDao.salvar(usuario);
                        //eDaoLog.salvar(logEvento);
                        System.out.println("passou no salvar");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
                        retorno = true;
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Usuario ja existe "));
                        retorno = false;
                    }
                } catch (SQLException ex1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex1.getMessage()));
                    retorno = false;
                }
            } catch (Exception ex2) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar usuario*", "Erro: " + ex2.getMessage()));
                retorno = false;
                throw ex2;
            }
            usuario = new Usuario();
        }
        return retorno;
    }


    public void atualizar() {
        eDao = new UsuarioDao();
        try {
            eDao.atualizar(usuario);
            System.out.println("passou no atualizar");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Alterado com sucesso "));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
        }

    }

    public void listar() throws Exception {
        UsuarioDao dao;
        try {
            dao = new UsuarioDao();
            listaUsuario = dao.todosUsuarios();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void consultar() throws Exception{
        System.out.println("consultar");
        UsuarioDao dao;
        dao = new UsuarioDao();
        try {
            listaUsuario = dao.consultaUsuarios(this.consultaNome);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao consultar ", "Erro: " + ex.getMessage()));
            throw ex;
        }
    }
    
   /* public void consultar() throws Exception{
        System.out.println("consultar");
        UsuarioDao dao;
        dao = new UsuarioDao();
        try {
           // listaUsuario = dao.consultaUsuarios(this.consultaNome);
            listaUsuario = dao.buscarTodos();
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao consultar ", "Erro: " + ex.getMessage()));
            throw ex;
        }
    }*/

    public void buscaPorID(Usuario per) throws Exception {
        UsuarioDao dao;
        Usuario temp;
        try {
            dao = new UsuarioDao();
            temp = dao.buscaPorID(per);
            if (temp != null) {
                this.usuario = temp;
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
                this.atualizar();
                this.limpiar();
                break;
        }
        context.addCallbackParam("fecharDialogo", fecharDialogo);
    }

    public void limpiar() {
       // this.usuario.setId(0);
        this.usuario.setNomeUsuario("");

    }

    public void obterUsuario(Usuario xUsuario) {
        this.accion = "Modificar";
        this.usuario = xUsuario;
    }
    /*  
     public void carregarLog() {
     eDaoLog = new LogEventoDAO();
     try {
     logEvento.setNomeUsuario(usuarioBean.verificarUsuarioLogado().getNome().trim());
     } catch (IOException ex) {
     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao ao buscar o nome do usuario para o log", "Erro: " + ex.getMessage()));
     }
     logEvento.setDescricao(logEvento.getNomeUsuario() + " inseriu um usuario no sistema");
     logEvento.setData(new Date());
     logEvento.setHora(new Date());
        
     }*/

    public void selecionar(Usuario var_usuario) {
        if (var_usuario!=null){
            System.out.println("usuario chegou no selecionar");
        } else {
            System.out.println("usuario não chegou no selecionar");
        }
        
        try{
        RequestContext.getCurrentInstance().closeDialog(var_usuario);
        } catch(Exception e){
            System.out.println("erro ao selecionar usuario");
        }
    }

    public void abrirDialogoUsuario() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", false);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 400);
        opcoes.put("contentWidth", 700);
        //opcoes.put("widgetVar", "wconsultaUsuarios");
        RequestContext.getCurrentInstance().openDialog("consultaUsuarios2", opcoes, null);
        System.out.println("passou no dialogo do usuario");
    }
    
    public void abrirDialogoUsuario2() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 470);
        opcoes.put("contentWidth", 1000);
        //opcoes.put("widgetVar", "wdialogoUUU");
        RequestContext.getCurrentInstance().openDialog("consultaUsuarios2", opcoes, null);
        System.out.println("passou no dialogo do usuario2");
    }
    
    public void abrirDialogoUsuario3() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 470);
        opcoes.put("contentWidth", 1000);
        //opcoes.put("widgetVar", "wdialogoUUU");
        RequestContext.getCurrentInstance().openDialog("wdialogoCadastroUsuarioesxxx", opcoes, null);
        System.out.println("passou no dialogo do usuario2");
    }

    public void handleKeyEvent() {
        usuario.setNomeUsuario(usuario.getNomeUsuario().toUpperCase());
    }
  

   

    public void zerarUsuario() {
        usuario = new Usuario();
    }
    
 public void nãoFazNada(){
     System.out.println("não faz nada");
 }

    
}
