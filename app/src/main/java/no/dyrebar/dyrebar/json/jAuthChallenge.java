package no.dyrebar.dyrebar.json;

import org.json.JSONException;
import org.json.JSONObject;

import no.dyrebar.dyrebar.classes.AuthChallenge;

public class jAuthChallenge
{
    public String encode(AuthChallenge sic) throws JSONException
    {
        JSONObject o = new JSONObject();
        o.put("action", "CHALLENGE");
        if (sic.getProvider() == AuthChallenge.oAuthProvider.FACEBOOK || sic.getProvider() == AuthChallenge.oAuthProvider.GOOGLE)
        {
            o.put("provider", sic.getProvider());
            o.put("id", sic.getId());
            o.put("email", sic.getEmail());
            o.put("token", sic.getToken());
            o.put("profile", new jProfile().jsonProfileChallenge(sic.getProfile()));
            o.put("client_type", "android");
            o.put("duid", sic.getDeviceId());
        }
        else
        {
            o.put("provider", sic.getProvider());
           // o.put("id", sic.getId());
            o.put("email", sic.getEmail());
            o.put("password", sic.getPassword());
            //o.put("profile", new jProfile().jsonProfileChallenge(sic.getProfile()));
            o.put("client_type", "android");
            o.put("duid", sic.getDeviceId());
        }



        return o.toString();
    }


}
