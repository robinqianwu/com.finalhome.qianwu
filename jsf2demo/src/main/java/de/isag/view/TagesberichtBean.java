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

    private List<String[]>          identificationsMerkmalen;

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

    public List<String[]> getIdentificationsMerkmalen()
    {
        if (identificationsMerkmalen == null)
        {
            identificationsMerkmalen = new ArrayList<String[]>();
            String[] item1 = new String[]{"1", "2", "3"};
            String[] item2 = new String[]{"1", "2", "3"};
            String[] item3 = new String[]{"1", "2", "3"};
            identificationsMerkmalen.add(item1);
            identificationsMerkmalen.add(item2);
            identificationsMerkmalen.add(item3);
        }
        return identificationsMerkmalen;
    }

    public void setIdentificationsMerkmalen(List<String[]> identificationsMerkmalen)
    {
        this.identificationsMerkmalen = identificationsMerkmalen;
    }
}
