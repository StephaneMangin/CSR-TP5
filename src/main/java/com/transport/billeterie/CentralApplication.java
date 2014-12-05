package com.transport.billeterie;

import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import com.transport.gare.GareResource;
import com.transport.gare.GaresResource;


/**
 * Configure les routes de l'application REST
 * 
 * @author blacknight
 *
 */
public class CentralApplication extends Application 
{

    public CentralApplication(Context context) 
    {
        super(context);
    }

    @Override
    public Restlet createInboundRoot() 
    {
        Router router = new Router(getContext());
        router.attach("/trajets", TrajetsResource.class);
        router.attach("/trajets/{trajetId}", TrajetResource.class);
        router.attach("/gares", GaresResource.class);
        router.attach("/gares/{gareId}", GareResource.class);
        router.attach("/billets", BilletsResource.class);
        router.attach("/billets/{billetId}", BilletResource.class);
        return router;  
    }

}
