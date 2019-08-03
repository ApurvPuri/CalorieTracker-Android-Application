package A1;

import A1.Users;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-12T14:28:56")
@StaticMetamodel(Report.class)
public class Report_ { 

    public static volatile SingularAttribute<Report, Date> date;
    public static volatile SingularAttribute<Report, Integer> reportid;
    public static volatile SingularAttribute<Report, Integer> totcalconsumed;
    public static volatile SingularAttribute<Report, Integer> calburned;
    public static volatile SingularAttribute<Report, Integer> caloriegoal;
    public static volatile SingularAttribute<Report, Users> userid;
    public static volatile SingularAttribute<Report, Integer> stepstaken;

}