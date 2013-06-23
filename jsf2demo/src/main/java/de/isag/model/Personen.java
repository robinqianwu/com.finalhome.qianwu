package de.isag.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "personen")
public class Personen implements Serializable
{
    private static final long serialVersionUID = 6730498691485616556L;

    private Long              pk_Person;
    private String            kurzName;
    private String            vorname;
    private String            nachname;

    public Personen()
    {}

    public Personen(String kurzName, String vorname, String nachname)
    {
        this.kurzName = kurzName;
        this.vorname = vorname;
        this.nachname = nachname;
    }

    public void setPk_Personen(Long pk_Person)
    {
        this.pk_Person = pk_Person;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_Person")
    public Long getPk_Personen()
    {
        return pk_Person;
    }

    public void setKurzName(String kurzName)
    {
        this.kurzName = kurzName;
    }

    @Column(name = "kurzName")
    public String getKurzName()
    {
        return kurzName;
    }

    public void setVorname(String vorname)
    {
        this.vorname = vorname;
    }

    @Column(name = "vorname")
    public String getVorname()
    {
        return vorname;
    }

    public void setNachname(String nachname)
    {
        this.nachname = nachname;
    }

    @Column(name = "nachname")
    public String getNachname()
    {
        return nachname;
    }

    @Override
    public String toString()
    {
        return "Person [id=" + pk_Person + ", kurzName=" + kurzName + ", firstName=" + vorname + ", lastName=" + nachname + "]";
    }

}
