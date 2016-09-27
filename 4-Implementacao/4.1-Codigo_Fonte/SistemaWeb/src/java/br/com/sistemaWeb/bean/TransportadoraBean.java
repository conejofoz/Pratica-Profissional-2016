/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemaWeb.bean;

import br.com.sistemaWeb.classes.CepWebService;
import br.com.sistemaWeb.classes.Cidade;
import br.com.sistemaWeb.classes.Transportadora;
import br.com.sistemaWeb.dao.CidadeDao;
import br.com.sistemaWeb.dao.TransportadoraDao;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
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
@SessionScoped
public class TransportadoraBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Transportadora transportadora = new Transportadora();
    private List<Transportadora> listaTransportadora;
    private TransportadoraDao eDao;
//    private LogEventoDAO eDaoLog;
    private String accion;
    private Transportadora transportadoraSelecionado;
    private boolean exibeCampoSexo;
    private boolean exibeCampoCpf;
    private boolean exibeCampoIdentidade;
    private boolean exibeCampoNascimento;
    private boolean exibeCampoCnpj;
    private boolean requererTelefone = false;
    private boolean requerSexo = false;
    private boolean requerCpf = false;
    private boolean requerEmail = false;
    private String nomeCampoNome = "Nome:";
    private String enterSite = "edtCnpj";
    
    private String consultaNome;
    private String consultaCpf;
    private String consultaCnpj;
    
  //  private LogEvento logEvento = new LogEvento();

    //paga o log
    // private UsuarioBean usuarioBean = new UsuarioBean();

    public String getConsultaNome() {
        return consultaNome;
    }

    public void setConsultaNome(String consultaNome) {
        this.consultaNome = consultaNome;
    }

    public String getConsultaCpf() {
        return consultaCpf;
    }

    public void setConsultaCpf(String consultaCpf) {
        this.consultaCpf = consultaCpf;
    }

    public String getConsultaCnpj() {
        return consultaCnpj;
    }

    public void setConsultaCnpj(String consultaCnpj) {
        this.consultaCnpj = consultaCnpj;
    }

  
    

    
    
    public String getEnterSite() {
        return enterSite;
    }

    public void setEnterSite(String enterSite) {
        this.enterSite = enterSite;
    }
    
    

    public boolean isRequerEmail() {
        return requerEmail;
    }

    public void setRequerEmail(boolean requerEmail) {
        this.requerEmail = requerEmail;
    }

    
    
    
    public boolean isRequerSexo() {
        return requerSexo;
    }

    public void setRequerSexo(boolean requerSexo) {
        this.requerSexo = requerSexo;
    }

    public boolean isRequerCpf() {
        return requerCpf;
    }

    public void setRequerCpf(boolean requerCpf) {
        this.requerCpf = requerCpf;
    }
    
    

    public boolean isRequererTelefone() {
        return requererTelefone;
    }

    public void setRequererTelefone(boolean requererTelefone) {
        this.requererTelefone = requererTelefone;
    }
    
    

    public boolean isExibeCampoCnpj() {
        return exibeCampoCnpj;
    }

    public void setExibeCampoCnpj(boolean exibeCampoCnpj) {
        this.exibeCampoCnpj = exibeCampoCnpj;
    }
    
    

    public String getNomeCampoNome() {
        return nomeCampoNome;
    }

    public void setNomeCampoNome(String nomeCampoNome) {
        this.nomeCampoNome = nomeCampoNome;
    }
    
    

    public boolean isExibeCampoNascimento() {
        return exibeCampoNascimento;
    }

    public void setExibeCampoNascimento(boolean exibeCampoNascimento) {
        this.exibeCampoNascimento = exibeCampoNascimento;
    }
    
    

    public boolean isExibeCampoIdentidade() {
        return exibeCampoIdentidade;
    }

    public void setExibeCampoIdentidade(boolean exibeCampoIdentidade) {
        this.exibeCampoIdentidade = exibeCampoIdentidade;
    }

   
    
    

    public boolean isExibeCampoSexo() {
        return exibeCampoSexo;
    }

    public void setExibeCampoSexo(boolean exibeCampoSexo) {
        this.exibeCampoSexo = exibeCampoSexo;
    }

    public boolean isExibeCampoCpf() {
        return exibeCampoCpf;
    }

    public void setExibeCampoCpf(boolean exibeCampoCpf) {
        this.exibeCampoCpf = exibeCampoCpf;
    }
    
    
    
    public Transportadora getTransportadoraSelecionado() {
        return transportadoraSelecionado;
    }

    public void setTransportadoraSelecionado(Transportadora transportadoraSelecionado) {
        this.transportadoraSelecionado = transportadoraSelecionado;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Transportadora getTransportadora() {
        return transportadora;
    }

    public void setTransportadora(Transportadora transportadora) {
        this.transportadora = transportadora;
    }

    public List<Transportadora> getListaTransportadora() {
        return listaTransportadora;
    }

    public void setListaTransportadora(List<Transportadora> listaTransportadora) {
        this.listaTransportadora = listaTransportadora;
    }

    public TransportadoraDao geteDao() {
        return eDao;
    }

    public void seteDao(TransportadoraDao eDao) {
        this.eDao = eDao;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public void apagar() {
        eDao = new TransportadoraDao();
        try {
            eDao.apagar(transportadoraSelecionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Eliminado com sucesso"));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Erro ao eliminar"));
        }
    }

    public boolean salvar() throws Exception{
    boolean retorno = false;    
    System.out.println("entrou no salbarBean");
    System.out.println("valor do campo data: "+transportadora.getDataNascimento());
        
        
        if (transportadora != null) {
            eDao = new TransportadoraDao();

            try {
                try {
                    if (!eDao.buscaPorNome(transportadora)) {
                        //carregarLog();
                        eDao.salvar(transportadora);
                        //eDaoLog.salvar(logEvento);
                        System.out.println("passou no salvar");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
                        retorno = true;
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Transportadora ja existe "));
                        retorno = false;
                    }
                } catch (SQLException ex1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex1.getMessage()));
                    retorno = false;
                }
            } catch (Exception ex2) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar transportadora*", "Erro: " + ex2.getMessage()));
                retorno = false;
                throw ex2;
            }
            transportadora = new Transportadora();
        }
        return retorno;
    }

    public void salvarAntigo() throws Exception {
        if (transportadora != null) {
            eDao = new TransportadoraDao();
            try {

                eDao.salvar(transportadora);
                System.out.println("passou no salvar");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
            } catch (SQLException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
            }
            transportadora = new Transportadora();
        }
    }

    public void atualizar() {
        eDao = new TransportadoraDao();
        try {
            eDao.atualizar(transportadora);
            System.out.println("passou no atualizar");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Alterado com sucesso "));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
        }

    }

    public void listar() throws Exception {
        TransportadoraDao dao;
        try {
            dao = new TransportadoraDao();
            listaTransportadora = dao.todosTransportadoraes();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void consultar() throws Exception{
        TransportadoraDao dao;
        dao = new TransportadoraDao();
        try {
            listaTransportadora = dao.consultaTransportadoraes(this.consultaNome);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao consultar ", "Erro: " + ex.getMessage()));
            throw ex;
        }
    }

    public void buscaPorID(Transportadora per) throws Exception {
        TransportadoraDao dao;
        Transportadora temp;
        try {
            dao = new TransportadoraDao();
            temp = dao.buscaPorID(per);
            if (temp != null) {
                this.transportadora = temp;
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
        
        if(!validacpf()){
            return;
        }
        
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
        this.transportadora.setId(0);
        this.transportadora.setNomeTransportadora("");
        this.transportadora.setTelefone("");
        this.transportadora.setEmail("");
    }

    public void obterTransportadora(Transportadora xTransportadora) {
        this.accion = "Modificar";
        this.transportadora = xTransportadora;
    }
    /*  
     public void carregarLog() {
     eDaoLog = new LogEventoDAO();
     try {
     logEvento.setNomeUsuario(usuarioBean.verificarUsuarioLogado().getNome().trim());
     } catch (IOException ex) {
     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao ao buscar o nome do transportadora para o log", "Erro: " + ex.getMessage()));
     }
     logEvento.setDescricao(logEvento.getNomeUsuario() + " inseriu um transportadora no sistema");
     logEvento.setData(new Date());
     logEvento.setHora(new Date());
        
     }*/

    public void selecionar(Transportadora var_transportadora) {
        if (var_transportadora!=null){
            System.out.println("transportadora chegou no selecionar");
        } else {
            System.out.println("transportadora não chegou no selecionar");
        }
        
        try{
        RequestContext.getCurrentInstance().closeDialog(var_transportadora);
        } catch(Exception e){
            System.out.println("erro ao selecionar transportadora");
        }
    }

    public void abrirDialogoTransportadora() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", false);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 470);
        opcoes.put("contentWidth", 1000);
        opcoes.put("widgetVar", "wconsultaTransportadoras");
        RequestContext.getCurrentInstance().openDialog("consultaTransportadoras", opcoes, null);
        System.out.println("passou no dialogo do transportadora");
    }
    
    public void abrirDialogoTransportadora2() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 470);
        opcoes.put("contentWidth", 1000);
        //opcoes.put("widgetVar", "wdialogoUUU");
        RequestContext.getCurrentInstance().openDialog("consultaTransportadoras2", opcoes, null);
        System.out.println("passou no dialogo do transportadora2");
    }
    
    public void abrirDialogoTransportadora3() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 470);
        opcoes.put("contentWidth", 1000);
        //opcoes.put("widgetVar", "wdialogoUUU");
        RequestContext.getCurrentInstance().openDialog("wdialogoCadastroTransportadoraesxxx", opcoes, null);
        System.out.println("passou no dialogo do transportadora2");
    }

    public void handleKeyEvent() {
        transportadora.setNomeTransportadora(transportadora.getNomeTransportadora().toUpperCase());
    }

    public void handleKeyEventEmail() {
        transportadora.setEmail(transportadora.getEmail().toLowerCase());
    }

    public void zerarTransportadora() {
        transportadora = new Transportadora();
    }
    
    public boolean verificaTipoPessoa(){
        boolean resultado = false;
        if (transportadora.getTipoPessoa().equals('F')){
            resultado = true;
        }
        return resultado;
    }

    public Cidade buscaPorID2(Cidade per) throws Exception {
        CidadeDao dao;
        Cidade temp;
        try {
            dao = new CidadeDao();
            temp = dao.buscaPorID(per);
            if (temp != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Cidade encontrada "));
                this.transportadora.setCidade(temp);
                // this.accion = "Modificar";
                System.out.println("codigo do cargo: " + transportadora.getCidade().getId());
                System.out.println("Nome do cargo  : " + transportadora.getCidade().getNomeCidade());
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Cidade n'ao existe "));
            }
        } catch (Exception e) {
            throw e;
        }
        return temp;
    }
    
    public Cidade buscaCidadePorNome(Cidade pCidade) throws Exception{
        CidadeDao cidadeDao;
        Cidade tempCidade;
        
        try{
            cidadeDao = new CidadeDao();
            tempCidade = cidadeDao.buscaPorNome2(pCidade);
            if(tempCidade !=null){
                this.transportadora.getCidade().setId(tempCidade.getId());
            } else {
                this.transportadora.getCidade().setId(0);
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Cidade não cadastrada no sistema!")); 
            }
        } catch (Exception e){
            throw e;
        }
        return tempCidade;
    }
    

    public void setarCidadeSelecionada(SelectEvent event) {
        Cidade cidadeNova = (Cidade) event.getObject();
        cidadeNova.getClass().getName(); //esta linha maldida me fez patiar 2 dias
        transportadora.setCidade(cidadeNova);
        //setCidade(cidadeNova);
    }

   //
    public boolean validacpf() {
        //System.out.println(strCpf);
        if(this.requerCpf==false){
            return true;
        }
        if (CPF(transportadora.getCpf())) {
           // FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "cpf validado"));
            return true;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "cpf não confere"));
            return false;
        }
    }
    
    
    public void mostraCampoSexo(){
        if(this.transportadora.getTipoPessoa().equals("F")){
            this.exibeCampoSexo = true;
            this.exibeCampoCpf = true;
            this.exibeCampoIdentidade = true;
            this.exibeCampoNascimento = true;
            this.exibeCampoCnpj = false;
            this.nomeCampoNome = "Nome:";
            this.requerSexo = true;
            this.requerCpf = true;
            this.enterSite = "edtDocumento";
        } else {
            if(this.transportadora.getTipoPessoa().equals("J")){
            this.exibeCampoSexo = false;
            this.exibeCampoCpf = false;
            this.exibeCampoIdentidade = false;
            this.exibeCampoNascimento = false;
            this.exibeCampoCnpj = true;
            this.nomeCampoNome = "Razão Social:";
            this.requerSexo = false;
            this.requerCpf = false;
            this.enterSite = "edtCnpj";
            } else {
                this.enterSite = "edtTransportadora";
            }
        }
    }
    
    public boolean mostraCnpj(){
        boolean retorno = false;
        if(this.transportadora.getTipoPessoa().equals("J")){
            retorno = true;
        }
        return retorno;
    }

    public boolean CPF(String strCpf) {
        if (strCpf==null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Impossível validar! cpf está nulo."));
            return false;
        }
        if (strCpf.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Preencha o cpf*"));
            return false;
        }
        int d1, d2;
        int digito1, digito2, resto;
        int digitoCPF;
        String nDigResult;

        d1 = d2 = 0;
        digito1 = digito2 = resto = 0;

        for (int nCount = 1; nCount < strCpf.length() - 1; nCount++) {
            digitoCPF = Integer.valueOf(strCpf.substring(nCount - 1, nCount)).intValue();

            //multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4 e assim por diante.    
            d1 = d1 + (11 - nCount) * digitoCPF;

            //para o segundo digito repita o procedimento incluindo o primeiro digito calculado no passo anterior.    
            d2 = d2 + (12 - nCount) * digitoCPF;
        }

        //Primeiro resto da divisão por 11.    
        resto = (d1 % 11);

        //Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.    
        if (resto < 2) {
            digito1 = 0;
        } else {
            digito1 = 11 - resto;
        }

        d2 += 2 * digito1;

        //Segundo resto da divisão por 11.    
        resto = (d2 % 11);

        //Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.    
        if (resto < 2) {
            digito2 = 0;
        } else {
            digito2 = 11 - resto;
        }

        //Digito verificador do CPF que está sendo validado.    
        String nDigVerific = strCpf.substring(strCpf.length() - 2, strCpf.length());

        //Concatenando o primeiro resto com o segundo.    
        nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

        //comparar o digito verificador do cpf com o primeiro resto + o segundo resto.    
        return nDigVerific.equals(nDigResult);
    }

   //
    
    	public void encontraCEP() {
		CepWebService cepWebService = new CepWebService(transportadora.getCep());

		if (cepWebService.getResultado() >= 1) {
			transportadora.setTipoLogradouro(cepWebService.getTipoLogradouro());
			transportadora.setEndereco(cepWebService.getLogradouro());
			transportadora.getCidade().getEstado().setSiglaEstado(cepWebService.getEstado());
			transportadora.getCidade().setNomeCidade(cepWebService.getCidade());
                       
			transportadora.setBairro(cepWebService.getBairro());
                        //mensagemAchou();
                    try {                        
                        //procura no bd a cidade encontrada nos correios
                        buscaCidadePorNome(transportadora.getCidade());
                    } catch (Exception ex) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "erro ao procurar cidade no banco "));
                    }
		} else {

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Servidor não está respondendo",
							"Servidor não está respondendo" + cepWebService.getResultado()+this.transportadora.getCep()));
		}
                
                if(transportadora.getCep()==null){
                    FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"o cep está null",
							""));
                    
                }
	}

    
}
