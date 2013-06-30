package de.isag.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "firmen")
public class Firmen implements Serializable
{
    private static final long serialVersionUID = -2328838058260665359L;

    private Long              pk_Firmen;
    private String            firmenName;
    private String            kurzZeichen;

    public Firmen()
    {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_Firmen", nullable = false)
    public Long getPk_Firmen()
    {
        return pk_Firmen;
    }

    public void setPk_Firmen(Long pk_Firmen)
    {
        this.pk_Firmen = pk_Firmen;
    }

    @Column(name = "firmenName")
    public String getFirmenName()
    {
        return firmenName;
    }

    public void setFirmenName(String firmenName)
    {
        this.firmenName = firmenName;
    }

    @Column(name = "kurzZeichen")
    public String getKurzZeichen()
    {
        return kurzZeichen;
    }

    public void setKurzZeichen(String kurzZeichen)
    {
        this.kurzZeichen = kurzZeichen;
    }
}
