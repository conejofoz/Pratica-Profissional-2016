package br.com.sistemaWeb.dao;

import br.com.sistemaWeb.classes.FabricaConexao;
import br.com.sistemaWeb.classes.Grupo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

/**
 *
 * @author Silvio Coelho
 */
public class GrupoDao {
    public EntityManager getEM(){
        //EntityManagerFactory factory = Persistence.createEntityManagerFactory("SistemaWebPU");
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("firebirdPU");
        return factory.createEntityManager();
    }
    
    

    PreparedStatement pstm;
    ResultSet rs;
    private Connection conexao;

    public void salvar(Grupo grupo) throws Exception {
        try {
            String sql = "INSERT INTO grupos(nomegrupo) VALUES(?) ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, grupo.getNomeGrupo());

            pstm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
    }
    
    public void salvarH(Grupo grupo) throws Exception{
        EntityManager em = getEM();
        try {
            em.getTransaction().begin();
            em.merge(grupo);
            em.getTransaction().commit();
         } catch(PersistenceException es){
             System.out.println("já existe");
            //em.getTransaction().rollback();   
            throw new Exception("Registro duplicado " + grupo.getNomeGrupo() + "Já existe");
        } catch(Exception eH) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }



    public void atualizar(Grupo grupo) throws SQLException {
        String sql = "UPDATE grupos SET nomegrupo=? WHERE id =?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setString(1, grupo.getNomeGrupo());

        pstm.setInt(2, grupo.getId());
        pstm.executeUpdate();
    }
    
    public void atualizarH(Grupo grupo) throws SQLException{
        EntityManager em = getEM();
        try {
            em.getTransaction().begin();
            em.merge(grupo);
            em.getTransaction().commit();
        } catch(Exception eH){
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public void apagarJDBC(Grupo grupo) throws SQLException {
        String sql = "DELETE FROM grupos WHERE id = ?";
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        pstm.setInt(1, grupo.getId());
        pstm.executeUpdate();
    }    
    
    public void apagar(Grupo grupo) throws Exception{
        EntityManager em = getEM();
        try {
            em.getTransaction().begin();
            grupo = em.find(Grupo.class, grupo.getId());
            em.remove(grupo);
            em.getTransaction().commit();
        } catch( Exception e){
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    @Transactional
    public void apagar1111(Grupo grupo) throws Exception{
        EntityManager em = getEM();
        Grupo grupoAux = em.find(Grupo.class, grupo.getId());
        System.out.println("grupo para apagar" + grupo.toString());
        em.remove(grupoAux);
        em.flush();
    }
    
    public List<Grupo> todosGrupos() throws SQLException {
        EntityManager em = getEM();
        return em.createQuery("from Grupo").getResultList();
    }
    
    public List<Grupo> todosGruposJDBC() throws SQLException {
        List<Grupo> listaGrupo = new ArrayList<Grupo>();

        String sql = "SELECT id, nomegrupo FROM grupos order by nomeGrupo";
        try{
            
        
            
        conexao = FabricaConexao.conectar();
        pstm = conexao.prepareStatement(sql);
        rs = pstm.executeQuery();
        }catch(SQLException ex){
           throw ex; 
        }
        while (rs.next()) {
            Grupo tempGrupo = new Grupo();
            tempGrupo.setId(rs.getInt("id"));
            tempGrupo.setNomeGrupo(rs.getString("nomeGrupo"));

            listaGrupo.add(tempGrupo);
        }
        return listaGrupo;
    }

    public Grupo buscaPorID(Grupo grupo){
        EntityManager em = getEM();
        Grupo grupoAux = em.find(Grupo.class, grupo.getId());
        return grupoAux;
    }
    
    public Grupo buscaPorIDJDBC(Grupo per) throws Exception {
        Grupo tempGrupo = null;
        //ResultSet rs;
        try {
            String sql = "select *  from grupos where id=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, per.getId());
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempGrupo = new Grupo();
                tempGrupo.setId(rs.getInt("id"));
                tempGrupo.setNomeGrupo(rs.getString("nomeGrupo"));

            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        return tempGrupo;
    }

    public boolean buscaPorNome(Grupo grupo) throws Exception{
        EntityManager em = getEM();
        String jpql = "select g.nomeGrupo from Grupo g where g.nomeGrupo = :nomeGrupo";
        try {
            String nomeAux = em.createQuery(jpql, String.class).setParameter("nomeGrupo", grupo.getNomeGrupo().toUpperCase()).getSingleResult();
            return true;
        } catch (NoResultException nre){
            return false;
        }
    }
    
    public boolean buscaPorNomeJDBC(Grupo grupoParam) throws Exception {
        boolean achou = false;
        //ResultSet rs;
        try {
            String sql = "select id, nomeGrupo from grupos where nomeGrupo=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, grupoParam.getNomeGrupo().trim());
            rs = pstm.executeQuery();
            while (rs.next()) {
                achou = true;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        return achou;
    }

    public Grupo buscaPorCodigo(int id) throws Exception {
        System.out.println("entrou no buscaESTAD por sigla");
        Grupo tempGrupo = null;

        try {
            String sql = "select *  from grupos where id=? ";
            conexao = FabricaConexao.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();

            while (rs.next()) {
                tempGrupo = new Grupo();
                tempGrupo.setId(rs.getInt("id"));
                tempGrupo.setNomeGrupo(rs.getString("nomeGrupo"));

            }
        } catch (Exception e) {
            throw e;
        } finally {
            conexao.close();
        }
        if (tempGrupo == null) {
            System.out.println("objeto estado null");
        } else {
            System.out.println("objeto pais :" + tempGrupo.getNomeGrupo());
        }
        return tempGrupo;
    }

}
