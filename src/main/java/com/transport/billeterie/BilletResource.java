package com.transport.billeterie;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.transport.gare.Gare;


public class BilletResource extends ServerResource {

	private String billetId;
	private Trajet trajet;
	
	public BilletResource() 
    {
        super();
    }

    @Override
    protected void doInit() throws ResourceException 
    {

    	billetId = (String) getRequest().getAttributes().get("billetId");
        Gare gareDepart = CentralServer.getGare(billetId.split("-")[0]);
        Gare gareArrivee = CentralServer.getGare(billetId.split("-")[1]);
        trajet = CentralServer.getTrajet(gareDepart, gareArrivee);
    }
    
    @Get("json")
    public Representation getBillet() throws JSONException
    {
        JSONObject current = new JSONObject();
        current.put("billetId", billetId);
        current.put("trajetId", trajet.toString());
        current.put("url", "/billets/" + billetId);
        return new JsonRepresentation(current);
    }
    
    
    
}
