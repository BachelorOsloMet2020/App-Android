package no.dyrebar.dyrebar.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import no.dyrebar.dyrebar.classes.ProfileAnimal;

public class jProfileAnimal
{
    public String encode(int uid, ProfileAnimal animal)
    {
        JSONObject o = new JSONObject();
        /** Required to provide user id*/
        try
        {
            if (animal.get_ID() >= 1)
                o.put("id", animal.get_ID());
            o.put("userId", uid);
            o.put("name", animal.getName());
            o.put("idTag", animal.getTag_ID());
            o.put("animalType", animal.getAnimalType());
            o.put("extras", animal.getExtras());
            o.put("sex", animal.getSex());
            o.put("sterilized", animal.getSterilized());
            o.put("color", animal.getColor());
            o.put("furLength", animal.getFurLength());
            o.put("furPattern", animal.getFurPattern());
            o.put("description", animal.getDescription());
            o.put("image", animal.getImage());
            o.put("imageType", animal.getImageType());
            return o.toString();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public ProfileAnimal decode(String j) throws JSONException
    {
        ProfileAnimal animal = null;
        JSONObject o = new JSONObject(j);
        if (o.has("data"))
            o = o.getJSONObject("data");


        return animal;
    }

    public ArrayList<ProfileAnimal> decodeArray(String j)
    {
        ArrayList<ProfileAnimal> items = new ArrayList<>();
        try
        {
            JSONObject o = new JSONObject(j);
            JSONArray a = o.getJSONArray("data");
            for (int i = 0; i < a.length(); i++)
            {
                ProfileAnimal pa = decode(a.getJSONObject(i));
                if (pa != null)
                    items.add(pa);
            }
            return items;
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return items;
    }

    private ProfileAnimal decode(JSONObject o)
    {
        try
        {
            return new ProfileAnimal(
                o.getInt("id"),
                o.getInt("userId"),
                (o.isNull("idTag") ? "" : o.getString("idTag")),
                o.getString("image"),
                o.getString("name"),
                o.getInt("animalType"),
                (o.isNull("animalTypeExtras") ? "" : o.getString("animalTypeExtras")),
                o.getInt("sex"),
                o.getInt("sterilized"),
                o.getString("color"),
                o.getInt("furLength"),
                o.getInt("furPattern"),
                (o.isNull("description") ? "" : o.getString("description"))
            );
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }



}
