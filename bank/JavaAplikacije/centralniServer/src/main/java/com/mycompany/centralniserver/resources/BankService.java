/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.centralniserver.resources;

import java.util.ArrayList;
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
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author PC
 */
@Path("bankService")
public class BankService {
    
     @Resource(lookup = "bankaFac")
    public ConnectionFactory cf;
    
    @Resource(lookup = "p1")
    public Queue myQueue;
    
    @Resource(lookup = "p2")
    public Queue myQueue2;
    
    @Resource(lookup = "c1")
    public Queue centralQueue;
    
    
    public Response sendToSubsystem(int task, int intProp1, String strProp1, String strProp2, int intProp2, int intProp3){
         try {
             JMSContext context = cf.createContext();
             JMSConsumer consumer = context.createConsumer(centralQueue);
             JMSProducer producer = context.createProducer();
             TextMessage textMsg = context.createTextMessage();
             textMsg.setText("Poruka centralnog podsistemu 1");
             textMsg.setIntProperty("task", task);
             if(strProp1 != null) textMsg.setStringProperty("strProp1", strProp1);
             if(strProp2 != null) textMsg.setStringProperty("strProp2", strProp2);
             textMsg.setIntProperty("intProp1", intProp1);
             textMsg.setIntProperty("intProp2", intProp2);
             textMsg.setIntProperty("intProp3", intProp3);
             if(task == 1 || task == 2 || task == 3 || task == 4 || task == 10 || task == 11 || task == 12) producer.send(myQueue, textMsg);
             else if(task == 5 || task == 6 || task == 7 || task == 8 || task == 9 || task == 13 || task == 14) producer.send(myQueue2, textMsg);

             ObjectMessage objMsg = (ObjectMessage)consumer.receive();
             ArrayList<ArrayList<String>> zaKlijenta = (ArrayList<ArrayList<String>>)objMsg.getObject();
             
             return Response.ok().entity(zaKlijenta).build();

         } catch (JMSException ex) {
             Logger.getLogger(BankService.class.getName()).log(Level.SEVERE, null, ex);
         }
                      
             return Response
                     .ok("zahtev poslat uspesno")
                     .build();
    }
    
    private Response getClientTask(){
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(myQueue);
        TextMessage txtMsg;
        try {
            Message msg = consumer.receive();
            txtMsg = (TextMessage)msg;
            System.out.println(txtMsg.getText());
        } catch (JMSException ex) {
            Logger.getLogger(BankService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return Response.ok("zahtev od klijenta primljen").build();
    }
    
    @GET
    @Path("test")
    public Response test(){
        return Response.ok("teeeest").build();
    }
    
    @GET
    @Path("kreirajMesto")
    public Response novoMesto(@QueryParam("naziv") String nazivM, @QueryParam("pos") int postanskiB){
        System.out.println("Kreiram mesto centralni server ovde");
        int task = 1;
        String ime = nazivM;
        int pos = postanskiB;
        return sendToSubsystem(task, pos, nazivM, null, 0, 0);
    }
    
    @GET
    @Path("kreirajFilijalu")
    public Response novaFilijala(@QueryParam("naziv") String nazivF, @QueryParam("adresa") String adr, @QueryParam("mestoId") int idM){
        int task = 2;
        int idMesta = idM;
        String ime = nazivF;
        String adresa = adr;
        return sendToSubsystem(task, idMesta, ime, adresa, 0, 0);
    }
    
    @GET
    @Path("kreirajKomitenta")
    public Response noviKomitent(@QueryParam("naziv") String nazivK, @QueryParam("adresa") String adr, @QueryParam("mestoId") int idM){
        int task = 3;
        int idMesta = idM;
        String ime = nazivK;
        String adresa = adr;
        return sendToSubsystem(task, idM, nazivK, adr, 0, 0);
    }
    
    @GET
    @Path("promeniSediste")
    public Response promSediste(@QueryParam("idK") int idKom, @QueryParam("adr") String adresa){
        int task = 4;
        return sendToSubsystem(task, idKom, adresa, null, 0, 0);
    }
    
    @GET
    @Path("dohvatiMesta")
    public Response getMesta(){
        int task = 10;
        return sendToSubsystem(task, 0, null, null, 0, 0);
    }
    
    @GET
    @Path("dohvatiFilijale")
    public Response getFilijale(){
        int task = 11;
        return sendToSubsystem(task, 0, null, null, 0, 0);
    }
    
    @GET
    @Path("dohvatiKomitente")
    public Response getKomitente(){
        int task = 12;
        return sendToSubsystem(task, 0, null, null, 0, 0);
    }
    
    @GET
    @Path("otvoriRacun")
    public Response openRacun(@QueryParam("idK") int idKom, @QueryParam("idM") int idM, @QueryParam("dozMin") int dozMinus){
        int task = 5;
        return sendToSubsystem(task, idKom, null, null, idM, dozMinus);
    }
    
    @GET
    @Path("zatvoriRacun")
    public Response closeRacun(@QueryParam("idR") int idR){
        int task = 6;
        return sendToSubsystem(task, idR, null, null, 0, 0);
    }
    
    @GET
    @Path("prenesiNovac")
    public Response transferNovac(@QueryParam("idPos") int idRPos, @QueryParam("idPrim") int idRPrim, @QueryParam("iznos") int iznos, @QueryParam("svrha") String svrha){
        int task = 7;
        return sendToSubsystem(task, idRPos, svrha, null, idRPrim, iznos);
    }
    
    @GET
    @Path("dohvatiRacune")
    public Response getRacuni(@QueryParam("idK") int idKom){
        int task = 13;
        return sendToSubsystem(task, idKom, null, null, 0, 0);
    }
    
    @GET
    @Path("uplata")
    public Response uplata(@QueryParam("idR") int idR, @QueryParam("idF") int idF, @QueryParam("iznos") int iznos, @QueryParam("svrha") String svrha){
        int task = 8;
        return sendToSubsystem(task, idR, svrha, null, iznos, idF);
    }
    
    @GET
    @Path("isplata")
    public Response isplata(@QueryParam("idR") int idR, @QueryParam("idF") int idF, @QueryParam("iznos") int iznos, @QueryParam("svrha") String svrha){
        int task = 9;
        return sendToSubsystem(task, idR, svrha, null, iznos, idF);
    }
    
    @GET
    @Path("dohvatiTransakcije")
    public Response getTransakcije(@QueryParam("idR") int idR){
        int task = 14;
        return sendToSubsystem(task, idR, null, null, 0, 0);
    }
}
