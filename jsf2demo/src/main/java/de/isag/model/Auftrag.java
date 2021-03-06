package de.isag.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "auftrag")
public class Auftrag implements Serializable
{
    private static final long serialVersionUID = 6730498691485616556L;

    private Long              pk_Auftrag;
    private Long              fk_Angebot;
    private Long              fk_Firmen;
    private String            auftragsbezeichnung;

    private Angebot           angebot          = new Angebot();

    private Firmen            firmen           = new Firmen();

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_Auftrag")
    public Long getPk_Auftrag()
    {
        return pk_Auftrag;
    }

    public void setPk_Auftrag(Long pk_Auftrag)
    {
        this.pk_Auftrag = pk_Auftrag;
    }

    @Column(name = "fk_Angebot")
    public Long getFk_Angebot()
    {
        return fk_Angebot;
    }

    public void setFk_Angebot(Long fk_Angebot)
    {
        this.fk_Angebot = fk_Angebot;
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

    @Column(name = "auftragsbezeichnung")
    public String getAuftragsbezeichnung()
    {
        return auftragsbezeichnung;
    }

    public void setAuftragsbezeichnung(String auftragsbezeichnung)
    {
        this.auftragsbezeichnung = auftragsbezeichnung;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_Angebot", nullable = false, referencedColumnName = "pk_Angebot", updatable = false, insertable = false)
    public Angebot getAngebot()
    {
        return angebot;
    }

    public void setAngebot(Angebot angebot)
    {
        this.angebot = angebot;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_Firmen", nullable = false, referencedColumnName = "pk_Firmen", updatable = false, insertable = false)
    public Firmen getFirmen()
    {
        return firmen;
    }

    public void setFirmen(Firmen firmen)
    {
        this.firmen = firmen;
    }
}
