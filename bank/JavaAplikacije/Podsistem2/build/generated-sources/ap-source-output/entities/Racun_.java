package entities;

import entities.Komitent;
import entities.Transakcija;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2022-02-26T02:04:34")
@StaticMetamodel(Racun.class)
public class Racun_ { 

    public static volatile SingularAttribute<Racun, Integer> brojTransakcija;
    public static volatile SingularAttribute<Racun, Double> stanje;
    public static volatile SingularAttribute<Racun, Date> datumOtvaranja;
    public static volatile SingularAttribute<Racun, Komitent> komitent;
    public static volatile ListAttribute<Racun, Transakcija> transakcijaList;
    public static volatile SingularAttribute<Racun, Integer> mestoId;
    public static volatile SingularAttribute<Racun, Double> dozvoljeniMinus;
    public static volatile SingularAttribute<Racun, Integer> idRac;
    public static volatile SingularAttribute<Racun, String> status;

}