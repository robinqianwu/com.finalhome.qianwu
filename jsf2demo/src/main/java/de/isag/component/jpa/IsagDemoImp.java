package de.isag.component.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import de.isag.model.Personen;

public class IsagDemoImp implements IsagDemoInterface
{

    EntityManager em;

    public EntityManager getEm()
    {
        if (em == null)
        {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("isag");
            em = emf.createEntityManager();
        }
        return em;
    }

    public void setEm(EntityManager em)
    {
        this.em = em;
    }

    public IsagDemoImp()
    {
        em = getEm();
    }

    @Override
    public void addPerson(Personen person)
    {
        em.getTransaction().begin();
        em.persist(person);
        em.getTransaction().commit();
    }

    @Override
    public List<Personen> getPersons()
    {
        @SuppressWarnings("unchecked")
        List<Personen> persons = em.createQuery("from Personen").getResultList();
        return persons;
    }

}
