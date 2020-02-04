package no.dyrebar.dyrebar.json;

import org.json.JSONException;
import org.json.JSONObject;

import no.dyrebar.dyrebar.classes.Profile;

public class jProfile
{
    public Profile jGraphProfile(JSONObject o) throws JSONException
    {
        return new Profile(
                o.getString("first_name"),
                o.getString("last_name"),
                (o.has("email")) ? o.getString("email") : null,
                "https://graph.facebook.com/" + o.getString("id") +"/picture?type=large"
        );
    }

    public JSONObject jsonProfileChallenge(Profile profile) throws JSONException
    {
        JSONObject o = new JSONObject();
        o.put("firstName", profile.getFirstName());
        o.put("lastName", profile.getLastName());
        o.put("email", profile.getEmail());
        return o;
    }


    public Profile decode(String json)
    {
        try
        {
            JSONObject o = new JSONObject(json).getJSONObject("profile");
            Profile profile = new Profile(
                o.getString("id"),
                    o.getInt("authId"),
                    o.getString("firstName"),
                    o.getString("lastName"),
                    o.getString("email"),
                    o.getString("phoneNumber"),
                    o.getString("address"),
                    o.getInt("postNumber"),
                    o.getString("image")
            );
            return profile;
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public String encode(Profile profile, String token)
    {
        JSONObject o = new JSONObject();
        try
        {
            if (token != null)
                o.put("token", token);
            o.put("authId", profile.getAuthId());
            o.put("firstName", profile.getFirstName());
            o.put("lastName", profile.getLastName());
            o.put("email", profile.getEmail());
            o.put("address", profile.getAddress());
            o.put("postNumber", profile.getpostNumber());
            o.put("phone", profile.getTlf());
            o.put("image", profile.getImage());
            if (profile.getImageType() != null && profile.getImageType().length() > 0)
                o.put("imageType", profile.getImageType());
            return o.toString();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }


}
