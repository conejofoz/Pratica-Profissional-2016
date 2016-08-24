/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemaWeb.bean;

import br.com.sistemaWeb.classes.Estado;
import br.com.sistemaWeb.classes.Pais;
import br.com.sistemaWeb.dao.EstadoDao;
import br.com.sistemaWeb.dao.PaisDao;
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
public class EstadoBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Estado estado = new Estado();
    private List<Estado> listaEstado;
    private EstadoDao eDao;
//    private LogEventoDAO eDaoLog;
    private String accion;
    private Estado estadoSelecionado;
  //  private LogEvento logEvento = new LogEvento();

    //paga o log
    // private UsuarioBean usuarioBean = new UsuarioBean();
    private Pais pais = new Pais();

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Estado getEstadoSelecionado() {
        return estadoSelecionado;
    }

    public void setEstadoSelecionado(Estado estadoSelecionado) {
        this.estadoSelecionado = estadoSelecionado;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public List<Estado> getListaEstado() {
        return listaEstado;
    }

    public void setListaEstado(List<Estado> listaEstado) {
        this.listaEstado = listaEstado;
    }

    public EstadoDao geteDao() {
        return eDao;
    }

    public void seteDao(EstadoDao eDao) {
        this.eDao = eDao;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public void apagar() {
        eDao = new EstadoDao();
        try {
            eDao.apagar(estadoSelecionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Eliminado com sucesso"));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Erro ao eliminar"));
        }
    }

    public void salvar() {
        if (estado != null) {
            eDao = new EstadoDao();

            try {
                try {
                    if (!eDao.buscaPorNome(estado)) {
                        //carregarLog();
                        eDao.salvar(estado);
                        //eDaoLog.salvar(logEvento);
                        System.out.println("passou no salvar");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Estado ja existe "));
                    }
                } catch (SQLException ex1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex1.getMessage()));
                }
            } catch (Exception ex2) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex2.getMessage()));
            }
            estado = new Estado();
        }
    }

    public void salvarAntigo() throws Exception {
        if (estado != null) {
            eDao = new EstadoDao();
            try {

                eDao.salvar(estado);
                System.out.println("passou no salvar");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
            } catch (SQLException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
            }
            estado = new Estado();
        }
    }

    public void atualizar() {
        eDao = new EstadoDao();
        try {
            eDao.atualizar(estado);
            System.out.println("passou no atualizar");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Alterado com sucesso "));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
        }

    }

    public void listar() throws Exception {
        EstadoDao dao;
        try {
            dao = new EstadoDao();
            listaEstado = dao.todosEstadoes();
        } catch (Exception e) {
            throw e;
        }
    }

    public void buscaPorID(Estado per) throws Exception {
        EstadoDao dao;
        Estado temp;
        try {
            dao = new EstadoDao();
            temp = dao.buscaPorID(per);
            if (temp != null) {
                this.estado = temp;
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
                    this.salvar();
                    this.limpiar();
                    fecharDialogo = true;
                    System.out.println(fecharDialogo);
                } catch (Exception e) {
                    System.out.println("passou no erro do operar");
                }
                break;
            case "Modificar":
                try {
                    this.atualizar();
                    this.limpiar();
                    fecharDialogo = true;
                } catch (Exception e2) {

                }
                break;
        }
        context.addCallbackParam("fecharDialogo", fecharDialogo);
    }

    public void limpiar() {
        this.estado.setId(0);
        this.estado.setSiglaEstado("");
        this.estado.setNomeEstado("");
    }

    public void obterEstado(Estado xEstado) {
        this.accion = "Modificar";
        this.estado = xEstado;
    }
    /*  
     public void carregarLog() {
     eDaoLog = new LogEventoDAO();
     try {
     logEvento.setNomeUsuario(usuarioBean.verificarUsuarioLogado().getNome().trim());
     } catch (IOException ex) {
     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao ao buscar o nome do estado para o log", "Erro: " + ex.getMessage()));
     }
     logEvento.setDescricao(logEvento.getNomeUsuario() + " inseriu um estado no sistema");
     logEvento.setData(new Date());
     logEvento.setHora(new Date());
        
     }*/

    public void buscaPaisBySigla() {
        System.out.println("entrou no busca pais por sigla do estadobean");
        PaisDao xdaoPais;
        Pais temp;
        String sigla = this.estado.getPais().getSiglaPais();
        System.out.println(sigla);
        try {
            xdaoPais = new PaisDao();
            temp = xdaoPais.buscaPorSigla(sigla);
            if (temp != null) {
                this.estado.setPais(temp);
                System.out.println("achou o pais " + this.estado.getPais().getNomePais());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ACHOU O PAIS", "Erro: "));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao PROCURAR PAIS", "Erro: "));
                System.out.println(" nao achou o pais ");
            }
        } catch (Exception e) {
            //throw e;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO NO TRY BUSCA PAIS", "Erro: "));
        }

    }

    public void setarPaisSelecionado(SelectEvent event) {
        Pais paisNovo = (Pais) event.getObject();
        paisNovo.getClass().getName(); //esta linha maldida me fez patiar 2 dias
        estado.setPais(paisNovo);
        setPais(paisNovo);
    }

    public void listavalor() {
        System.out.println(" valor do id do pais: " + estado.getPais().getId());
        System.out.println(" valor do id nome pais:" + estado.getPais().getNomePais());
    }

    public void selecionar(Estado estado) {
        RequestContext.getCurrentInstance().closeDialog(estado);
    }

    public void abrirDialogoEstado() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 470);
        opcoes.put("contentWidth", 1000);
        //opcoes.put("dynamic", true);
        RequestContext.getCurrentInstance().openDialog("consultaEstados", opcoes, null);
        System.out.println("passou no abrir dialogo estados");
    }

    public void abrirDialogoEstado2() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 470);
        opcoes.put("contentWidth", 1000);
        //opcoes.put("dynamic", true);
        RequestContext.getCurrentInstance().openDialog("consultaEstados2", opcoes, null);
        System.out.println("passou no abrir dialogo estados");
    }

}
