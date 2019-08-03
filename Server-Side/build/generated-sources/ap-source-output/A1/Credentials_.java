package A1;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-12T14:28:56")
@StaticMetamodel(Credentials.class)
public class Credentials_ { 

    public static volatile SingularAttribute<Credentials, Date> signupdate;
    public static volatile SingularAttribute<Credentials, String> passwordhash;
    public static volatile SingularAttribute<Credentials, Integer> userid;
    public static volatile SingularAttribute<Credentials, String> username;

}