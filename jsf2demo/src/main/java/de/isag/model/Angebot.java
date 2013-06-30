package de.isag.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "angebot")
public class Angebot implements Serializable
{
    private static final long serialVersionUID = -3263944166747560110L;

    private Long              pk_Angebot;
    private Long              fk_Firmen;
    private Long              fk_AngebotStatus;

    public Angebot()
    {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_Angebot")
    public Long getPk_Angebot()
    {
        return pk_Angebot;
    }

    public void setPk_Angebot(Long pk_Angebot)
    {
        this.pk_Angebot = pk_Angebot;
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

    @Column(name = "fk_AngebotStatus")
    public Long getFk_AngebotStatus()
    {
        return fk_AngebotStatus;
    }

    public void setFk_AngebotStatus(Long fk_AngebotStatus)
    {
        this.fk_AngebotStatus = fk_AngebotStatus;
    }
}
