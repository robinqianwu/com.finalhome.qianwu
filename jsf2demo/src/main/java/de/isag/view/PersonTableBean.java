package de.isag.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.isag.model.Personen;

@ManagedBean
@SessionScoped
public class PersonTableBean implements Serializable
{
    private static final long serialVersionUID = -8729798601613552153L;

    private List<Personen>    personenInputList;

    public PersonTableBean()
    {}

    public List<Personen> getPersonenInputList()
    {
        if (personenInputList == null)
        {
            personenInputList = new ArrayList<Personen>(6);

            Personen personen1 = new Personen("k1", "v1", "n1");
            Personen personen2 = new Personen("k2", "v2", "n2");
            Personen personen3 = new Personen("k3", "v3", "n3");
            Personen personen4 = new Personen("k4", "v4", "n4");
            Personen personen5 = new Personen("k5", "v5", "n5");
            Personen personen6 = new Personen("k6", "v6", "n6");

            personenInputList.add(personen1);
            personenInputList.add(personen2);
            personenInputList.add(personen3);
            personenInputList.add(personen4);
            personenInputList.add(personen5);
            personenInputList.add(personen6);
        }
        return personenInputList;
    }
}
