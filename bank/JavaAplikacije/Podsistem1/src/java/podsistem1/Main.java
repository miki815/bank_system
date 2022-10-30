/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package podsistem1;


import entities.Filijala;
import entities.Komitent;
import entities.Mesto;
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
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author PC
 */
public class Main {

    @Resource(lookup = "bankaFac")
    private static ConnectionFactory cf;
    
    @Resource(lookup = "p1")
    private static Queue myQueue;
    
    @Resource(lookup = "c1")
    private static Queue centralQueue;
    
    public static Mesto kreirajMesto(String ime, int postanski){
        Mesto mesto = new Mesto();
        mesto.setNaziv(ime);
        mesto.setPostanskiBroj(postanski);
        return mesto;
    }
    
    public static Filijala kreirajFilijalu(TextMessage txtMsg, Mesto mesto){
        Filijala fil = null;
        try {
            fil = new Filijala();
            String ime = txtMsg.getStringProperty("strProp1");
            String adresa = txtMsg.getStringProperty("strProp2");
            fil.setNaziv(ime);
            fil.setAdresa(adresa);
            fil.setMesto(mesto);
            return fil;
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fil;
    }
    
      public static Komitent kreirajKomitenta(TextMessage txtMsg, Mesto mesto){
        Komitent kom = null;
        try {
            kom = new Komitent();
            String ime = txtMsg.getStringProperty("strProp1");
            String adresa = txtMsg.getStringProperty("strProp2");
            kom.setNaziv(ime);
            kom.setAdresa(adresa);
            kom.setMesto(mesto);
            return kom;
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kom;
    }
      
    public static void promeniSediste(EntityManager em, TextMessage txtMsg){
        try {
            int idKom = txtMsg.getIntProperty("intProp1");
            Komitent kom = em.find(Komitent.class, idKom);
            if(kom == null) {System.out.println("Ne postoji komitent sa unetim id!"); return;}
            String adresa = txtMsg.getStringProperty("strProp1");
            kom.setAdresa(adresa);
            em.getTransaction().begin();
            em.persist(kom);
            em.getTransaction().commit();
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public static void main(String[] args) {
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(myQueue);
        JMSProducer producer = context.createProducer();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1PU");
        EntityManager em = emf.createEntityManager();
        
        
        while(true){
            try {
                Message msg = consumer.receive();
                TextMessage txtMsg = (TextMessage)msg;
                System.out.println(txtMsg.getText());
                
                int task = txtMsg.getIntProperty("task");
                switch(task){
                    case 1:{
                        System.out.println("Kreiraj mesto");
                        String ime = txtMsg.getStringProperty("strProp1");
                        int postanski = txtMsg.getIntProperty("intProp1");
                        Mesto mesto = kreirajMesto(ime, postanski);
                        em.getTransaction().begin();
                        em.persist(mesto);
                        em.getTransaction().commit();
                        System.out.println("Mesto kreirano!");
                        
                        ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
                        ArrayList<String> poruka = new ArrayList<>();
                        poruka.add("Mesto " + ime + " je kreirano");
                        zaCentralni.add(poruka);
                        ObjectMessage objMsg = context.createObjectMessage();
                        objMsg.setObject(zaCentralni);
                        producer.send(centralQueue, objMsg);
                        break;
                    }
                    case 2:{
                        System.out.println("Kreiraj filijalu");
                        int idMesta = txtMsg.getIntProperty("intProp1");
                        Mesto mesto = em.find(Mesto.class, idMesta);
                        if(mesto == null) {System.out.println("Ne postoji mesto sa unetim id!"); break;}
                        Filijala fil = kreirajFilijalu(txtMsg, mesto);
                        em.getTransaction().begin();
                        em.persist(fil);
                        em.getTransaction().commit();
                        System.out.println("Filijala kreirana!");
                        
                        ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
                        ArrayList<String> poruka = new ArrayList<>();
                        poruka.add("Filijala " + txtMsg.getStringProperty("strProp1") + " je kreirana");
                        zaCentralni.add(poruka);
                        ObjectMessage objMsg = context.createObjectMessage();
                        objMsg.setObject(zaCentralni);
                        producer.send(centralQueue, objMsg);
                        break;
                    }
                    case 3:{
                        System.out.println("Kreiraj komitenta");
                        int idMesta = txtMsg.getIntProperty("intProp1");
                        Mesto mesto = em.find(Mesto.class, idMesta);
                        if(mesto == null) {System.out.println("Ne postoji mesto sa unetim id!"); break;}
                        Komitent kom = kreirajKomitenta(txtMsg, mesto);
                        em.getTransaction().begin();
                        em.persist(kom);
                        em.getTransaction().commit();
                        System.out.println("Komitent kreiran!");
                        
                        ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
                        ArrayList<String> poruka = new ArrayList<>();
                        poruka.add("Komitent " + txtMsg.getStringProperty("strProp1") + " je kreiran");
                        zaCentralni.add(poruka);
                        ObjectMessage objMsg = context.createObjectMessage();
                        objMsg.setObject(zaCentralni);
                        producer.send(centralQueue, objMsg);
                        break;
                    }
                    case 4:{
                        System.out.println("Promeni sediste komitentu");
                        promeniSediste(em, txtMsg);
                        System.out.println("Sediste promenjeno");
                        
                        ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
                        ArrayList<String> poruka = new ArrayList<>();
                        poruka.add("Sediste komitentu promenjeno");
                        zaCentralni.add(poruka);
                        ObjectMessage objMsg = context.createObjectMessage();
                        objMsg.setObject(zaCentralni);
                        producer.send(centralQueue, objMsg);
                        break;
                    }
                    case 10:{
                        System.out.println("Dohvati mesta");
                        TypedQuery<Mesto> mestaQuery = em.createNamedQuery("Mesto.findAll", Mesto.class);
                        List<Mesto> resultList = mestaQuery.getResultList();
                        for (Mesto mesto : resultList) System.out.println(mesto.getNaziv());
                        
                        ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
                        for(Mesto m: resultList){
                            ArrayList<String> poruka = new ArrayList<>();
                            poruka.add(m.getIdmesto()+"");
                            poruka.add(m.getNaziv());
                            poruka.add(m.getPostanskiBroj()+"");
                            zaCentralni.add(poruka);
                        }
                        ObjectMessage objMsg = context.createObjectMessage();
                        objMsg.setObject(zaCentralni);
                        producer.send(centralQueue, objMsg);
                        break;
                    }
                    case 11:{
                        System.out.println("Dohvati filijale");
                        TypedQuery<Filijala> filijalaQuery = em.createNamedQuery("Filijala.findAll", Filijala.class);
                        List<Filijala> resultList = filijalaQuery.getResultList();
                        for (Filijala fil : resultList) System.out.println(fil.getNaziv());
                        
                        ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
                        for(Filijala f: resultList){
                            ArrayList<String> poruka = new ArrayList<>();
                            poruka.add(f.getIdfilijala()+"");
                            poruka.add(f.getNaziv());
                            poruka.add(f.getAdresa()+"");
                            poruka.add(f.getMesto().getIdmesto()+"");
                            zaCentralni.add(poruka);
                        }
                        ObjectMessage objMsg = context.createObjectMessage();
                        objMsg.setObject(zaCentralni);
                        producer.send(centralQueue, objMsg);
                        break;
                    }
                    case 12:{
                        System.out.println("Dohvati komitente");
                        TypedQuery<Komitent> komitentQuery = em.createNamedQuery("Komitent.findAll", Komitent.class);
                        List<Komitent> resultList = komitentQuery.getResultList();
                        for (Komitent kom : resultList) System.out.println(kom.getNaziv());
                        
                        ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
                        for(Komitent k: resultList){
                            ArrayList<String> poruka = new ArrayList<>();
                            poruka.add(k.getIdkomitent()+"");
                            poruka.add(k.getNaziv());
                            poruka.add(k.getAdresa()+"");
                            poruka.add(k.getMesto().getIdmesto()+"");
                            zaCentralni.add(poruka);
                        }
                        ObjectMessage objMsg = context.createObjectMessage();
                        objMsg.setObject(zaCentralni);
                        producer.send(centralQueue, objMsg);
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