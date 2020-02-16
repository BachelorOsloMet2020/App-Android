package no.dyrebar.dyrebar.json;

import org.json.JSONException;
import org.json.JSONObject;

import no.dyrebar.dyrebar.classes.ProfileAnimal;

public class jProfileAnimal
{
    public String encode(String uid, ProfileAnimal animal)
    {
        JSONObject o = new JSONObject();
        /** Required to provide user id*/
        try
        {
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



}
