/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author PC
 */
@Entity
@Table(name = "racun")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Racun.findAll", query = "SELECT r FROM Racun r"),
    @NamedQuery(name = "Racun.findByIdRac", query = "SELECT r FROM Racun r WHERE r.idRac = :idRac"),
    @NamedQuery(name = "Racun.findByStanje", query = "SELECT r FROM Racun r WHERE r.stanje = :stanje"),
    @NamedQuery(name = "Racun.findByStatus", query = "SELECT r FROM Racun r WHERE r.status = :status"),
    @NamedQuery(name = "Racun.findByDozvoljeniMinus", query = "SELECT r FROM Racun r WHERE r.dozvoljeniMinus = :dozvoljeniMinus"),
    @NamedQuery(name = "Racun.findByMestoId", query = "SELECT r FROM Racun r WHERE r.mestoId = :mestoId"),
    @NamedQuery(name = "Racun.findByDatumOtvaranja", query = "SELECT r FROM Racun r WHERE r.datumOtvaranja = :datumOtvaranja"),
    @NamedQuery(name = "Racun.findByBrojTransakcija", query = "SELECT r FROM Racun r WHERE r.brojTransakcija = :brojTransakcija")})
public class Racun implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idRac")
    private Integer idRac;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "stanje")
    private Double stanje;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dozvoljeniMinus")
    private double dozvoljeniMinus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mestoId")
    private int mestoId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datumOtvaranja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumOtvaranja;
    @Column(name = "brojTransakcija")
    private Integer brojTransakcija;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "racun")
    private List<Transakcija> transakcijaList;
    @JoinColumns({
        @JoinColumn(name = "idKom", referencedColumnName = "idKom"),
        @JoinColumn(name = "idKom", referencedColumnName = "idKom"),
        @JoinColumn(name = "idKom", referencedColumnName = "idKom")})
    @ManyToOne(optional = false)
    private Komitent komitent;

    public Racun() {
    }

    public Racun(Integer idRac) {
        this.idRac = idRac;
    }

    public Racun(Integer idRac, String status, double dozvoljeniMinus, int mestoId, Date datumOtvaranja) {
        this.idRac = idRac;
        this.status = status;
        this.dozvoljeniMinus = dozvoljeniMinus;
        this.mestoId = mestoId;
        this.datumOtvaranja = datumOtvaranja;
    }

    public Integer getIdRac() {
        return idRac;
    }

    public void setIdRac(Integer idRac) {
        this.idRac = idRac;
    }

    public Double getStanje() {
        return stanje;
    }

    public void setStanje(Double stanje) {
        this.stanje = stanje;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getDozvoljeniMinus() {
        return dozvoljeniMinus;
    }

    public void setDozvoljeniMinus(double dozvoljeniMinus) {
        this.dozvoljeniMinus = dozvoljeniMinus;
    }

    public int getMestoId() {
        return mestoId;
    }

    public void setMestoId(int mestoId) {
        this.mestoId = mestoId;
    }

    public Date getDatumOtvaranja() {
        return datumOtvaranja;
    }

    public void setDatumOtvaranja(Date datumOtvaranja) {
        this.datumOtvaranja = datumOtvaranja;
    }

    public Integer getBrojTransakcija() {
        return brojTransakcija;
    }

    public void setBrojTransakcija(Integer brojTransakcija) {
        this.brojTransakcija = brojTransakcija;
    }

    @XmlTransient
    public List<Transakcija> getTransakcijaList() {
        return transakcijaList;
    }

    public void setTransakcijaList(List<Transakcija> transakcijaList) {
        this.transakcijaList = transakcijaList;
    }

    public Komitent getKomitent() {
        return komitent;
    }

    public void setKomitent(Komitent komitent) {
        this.komitent = komitent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRac != null ? idRac.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Racun)) {
            return false;
        }
        Racun other = (Racun) object;
        if ((this.idRac == null && other.idRac != null) || (this.idRac != null && !this.idRac.equals(other.idRac))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Racun[ idRac=" + idRac + " ]";
    }
    
}
