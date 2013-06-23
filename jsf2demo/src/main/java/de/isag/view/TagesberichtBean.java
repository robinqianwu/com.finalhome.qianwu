package de.isag.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

@ManagedBean
@SessionScoped
public class TagesberichtBean implements Serializable
{
    private static final long       serialVersionUID = -9112425584264761337L;

    private Date                    tbDatum;
    private String                  tbAuftragsnummer;
    private static List<SelectItem> tbAuftragsnummerItems;

    static
    {
        if (tbAuftragsnummerItems == null)
        {
            tbAuftragsnummerItems = new ArrayList<SelectItem>();
            SelectItem si = new SelectItem("Auftragsnummer1", "Auftragesnummer A");
            SelectItem si2 = new SelectItem("Auftragsnummer2", "Auftragesnummer B");
            tbAuftragsnummerItems.add(si);
            tbAuftragsnummerItems.add(si2);
        }
    }

    public TagesberichtBean()
    {}

    public Date getTbDatum()
    {
        return tbDatum;
    }

    public void setTbDatum(Date tbDatum)
    {
        this.tbDatum = tbDatum;
    }

    public String getTbAuftragsnummer()
    {
        return tbAuftragsnummer;
    }

    public void setTbAuftragsnummer(String tbAuftragsnummer)
    {
        this.tbAuftragsnummer = tbAuftragsnummer;
    }

    public List<SelectItem> getTbAuftragsnummerItems()
    {
        return tbAuftragsnummerItems;
    }

    public void setTbAuftragsnummerItems(List<SelectItem> tbAuftragsnummerItems)
    {
        TagesberichtBean.tbAuftragsnummerItems = tbAuftragsnummerItems;
    }
}
