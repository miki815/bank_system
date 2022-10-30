/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package klijent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author PC
 */
public class Klijent {
    
    public static void dohvatiRezultat(HttpURLConnection konekcija){
        BufferedReader in = null;
        String podatak = null;
        try {
            in = new BufferedReader(new InputStreamReader(konekcija.getInputStream()));
            while ((podatak = in.readLine()) != null) System.out.println("\n" + podatak + "\n");
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void posaljiZahtev(String URLadresa){
        try {
            URL url = new URL(URLadresa);
            HttpURLConnection konekcija = (HttpURLConnection) url.openConnection();
            konekcija.setRequestMethod("GET");
            dohvatiRezultat(konekcija);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void kreirajMesto(String ime, int postanski){
        System.out.println("Klijent zahtev: kreiraj mesto");
        String URLadresa = "http://localhost:8080/CentralniServer/resources/bankService/kreirajMesto?naziv=" + ime + "&pos=" + postanski;
        posaljiZahtev(URLadresa);
    }
    
    public static void kreirajFilijalu(String ime, String adresa, int idM){
        System.out.println("Klijent zahtev: kreiraj filijalu");
        String URLadresa = "http://localhost:8080/CentralniServer/resources/bankService/kreirajFilijalu?naziv=" + ime + "&adresa=" + adresa + "&mestoId=" + idM;            
        posaljiZahtev(URLadresa);
    }
    
    public static void kreirajKomitenta(String ime, String adresa, int idM){
        System.out.println("Klijent zahtev: kreiraj komitenta");
        String URLadresa = "http://localhost:8080/CentralniServer/resources/bankService/kreirajKomitenta?naziv=" + ime + "&adresa=" + adresa + "&mestoId=" + idM;            
        posaljiZahtev(URLadresa);
    }

    public static void promeniSediste(int idK, String adresa){
        System.out.println("Klijent zahtev: promeni sediste komitentu");
        String URLadresa = "http://localhost:8080/CentralniServer/resources/bankService/promeniSediste?idK=" + idK + "&adr=" + adresa;            
        posaljiZahtev(URLadresa);
    }
    
    public static void dohvatiMesta(){
        System.out.println("Klijent zahtev: dohvati mesta");
        String URLadresa = "http://localhost:8080/CentralniServer/resources/bankService/dohvatiMesta";            
        posaljiZahtev(URLadresa);
    }
    
    public static void dohvatiFilijale(){
        System.out.println("Klijent zahtev: dohvati filijale");
        String URLadresa = "http://localhost:8080/CentralniServer/resources/bankService/dohvatiFilijale";            
        posaljiZahtev(URLadresa);
    }
     
    public static void dohvatiKomitente(){
        System.out.println("Klijent zahtev: dohvati komitente");
        String URLadresa = "http://localhost:8080/CentralniServer/resources/bankService/dohvatiKomitente";            
        posaljiZahtev(URLadresa);
    }
    
    public static void otvoriRacun(int idK, int idM, int dozMin){
        System.out.println("Klijent zahtev: otvori racun");
        String URLadresa = "http://localhost:8080/CentralniServer/resources/bankService/otvoriRacun?idK=" + idK + "&idM=" + idM + "&dozMin=" + dozMin;            
        posaljiZahtev(URLadresa);
    }
    
    public static void zatvoriRacun(int idR){
        System.out.println("Klijent zahtev: zatvori racun");
        String URLadresa = "http://localhost:8080/CentralniServer/resources/bankService/zatvoriRacun?idR=" + idR;           
        posaljiZahtev(URLadresa);
    }
    
    public static void prenesiNovac(int idPos, int idPrim, int iznos, String svrha){
        System.out.println("Klijent zahtev: prenesi novac");
        String URLadresa = "http://localhost:8080/CentralniServer/resources/bankService/prenesiNovac?idPos=" + idPos + "&idPrim=" + idPrim + "&iznos=" + iznos + "&svrha=" + svrha;            
        posaljiZahtev(URLadresa); 
    }
    
    public static void uplata(int idR, int idF, int iznos, String svrha){
        System.out.println("Klijent zahtev: uplata novca");
        String URLadresa = "http://localhost:8080/CentralniServer/resources/bankService/uplata?idR=" + idR + "&idF=" + idF + "&iznos=" + iznos + "&svrha=" + svrha;            
        posaljiZahtev(URLadresa);
    }
    
    public static void isplata(int idR, int idF, int iznos, String svrha){
        System.out.println("Klijent zahtev: isplata novca");
        String URLadresa = "http://localhost:8080/CentralniServer/resources/bankService/isplata?idR=" + idR + "&idF=" + idF + "&iznos=" + iznos + "&svrha=" + svrha;            
        posaljiZahtev(URLadresa);
    }
    
    public static void dohvatiRacune(int idK){
        System.out.println("Klijent zahtev: dohvati racune");
        String URLadresa = "http://localhost:8080/CentralniServer/resources/bankService/dohvatiRacune?idK=" + idK;           
        posaljiZahtev(URLadresa);
    }
    
    public static void dohvatiTransakcije(int idR){
        System.out.println("Klijent zahtev: dohvati transakcije");
        String URLadresa = "http://localhost:8080/CentralniServer/resources/bankService/dohvatiRacune?idK=" + idR;           
        posaljiZahtev(URLadresa);
    }
    
    public static void main(String[] args) {
        Scanner skener = new Scanner(System.in);
        
        while(true){
            System.out.println("Izaberite opciju:\n" + "1.Kreiranje mesta\n" + "2.Kreiranje filijale\n" + "3.Kreiranje komitenta\n" +
                               "4.Promena sedista komitentu\n" + "5.Otvaranje racuna\n" + "6.Zatvaranje racuna\n" +
                               "7.Prenos novca sa racuna na racun\n" + "8.Uplata novca na racun\n" + "9.Isplata novca sa racuna\n" +
                               "10.Dohvati mesta\n" + "11.Dohvati filijale\n" + "12.Dohvati komitente\n" + 
                               "13.Dohvati racune za komitenta\n" + "14.Dohvati transakcije za racun\n" + "15.Kraj\n");
            int task = skener.nextInt();
            skener.nextLine(); // "\n"
            
            switch(task){
                case 1:{
                    System.out.println("Ime mesta: ");
                    String ime = skener.nextLine().replace(" ", "%20");
                    System.out.println("Postanski broj: ");
                    int postanski = skener.nextInt();
                    skener.nextLine();
                    kreirajMesto(ime, postanski);
                    break;
                }
                case 2:{
                    System.out.println("Ime filijale: ");
                    String ime = skener.nextLine().replace(" ", "%20");
                    System.out.println("Adresa: ");
                    String adresa = skener.nextLine().replace(" ", "%20");
                    System.out.println("ID mesta: ");
                    int idM = skener.nextInt();
                    skener.nextLine();
                    kreirajFilijalu(ime, adresa, idM);
                    break;
                }
                case 3:{
                    System.out.println("Ime komitenta: ");
                    String ime = skener.nextLine().replace(" ", "%20");
                    System.out.println("Adresa: ");
                    String adresa = skener.nextLine().replace(" ", "%20");
                    System.out.println("ID mesta: ");
                    int idM = skener.nextInt();
                    skener.nextLine();
                    kreirajKomitenta(ime, adresa, idM);
                    break;
                }
                case 4:{
                    System.out.println("ID komitenta: ");
                    int idK = skener.nextInt();
                    skener.nextLine();
                    System.out.println("Nova adresa: ");
                    String adresa = skener.nextLine().replace(" ", "%20");
                    promeniSediste(idK, adresa);
                    break;
                }
                case 5:{
                    System.out.println("ID komitenta: ");
                    int idK = skener.nextInt();
                    skener.nextLine();
                    System.out.println("ID mesta ");
                    int idM = skener.nextInt();
                    skener.nextLine();
                    System.out.println("Dozvoljeni minus: ");
                    int dozMin = skener.nextInt();
                    skener.nextLine();
                    otvoriRacun(idK, idM, dozMin);
                    break;
                }
                case 6:{
                    System.out.println("ID racuna: ");
                    int idR = skener.nextInt();
                    skener.nextLine();
                    zatvoriRacun(idR);
                    break;
                }
                case 7:{
                    System.out.println("ID racuna posiljaoca: ");
                    int idPos = skener.nextInt();
                    skener.nextLine();
                    System.out.println("ID racuna primaoca: ");
                    int idPrim = skener.nextInt();
                    skener.nextLine();
                    System.out.println("Iznos za slanje: ");
                    int iznos = skener.nextInt();
                    skener.nextLine();
                    System.out.println("Svrha slanja: ");
                    String svrha = skener.nextLine().replace(" ", "%20");
                    prenesiNovac(idPos, idPrim, iznos, svrha);
                    break;
                } 
                case 8:{
                    System.out.println("ID racuna: ");
                    int idR = skener.nextInt();
                    skener.nextLine();
                    System.out.println("ID filijale: ");
                    int idF = skener.nextInt();
                    skener.nextLine();
                    System.out.println("Iznos za uplatu: ");
                    int iznos = skener.nextInt();
                    skener.nextLine();
                    System.out.println("Svrha uplate: ");
                    String svrha = skener.nextLine().replace(" ", "%20");
                    uplata(idR, idF, iznos, svrha);
                    break;
                } 
                case 9:{
                    System.out.println("ID racuna: ");
                    int idR = skener.nextInt();
                    skener.nextLine();
                    System.out.println("ID filijale: ");
                    int idF = skener.nextInt();
                    skener.nextLine();
                    System.out.println("Iznos za isplatu: ");
                    int iznos = skener.nextInt();
                    skener.nextLine();
                    System.out.println("Svrha isplate: ");
                    String svrha = skener.nextLine().replace(" ", "%20");
                    isplata(idR, idF, iznos, svrha);
                    break;
                } 
                case 10 :{
                    dohvatiMesta();
                    break;
                }
                case 11 :{
                    dohvatiFilijale();
                    break;
                }
                case 12 :{
                    dohvatiKomitente();
                    break;
                }
                case 13:{
                    System.out.println("ID komitenta: ");
                    int idK = skener.nextInt();
                    skener.nextLine();
                    dohvatiRacune(idK);
                    break;
                }
                case 14:{
                    System.out.println("ID racuna: ");
                    int idR = skener.nextInt();
                    skener.nextLine();
                    dohvatiTransakcije(idR);
                    break;
                }
                default:{
                    break;
                }
            }
        }
        
    }
    
}