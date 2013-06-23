package de.jsf.service.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextListener implements ApplicationContextAware
{
    private final String contextId;

    public SpringContextListener(String contextId)
    {
        this.contextId = contextId;
    }

    @Override
    public void setApplicationContext(ApplicationContext appContextToBeRegistered) throws BeansException
    {
        SpringAccess.registerApplicationContext(contextId, appContextToBeRegistered);
    }
}
