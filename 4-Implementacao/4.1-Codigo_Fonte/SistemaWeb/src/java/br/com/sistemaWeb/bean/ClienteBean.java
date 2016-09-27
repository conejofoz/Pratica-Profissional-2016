    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemaWeb.bean;

import br.com.sistemaWeb.classes.CepWebService;
import br.com.sistemaWeb.classes.Cidade;
import br.com.sistemaWeb.classes.Cliente;
import br.com.sistemaWeb.dao.CidadeDao;
import br.com.sistemaWeb.dao.ClienteDao;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIForm;
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
public class ClienteBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Cliente cliente = new Cliente();
    private List<Cliente> listaCliente;
    private ClienteDao eDao;
//    private LogEventoDAO eDaoLog;
    private String accion;
    private Cliente clienteSelecionado;
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
    private String enterSite = "edtDocumento";
    
    private String edtConsultaNome;
    private String consultaCpf;
    private String consultaCnpj;
    
  //  private LogEvento logEvento = new LogEvento();

    //paga o log
    // private UsuarioBean usuarioBean = new UsuarioBean();

    public String getEdtConsultaNome() {
        return edtConsultaNome;
    }

    public void setEdtConsultaNome(String edtConsultaNome) {
        this.edtConsultaNome = edtConsultaNome;
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
    
    
    
    public Cliente getClienteSelecionado() {
        return clienteSelecionado;
    }

    public void setClienteSelecionado(Cliente clienteSelecionado) {
        this.clienteSelecionado = clienteSelecionado;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Cliente> getListaCliente() {
        return listaCliente;
    }

    public void setListaCliente(List<Cliente> listaCliente) {
        this.listaCliente = listaCliente;
    }

    public ClienteDao geteDao() {
        return eDao;
    }

    public void seteDao(ClienteDao eDao) {
        this.eDao = eDao;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public void apagar() {
        eDao = new ClienteDao();
        try {
            eDao.apagar(clienteSelecionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Eliminado com sucesso"));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Erro ao eliminar"));
        }
    }

    public boolean salvar() throws Exception{
    boolean retorno = false;    
    System.out.println("entrou no salbarBean");
    System.out.println("valor do campo data: "+cliente.getDataNascimento());
        if (cliente != null) {
            eDao = new ClienteDao();
            try {
                try {
                    if (!eDao.buscaPorNome(cliente)) {
                        //carregarLog();
                        eDao.salvar(cliente);
                        //eDaoLog.salvar(logEvento);
                        System.out.println("passou no salvar");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
                        retorno = true;
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso:", "Cliente ja existe "));
                        retorno = false;
                    }
                } catch (SQLException ex1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex1.getMessage()));
                    retorno = false;
                }
            } catch (Exception ex2) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar cliente*", "Erro: " + ex2.getMessage()));
                retorno = false;
                throw ex2;
            }
            cliente = new Cliente();
        }
        return retorno;
    }
    
    
    

    public void salvarAntigo() throws Exception {
        if (cliente != null) {
            eDao = new ClienteDao();
            try {

                eDao.salvar(cliente);
                System.out.println("passou no salvar");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Salvo com sucesso "));
            } catch (SQLException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
            }
            cliente = new Cliente();
        }
    }

    public void atualizar() {
        eDao = new ClienteDao();
        try {
            eDao.atualizar(cliente);
            System.out.println("passou no atualizar");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "Alterado com sucesso "));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", "Erro: " + ex.getMessage()));
        }

    }

    public void listar() throws Exception {
        ClienteDao dao;
        try {
            dao = new ClienteDao();
            listaCliente = dao.todosClientes();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void consultar() throws Exception{
        ClienteDao dao;
        dao = new ClienteDao();
        try {
            listaCliente = dao.consultaClientes(this.edtConsultaNome);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao consultar ", "Erro: " + ex.getMessage()));
            throw ex;
        }
    }

    public void buscaPorID(Cliente per) throws Exception {
        ClienteDao dao;
        Cliente temp;
        try {
            dao = new ClienteDao();
            temp = dao.buscaPorID(per);
            if (temp != null) {
                this.cliente = temp;
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
        this.cliente.setId(0);
        this.cliente.setNomeCliente("");
        this.cliente.setTelefone("");
        this.cliente.setEmail("");
    }
    
    
    public void obterCliente(Cliente xCliente) {
        this.accion = "Modificar";
        this.cliente = xCliente;
    }
    /*  
     public void carregarLog() {
     eDaoLog = new LogEventoDAO();
     try {
     logEvento.setNomeUsuario(usuarioBean.verificarUsuarioLogado().getNome().trim());
     } catch (IOException ex) {
     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao ao buscar o nome do cliente para o log", "Erro: " + ex.getMessage()));
     }
     logEvento.setDescricao(logEvento.getNomeUsuario() + " inseriu um cliente no sistema");
     logEvento.setData(new Date());
     logEvento.setHora(new Date());
        
     }*/

    public void selecionar(Cliente var_cliente) {
        RequestContext.getCurrentInstance().closeDialog(var_cliente);
    }

    public void abrirDialogoCliente() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 470);
        opcoes.put("contentWidth", 1000);
        RequestContext.getCurrentInstance().openDialog("consultaClientes", opcoes, null);
        System.out.println("passou");
    }

    public void handleKeyEvent() {
        cliente.setNomeCliente(cliente.getNomeCliente().toUpperCase());
    }

    public void handleKeyEventEmail() {
        cliente.setEmail(cliente.getEmail().toLowerCase());
    }

    public void zerarCliente() {
        cliente = new Cliente();
    }
    
    public boolean verificaTipoPessoa(){
        boolean resultado = false;
        if (cliente.getTipoPessoa().equals('F')){
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
                this.cliente.setCidade(temp);
                // this.accion = "Modificar";
                System.out.println("codigo do cargo: " + cliente.getCidade().getId());
                System.out.println("Nome do cargo  : " + cliente.getCidade().getNomeCidade());
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
                this.cliente.getCidade().setId(tempCidade.getId());
            } else {
                this.cliente.getCidade().setId(0);
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
        cliente.setCidade(cidadeNova);
        //setCidade(cidadeNova);
    }

   //
    public boolean validacpf() {
        //System.out.println(strCpf);
        if(this.requerCpf==false){
            return true;
        }
        if (CPF(cliente.getCpf())) {
           // FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "cpf validado"));
            return true;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "cpf não confere"));
            return false;
        }
    }
    
    
    public void mostraCampoSexo(){
        if(this.cliente.getTipoPessoa().equals("F")){
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
            if(this.cliente.getTipoPessoa().equals("J")){
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
                this.enterSite = "edtCliente";
            }
        }
    }
    
    public boolean mostraCnpj(){
        boolean retorno = false;
        if(this.cliente.getTipoPessoa().equals("J")){
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
		CepWebService cepWebService = new CepWebService(cliente.getCep());

		if (cepWebService.getResultado() >= 1) {
			cliente.setTipoLogradouro(cepWebService.getTipoLogradouro());
			cliente.setEndereco(cepWebService.getLogradouro());
			cliente.getCidade().getEstado().setSiglaEstado(cepWebService.getEstado());
			cliente.getCidade().setNomeCidade(cepWebService.getCidade());
                       
			cliente.setBairro(cepWebService.getBairro());
                        //mensagemAchou();
                    try {                        
                        //procura no bd a cidade encontrada nos correios
                        buscaCidadePorNome(cliente.getCidade());
                    } catch (Exception ex) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "erro ao procurar cidade no banco "));
                    }
		} else {

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Servidor não está respondendo",
							"Servidor não está respondendo" + cepWebService.getResultado()+this.cliente.getCep()));
		}
                
                if(cliente.getCep()==null){
                    FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"o cep está null",
							""));
                    
                }
	}

    
}
