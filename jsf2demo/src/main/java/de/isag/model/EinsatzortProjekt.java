package de.isag.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "einsatzortprojekt")
public class EinsatzortProjekt implements Serializable
{
    private static final long serialVersionUID = 6730498691485616556L;

    private Long              pk_EinsatzortProjekt;
    private Long              fk_Firmen;
    private Long              fk_Projekt;
    private String            einsatzortName;

    private Firmen            firmen           = new Firmen();

    public EinsatzortProjekt()
    {

    }

    public EinsatzortProjekt(Long pk_EinsatzortProjekt, Long fk_Firmen, Long fk_Projekt, String einsatzortName)
    {
        this.pk_EinsatzortProjekt = pk_EinsatzortProjekt;
        this.fk_Firmen = fk_Firmen;
        this.fk_Projekt = fk_Projekt;
        this.einsatzortName = einsatzortName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_EinsatzortProjekt")
    public Long getPk_EinsatzortProjekt()
    {
        return pk_EinsatzortProjekt;
    }

    public void setPk_EinsatzortProjekt(Long pk_EinsatzortProjekt)
    {
        this.pk_EinsatzortProjekt = pk_EinsatzortProjekt;
    }

    @Column(name = "fk_Firmen")
    public Long getFk_Firmen()
    {
        return fk_Firmen;
    }

    public void setFk_Firmen(Long fk_Firmen)
    {
        this.fk_Firmen = fk_Firmen;
    }

    @Column(name = "fk_Projekt")
    public Long getFk_Projekt()
    {
        return fk_Projekt;
    }

    public void setFk_Projekt(Long fk_Projekt)
    {
        this.fk_Projekt = fk_Projekt;
    }

    @Column(name = "einsatzortName")
    public String getEinsatzortName()
    {
        return einsatzortName;
    }

    public void setEinsatzortName(String einsatzortName)
    {
        this.einsatzortName = einsatzortName;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_Firmen", insertable = false, updatable = false)
    public Firmen getFirmen()
    {
        return firmen;
    }

    public void setFirmen(Firmen firmen)
    {
        this.firmen = firmen;
    }

}
