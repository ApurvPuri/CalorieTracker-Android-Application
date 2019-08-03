/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A1.service;

import A1.Users;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
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
import jdk.nashorn.internal.parser.JSONParser;

/**
 *
 * @author apurv
 */
@Stateless
@Path("a1.users")
public class UsersFacadeREST extends AbstractFacade<Users> {

    @PersistenceContext(unitName = "Assignment1PU")
    private EntityManager em;

    public UsersFacadeREST() {
        super(Users.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Users entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Users entity) {
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
    public Users find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Path("findByNameandDoB/{name}/{dateofbirth}")
    @Produces({"application/json"})
    public List<Users> findByNameandDoB(@PathParam("name") String name, @PathParam("dateofbirth") String dateofbirth) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(dateofbirth);
        TypedQuery<Users> query = em.createQuery("SELECT s FROM Users s WHERE s.name = :name AND s.dateofbirth = :dateofbirth", Users.class);
        query.setParameter("name", name);
        query.setParameter("dateofbirth", date);
        return query.getResultList();
    }
    
    @GET
    @Path("findFirstName/{userId}")
    //@Produces({"application/json"})
    public String findFirstName(@PathParam("userId") String userId) {
        int iD = Integer.parseInt(userId);
        TypedQuery<Users> query = em.createQuery("SELECT s FROM Users s WHERE s.userid = :userid", Users.class);
        query.setParameter("userid", iD);
        Users user = query.getSingleResult();
        String firstName = user.getName();
        return firstName;
    }
    
    @GET
    @Path("findAddress/{userId}")
    //@Produces({"application/json"})
    public String findAddress(@PathParam("userId") String userId) {
        int iD = Integer.parseInt(userId);
        TypedQuery<Users> query = em.createQuery("SELECT s FROM Users s WHERE s.userid = :userid", Users.class);
        query.setParameter("userid", iD);
        Users user = query.getSingleResult();
        String address = user.getAddress();
        String postCode = user.getPostcode().toString();
        return (address + " " + postCode);
    }

    @GET
    @Path("findCalBurnedPerStep/{userid}")
   // @Produces({"application/json"})
    public String findCalBurnedPerStep(@PathParam("userid") Integer userid) {
        Users user1 = (Users) em.createNamedQuery("Users.findByUserid").setParameter("userid", userid).getSingleResult();
        double weightInPounds = user1.getWeight() * 2.20462;
        double stepsPerMile = user1.getStepspermile();
        double calBurnedPerMile = weightInPounds * 0.49;
        double calBurnedPerStep = calBurnedPerMile / stepsPerMile;
        String sCalBurned = Double.toString(calBurnedPerStep);
        return sCalBurned;        
    }
    
//     @GET
//    @Path("findCalBurnedPerStep/{userid}")
//    @Produces({"application/json"})
//    public Object findCalBurnedPerStep(@PathParam("userid") Integer userid) {
//        Users user1 = (Users) em.createNamedQuery("Users.findByUserid").setParameter("userid", userid).getSingleResult();
//        double weightInPounds = user1.getWeight() * 2.20462;
//        double stepsPerMile = user1.getStepspermile();
//        double calBurnedPerMile = weightInPounds * 0.49;
//        double calBurnedPerStep = calBurnedPerMile / stepsPerMile;
//        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
//        JsonObject reportObject = Json.createObjectBuilder().
//                add("Total calories burned per step", calBurnedPerStep).build();
//        arrayBuilder.add(reportObject);
//        JsonArray jArray = arrayBuilder.build();
//        return jArray;        
//    }
    
     @GET
    @Path("getEmailStatus/{email}")
    //@Produces({"application/json"})
    public String getEmailStatus(@PathParam("email") String email) {
        TypedQuery<Users> query = em.createQuery("SELECT s FROM Users s WHERE s.email = :email", Users.class);
        query.setParameter("email", email);
        try {
        Users users = query.getSingleResult();
        return "Email found";}
        catch (Exception e){
            return "Email not found";
        }
        
    }
    

    @GET
    @Path("findBMR/{userid}")
    //@Produces({"application/json"})
    public double findBMR(@PathParam("userid") Integer userid) {
        Users user1 = (Users) em.createNamedQuery("Users.findByUserid").setParameter("userid", userid).getSingleResult();
        int weightInKgs = user1.getWeight();
        int heightInCms = user1.getHeight();
        Date DoB = user1.getDateofbirth();
        Calendar today = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();
        birthDate.setTime(DoB);
        int age;
        if (today.get(Calendar.MONTH) < birthDate.get(Calendar.MONTH)) {
            age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR) - 1;
        } else {
            age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
        }
        String gender = user1.getGender();
        double BMR;
        if (gender.equals("Male")) {
            BMR = (13.75 * weightInKgs) + (5.003 * heightInCms) - (6.755 * age) + 66.5;
        } else {
            BMR = (9.563 * weightInKgs) + (1.85 * heightInCms) - (4.676 * age) + 655.1;
        }
      
       return BMR;
    }
    
    
    @GET
    @Path("findCalBurnedAtRest/{userid}")
    //@Produces({"application/json"})
    public int findCalBurnedAtRest(@PathParam("userid") Integer userid) {
        Users user1 = (Users) em.createNamedQuery("Users.findByUserid").setParameter("userid", userid).getSingleResult();
        double BMR = findBMR(userid);
       
        int activityLevel = user1.getLevelofactivity();
        double calBurned = 0;
        switch (activityLevel) {
            case 1:
                calBurned = BMR * 1.2;
                break;
            case 2:
                calBurned = BMR * 1.375;
                break;
            case 3:
                calBurned = BMR * 1.55;
                break;
            case 4:
                calBurned = BMR * 1.725;
                break;
            case 5:
                calBurned = BMR * 1.9;
                break;
            default:
                break;
        }
        
//        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
//        JsonObject reportObject = Json.createObjectBuilder().
//                add("Total calories burned At rest", calBurned).build();
//        arrayBuilder.add(reportObject);
//        JsonArray jArray = arrayBuilder.build();


        return (int) Math.round(calBurned);  
    }
    
    @GET
    @Path("findCalBurnedAtRestString/{userid}")
    //@Produces({"application/json"})
    public String findCalBurnedAtRestString(@PathParam("userid") Integer userid) {
        Users user1 = (Users) em.createNamedQuery("Users.findByUserid").setParameter("userid", userid).getSingleResult();
        double BMR = findBMR(userid);
       
        int activityLevel = user1.getLevelofactivity();
        double calBurned = 0;
        switch (activityLevel) {
            case 1:
                calBurned = BMR * 1.2;
                break;
            case 2:
                calBurned = BMR * 1.375;
                break;
            case 3:
                calBurned = BMR * 1.55;
                break;
            case 4:
                calBurned = BMR * 1.725;
                break;
            case 5:
                calBurned = BMR * 1.9;
                break;
            default:
                break;
        }
        
//        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
//        JsonObject reportObject = Json.createObjectBuilder().
//                add("Total calories burned At rest", calBurned).build();
//        arrayBuilder.add(reportObject);
//        JsonArray jArray = arrayBuilder.build();


        return Integer.toString((int) Math.round(calBurned));  
    }
    
    
    

//    @GET
//    @Path("findCalBurnedAtRest/{userid}")
//    @Produces({"application/json"})
//    public Object findCalBurnedAtRest(@PathParam("userid") Integer userid) {
//        Users user1 = (Users) em.createNamedQuery("Users.findByUserid").setParameter("userid", userid).getSingleResult();
//        double BMR = findBMR(userid);
//       
//        int activityLevel = user1.getLevelofactivity();
//        double calBurned = 0;
//        switch (activityLevel) {
//            case 1:
//                calBurned = BMR * 1.2;
//                break;
//            case 2:
//                calBurned = BMR * 1.375;
//                break;
//            case 3:
//                calBurned = BMR * 1.55;
//                break;
//            case 4:
//                calBurned = BMR * 1.725;
//                break;
//            case 5:
//                calBurned = BMR * 1.9;
//                break;
//            default:
//                break;
//        }
//        
//        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
//        JsonObject reportObject = Json.createObjectBuilder().
//                add("Total calories burned At rest", calBurned).build();
//        arrayBuilder.add(reportObject);
//        JsonArray jArray = arrayBuilder.build();
//        return jArray;  
//    }
    
    

    @GET
    @Path("findByName/{name}")
    @Produces({"application/json"})
    public List<Users> findByName(@PathParam("name") String name) {
        Query query = em.createNamedQuery("Users.findByName");
        query.setParameter("name", name);
        return query.getResultList();
    }

    @GET
    @Path("findBySurname/{surname}")
    @Produces({"application/json"})
    public List<Users> findBySurname(@PathParam("surname") String surname) {
        Query query = em.createNamedQuery("Users.findBySurname");
        query.setParameter("surname", surname);
        return query.getResultList();
    }

    @GET
    @Path("findByEmail/{email}")
    @Produces({"application/json"})
    public List<Users> findByEmail(@PathParam("email") String email) {
        Query query = em.createNamedQuery("Users.findByEmail");
        query.setParameter("email", email);
        return query.getResultList();
    }

    @GET
    @Path("findByDateofbirth/{dateofbirth}")
    @Produces({"application/json"})
    public List<Users> findByDateofbirth(@PathParam("dateofbirth") String dateofbirth) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(dateofbirth);
        Query query;
        query = em.createNamedQuery("Users.findByDateofbirth");
        query.setParameter("dateofbirth", date);
        return query.getResultList();
    }

    @GET
    @Path("findByHeight/{height}")
    @Produces({"application/json"})
    public List<Users> findByHeight(@PathParam("height") Integer height) {
        Query query = em.createNamedQuery("Users.findByHeight");
        query.setParameter("height", height);
        return query.getResultList();
    }

    @GET
    @Path("findByWeight/{weight}")
    @Produces({"application/json"})
    public List<Users> findByWeight(@PathParam("weight") Integer weight) {
        Query query = em.createNamedQuery("Users.findByWeight");
        query.setParameter("weight", weight);
        return query.getResultList();
    }

    @GET
    @Path("findByGender/{gender}")
    @Produces({"application/json"})
    public List<Users> findByGender(@PathParam("gender") String gender) {
        Query query = em.createNamedQuery("Users.findByGender");
        query.setParameter("gender", gender);
        return query.getResultList();
    }

    @GET
    @Path("findByAddress/{address}")
    @Produces({"application/json"})
    public List<Users> findByAddress(@PathParam("address") String address) {
        Query query = em.createNamedQuery("Users.findByAddress");
        query.setParameter("address", address);
        return query.getResultList();
    }

    @GET
    @Path("findByPostcode/{postcode}")
    @Produces({"application/json"})
    public List<Users> findByPostcode(@PathParam("postcode") Integer postcode) {
        Query query = em.createNamedQuery("Users.findByPostcode");
        query.setParameter("postcode", postcode);
        return query.getResultList();
    }

    @GET
    @Path("findByLevelofactivity/{levelofactivity}")
    @Produces({"application/json"})
    public List<Users> findByLevelofactivity(@PathParam("levelofactivity") Integer levelofactivity) {
        Query query = em.createNamedQuery("Users.findByLevelofactivity");
        query.setParameter("levelofactivity", levelofactivity);
        return query.getResultList();
    }

    @GET
    @Path("findByStepspermile/{stepspermile}")
    @Produces({"application/json"})
    public List<Users> findByStepspermile(@PathParam("stepspermile") Integer stepspermile) {
        Query query = em.createNamedQuery("Users.findByStepspermile");
        query.setParameter("stepspermile", stepspermile);
        return query.getResultList();
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Users> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Users> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
