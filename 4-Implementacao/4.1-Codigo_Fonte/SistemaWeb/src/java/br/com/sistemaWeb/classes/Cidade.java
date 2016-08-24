package br.com.sistemaWeb.classes;

/**
 *
 * @author conejo
 */
public class Cidade {
    private int id;
    private String nomeCidade;
    private Estado estado = new Estado();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeCidade() {
        return nomeCidade;
    }

    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
    
    
}
