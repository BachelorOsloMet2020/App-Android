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
                (o.has("email")) ? o.getString("email") : null
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


}
