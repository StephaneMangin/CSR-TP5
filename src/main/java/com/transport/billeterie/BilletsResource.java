package com.transport.billeterie;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * Resource exposant les utilisateurs.
 * @author msimonin
 *
 */
public class BilletsResource extends ServerResource 
{
    /**
     * Constructor.
     * Call for every single user request.
     */
    public BilletsResource()
    {
        super();
    }

    /**
     * 
     * Retourne la liste de tous les utilisateurs crée.
     *
     * @return  la representation JSON de tous les utilisateurs.
     * @throws JSONException
     */
    @Get("json")
    public Representation getBillets() throws JSONException 
    {
        Collection<JSONObject> jsonTrajets = new ArrayList<JSONObject>();
        
        while (CentralServer.getTrajets().hasNext()) {
            Trajet trajet = CentralServer.getTrajets().next();
            JSONObject current = new JSONObject();
            current.put("trajetId", trajet.gareDepart().getName() + "-" + trajet.gareArrivee().getName());
            current.put("url", "/trajets/" + trajet.gareDepart().getName() + "-" + trajet.gareArrivee().getName());
            jsonTrajets.add(current);
        }
        JSONArray jsonArray = new JSONArray(jsonTrajets);
        return new JsonRepresentation(jsonArray);
    }
 
}