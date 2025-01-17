package no.dyrebar.dyrebar.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import no.dyrebar.dyrebar.classes.Found;

public class jFound
{

    public String encode(Found found)
    {
        JSONObject o = new JSONObject();
        try
        {
            o.put("animalId", found.getAnimalId());
            o.put("userId", found.getUser_ID());
            o.put("lat", found.getLat());
            o.put("lng", found.getLng());
            o.put("timeDate", found.getTime());
            o.put("area", found.getArea());
            o.put("fdesc", found.getFdesc());
            return o.toString();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }


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
                (!o.isNull("area") ? o.getString("area") : null),
                (o.has("fdesc") ? o.getString("fdesc") : "")
        );
    }


    public Found decode(String j)
    {
        try
        {
            JSONObject o = new JSONObject(j);
            JSONObject m = o.getJSONObject("found");
            return decodeItem(m.toString());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public Found decodeItem(String j) throws JSONException
    {
        JSONObject o = new JSONObject(j);
        return new Found(
                o.getInt("foundId"),
                o.getDouble("lat"),
                o.getDouble("lng"),
                o.getLong("timeDate"),

                (o.has("animalId") ? o.getInt("animalId") : 0),
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
                o.getString("description"),
                (o.has("fdesc") ? o.getString("fdesc") : "")
        );
    }
}
