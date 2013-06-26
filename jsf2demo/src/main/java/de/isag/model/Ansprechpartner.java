package de.isag.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Ansprechpartner")
public class Ansprechpartner implements Serializable
{

    private static final long serialVersionUID = 6730498691485616556L;

    private Long              pk_Ansprechpartner;
    private Long              fk_Mitarbeiter;
    private Long              fk_Projekt;
    private Long              fk_AnsprechpartnerStatus;

    public Ansprechpartner()
    {

    }

    public Ansprechpartner(Long pk_Ansprechpartner, Long fk_Mitarbeiter, Long fk_Projekt, Long fk_AnsprechpartnerStatus)
    {
        this.pk_Ansprechpartner = pk_Ansprechpartner;
        this.fk_Mitarbeiter = fk_Mitarbeiter;
        this.fk_Projekt = fk_Projekt;
        this.fk_AnsprechpartnerStatus = fk_AnsprechpartnerStatus;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_Ansprechpartner")
    public Long getPk_Ansprechpartner()
    {
        return pk_Ansprechpartner;
    }

    public void setPk_Ansprechpartner(Long pk_Ansprechpartner)
    {
        this.pk_Ansprechpartner = pk_Ansprechpartner;
    }

    @Column(name = "fk_Mitarbeiter")
    public Long getFk_Mitarbeiter()
    {
        return fk_Mitarbeiter;
    }

    public void setFk_Mitarbeiter(Long fk_Mitarbeiter)
    {
        this.fk_Mitarbeiter = fk_Mitarbeiter;
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

    @Column(name = "fk_AnsprechpartnerStatus")
    public Long getFk_AnsprechpartnerStatus()
    {
        return fk_AnsprechpartnerStatus;
    }

    public void setFk_AnsprechpartnerStatus(Long fk_AnsprechpartnerStatus)
    {
        this.fk_AnsprechpartnerStatus = fk_AnsprechpartnerStatus;
    }
}
