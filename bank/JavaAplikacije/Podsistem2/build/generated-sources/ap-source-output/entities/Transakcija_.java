package entities;

import entities.Racun;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2022-02-26T02:04:34")
@StaticMetamodel(Transakcija.class)
public class Transakcija_ { 

    public static volatile SingularAttribute<Transakcija, Double> iznos;
    public static volatile SingularAttribute<Transakcija, Date> datumObavljanja;
    public static volatile SingularAttribute<Transakcija, String> svrha;
    public static volatile SingularAttribute<Transakcija, Integer> idTr;
    public static volatile SingularAttribute<Transakcija, Integer> idFil;
    public static volatile SingularAttribute<Transakcija, Racun> racun;
    public static volatile SingularAttribute<Transakcija, Integer> idRacPrim;
    public static volatile SingularAttribute<Transakcija, Integer> tip;
    public static volatile SingularAttribute<Transakcija, Integer> redniBroj;

}