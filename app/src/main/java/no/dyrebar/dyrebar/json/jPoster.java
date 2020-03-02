package no.dyrebar.dyrebar.json;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import no.dyrebar.dyrebar.classes.Found;
import no.dyrebar.dyrebar.classes.Missing;

public class jPoster
{
    public ArrayList<Object> decode(String j)
    {
        ArrayList<Object> items = new ArrayList<>();
        try
        {
            JSONObject o = new JSONObject(j);
            ArrayList<Missing> missings = new jMissing().decodeArray(o.getJSONObject("missing").toString());
            items.addAll(missings);

            ArrayList<Found> founds = new jFound().decodeArray(o.getJSONObject("found").toString());
            items.addAll(founds);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return items;
    }

}
