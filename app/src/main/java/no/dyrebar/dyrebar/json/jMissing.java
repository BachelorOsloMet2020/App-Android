package no.dyrebar.dyrebar.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import no.dyrebar.dyrebar.classes.Missing;

public class jMissing
{

    public String encode(Missing missing)
    {
        JSONObject o = new JSONObject();
        try
        {
            o.put("animalId", missing.getAnimalId());
            o.put("lat", missing.getLat());
            o.put("lng", missing.getLng());
            o.put("timeDate", missing.getTime());
            o.put("area", missing.getArea());
            return o.toString();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }



    public ArrayList<Missing> decodeArray(String j)
    {
        ArrayList<Missing> missings = new ArrayList<>();

        try
        {
            JSONObject o = new JSONObject(j);
            JSONArray a = o.getJSONArray("data");
            for (int i = 0; i < a.length(); i++)
            {
                Missing m = decodeItems(a.getJSONObject(i).toString());
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

    /**
     * For simple items
     * @param j
     * @return
     * @throws JSONException
     */
    public Missing decodeItems(String j) throws JSONException
    {
        JSONObject o = new JSONObject(j);
        return new Missing(
                o.getInt("missingId"),
                o.getDouble("lat"),
                o.getDouble("lng"),
                o.getLong("timeDate"),

                o.getInt("animalId"),
                o.getString("name"),
                o.getString("image"),

                o.getInt("animalType"),
                (!o.isNull("animalTypeExtras") ? o.getString("animalTypeExtras") : null),
                o.getString("color"),
                (!o.isNull("area") ? o.getString("area") : null)
        );
    }

    /**
     * For complete item
     * @param j
     * @return
     * @throws JSONException
     */
    public Missing decodeItem(String j) throws JSONException
    {
        JSONObject o = new JSONObject(j);
        return new Missing(
            o.getInt("missingId"),
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
            o.getInt("sterilized"),
            o.getString("color"),
            o.getInt("furLength"),
            o.getInt("furPattern"),
            o.getString("description")
        );
    }
}
