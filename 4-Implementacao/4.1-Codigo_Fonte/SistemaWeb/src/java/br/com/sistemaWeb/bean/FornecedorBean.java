/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemaWeb.bean;

import br.com.sistemaWeb.classes.CepWebService;
import br.com.sistemaWeb.classes.Cidade;
import br.com.sistemaWeb.classes.Fornecedor;
import br.com.sistemaWeb.dao.CidadeDao;
import br.com.sistemaWeb.dao.FornecedorDao;
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
import org.apache.jasper.tagplugins.jstl.core.Catch;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author geral
 */
@ManagedBean
@ViewScoped
public class FornecedorBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Fornecedor fornecedor = new Fornecedor();
    private List<Fornecedor> listaFornecedor;
    private FornecedorDao eDao;
//    private LogEventoDAO eDaoLog;
    private String accion;
    private Fornecedor fornecedorSelecionado;
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
    
    
    
    public Fornecedor getFornecedorSelecionado() {
        return fornecedorSelecionado;
    }

    public void setFornecedorSelecionado(Fornecedor fornecedorSelecionado) {
        this.fornecedorSelecionado = fornecedorSelecionado;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public List<Fornecedor> getListaFornecedor() {
        return listaFornecedor;
    }

    public void setListaFornecedor(List<Fornecedor> listaFornecedor) {
        this.listaFornecedor = listaFornecedor;
    }

    public FornecedorDao geteDao() {
        return eDao;
    }

    public void seteDao(FornecedorDao eDao) {
        this.eDao = eDao;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public void apagar() {
        eDao = new FornecedorDao();
        try {
            eDao.apagar(fornecedorSelecionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Eliminado com sucesso"));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Erro ao eliminar"));
        }
    }

    public boolean salvar() throws Exception{
    boolean retorno = false;    
    System.out.println("entrou no salbarBean");
    System.out.println("valor do campo data: "+fornecedor.getDataNascimento());
        
        
        if (fornecedor != null) {
            eDao = new FornecedorDao();

            try {
                try {
                    if (!eDao.buscaPorNome(fornecedor)) {
                        //carregarLog();
                        eDao.salvar(fornecedor);
                        //eDaoLog.salvar(logEvento);
                        System.out.println("passou no salvar");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
                        retorno = true;
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Fornecedor ja existe "));
                        retorno = false;
                    }
                } catch (SQLException ex1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex1.getMessage()));
                    retorno = false;
                }
            } catch (Exception ex2) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar fornecedor*", "Erro: " + ex2.getMessage()));
                retorno = false;
                throw ex2;
            }
            fornecedor = new Fornecedor();
        }
        return retorno;
    }

    public void salvarAntigo() throws Exception {
        if (fornecedor != null) {
            eDao = new FornecedorDao();
            try {

                eDao.salvar(fornecedor);
                System.out.println("passou no salvar");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
            } catch (SQLException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
            }
            fornecedor = new Fornecedor();
        }
    }

    public void atualizar() {
        eDao = new FornecedorDao();
        try {
            eDao.atualizar(fornecedor);
            System.out.println("passou no atualizar");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Alterado com sucesso "));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
        }

    }

    public void listar() throws Exception {
        FornecedorDao dao;
        try {
            dao = new FornecedorDao();
            listaFornecedor = dao.todosFornecedores();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void consultar() throws Exception{
        FornecedorDao dao;
        dao = new FornecedorDao();
        try {
            listaFornecedor = dao.consultaFornecedores(this.consultaNome);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao consultar ", "Erro: " + ex.getMessage()));
            throw ex;
        }
    }

    public void buscaPorID(Fornecedor per) throws Exception {
        FornecedorDao dao;
        Fornecedor temp;
        try {
            dao = new FornecedorDao();
            temp = dao.buscaPorID(per);
            if (temp != null) {
                this.fornecedor = temp;
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
                       //this.limpiar();
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
                } catch(Exception e2){
                  System.out.println("passou no erro do operar alterar"); 
                }
                break;
        }
        context.addCallbackParam("fecharDialogo", fecharDialogo);
    }

    public void limpiar() {
        this.fornecedor.setId(0);
        this.fornecedor.setNomeFornecedor("");
        this.fornecedor.setTelefone("");
        this.fornecedor.setEmail("");
    }

    public void obterFornecedor(Fornecedor xFornecedor) {
        this.accion = "Modificar";
        this.fornecedor = xFornecedor;
    }
    /*  
     public void carregarLog() {
     eDaoLog = new LogEventoDAO();
     try {
     logEvento.setNomeUsuario(usuarioBean.verificarUsuarioLogado().getNome().trim());
     } catch (IOException ex) {
     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao ao buscar o nome do fornecedor para o log", "Erro: " + ex.getMessage()));
     }
     logEvento.setDescricao(logEvento.getNomeUsuario() + " inseriu um fornecedor no sistema");
     logEvento.setData(new Date());
     logEvento.setHora(new Date());
        
     }*/

    public void selecionar(Fornecedor var_fornecedor) {
        try{
        RequestContext.getCurrentInstance().closeDialog(var_fornecedor);
        } catch(Exception e){
            System.out.println("erro ao selecionar fornecedor");
        }
    }

    public void abrirDialogoFornecedor() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", false);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 470);
        opcoes.put("contentWidth", 1000);
        opcoes.put("widgetVar", "wconsultaFornecedores");
        RequestContext.getCurrentInstance().openDialog("consultaFornecedores", opcoes, null);
        System.out.println("passou no dialogo do fornecedor");
    }
    
    public void abrirDialogoFornecedor2() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 470);
        opcoes.put("contentWidth", 1000);
        //opcoes.put("widgetVar", "wdialogoUUU");
        RequestContext.getCurrentInstance().openDialog("consultaFornecedores2", opcoes, null);
        System.out.println("passou no dialogo do fornecedor2");
    }
    
    public void abrirDialogoFornecedor3() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 470);
        opcoes.put("contentWidth", 1000);
        //opcoes.put("widgetVar", "wdialogoUUU");
        RequestContext.getCurrentInstance().openDialog("wdialogoCadastroFornecedoresxxx", opcoes, null);
        System.out.println("passou no dialogo do fornecedor2");
    }

    public void handleKeyEvent() {
        fornecedor.setNomeFornecedor(fornecedor.getNomeFornecedor().toUpperCase());
    }

    public void handleKeyEventEmail() {
        fornecedor.setEmail(fornecedor.getEmail().toLowerCase());
    }

    public void zerarFornecedor() {
        fornecedor = new Fornecedor();
    }
    
    public boolean verificaTipoPessoa(){
        boolean resultado = false;
        if (fornecedor.getTipoPessoa().equals('F')){
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
                this.fornecedor.setCidade(temp);
                // this.accion = "Modificar";
                System.out.println("codigo do cargo: " + fornecedor.getCidade().getId());
                System.out.println("Nome do cargo  : " + fornecedor.getCidade().getNomeCidade());
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
                this.fornecedor.getCidade().setId(tempCidade.getId());
            } else {
                this.fornecedor.getCidade().setId(0);
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
        fornecedor.setCidade(cidadeNova);
        //setCidade(cidadeNova);
    }

   //
    public boolean validacpf() {
        //System.out.println(strCpf);
        if(this.requerCpf==false){
            return true;
        }
        if (CPF(fornecedor.getCpf())) {
           // FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "cpf validado"));
            return true;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "cpf não confere"));
            return false;
        }
    }
    
    
    public void mostraCampoSexo(){
        if(this.fornecedor.getTipoPessoa().equals("F")){
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
            if(this.fornecedor.getTipoPessoa().equals("J")){
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
                this.enterSite = "edtFornecedor";
            }
        }
    }
    
    public boolean mostraCnpj(){
        boolean retorno = false;
        if(this.fornecedor.getTipoPessoa().equals("J")){
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
		CepWebService cepWebService = new CepWebService(fornecedor.getCep());

		if (cepWebService.getResultado() >= 1) {
			fornecedor.setTipoLogradouro(cepWebService.getTipoLogradouro());
			fornecedor.setEndereco(cepWebService.getLogradouro());
			fornecedor.getCidade().getEstado().setSiglaEstado(cepWebService.getEstado());
			fornecedor.getCidade().setNomeCidade(cepWebService.getCidade());
                       
			fornecedor.setBairro(cepWebService.getBairro());
                        //mensagemAchou();
                    try {                        
                        //procura no bd a cidade encontrada nos correios
                        buscaCidadePorNome(fornecedor.getCidade());
                    } catch (Exception ex) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "erro ao procurar cidade no banco "));
                    }
		} else {

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Servidor não está respondendo",
							"Servidor não está respondendo" + cepWebService.getResultado()+this.fornecedor.getCep()));
		}
                
                if(fornecedor.getCep()==null){
                    FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"o cep está null",
							""));
                    
                }
	}

    
}
