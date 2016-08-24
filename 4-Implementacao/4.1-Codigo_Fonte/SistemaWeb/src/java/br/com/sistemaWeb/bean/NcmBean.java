/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemaWeb.bean;

import br.com.sistemaWeb.classes.Ncm;
import br.com.sistemaWeb.dao.NcmDao;
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
public class NcmBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Ncm ncm = new Ncm();
    private List<Ncm> listaNcm;
    private NcmDao eDao;
//    private LogEventoDAO eDaoLog;
    private String accion;
    private Ncm ncmSelecionado;
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
    public Ncm getNcmSelecionado() {
        return ncmSelecionado;
    }

    public void setNcmSelecionado(Ncm ncmSelecionado) {
        this.ncmSelecionado = ncmSelecionado;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Ncm getNcm() {
        return ncm;
    }

    public void setNcm(Ncm ncm) {
        this.ncm = ncm;
    }

    public List<Ncm> getListaNcm() {
        return listaNcm;
    }

    public void setListaNcm(List<Ncm> listaNcm) {
        this.listaNcm = listaNcm;
    }

    public NcmDao geteDao() {
        return eDao;
    }

    public void seteDao(NcmDao eDao) {
        this.eDao = eDao;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public void apagar() {
        eDao = new NcmDao();
        try {
            eDao.apagar(ncmSelecionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Eliminado com sucesso"));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Erro ao eliminar"));
        }
    }

    public boolean salvar() throws Exception {
        boolean retorno = false;
        System.out.println("entrou no salbarBean");
        if (ncm != null) {
            eDao = new NcmDao();
            try {
                try {
                    if (!eDao.buscaPorNome(ncm)) {
                        //carregarLog();
                        eDao.salvar(ncm);
                        //eDaoLog.salvar(logEvento);
                        System.out.println("passou no salvar");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
                        retorno = true;
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Ncm ja existe "));
                        retorno = false;
                    }
                } catch (SQLException ex1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex1.getMessage()));
                    retorno = false;
                }
            } catch (Exception ex2) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar ncm*", "Erro: " + ex2.getMessage()));
                retorno = false;
                throw ex2;
            }
            ncm = new Ncm();
        }
        return retorno;
    }

    public void atualizar() {
        eDao = new NcmDao();
        try {
            eDao.atualizar(ncm);
            System.out.println("passou no atualizar");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Alterado com sucesso "));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
        }

    }

    public void listar() throws Exception {
        NcmDao dao;
        try {
            dao = new NcmDao();
            listaNcm = dao.todosNcmes();
        } catch (Exception e) {
            throw e;
        }
    }

    public void consultar() throws Exception {
        System.out.println("consultar");
        NcmDao dao;
        dao = new NcmDao();
        try {
            listaNcm = dao.consultaNcmes(this.consultaNome);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao consultar ", "Erro: " + ex.getMessage()));
            throw ex;
        }
    }

    public void buscaPorID(Ncm per) throws Exception {
        NcmDao dao;
        Ncm temp;
        try {
            dao = new NcmDao();
            temp = dao.buscaPorID(per);
            if (temp != null) {
                this.ncm = temp;
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
                    if (this.salvar()) {
                        this.limpiar();
                        fecharDialogo = true;
                    } else {
                        System.out.println("erro booooooo");
                    };
                    System.out.println("valor do fechardialogo" + fecharDialogo);
                } catch (Exception e) {
                    System.out.println("passou no erro do operar");

                }

                break;
            case "Modificar":
                try {
                    this.atualizar();
                    this.limpiar();
                    fecharDialogo = true;
                    break;
                } catch (Exception e1) {
                    System.out.println("Erro ao atualizar");
                }

        }
        context.addCallbackParam("fecharDialogo", fecharDialogo);
    }

    public void limpiar() {
        // this.ncm.setId(0);
        this.ncm.setNomeNcm("");

    }

    public void obterNcm(Ncm xNcm) {
        this.accion = "Modificar";
        this.ncm = xNcm;
    }
    /*  
     public void carregarLog() {
     eDaoLog = new LogEventoDAO();
     try {
     logEvento.setNomeUsuario(usuarioBean.verificarUsuarioLogado().getNome().trim());
     } catch (IOException ex) {
     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao ao buscar o nome do ncm para o log", "Erro: " + ex.getMessage()));
     }
     logEvento.setDescricao(logEvento.getNomeUsuario() + " inseriu um ncm no sistema");
     logEvento.setData(new Date());
     logEvento.setHora(new Date());
        
     }*/

    public void selecionar(Ncm var_ncm) {
        if (var_ncm != null) {
            System.out.println("ncm chegou no selecionar");
        } else {
            System.out.println("ncm não chegou no selecionar");
        }

        try {
            RequestContext.getCurrentInstance().closeDialog(var_ncm);
        } catch (Exception e) {
            System.out.println("erro ao selecionar ncm");
        }
    }

    public void abrirDialogoNcm() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", false);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 400);
        opcoes.put("contentWidth", 700);
        //opcoes.put("widgetVar", "wconsultaNcms");
        RequestContext.getCurrentInstance().openDialog("consultaNcm2", opcoes, null);
        System.out.println("passou no dialogo do ncm");
    }

    public void abrirDialogoNcm2() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 470);
        opcoes.put("contentWidth", 1000);
        //opcoes.put("widgetVar", "wdialogoUUU");
        RequestContext.getCurrentInstance().openDialog("consultaNcms2", opcoes, null);
        System.out.println("passou no dialogo do ncm2");
    }

    public void abrirDialogoNcm3() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 470);
        opcoes.put("contentWidth", 1000);
        //opcoes.put("widgetVar", "wdialogoUUU");
        RequestContext.getCurrentInstance().openDialog("wdialogoCadastroNcmesxxx", opcoes, null);
        System.out.println("passou no dialogo do ncm2");
    }

    public void handleKeyEvent() {
        ncm.setNomeNcm(ncm.getNomeNcm().toUpperCase());
    }

    public void zerarNcm() {
        ncm = new Ncm();
    }

    public void nãoFazNada() {
        System.out.println("não faz nada");
    }

}
