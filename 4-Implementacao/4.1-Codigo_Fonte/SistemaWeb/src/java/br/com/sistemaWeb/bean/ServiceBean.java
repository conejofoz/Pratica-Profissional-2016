/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemaWeb.bean;


import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import br.com.sistemaWeb.classes.CepWebService;
import javax.faces.bean.ViewScoped;

/**
 * @author - Felipe
 * @since - 09/08/2012
 */
@ManagedBean(name = "mbService")
@ViewScoped
public class ServiceBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String cep = null;

	private String tipoLogradouro;
	private String logradouro;
	private String estado;
	private String cidade;
	private String bairro;
        

   
        

	public void encontraCEP() {
                System.out.println(getCep());
		CepWebService cepWebService = new CepWebService(getCep());

		if (cepWebService.getResultado() >= 1) {
			setTipoLogradouro(cepWebService.getTipoLogradouro());
			setLogradouro(cepWebService.getLogradouro());
			setEstado(cepWebService.getEstado());
			setCidade(cepWebService.getCidade());
                       
			setBairro(cepWebService.getBairro());
                        mensagemAchou();
		} else {

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Servidor não está respondendo",
							"Servidor não está respondendo" + cepWebService.getResultado()+this.getCep()));
		}
                
                if(cep==null){
                    FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"o cep está null",
							""));
                    
                }
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getTipoLogradouro() {
		return tipoLogradouro;
	}

	public void setTipoLogradouro(String tipoLogradouro) {
		this.tipoLogradouro = tipoLogradouro;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = tipoLogradouro + " " + logradouro;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
        
        
        public void mensagemAchou(){
            FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Encontrou weService",
							"Resultado para o cep: " + this.getCep()));
        }
}