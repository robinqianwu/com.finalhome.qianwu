package de.isag.view;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import de.isag.component.jpa.IsagDemoImp;
import de.isag.model.Personen;

@ManagedBean
@SessionScoped
public class PersonBean implements Serializable
{
    private static final long serialVersionUID = -364946342674751722L;

    private List<Personen>    personenList;
    private IsagDemoImp       isag;

    public IsagDemoImp getIsag()
    {
        if (isag == null)
        {
            isag = new IsagDemoImp();
        }
        return isag;
    }

    public void setIsag(IsagDemoImp isag)
    {
        this.isag = isag;
    }

    public List<Personen> getPersonenList()
    {
        isag = getIsag();
        personenList = isag.getPersons();
        return personenList;
    }

    public void setPersonenList(List<Personen> personenList)
    {
        this.personenList = personenList;
    }

    public void savePersonenList()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        PersonTableBean ptb = (PersonTableBean) getObjectByValueExpression(context, "#{personTableBean}", PersonTableBean.class);

        if (ptb != null)
        {
            isag = getIsag();
            List<Personen> list = ptb.getPersonenInputList();
            for ( Iterator<Personen> it = list.iterator(); it.hasNext();)
            {
                Personen p = it.next();
                isag.addPerson(p);
            }
        }
    }

    public static Object getObjectByValueExpression(FacesContext context, String valueExpression, Class<?> targetClass)
    {
        ELContext elContext = context.getELContext();
        return context.getApplication().getExpressionFactory().createValueExpression(elContext, valueExpression, targetClass)
                .getValue(elContext);
    }
}
