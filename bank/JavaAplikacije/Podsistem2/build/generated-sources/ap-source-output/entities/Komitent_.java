package entities;

import entities.Racun;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2022-02-26T02:04:34")
@StaticMetamodel(Komitent.class)
public class Komitent_ { 

    public static volatile SingularAttribute<Komitent, Integer> idM;
    public static volatile ListAttribute<Komitent, Racun> racunList;
    public static volatile SingularAttribute<Komitent, Integer> idKom;
    public static volatile SingularAttribute<Komitent, String> naziv;
    public static volatile SingularAttribute<Komitent, String> adresa;

}