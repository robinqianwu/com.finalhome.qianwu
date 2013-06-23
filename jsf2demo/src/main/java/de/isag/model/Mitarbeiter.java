package de.isag.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Mitarbeiter")
public class Mitarbeiter implements Serializable
{

    private static final long serialVersionUID = 6730498691485616556L;

    private Long              pk_Mitarbeiter;
    private Long              fk_Personen;
    private Long              fk_Firmen;
    private Long              fk_BeschaeftigungsStatus;

    public Mitarbeiter()
    {

    }

    public Mitarbeiter(Long pk_Mitarbeiter, Long fk_Personen, Long fk_Firmen, Long fk_BeschaeftigungsStatus)
    {
        this.pk_Mitarbeiter = pk_Mitarbeiter;
        this.fk_Personen = fk_Personen;
        this.fk_Firmen = fk_Firmen;
        this.fk_BeschaeftigungsStatus = fk_BeschaeftigungsStatus;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_Mitarbeiter")
    public Long getPk_Mitarbeiter()
    {
        return pk_Mitarbeiter;
    }

    public void setPk_Mitarbeiter(long pk_Mitarbeiter)
    {
        this.pk_Mitarbeiter = pk_Mitarbeiter;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fk_Personen")
    public Long getFk_Personen()
    {
        return fk_Personen;
    }

    public void setFk_Personen(Long fk_Personen)
    {
        this.fk_Personen = fk_Personen;
    }

    public Long getFk_Firmen()
    {
        return fk_Firmen;
    }

    public void setFk_Firmen(Long fk_Firmen)
    {
        this.fk_Firmen = fk_Firmen;
    }

    public Long getFk_BeschaeftigungsStatus()
    {
        return fk_BeschaeftigungsStatus;
    }

    public void setFk_BeschaeftigungsStatus(Long fk_BeschaetigungsStatus)
    {
        fk_BeschaeftigungsStatus = fk_BeschaetigungsStatus;
    }

}
