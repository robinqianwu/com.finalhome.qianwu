package de.isag.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import de.isag.component.jpa.IsagDemoImp;
import de.isag.model.Auftrag;
import de.isag.model.EinsatzortProjekt;
import de.isag.model.Firmen;

@ManagedBean
@SessionScoped
public class TagesberichtBean implements Serializable
{
    private static final long       serialVersionUID     = -9112425584264761337L;
    private static final Logger     logger               = Logger.getLogger(TagesberichtBean.class);

    private static IsagDemoImp      isag;
    private Date                    tbDatum;

    private Auftrag                 tbAuftrag;
    private Long                    tbAuftragId;
    private static List<SelectItem> tbAuftragsnummerItems;

    private Firmen                  tbFirmen;
    private Long                    tbFirmenId;
    private List<SelectItem>        tbFirmenItems        = new ArrayList<SelectItem>();

    private EinsatzortProjekt       tbEinsatzortProjekt;
    private Long                    tbEinsatzortProjektId;
    private List<SelectItem>        tbEinsatzortProjekts = new ArrayList<SelectItem>();

    private List<String[]>          identificationsMerkmalen;

    static
    {
        if (tbAuftragsnummerItems == null)
        {
            tbAuftragsnummerItems = new ArrayList<SelectItem>();
            List<Auftrag> auftrags = getIsag().getAuftrags();
            for ( Iterator<Auftrag> it = auftrags.iterator(); it.hasNext();)
            {
                Auftrag at = it.next();
                SelectItem si = new SelectItem(at.getPk_Auftrag(), at.getAuftragsbezeichnung());
                tbAuftragsnummerItems.add(si);
            }
            //            SelectItem si = new SelectItem(1, "Auftragesnummer A");
            //            SelectItem si2 = new SelectItem(2, "Auftragesnummer B");
            //            tbAuftragsnummerItems.add(si);
            //            tbAuftragsnummerItems.add(si2);
        }
    }

    public TagesberichtBean()
    {}

    public static IsagDemoImp getIsag()
    {
        if (isag == null)
        {
            isag = new IsagDemoImp();
        }
        return isag;
    }

    public void setIsag(IsagDemoImp isag)
    {
        TagesberichtBean.isag = isag;
    }

    public void tbLoadFirmen(AjaxBehaviorEvent e)
    {
        logger.info("current trigged Event is: " + e.getBehavior());
        if (tbAuftragId != null && !tbAuftragId.equals(0L))
        {
            // TODO:: should be change to findbyid
            tbFirmenItems.clear();
            tbEinsatzortProjekts.clear();

            for ( Auftrag auftrag : getIsag().getAuftrags())
            {
                if (auftrag.getPk_Auftrag().equals(tbAuftragId))
                {
                    tbAuftrag = auftrag;
                    break;
                }
            }

            if (tbAuftrag != null)
            {
                Firmen f = tbAuftrag.getFirmen();

                SelectItem si = new SelectItem(f.getPk_Firmen(), f.getKurzZeichen());
                tbFirmenItems.add(si);

                Set<EinsatzortProjekt> eps = f.getEinsatzortProjekts();
                for ( Iterator<EinsatzortProjekt> it = eps.iterator(); it.hasNext();)
                {
                    EinsatzortProjekt ep = it.next();
                    SelectItem einsatzSi = new SelectItem(ep.getPk_EinsatzortProjekt(), ep.getEinsatzortName());
                    tbEinsatzortProjekts.add(einsatzSi);
                }
            }
        }
        else
        {
            tbAuftrag = null;
            tbFirmenItems.clear();
            tbEinsatzortProjekts.clear();
        }
    }

    public Date getTbDatum()
    {
        return tbDatum;
    }

    public void setTbDatum(Date tbDatum)
    {
        this.tbDatum = tbDatum;
    }

    public Auftrag getTbAuftrag()
    {
        return tbAuftrag;
    }

    public void setTbAuftrag(Auftrag tbAuftrag)
    {
        this.tbAuftrag = tbAuftrag;
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

    public Long getTbAuftragId()
    {
        return tbAuftragId;
    }

    public void setTbAuftragId(Long tbAuftragId)
    {
        this.tbAuftragId = tbAuftragId;
    }

    public Firmen getTbFirmen()
    {
        return tbFirmen;
    }

    public void setTbFirmen(Firmen tbFirmen)
    {
        this.tbFirmen = tbFirmen;
    }

    public Long getTbFirmenId()
    {
        return tbFirmenId;
    }

    public void setTbFirmenId(Long tbFirmenId)
    {
        this.tbFirmenId = tbFirmenId;
    }

    public List<SelectItem> getTbFirmenItems()
    {
        return tbFirmenItems;
    }

    public void setTbFirmenItems(List<SelectItem> tbFirmenItems)
    {
        this.tbFirmenItems = tbFirmenItems;
    }

    public List<SelectItem> getTbEinsatzortProjekts()
    {
        return tbEinsatzortProjekts;
    }

    public void setTbEinsatzortProjekts(List<SelectItem> tbEinsatzortProjekts)
    {
        this.tbEinsatzortProjekts = tbEinsatzortProjekts;
    }

    public EinsatzortProjekt getTbEinsatzortProjekt()
    {
        return tbEinsatzortProjekt;
    }

    public void setTbEinsatzortProjekt(EinsatzortProjekt tbEinsatzortProjekt)
    {
        this.tbEinsatzortProjekt = tbEinsatzortProjekt;
    }

    public Long getTbEinsatzortProjektId()
    {
        return tbEinsatzortProjektId;
    }

    public void setTbEinsatzortProjektId(Long tbEinsatzortProjektId)
    {
        this.tbEinsatzortProjektId = tbEinsatzortProjektId;
    }
}
