package de.isag.model;

import java.io.Serializable;

import javax.persistence.Id;

// @Entity
// @Table(name = "Projekt")
public class Projekt implements Serializable
{
    private static final long serialVersionUID = 6730498691485616556L;

    private Long              pk_Projekt;
    private Long              fk_Auftrag;
    private Long              fk_Projektstatus;
    private Long              fk_Firmen;

    public Projekt()
    {

    }

    public Projekt(Long pk_Projekt, Long fk_Auftrag, Long fk_Projektstatus, Long fk_Firmen)
    {
        this.pk_Projekt = pk_Projekt;
        this.fk_Auftrag = fk_Auftrag;
        this.fk_Projektstatus = fk_Projektstatus;
        this.fk_Firmen = fk_Firmen;
    }

    @Id
    public Long getPk_Projekt()
    {
        return pk_Projekt;
    }

    public void setPk_Projekt(Long pk_Projekt)
    {
        this.pk_Projekt = pk_Projekt;
    }

    public Long getFk_Auftrag()
    {
        return fk_Auftrag;
    }

    public void setFk_Auftrag(Long fk_Auftrag)
    {
        this.fk_Auftrag = fk_Auftrag;
    }

    public Long getFk_Projektstatus()
    {
        return fk_Projektstatus;
    }

    public void setFk_Projektstatus(Long fk_Projektstatus)
    {
        this.fk_Projektstatus = fk_Projektstatus;
    }

    public Long getFk_Firmen()
    {
        return fk_Firmen;
    }

    public void setFk_Firmen(Long fk_Firmen)
    {
        this.fk_Firmen = fk_Firmen;
    }

}
