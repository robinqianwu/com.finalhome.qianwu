package de.isag.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Tagesbericht")
public class Tagesbericht implements Serializable
{
    private static final long serialVersionUID = 6730498691485616556L;

    private Long              pk_Tagesbericht;
    private Date              Datum;
    private Long              fk_Einsatzort;
    private Long              fk_Ansprechpartner;
    private Long              fk_TBStatus;
    private Long              fk_Projekt;

    public Tagesbericht()
    {

    }

    public Tagesbericht(Long pk_Tagesbericht, Date Datum, Long fk_Einsatzort, Long fk_Ansprechpartner, Long fk_TBStatus, Long fk_Projekt)
    {
        this.pk_Tagesbericht = pk_Tagesbericht;
        this.Datum = Datum;
        this.fk_Einsatzort = fk_Einsatzort;
        this.fk_Ansprechpartner = fk_Ansprechpartner;
        this.fk_TBStatus = fk_TBStatus;
        this.fk_Projekt = fk_Projekt;
    }

    public Long getPk_Tagesbericht()
    {
        return pk_Tagesbericht;
    }

    public void setPk_Tagesbericht(Long pk_Tagesbericht)
    {
        this.pk_Tagesbericht = pk_Tagesbericht;
    }

    public Date getDatum()
    {
        return Datum;
    }

    public void setDatum(Date datum)
    {
        Datum = datum;
    }

    public Long getFk_Einsatzort()
    {
        return fk_Einsatzort;
    }

    public void setFk_Einsatzort(Long fk_Einsatzort)
    {
        this.fk_Einsatzort = fk_Einsatzort;
    }

    public Long getFk_Ansprechpartner()
    {
        return fk_Ansprechpartner;
    }

    public void setFk_Ansprechpartner(Long fk_Ansprechpartner)
    {
        this.fk_Ansprechpartner = fk_Ansprechpartner;
    }

    public Long getFk_TBStatus()
    {
        return fk_TBStatus;
    }

    public void setFk_TBStatus(Long fk_TBStatus)
    {
        this.fk_TBStatus = fk_TBStatus;
    }

    public Long getFk_Projekt()
    {
        return fk_Projekt;
    }

    public void setFk_Projekt(Long fk_Projekt)
    {
        this.fk_Projekt = fk_Projekt;
    }

}
