/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A1.service;

import A1.Consumption;
import A1.Food;
import A1.Report;
import A1.Users;
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
import javax.xml.registry.infomodel.User;

/**
 *
 * @author apurv
 */
@Stateless
@Path("a1.report")
public class ReportFacadeREST extends AbstractFacade<Report> {

    @PersistenceContext(unitName = "Assignment1PU")
    private EntityManager em;

    public ReportFacadeREST() {
        super(Report.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Report entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Report entity) {
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
    public Report find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Path("findGetReport/{userid}/{date}")
    @Produces({MediaType.APPLICATION_JSON})
    public Object findGetReport(@PathParam("userid") Integer userid, @PathParam("date") String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateObject = sdf.parse(date);
        Report rep = (Report) em.createQuery("SELECT s FROM Report s WHERE s.userid.userid = :userid AND s.date = :date",
                Report.class).setParameter("userid", userid).setParameter("date", dateObject).getSingleResult();

        double calConsumed = rep.getTotcalconsumed();
        double calBurned = rep.getCalburned();
        //double calRemaining = rep.getCaloriegoal() - (calConsumed - calBurned);
        double calRemaining = rep.getCaloriegoal() - (calConsumed - calBurned);
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        JsonObject reportObject = Json.createObjectBuilder().
                add("Total calories consumed", calConsumed)
                .add("Total calories burned", calBurned)
                .add("Remaining calories", calRemaining).build();
        arrayBuilder.add(reportObject);
        JsonArray jArray = arrayBuilder.build();
        return jArray;
    }

    @GET
    @Path("findGetReportForADuration/{userid}/{startdate}/{enddate}")
    @Produces({MediaType.APPLICATION_JSON})
    public Object findGetReportForADuration(@PathParam("userid") Integer userid, @PathParam("startdate") String startdate, @PathParam("enddate") String enddate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startdateObject = sdf.parse(startdate);
        Date enddateObject = sdf.parse(enddate);
        TypedQuery<Report> q = em.createQuery("SELECT s FROM Report s WHERE s.userid.userid = :userid AND (s.date BETWEEN :startdate AND :enddate)",
                Report.class).setParameter("userid", userid).setParameter("startdate", startdateObject).setParameter("enddate", enddateObject);
        int totalCalConsumed = 0;
        int totalCalBurned = 0;
        int totalStepsTaken = 0;
        for (int i = 0; i < q.getResultList().size(); i++) {
            int calConsumed = q.getResultList().get(i).getTotcalconsumed();
            int calBurned = q.getResultList().get(i).getCalburned();
            int stepsTaken = q.getResultList().get(i).getStepstaken();
            totalCalConsumed += calConsumed;
            totalCalBurned += calBurned;
            totalStepsTaken += stepsTaken;
        }
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        JsonObject reportObject = Json.createObjectBuilder().
                add("Total calories consumed for the duration", totalCalConsumed)
                .add("Total calories burned for the duration", totalCalBurned)
                .add("Total steps taken for the duration", totalStepsTaken).build();
        arrayBuilder.add(reportObject);
        JsonArray jArray = arrayBuilder.build();
        return jArray;
    }

    @GET
    @Path("findGetReportForADurationPerDay/{userid}/{startdate}/{enddate}")
    @Produces({MediaType.APPLICATION_JSON})
    public Object findGetReportForADurationPerDay(@PathParam("userid") Integer userid, @PathParam("startdate") String startdate, @PathParam("enddate") String enddate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startdateObject = sdf.parse(startdate);
        Date enddateObject = sdf.parse(enddate);
        TypedQuery<Report> q = em.createQuery("SELECT s FROM Report s WHERE s.userid.userid = :userid AND (s.date BETWEEN :startdate AND :enddate)",
                Report.class).setParameter("userid", userid).setParameter("startdate", startdateObject).setParameter("enddate", enddateObject);

        JsonObject objectBuilder = null;// = Json.createArrayBuilder();
        JsonObject reportObject;
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (int i = 0; i < q.getResultList().size(); i++) {
            int calConsumed = q.getResultList().get(i).getTotcalconsumed();
            int calBurned = q.getResultList().get(i).getCalburned();
            int stepsTaken = q.getResultList().get(i).getStepstaken();
            Date date = q.getResultList().get(i).getDate();
 

            reportObject = Json.createObjectBuilder().add("Total calories consumed for the duration", calConsumed)
                    .add("Total calories burned for the duration", calBurned)
                    .add("Total steps taken for the duration", stepsTaken).build();
            objectBuilder  = Json.createObjectBuilder().add(sdf.format(date),reportObject).build();
            arrayBuilder.add(objectBuilder);
        }

        
        JsonArray jArray = arrayBuilder.build();
        return jArray;
    }

    @GET
    @Path("findByUserName/{username}")
    @Produces({"application/json"})
    public List<Report> findByUserName(@PathParam("username") String username) {
        Query query = em.createNamedQuery("Report.findByUserName");
        query.setParameter("username", username);
        return query.getResultList();
    }

    @GET
    @Path("findByUserid/{userid}")
    @Produces({"application/json"})
    public List<Report> findByUserid(@PathParam("userid") int userid) {
        Query query = em.createNamedQuery("Report.findByUserid");
        query.setParameter("userid", userid);
        return query.getResultList();
    }

    @GET
    @Path("findByDate/{date}")
    @Produces({"application/json"})
    public List<Report> findByDate(@PathParam("date") String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateObject = sdf.parse(date);
        Query query = em.createNamedQuery("Report.findByDate");
        query.setParameter("date", dateObject);
        return query.getResultList();
    }

    @GET
    @Path("findByTotcalconsumed/{totcalconsumed}")
    @Produces({"application/json"})
    public List<Report> findByTotcalconsumed(@PathParam("totcalconsumed") Integer totcalconsumed
    ) {
        Query query = em.createNamedQuery("Report.findByTotcalconsumed");
        query.setParameter("totcalconsumed", totcalconsumed);
        return query.getResultList();
    }

    @GET
    @Path("findByCalburned/{calburned}")
    @Produces({"application/json"})
    public List<Report> findByCalburned(@PathParam("calburned") Integer calburned
    ) {
        Query query = em.createNamedQuery("Report.findByCalburned");
        query.setParameter("calburned", calburned);
        return query.getResultList();
    }

    @GET
    @Path("findByStepstaken/{stepstaken}")
    @Produces({"application/json"})
    public List<Report> findByStepstaken(@PathParam("stepstaken") Integer stepstaken
    ) {
        Query query = em.createNamedQuery("Report.findByStepstaken");
        query.setParameter("stepstaken", stepstaken);
        return query.getResultList();
    }

    @GET
    @Path("findByCaloriegoal/{caloriegoal}")
    @Produces({"application/json"})
    public List<Report> findByCaloriegoal(@PathParam("caloriegoal") Integer caloriegoal
    ) {
        Query query = em.createNamedQuery("Report.findByCaloriegoal");
        query.setParameter("caloriegoal", caloriegoal);
        return query.getResultList();
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Report> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Report> findRange(@PathParam("from") Integer from,
            @PathParam("to") Integer to
    ) {
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
