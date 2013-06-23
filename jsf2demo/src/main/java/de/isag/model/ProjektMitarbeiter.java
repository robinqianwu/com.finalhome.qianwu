package de.isag.model;

import java.io.Serializable;

import javax.persistence.Id;

// @Entity
// @Table(name = "ProjektMitarbeiter")
public class ProjektMitarbeiter implements Serializable
{

    private static final long serialVersionUID = 6730498691485616556L;

    private Long              pk_ProjektMitarbeiter;
    private Long              fk_Mitarbeiter;
    private Long              fk_Projket;
    private Long              fk_ProjektMitarbeiterStatus;

    public ProjektMitarbeiter()
    {

    }

    public ProjektMitarbeiter(Long pk_ProjektMitarbeiter, Long fk_Mitarbeiter, Long fk_Projket, Long fk_ProjektMiarbeiterStatus)
    {
        this.pk_ProjektMitarbeiter = pk_ProjektMitarbeiter;
        this.fk_Mitarbeiter = fk_Mitarbeiter;
        this.fk_Projket = fk_Projket;
        fk_ProjektMitarbeiterStatus = fk_ProjektMiarbeiterStatus;
    }

    @Id
    public Long getPk_ProjektMitarbeiter()
    {
        return pk_ProjektMitarbeiter;
    }

    public void setPk_ProjektMitarbeiter(Long pk_ProjektMitarbeiter)
    {
        this.pk_ProjektMitarbeiter = pk_ProjektMitarbeiter;
    }

    public Long getFk_Mitarbeiter()
    {
        return fk_Mitarbeiter;
    }

    public void setFk_Mitarbeiter(Long fk_Mitarbeiter)
    {
        this.fk_Mitarbeiter = fk_Mitarbeiter;
    }

    public Long getFk_Projket()
    {
        return fk_Projket;
    }

    public void setFk_Projket(Long fk_Projket)
    {
        this.fk_Projket = fk_Projket;
    }

    public Long getFk_ProjektMitarbeiterStatus()
    {
        return fk_ProjektMitarbeiterStatus;
    }

    public void setFk_ProjektMitarbeiterStatus(Long fk_ProjektMitarbeiterStatus)
    {
        this.fk_ProjektMitarbeiterStatus = fk_ProjektMitarbeiterStatus;
    }

}
