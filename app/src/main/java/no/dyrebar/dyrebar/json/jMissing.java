package no.dyrebar.dyrebar.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import no.dyrebar.dyrebar.classes.Missing;

public class jMissing
{
    public ArrayList<Missing> decodeArray(String j)
    {
        ArrayList<Missing> missings = new ArrayList<>();

        try
        {
            JSONObject o = new JSONObject(j);
            JSONArray a = o.getJSONArray("data");
            for (int i = 0; i < a.length(); i++)
            {
                Missing m = decode(a.getJSONObject(i).toString());
                if (m != null)
                    missings.add(m);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return missings;
    }

    public Missing decode(String j) throws JSONException
    {
        JSONObject o = new JSONObject(j);
        return new Missing(
            o.getInt("missingId"),
            o.getDouble("lat"),
            o.getDouble("long"),
            o.getLong("timeDate"),

            o.getInt("animalId"),
            o.getInt("userId"),
            o.getString("image"),
            o.getString("idTag"),
            o.getString("name"),
            o.getInt("animalType"),
            o.getString("animalTypeExtras"),
            o.getInt("sex"),
            o.getInt("sterilized"),
            o.getString("color"),
            o.getInt("furLength"),
            o.getInt("furPattern"),
            o.getString("description")
        );
    }
}
