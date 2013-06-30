package de.isag.model;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "firmen")
public class Firmen implements Serializable
{
    private static final long      serialVersionUID   = -2328838058260665359L;

    private Long                   pk_Firmen;
    private String                 firmenName;
    private String                 kurzZeichen;

    private Set<EinsatzortProjekt> einsatzortProjekts = new TreeSet<EinsatzortProjekt>();

    public Firmen()
    {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_Firmen", nullable = false)
    public Long getPk_Firmen()
    {
        return pk_Firmen;
    }

    public void setPk_Firmen(Long pk_Firmen)
    {
        this.pk_Firmen = pk_Firmen;
    }

    @Column(name = "firmenName")
    public String getFirmenName()
    {
        return firmenName;
    }

    public void setFirmenName(String firmenName)
    {
        this.firmenName = firmenName;
    }

    @Column(name = "kurzZeichen")
    public String getKurzZeichen()
    {
        return kurzZeichen;
    }

    public void setKurzZeichen(String kurzZeichen)
    {
        this.kurzZeichen = kurzZeichen;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "firmen")
    public Set<EinsatzortProjekt> getEinsatzortProjekts()
    {
        return einsatzortProjekts;
    }

    public void setEinsatzortProjekts(Set<EinsatzortProjekt> einsatzortProjekts)
    {
        this.einsatzortProjekts = einsatzortProjekts;
    }
}
