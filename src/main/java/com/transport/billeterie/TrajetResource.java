package com.transport.billeterie;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.transport.gare.Gare;


public class TrajetResource extends ServerResource {

	private String trajetId;
	private Gare gareDepart;
	private Gare gareArrivee;
	
	public TrajetResource() 
    {
        super();
    }

    @Override
    protected void doInit() throws ResourceException 
    {

        trajetId = (String) getRequest().getAttributes().get("trajetId");
        gareDepart = CentralServer.getGare(trajetId.split("-")[0]);
        gareArrivee = CentralServer.getGare(trajetId.split("-")[1]);
    }
    
    @Get("json")
    public Representation getTrajet() throws JSONException
    {
        JSONObject current = new JSONObject();
        current.put("trajetId", trajetId);
        current.put("gareDepart", gareDepart.toString());
        current.put("gareArrivee", gareArrivee.toString());
        current.put("url", "/trajets/" + trajetId);
        return new JsonRepresentation(current);
        
    }
    
    
    
}
