package de.jsf.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.lucene.search.SortField;
import org.springframework.beans.factory.annotation.Autowired;

import de.jsf.dao.JsfGenericBaseDAO;
import de.jsf.search.support.LazyPreload;
import de.jsf.service.JsfDemoService;

public abstract class JsfDemoServiceImpl implements JsfDemoService
{

    protected JsfGenericBaseDAO dao;

    protected abstract Class getDefaultEntityClass();

    protected abstract String getDefaultEntityAlias();

    protected abstract List<LazyPreload> getDefaultPreloads();

    protected LinkedList<SortField> getDefaultSort()
    {
        return new LinkedList<SortField>();
    }

    @Autowired
    public void setDao(JsfGenericBaseDAO dao)
    {
        this.dao = dao;
    }

}
