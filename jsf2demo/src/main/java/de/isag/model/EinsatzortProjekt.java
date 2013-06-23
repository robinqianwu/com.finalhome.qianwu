package de.isag.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "EinsatzortProjekt")
public class EinsatzortProjekt implements Serializable
{
    private static final long serialVersionUID = 6730498691485616556L;

    private Long              pk_EinsatzortProjekt;
    private Long              fk_Firmen;
    private Long              fk_Projekt;
    private String            einsatzortName;

    public EinsatzortProjekt()
    {

    }

    public EinsatzortProjekt(Long pk_EinsatzortProjekt, Long fk_Firmen, Long fk_Projekt, String einsatzortName)
    {
        this.pk_EinsatzortProjekt = pk_EinsatzortProjekt;
        this.fk_Firmen = fk_Firmen;
        this.fk_Projekt = fk_Projekt;
        this.einsatzortName = einsatzortName;
    }

    public Long getPk_EinsatzortProjekt()
    {
        return pk_EinsatzortProjekt;
    }

    public void setPk_EinsatzortProjekt(Long pk_EinsatzortProjekt)
    {
        this.pk_EinsatzortProjekt = pk_EinsatzortProjekt;
    }

    public Long getFk_Firmen()
    {
        return fk_Firmen;
    }

    public void setFk_Firmen(Long fk_Firmen)
    {
        this.fk_Firmen = fk_Firmen;
    }

    public Long getFk_Projekt()
    {
        return fk_Projekt;
    }

    public void setFk_Projekt(Long fk_Projekt)
    {
        this.fk_Projekt = fk_Projekt;
    }

    public String getEinsatzortName()
    {
        return einsatzortName;
    }

    public void setEinsatzortName(String einsatzortName)
    {
        this.einsatzortName = einsatzortName;
    }

}
