package com.transport.billeterie;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.transport.gare.Gare;
import com.transport.gare.Trajet;


public class TrajetResources extends ServerResource {

	String trajetId;
	Gare gareDepart;
	Gare gareArrivee;
	
	public TrajetResources() 
    {
        super();
    }

    @Override
    protected void doInit() throws ResourceException 
    {

        trajetId = (String) getRequest().getAttributes().get("trajetId");
        System.out.println(gareDepart.toString());
        System.out.println(gareArrivee.toString());
        gareDepart = CentralServer.getGare(trajetId.split("-")[0]);
        gareArrivee = CentralServer.getGare(trajetId.split("-")[1]);
    }
    
    @Get("json")
    public Representation getTrajet() throws JSONException
    {
        // On récupère l'id passée dans l'URL
        // On récupère l'utilisateur depuis la base de donnée
        Trajet trajet = CentralServer.getTrajet(gareDepart, gareArrivee);
        
        Collection<JSONObject> jsonBillets = new ArrayList<JSONObject>();
        
        for (Billet billet: CentralServer.billets) {
            JSONObject current = new JSONObject();
            current.put("billetId", billet.getId());
            current.put("url", "/trajets/" + trajetId + "/" + billet.getId());
            jsonBillets.add(current);
            
        }
        JSONArray jsonArray = new JSONArray(jsonBillets);
        return new JsonRepresentation(jsonArray);
        
    }
    
    
    
}
