package de.isag.model;

import java.io.Serializable;

import javax.persistence.Entity;
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

    /**
     * @return the pk_Ansprechpartner
     */
    @Id
    public Long getPk_Ansprechpartner()
    {
        return pk_Ansprechpartner;
    }

    /**
     * @param pk_Ansprechpartner
     *            the pk_Ansprechpartner to set
     */
    public void setPk_Ansprechpartner(Long pk_Ansprechpartner)
    {
        this.pk_Ansprechpartner = pk_Ansprechpartner;
    }

    /**
     * @return the fk_Mitarbeiter
     */
    public Long getFk_Mitarbeiter()
    {
        return fk_Mitarbeiter;
    }

    /**
     * @param fk_Mitarbeiter
     *            the fk_Mitarbeiter to set
     */
    public void setFk_Mitarbeiter(Long fk_Mitarbeiter)
    {
        this.fk_Mitarbeiter = fk_Mitarbeiter;
    }

    /**
     * @return the fk_Projekt
     */
    public Long getFk_Projekt()
    {
        return fk_Projekt;
    }

    /**
     * @param fk_Projekt
     *            the fk_Projekt to set
     */
    public void setFk_Projekt(Long fk_Projekt)
    {
        this.fk_Projekt = fk_Projekt;
    }

    /**
     * @return the fk_AnsprechpartnerStatus
     */
    public Long getFk_AnsprechpartnerStatus()
    {
        return fk_AnsprechpartnerStatus;
    }

    /**
     * @param fk_AnsprechpartnerStatus
     *            the fk_AnsprechpartnerStatus to set
     */
    public void setFk_AnsprechpartnerStatus(Long fk_AnsprechpartnerStatus)
    {
        this.fk_AnsprechpartnerStatus = fk_AnsprechpartnerStatus;
    }
}
