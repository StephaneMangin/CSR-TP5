package com.transport.gare;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.transport.billeterie.CentralServer;
import com.transport.billeterie.Trajet;

/**
 * Resource exposant les gares.
 * @author msimonin
 *
 */
public class GaresResource extends ServerResource 
{
    /**
     * Constructor.
     * Call for every single user request.
     */
    public GaresResource()
    {
        super();
    }

    /**
     * 
     * Retourne la liste de tous les gares.
     *
     * @return la representation JSON de toutes les gares.
     * @throws JSONException
     */
    @Get("json")
    public Representation getGares() throws JSONException 
    {
        Collection<JSONObject> jsonTrajets = new ArrayList<JSONObject>();
        
        while (CentralServer.getGares().hasNext()) {
            Gare gare = CentralServer.getGares().next();
            JSONObject current = new JSONObject();
            current.put("gareId", gare.toString());
            current.put("url", "/gares/" + gare.toString());
            jsonTrajets.add(current);
        }
        JSONArray jsonArray = new JSONArray(jsonTrajets);
        return new JsonRepresentation(jsonArray);
    }
 
}