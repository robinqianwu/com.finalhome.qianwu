package de.isag.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Auftrag")
public class Auftrag implements Serializable
{
    private static final long serialVersionUID = 6730498691485616556L;

    private Long              pk_Auftrag;
    private Long              fk_Angebot;
    private Long              fk_Firmen;
    private String            auftragsbezeichnung;

    public Auftrag()
    {

    }

    public Auftrag(Long pk_Auftrag, Long fk_Angebot, Long fk_Firmen, String auftragsbezeichnung)
    {
        this.pk_Auftrag = pk_Auftrag;
        this.fk_Angebot = fk_Angebot;
        this.fk_Firmen = fk_Firmen;
        this.auftragsbezeichnung = auftragsbezeichnung;
    }

    public Long getPk_Auftrag()
    {
        return pk_Auftrag;
    }

    public void setPk_Auftrag(Long pk_Auftrag)
    {
        this.pk_Auftrag = pk_Auftrag;
    }

    public Long getFk_Angebot()
    {
        return fk_Angebot;
    }

    public void setFk_Angebot(Long fk_Angebot)
    {
        this.fk_Angebot = fk_Angebot;
    }

    public Long getFk_Firmen()
    {
        return fk_Firmen;
    }

    public void setFk_Firmen(Long fk_Firmen)
    {
        this.fk_Firmen = fk_Firmen;
    }

    public String getAuftragsbezeichnung()
    {
        return auftragsbezeichnung;
    }

    public void setAuftragsbezeichnung(String auftragsbezeichnung)
    {
        this.auftragsbezeichnung = auftragsbezeichnung;
    }

}
