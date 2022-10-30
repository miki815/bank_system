/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PC
 */
@Entity
@Table(name = "transakcija")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transakcija.findAll", query = "SELECT t FROM Transakcija t"),
    @NamedQuery(name = "Transakcija.findByIdTr", query = "SELECT t FROM Transakcija t WHERE t.idTr = :idTr"),
    @NamedQuery(name = "Transakcija.findByDatumObavljanja", query = "SELECT t FROM Transakcija t WHERE t.datumObavljanja = :datumObavljanja"),
    @NamedQuery(name = "Transakcija.findByIznos", query = "SELECT t FROM Transakcija t WHERE t.iznos = :iznos"),
    @NamedQuery(name = "Transakcija.findByRedniBroj", query = "SELECT t FROM Transakcija t WHERE t.redniBroj = :redniBroj"),
    @NamedQuery(name = "Transakcija.findBySvrha", query = "SELECT t FROM Transakcija t WHERE t.svrha = :svrha"),
    @NamedQuery(name = "Transakcija.findByIdRacPrim", query = "SELECT t FROM Transakcija t WHERE t.idRacPrim = :idRacPrim"),
    @NamedQuery(name = "Transakcija.findByIdFil", query = "SELECT t FROM Transakcija t WHERE t.idFil = :idFil"),
    @NamedQuery(name = "Transakcija.findByTip", query = "SELECT t FROM Transakcija t WHERE t.tip = :tip")})
public class Transakcija implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTr")
    private Integer idTr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datumObavljanja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumObavljanja;
    @Basic(optional = false)
    @NotNull
    @Column(name = "iznos")
    private double iznos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "redniBroj")
    private int redniBroj;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "svrha")
    private String svrha;
    @Column(name = "idRacPrim")
    private Integer idRacPrim;
    @Column(name = "idFil")
    private Integer idFil;
    @Column(name = "tip")
    private Integer tip;
    @JoinColumns({
        @JoinColumn(name = "idRac", referencedColumnName = "idRac"),
        @JoinColumn(name = "idRac", referencedColumnName = "idRac"),
        @JoinColumn(name = "idRac", referencedColumnName = "idRac")})
    @ManyToOne(optional = false)
    private Racun racun;

    public Transakcija() {
    }

    public Transakcija(Integer idTr) {
        this.idTr = idTr;
    }

    public Transakcija(Integer idTr, Date datumObavljanja, double iznos, int redniBroj, String svrha) {
        this.idTr = idTr;
        this.datumObavljanja = datumObavljanja;
        this.iznos = iznos;
        this.redniBroj = redniBroj;
        this.svrha = svrha;
    }

    public Integer getIdTr() {
        return idTr;
    }

    public void setIdTr(Integer idTr) {
        this.idTr = idTr;
    }

    public Date getDatumObavljanja() {
        return datumObavljanja;
    }

    public void setDatumObavljanja(Date datumObavljanja) {
        this.datumObavljanja = datumObavljanja;
    }

    public double getIznos() {
        return iznos;
    }

    public void setIznos(double iznos) {
        this.iznos = iznos;
    }

    public int getRedniBroj() {
        return redniBroj;
    }

    public void setRedniBroj(int redniBroj) {
        this.redniBroj = redniBroj;
    }

    public String getSvrha() {
        return svrha;
    }

    public void setSvrha(String svrha) {
        this.svrha = svrha;
    }

    public Integer getIdRacPrim() {
        return idRacPrim;
    }

    public void setIdRacPrim(Integer idRacPrim) {
        this.idRacPrim = idRacPrim;
    }

    public Integer getIdFil() {
        return idFil;
    }

    public void setIdFil(Integer idFil) {
        this.idFil = idFil;
    }

    public Integer getTip() {
        return tip;
    }

    public void setTip(Integer tip) {
        this.tip = tip;
    }

    public Racun getRacun() {
        return racun;
    }

    public void setRacun(Racun racun) {
        this.racun = racun;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTr != null ? idTr.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transakcija)) {
            return false;
        }
        Transakcija other = (Transakcija) object;
        if ((this.idTr == null && other.idTr != null) || (this.idTr != null && !this.idTr.equals(other.idTr))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Transakcija[ idTr=" + idTr + " ]";
    }
    
}
