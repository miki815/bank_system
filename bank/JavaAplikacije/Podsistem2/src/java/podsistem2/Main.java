/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package podsistem2;

import entities.Komitent;
import entities.Racun;
import entities.Transakcija;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author PC
 */
public class Main {

    @Resource(lookup = "bankaFac")
    private static ConnectionFactory cf;
    
    @Resource(lookup = "p2")
    private static Queue myQueue;
    
    @Resource(lookup = "c1")
    private static Queue centralQueue;
    
    
    public static void otvoriRacun(EntityManager em, TextMessage txtMsg, JMSContext context, JMSProducer producer){
        try {
            int idKom = txtMsg.getIntProperty("intProp1");
            int idM  = txtMsg.getIntProperty("intProp2");
            int dozMinus  = txtMsg.getIntProperty("intProp3");
            double stanje = 0;
            Komitent kom = em.find(Komitent.class, idKom);
            if(kom == null) {System.out.println("Ne postoji komitent sa unetim id!"); return;}
            Racun racun = new Racun();
            racun.setBrojTransakcija(0);
            racun.setDatumOtvaranja(new Timestamp(System.currentTimeMillis()));
            racun.setDozvoljeniMinus(dozMinus);
            racun.setKomitent(kom);
            racun.setMestoId(idM);
            racun.setStanje(stanje);
            racun.setStatus("Aktivan");
            em.getTransaction().begin();
            em.persist(racun);
            em.getTransaction().commit();
            
            ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
            ArrayList<String> poruka = new ArrayList<>();
            poruka.add("Racun " + racun.getIdRac() + " je otvoren");
            zaCentralni.add(poruka);
            ObjectMessage objMsg = context.createObjectMessage();
            objMsg.setObject(zaCentralni);
            producer.send(centralQueue, objMsg);
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void zatvoriRacun(EntityManager em, TextMessage txtMsg, JMSContext context, JMSProducer producer){
        try {
            int idR = txtMsg.getIntProperty("intProp1");
            Racun racun = em.find(Racun.class, idR);
            if(racun == null) {System.out.println("Ne postoji racun sa unetim id!"); return;}
            em.getTransaction().begin();
            em.remove(racun);
            em.getTransaction().commit();
            
            ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
            ArrayList<String> poruka = new ArrayList<>();
            poruka.add("Racun " + racun.getIdRac() + " je zatvoren");
            zaCentralni.add(poruka);
            ObjectMessage objMsg = context.createObjectMessage();
            objMsg.setObject(zaCentralni);
            producer.send(centralQueue, objMsg);
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void prenosNovca(EntityManager em, TextMessage txtMsg, JMSContext context, JMSProducer producer){
        try {
            int idRPos = txtMsg.getIntProperty("intProp1");
            int idRPrim = txtMsg.getIntProperty("intProp2");
            int iznos = txtMsg.getIntProperty("intProp3");
            String svrha = txtMsg.getStringProperty("strProp1");
            Racun rPos = em.find(Racun.class, idRPos);
            Racun rPrim = em.find(Racun.class, idRPrim);
            if(rPos == null || rPrim == null) {System.out.println("Ne postoji racun sa unetim id!"); return;}
            
            em.getTransaction().begin();
            // posiljalac
            double stanje = rPos.getStanje();
            double dozMinus = rPos.getDozvoljeniMinus();
            if(stanje - iznos < -dozMinus) rPos.setStatus("Blokiran");
            rPos.setStanje(stanje - iznos);
            rPos.setBrojTransakcija(rPos.getBrojTransakcija() + 1);
            // primalac
            stanje = rPrim.getStanje();
            dozMinus = rPrim.getDozvoljeniMinus();
            if(stanje + iznos > -dozMinus) rPrim.setStatus("Aktivan");    
            rPrim.setStanje(stanje + iznos);
            
            Transakcija t = new Transakcija();
            t.setDatumObavljanja(new Timestamp(System.currentTimeMillis()));
            t.setIznos(iznos);
            t.setRacun(rPos);
            t.setSvrha(svrha);
            t.setRedniBroj(rPos.getBrojTransakcija());
            t.setIdRacPrim(idRPrim);
            t.setTip(3);
            
   

            em.persist(rPrim);
            em.persist(rPos);
            em.persist(t);
        //   em.flush();
            em.getTransaction().commit();
            
            ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
            ArrayList<String> poruka = new ArrayList<>();
            poruka.add("Novac prenesen. Iznos: " + iznos);
            zaCentralni.add(poruka);
            ObjectMessage objMsg = context.createObjectMessage();
            objMsg.setObject(zaCentralni);
            producer.send(centralQueue, objMsg);
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void getRacuni(EntityManager em, TextMessage txtMsg, JMSContext context, JMSProducer producer){
        try {
            int idKom = txtMsg.getIntProperty("intProp1");
            Komitent kom = em.find(Komitent.class, idKom);
            TypedQuery<Racun> rQuery = em.createQuery("SELECT r FROM Racun r WHERE r.idKom = :kom", Racun.class);
            List<Racun> racuni = rQuery.setParameter("kom", kom).getResultList();
            for (Racun racun : racuni) System.out.println(racun.getDozvoljeniMinus());
            
            ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
            for(Racun r: racuni){
                ArrayList<String> poruka = new ArrayList<>();
                poruka.add(r.getIdRac()+"");
                poruka.add(r.getStanje()+"");
                poruka.add(r.getStatus());
                poruka.add(r.getDozvoljeniMinus()+"");
                poruka.add(r.getMestoId()+"");
                poruka.add(r.getDatumOtvaranja()+"");
                poruka.add(r.getKomitent().getIdKom()+"");
                poruka.add(r.getBrojTransakcija()+"");
                zaCentralni.add(poruka);
            }
            ObjectMessage objMsg = context.createObjectMessage();
            objMsg.setObject(zaCentralni);
            producer.send(centralQueue, objMsg);
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void uplataNovca(EntityManager em, TextMessage txtMsg, JMSContext context, JMSProducer producer){
        try {
            int idR = txtMsg.getIntProperty("intProp1");
            int iznos = txtMsg.getIntProperty("intProp2");
            int idFil = txtMsg.getIntProperty("intProp3");
            String svrha = txtMsg.getStringProperty("strProp1");
            Racun racun = em.find(Racun.class, idR);
            if(racun == null) {System.out.println("Ne postoji racun sa unetim id!"); return;}
            
            em.getTransaction().begin();
            double stanje = racun.getStanje();
            double dozMinus = racun.getDozvoljeniMinus();
            if(stanje + iznos > -dozMinus) racun.setStatus("Aktivan");    
            racun.setStanje(stanje + iznos);
            racun.setBrojTransakcija(racun.getBrojTransakcija() + 1);

            Transakcija t = new Transakcija();
            t.setDatumObavljanja(new Timestamp(System.currentTimeMillis()));
            t.setIznos(iznos);
            t.setRacun(racun);
            t.setSvrha(svrha);
            t.setRedniBroj(racun.getBrojTransakcija());
            t.setIdFil(idFil);
            t.setTip(1);
       
             
            em.persist(racun);
            em.persist(t);
            em.flush();
          
            em.getTransaction().commit();
            
            ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
            ArrayList<String> poruka = new ArrayList<>();
            poruka.add("Novac uplacen. Iznos: " + iznos);
            zaCentralni.add(poruka);
            ObjectMessage objMsg = context.createObjectMessage();
            objMsg.setObject(zaCentralni);
            producer.send(centralQueue, objMsg);
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void isplataNovca(EntityManager em, TextMessage txtMsg, JMSContext context, JMSProducer producer){
        try {
            int idR = txtMsg.getIntProperty("intProp1");
            int iznos = txtMsg.getIntProperty("intProp2");
            int idFil = txtMsg.getIntProperty("intProp3");
            String svrha = txtMsg.getStringProperty("strProp1");
            Racun racun = em.find(Racun.class, idR);
            if(racun == null) {System.out.println("Ne postoji racun sa unetim id!"); return;}
            
            em.getTransaction().begin();
            double stanje = racun.getStanje();
            double dozMinus = racun.getDozvoljeniMinus();
            if(stanje - iznos < -dozMinus) racun.setStatus("Blokiran");    
            racun.setStanje(stanje - iznos);
            int brojT = racun.getBrojTransakcija() + 1;
            racun.setBrojTransakcija(brojT);

            Transakcija t = new Transakcija();
            t.setDatumObavljanja(new Timestamp(System.currentTimeMillis()));
            t.setIznos(iznos);
            t.setRacun(racun);
            t.setSvrha(svrha);
            t.setRedniBroj(racun.getBrojTransakcija());
            t.setIdFil(idFil);
            t.setTip(2);
        
             
            em.persist(racun);
            em.persist(t);
            em.flush();

            em.getTransaction().commit();
            
            ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
            ArrayList<String> poruka = new ArrayList<>();
            poruka.add("Novac isplacen. Iznos: " + iznos);
            zaCentralni.add(poruka);
            ObjectMessage objMsg = context.createObjectMessage();
            objMsg.setObject(zaCentralni);
            producer.send(centralQueue, objMsg);
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
     public static void getTransakcije(EntityManager em, TextMessage txtMsg, JMSContext context, JMSProducer producer){
        try {
            int idR = txtMsg.getIntProperty("intProp1");
            Racun racun = em.find(Racun.class, idR);
            TypedQuery<Transakcija> rQuery = em.createQuery("SELECT t FROM Transakcija t WHERE t.idRac = :rac", Transakcija.class);
            List<Transakcija> transakcije = rQuery.setParameter("rac", racun).getResultList();
            for (Transakcija t : transakcije) System.out.println(t.getDatumObavljanja());
            
            ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
            for(Transakcija t: transakcije){
                ArrayList<String> poruka = new ArrayList<>();
                poruka.add(t.getIdTr()+"");
                poruka.add(t.getDatumObavljanja()+"");
                poruka.add(t.getIznos()+"");
                poruka.add(t.getRedniBroj()+"");
                poruka.add(t.getSvrha()+"");
                poruka.add(t.getRacun().getIdRac()+"");
                zaCentralni.add(poruka);
            }
            ObjectMessage objMsg = context.createObjectMessage();
            objMsg.setObject(zaCentralni);
            producer.send(centralQueue, objMsg);
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void main(String[] args) {
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(myQueue);
        JMSProducer producer = context.createProducer();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2PU");
        EntityManager em = emf.createEntityManager();
        
        while(true){
            try {
                Message msg = consumer.receive();
                TextMessage txtMsg = (TextMessage)msg;
                System.out.println(txtMsg.getText());
                
                int task = txtMsg.getIntProperty("task");
                switch(task){
                    case 5:{
                        System.out.println("Otvori racun");
                        otvoriRacun(em, txtMsg, context, producer);
                        System.out.println("Racun otvoren");
                        break;
                    }
                    case 6:{
                        System.out.println("Zatvori racun");
                        zatvoriRacun(em, txtMsg, context, producer);
                        System.out.println("Racun zatvoren");
                        break;
                    }
                    case 7:{
                        System.out.println("Prenesi novac");
                        prenosNovca(em, txtMsg, context, producer);
                        System.out.println("Transakcija zavrsena novac prenesen");
                        break;
                    }
                    case 8:{
                        System.out.println("Kreiraj uplatu");
                        uplataNovca(em, txtMsg, context, producer);
                        System.out.println("Transakcija zavrsena novac uplacen");
                        break;
                    }
                    case 9:{
                        System.out.println("Kreiraj isplatu");
                        isplataNovca(em, txtMsg, context, producer);
                        System.out.println("Transakcija zavrsena novac isplacen");
                        break;
                    }
                    case 13:{
                        System.out.println("Dohvati racune");
                        getRacuni(em, txtMsg, context, producer);
                        break;
                    }
                    case 14:{
                        System.out.println("Dohvati transakcije");
                        getTransakcije(em, txtMsg, context, producer);
                        break;
                    }
                    default:{
                        System.out.println("Nepoznat ID operacije");
                    }
                }
            } catch (JMSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    

    
}
