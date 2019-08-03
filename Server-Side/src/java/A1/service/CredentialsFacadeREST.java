/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A1.service;

import A1.Credentials;
import A1.Food;
import A1.Users;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author apurv
 */
@Stateless
@Path("a1.credentials")
public class CredentialsFacadeREST extends AbstractFacade<Credentials> {

    @PersistenceContext(unitName = "Assignment1PU")
    private EntityManager em;

    public CredentialsFacadeREST() {
        super(Credentials.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Credentials entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Credentials entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Credentials find(@PathParam("id") Integer id) {
        return super.find(id);
    }

     @GET
    @Path("findByUsername/{username}")
    @Produces({"application/json"})
    public Credentials findByUsername(@PathParam("username") String username) {
        Query query = em.createNamedQuery("Credentials.findByUsername");
        query.setParameter("username", username);
        return (Credentials) query.getSingleResult();
    }
    
    @GET
    @Path("authenticate/{username}/{passwordHash}")
    //@Produces({"application/json"})
    public String authenticate(@PathParam("username") String username, @PathParam("passwordHash") String passwordHash) {
        TypedQuery<Credentials> query = em.createQuery("SELECT s FROM Credentials s WHERE s.username = :username", Credentials.class);
        query.setParameter("username", username);
        Credentials credential = query.getSingleResult();
        String storedPassswordHash = credential.getPasswordhash();
        if (passwordHash.equalsIgnoreCase(storedPassswordHash))
        return "Logged In";
        else
            return "Wrong Credentials";
    }
    
    
     @GET
    @Path("getUserNameStatus/{username}")
    //@Produces({"application/json"})
    public String getUserNameStatus(@PathParam("username") String username) {
        TypedQuery<Credentials> query = em.createQuery("SELECT s FROM Credentials s WHERE s.username = :username", Credentials.class);
        query.setParameter("username", username);
        try {
        Credentials credentials = query.getSingleResult();
        return "User Name found";}
        catch (Exception e){
            return "User Name not found";
        }
    }
    
    @GET
    @Path("findUserId/{username}")
    //@Produces({"application/json"})
    public String findUserId(@PathParam("username") String username) {
        TypedQuery<Credentials> query = em.createQuery("SELECT s FROM Credentials s WHERE s.username = :username", Credentials.class);
        query.setParameter("username", username);
        Credentials credential = query.getSingleResult();
        String userId = credential.getUserid().toString();
        return userId;
    }
    
     @GET
    @Path("findBySignupdate/{signupdate}")
    @Produces({"application/json"})
    public List<Credentials> findBySignupdate(@PathParam("signupdate") String signupdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date date = sdf.parse(signupdate);
        Query query = em.createNamedQuery("Credentials.findBySignupdate");
        query.setParameter("signupdate",date);
        return query.getResultList();
    }
    
     @GET
    @Path("findByPasswordhash/{passwordhash}")
    @Produces({"application/json"})
    public List<Credentials> findByPasswordhash(@PathParam("passwordhash") String passwordhash) {
        Query query = em.createNamedQuery("Credentials.findByPasswordhash");
        query.setParameter("passwordhash", passwordhash);
        return query.getResultList();
    }
    
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Credentials> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Credentials> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
