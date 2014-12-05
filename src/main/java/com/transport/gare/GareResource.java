package com.transport.gare;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.transport.billeterie.CentralServer;
import com.transport.gare.Gare;


public class GareResource extends ServerResource {
	
	private Gare gare;
	
	public GareResource() 
    {
        super();
    }

    @Override
    protected void doInit() throws ResourceException 
    {
    	String gareID = (String) getRequest().getAttributes().get("gareID");
        gare = CentralServer.getGare(gareID);
    }
    
    @Get("json")
    public Representation getGare() throws JSONException
    {
        JSONObject current = new JSONObject();
        current.put("gareId", gare.getName());
        current.put("voyageurs_nb", gare.getNbVoyageurs());
        current.put("url", "/gares/" + gare.getName());
        return new JsonRepresentation(current);
        
    }
    
    
    
}
