package no.dyrebar.dyrebar.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import no.dyrebar.dyrebar.classes.Found;

public class jFound
{


    public ArrayList<Found> decodeArray(String j)
    {
        ArrayList<Found> founds = new ArrayList<>();

        try
        {
            JSONObject o = new JSONObject(j);
            JSONArray a = o.getJSONArray("data");
            for (int i = 0; i < a.length(); i++)
            {
                Found f = decodeItems(a.getJSONObject(i).toString());
                if (f != null)
                    founds.add(f);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return founds;
    }

    public Found decodeItems(String j) throws JSONException
    {
        JSONObject o = new JSONObject(j);
        return new Found(
                o.getInt("foundId"),
                o.getDouble("lat"),
                o.getDouble("lng"),
                o.getLong("timeDate"),
                (o.has("name") ? o.getString("name") : null),
                o.getString("image"),

                o.getInt("animalType"),
                (!o.isNull("animalTypeExtras") ? o.getString("animalTypeExtras") : null),
                o.getString("color"),
                (!o.isNull("area") ? o.getString("area") : null)
        );
    }

    public Found decodeItem(String j) throws JSONException
    {
        JSONObject o = new JSONObject(j);
        return new Found(
                o.getInt("foundId"),
                o.getDouble("lat"),
                o.getDouble("lng"),
                o.getLong("timeDate"),

                o.getInt("animalId"),
                o.getInt("userId"),
                o.getString("image"),
                o.getString("idTag"),
                o.getString("name"),
                o.getInt("animalType"),
                o.getString("animalTypeExtras"),
                o.getInt("sex"),
                (o.has("sterilized") ? o.getInt("sterilized") : 2),
                o.getString("color"),
                o.getInt("furLength"),
                o.getInt("furPattern"),
                o.getString("description")
        );
    }
}
