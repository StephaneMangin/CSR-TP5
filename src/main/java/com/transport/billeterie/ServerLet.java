package com.transport.billeterie;

import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;


public class ServerLet extends Application 
{

    public ServerLet(Context context) 
    {
        super(context);
    }

    @Override
    public Restlet createInboundRoot() 
    {
        Router router = new Router(getContext());
        router.attach("/trajets", TrajetsResources.class);
        router.attach("/trajets/{trajetId}", TrajetResources.class);
        return router;  
    }

}
