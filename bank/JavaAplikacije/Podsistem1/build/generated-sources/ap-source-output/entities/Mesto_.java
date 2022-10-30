package entities;

import entities.Filijala;
import entities.Komitent;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2022-02-26T02:02:42")
@StaticMetamodel(Mesto.class)
public class Mesto_ { 

    public static volatile SingularAttribute<Mesto, String> naziv;
    public static volatile SingularAttribute<Mesto, Integer> idmesto;
    public static volatile SingularAttribute<Mesto, Integer> postanskiBroj;
    public static volatile ListAttribute<Mesto, Filijala> filijalaList;
    public static volatile ListAttribute<Mesto, Komitent> komitentList;

}