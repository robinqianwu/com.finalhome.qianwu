package de.isag.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

// @Entity
// @Table(name = "TagesberichtEintragung")
public class TagesberichtEintragung implements Serializable
{

    private static final long serialVersionUID = 6730498691485616556L;

    private Long              pk_TagesberichtEintragung;
    private Long              fk_Tagesbericht;
    private Long              fk_ProjektMitarbeiter;
    private Date              beginn;
    private Date              ende;
    private Date              pause;

    public TagesberichtEintragung()
    {

    }

    public TagesberichtEintragung(Long pk_TagesberichtEintragung,
                                  Long fk_Tagesbericht,
                                  Long fk_ProjektMitarbeiter,
                                  Date beginn,
                                  Date ende,
                                  Date pause)
    {
        this.pk_TagesberichtEintragung = pk_TagesberichtEintragung;
        this.fk_Tagesbericht = fk_Tagesbericht;
        this.fk_ProjektMitarbeiter = fk_ProjektMitarbeiter;
        this.beginn = beginn;
        this.ende = ende;
        this.pause = pause;
    }

    @Id
    public Long getPk_TagesberichtEintragung()
    {
        return pk_TagesberichtEintragung;
    }

    public void setPk_TagesberichtEintragung(Long pk_TagesberichtEintragung)
    {
        this.pk_TagesberichtEintragung = pk_TagesberichtEintragung;
    }

    public Long getFk_Tagesbericht()
    {
        return fk_Tagesbericht;
    }

    public void setFk_Tagesbericht(Long fk_Tagesbericht)
    {
        this.fk_Tagesbericht = fk_Tagesbericht;
    }

    public Long getFk_ProjektMitarbeiter()
    {
        return fk_ProjektMitarbeiter;
    }

    public void setFk_ProjektMitarbeiter(Long fk_ProjektMitarbeiter)
    {
        this.fk_ProjektMitarbeiter = fk_ProjektMitarbeiter;
    }

    public Date getBeginn()
    {
        return beginn;
    }

    public void setBeginn(Date beginn)
    {
        this.beginn = beginn;
    }

    public Date getEnde()
    {
        return ende;
    }

    public void setEnde(Date ende)
    {
        this.ende = ende;
    }

    public Date getPause()
    {
        return pause;
    }

    public void setPause(Date pause)
    {
        this.pause = pause;
    }

}
