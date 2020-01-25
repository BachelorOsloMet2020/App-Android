package no.dyrebar.dyrebar.json;

import org.json.JSONException;
import org.json.JSONObject;

import no.dyrebar.dyrebar.classes.SignInChallenge;

public class jSignInChallenge
{
    public String encode(SignInChallenge sic) throws JSONException
    {
        JSONObject o = new JSONObject();
        o.put("provider", sic.getProvider());
        o.put("id", sic.getId());
        o.put("token", sic.getToken());
        o.put("profile", new jProfile().jsonProfileChallenge(sic.getProfile()));
        o.put("deviceId", sic.getDeviceId());
        return o.toString();
    }


}
