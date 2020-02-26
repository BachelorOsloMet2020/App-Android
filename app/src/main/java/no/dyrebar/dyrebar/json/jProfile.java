package no.dyrebar.dyrebar.json;

import org.json.JSONException;
import org.json.JSONObject;

import no.dyrebar.dyrebar.classes.Profile;
import no.dyrebar.dyrebar.classes.PublicProfile;

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
        if (json == null || json.length() == 0)
            return null;
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
                    o.getString("postNumber"),
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

    public PublicProfile decodePublic(String json)
    {
        if (json == null || json.length() == 0)
            return null;
        try
        {
            JSONObject o = new JSONObject(json).getJSONObject("profile");
            PublicProfile profile = new PublicProfile(
                    o.getString("id"),
                    o.getString("firstName"),
                    o.getString("lastName"),
                    o.getString("email"),
                    o.getString("phoneNumber"),
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
            if (profile.getId() != null && profile.getId().length() > 0)
                o.put("id", profile.getId());
            o.put("authId", profile.getAuthId());
            o.put("firstName", profile.getFirstName());
            o.put("lastName", profile.getLastName());
            o.put("email", profile.getEmail());
            o.put("address", profile.getAddress());
            o.put("postNumber", profile.getPostNumber());
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

    public String getPrivateProfileId(String json)
    {
        try
        {
            JSONObject o = new JSONObject(json);
            if (o.has("data"))
                return o.getString("data");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }


}
