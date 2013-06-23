package de.isag.component.jpa;

import java.util.List;

import de.isag.model.Personen;

public interface IsagDemoInterface
{

    void addPerson(Personen person);

    List<Personen> getPersons();
}
