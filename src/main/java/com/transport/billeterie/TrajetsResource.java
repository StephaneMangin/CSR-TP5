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
 * Resource exposant les trajets.
 * @author msimonin
 *
 */
public class TrajetsResource extends ServerResource 
{
    /**
     * Constructor.
     * Call for every single user request.
     */
    public TrajetsResource()
    {
        super();
    }

    /**
     * 
     * Retourne la liste de tous les trajets.
     *
     * @return  la representation JSON de tous les trajets.
     * @throws JSONException
     */
    @Get("json")
    public Representation getTrajets() throws JSONException 
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