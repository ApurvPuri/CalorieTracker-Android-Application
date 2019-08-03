package A1;

import A1.Consumption;
import A1.Report;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-12T14:28:56")
@StaticMetamodel(Users.class)
public class Users_ { 

    public static volatile SingularAttribute<Users, Date> dateofbirth;
    public static volatile SingularAttribute<Users, String> address;
    public static volatile SingularAttribute<Users, String> gender;
    public static volatile CollectionAttribute<Users, Consumption> consumptionCollection;
    public static volatile SingularAttribute<Users, Integer> stepspermile;
    public static volatile SingularAttribute<Users, Integer> postcode;
    public static volatile SingularAttribute<Users, Integer> weight;
    public static volatile SingularAttribute<Users, Integer> userid;
    public static volatile CollectionAttribute<Users, Report> reportCollection;
    public static volatile SingularAttribute<Users, String> surname;
    public static volatile SingularAttribute<Users, String> name;
    public static volatile SingularAttribute<Users, Integer> levelofactivity;
    public static volatile SingularAttribute<Users, String> email;
    public static volatile SingularAttribute<Users, Integer> height;

}