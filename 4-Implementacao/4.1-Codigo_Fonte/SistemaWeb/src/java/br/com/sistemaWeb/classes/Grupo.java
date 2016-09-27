/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemaWeb.classes;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;


/**
 *
 * @author conejo
 */
@Entity
//@Table(name = "grupos", uniqueConstraints = @UniqueConstraint(columnNames = {"id","nomegrupo"}))
@Table(name = "grupos2")
public class Grupo implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GenericGenerator(name = "grupo_id", strategy = "increment")
    @GeneratedValue(generator = "grupo_id")
    @Column(name = "id")
    private int id;
    
    
    @Column(name = "nomegrupo",unique = true)
    @NotBlank(message = "Campo nome é obrigatório")
    @Size(min = 3, max = 100, message = "Deve ter no mínimo 3 e no máximo 100 caracteres")
    //@Email(regexp=".+@.+\\..+")
    //@CPF
    private String nomeGrupo = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    public String getNomeGrupo() {
        return nomeGrupo;
    }

    public void setNomeGrupo(String nomeGrupo) {
        this.nomeGrupo = nomeGrupo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Grupo other = (Grupo) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Grupo{" + "id=" + id + ", nomeGrupo=" + nomeGrupo + '}';
    }

    
}
