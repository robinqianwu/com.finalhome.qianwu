package de.jsf.view.listener;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.log4j.Logger;

public class LifeCycleListener implements PhaseListener
{
    private static final long   serialVersionUID = 5572032379718527326L;

    private static final Logger logger           = Logger.getLogger(LifeCycleListener.class);

    @Override
    public void beforePhase(PhaseEvent event)
    {
        logger.debug("[beforePhase]: " + event.getPhaseId());
    }

    @Override
    public void afterPhase(PhaseEvent event)
    {
        logger.debug("[afterPhase]: " + event.getPhaseId());

        // store this phase's id in a request map -> used to check in BackToHttpListener if model validation passed
        List<PhaseId> phasesInvoked;
        if (event.getFacesContext().getExternalContext().getRequestMap().get("phasesInvoked") == null)
        {
            phasesInvoked = new ArrayList<PhaseId>();
        }
        else
        {
            phasesInvoked = (ArrayList<PhaseId>) event.getFacesContext().getExternalContext().getRequestMap().get("phasesInvoked");
        }
        phasesInvoked.add(event.getPhaseId());
        event.getFacesContext().getExternalContext().getRequestMap().put("phasesInvoked", phasesInvoked);

        if (event.getPhaseId() == PhaseId.RESTORE_VIEW)
        {
            FacesContext fc = event.getFacesContext();
            UIViewRoot view = fc.getViewRoot();
            if (view == null)
            {
                logger.debug("View EXPIRED");
            }
            else
            {
                String viewId = view.getViewId();
            }
        }
    }

    @Override
    public PhaseId getPhaseId()
    {
        return PhaseId.ANY_PHASE;
    }

}