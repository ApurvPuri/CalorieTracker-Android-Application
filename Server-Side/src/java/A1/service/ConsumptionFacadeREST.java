/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A1.service;

import A1.Consumption;
import A1.Food;
import A1.Users;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

/**
 *
 * @author apurv
 */
@Stateless
@Path("a1.consumption")
public class ConsumptionFacadeREST extends AbstractFacade<Consumption> {

    @PersistenceContext(unitName = "Assignment1PU")
    private EntityManager em;

    public ConsumptionFacadeREST() {
        super(Consumption.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Consumption entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Consumption entity) {
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
    public Consumption find(@PathParam("id") Integer id) {
        return super.find(id);
    }

//    @GET
//    @Path("findCalconsumedOnADay/{userid}/{date}")
//    @Produces({"application/json"})
//    public Object findCalconsumedOnADay(@PathParam("userid") Integer userid, @PathParam("date") String date) throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date dateObject = sdf.parse(date);
//        TypedQuery<Consumption> q = em.createQuery("SELECT s FROM Consumption s WHERE s.userid.userid = :userid AND s.date = :date", Consumption.class);
//        q.setParameter("userid", userid);
//        q.setParameter("date", dateObject);
//        int totalCalories = 0;
//        for (int i = 0; i < q.getResultList().size(); i++) {
//            int foodid = q.getResultList().get(i).getFoodid().getFoodid();
//            Food food = (Food) em.createNamedQuery("Food.findByFoodid").setParameter("foodid", foodid).getSingleResult();
//            int calAmount = food.getCalorieamount();
//            // BigDecimal stdServingAmount = food.getServingamount();
//            int actualServings = q.getResultList().get(i).getFoodserving();
//            totalCalories += (actualServings * calAmount);
//        }
//
//        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
//        JsonObject reportObject = Json.createObjectBuilder().
//                add("Total calories consumed for the day", totalCalories).build();
//        arrayBuilder.add(reportObject);
//        JsonArray jArray = arrayBuilder.build();
//        return jArray;
//
//    }
    
    @GET
    @Path("findCalconsumedOnADay/{userid}/{date}")
    //@Produces({"application/json"})
    public String findCalconsumedOnADay(@PathParam("userid") Integer userid, @PathParam("date") String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateObject = sdf.parse(date);
        TypedQuery<Consumption> q = em.createQuery("SELECT s FROM Consumption s WHERE s.userid.userid = :userid AND s.date = :date", Consumption.class);
        q.setParameter("userid", userid);
        q.setParameter("date", dateObject);
        int totalCalories = 0;
        for (int i = 0; i < q.getResultList().size(); i++) {
            int foodid = q.getResultList().get(i).getFoodid().getFoodid();
            Food food = (Food) em.createNamedQuery("Food.findByFoodid").setParameter("foodid", foodid).getSingleResult();
            int calAmount = food.getCalorieamount();
            // BigDecimal stdServingAmount = food.getServingamount();
            int actualServings = q.getResultList().get(i).getFoodserving();
            totalCalories += (actualServings * calAmount);
        }
        
        String sTotalCal = Integer.toString(totalCalories);

        return sTotalCal;

    }

    @GET
    @Path("findByDateAndCategory/{date}/{category}")
    @Produces({"application/json"})
    public List<Consumption> findByDateAndCategory(@PathParam("date") String date, @PathParam("category") String category) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateObject = sdf.parse(date);
        TypedQuery<Consumption> q = em.createQuery("SELECT s FROM Consumption s WHERE s.date = :date AND s.foodid.category = :category", Consumption.class);
        q.setParameter("category", category);
        q.setParameter("date", dateObject);
        return q.getResultList();
    }

    @GET
    @Path("findByDateAndFat/{date}/{fat}")
    @Produces({"application/json"})
    public List<Consumption> findByDateAndFat(@PathParam("date") String date, @PathParam("fat") Integer fat) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateObject = sdf.parse(date);
        Query q = em.createNamedQuery("Consumption.findByDateAndFat");
        q.setParameter("fat", fat);
        q.setParameter("date", dateObject);
        return q.getResultList();
    }

    @GET
    @Path("findByFoodid/{foodid}")
    @Produces({"application/json"})
    public List<Consumption> findByFoodid(@PathParam("foodid") int foodid) {
        Query q = em.createNamedQuery("Consumption.findByFoodid");
        q.setParameter("foodid", foodid);
        return q.getResultList();
    }

    @GET
    @Path("findByUserid/{userid}")
    @Produces({"application/json"})
    public List<Consumption> findByUserid(@PathParam("userid") int userid) {
        Query q = em.createNamedQuery("Consumption.findByUserid");
        q.setParameter("userid", userid);
        return q.getResultList();
    }

    @GET
    @Path("findByDate/{date}")
    @Produces({"application/json"})
    public List<Consumption> findByDate(@PathParam("date") String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateObject = sdf.parse(date);
        Query query = em.createNamedQuery("Consumption.findByDate");
        query.setParameter("date", dateObject);
        return query.getResultList();
    }

    @GET
    @Path("findByFoodserving/{foodserving}")
    @Produces({"application/json"})
    public List<Consumption> findByFoodserving(@PathParam("foodserving") Integer foodserving) {
        Query query = em.createNamedQuery("Consumption.findByFoodserving");
        query.setParameter("foodserving", foodserving);
        return query.getResultList();
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Consumption> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Consumption> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
