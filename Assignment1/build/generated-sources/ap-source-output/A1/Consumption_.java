package A1;

import A1.Food;
import A1.Users;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-12T14:28:56")
@StaticMetamodel(Consumption.class)
public class Consumption_ { 

    public static volatile SingularAttribute<Consumption, Date> date;
    public static volatile SingularAttribute<Consumption, Integer> foodserving;
    public static volatile SingularAttribute<Consumption, Integer> consumptionid;
    public static volatile SingularAttribute<Consumption, Food> foodid;
    public static volatile SingularAttribute<Consumption, Users> userid;

}