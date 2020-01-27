package no.dyrebar.dyrebar.json;

import org.json.JSONException;
import org.json.JSONObject;

import no.dyrebar.dyrebar.classes.AuthSession;
import no.dyrebar.dyrebar.classes.AuthChallenge;

public class jAuthSession
{
    public AuthSession decode(String json) throws JSONException, IllegalArgumentException
    {
        JSONObject o = new JSONObject(json);
        return new AuthSession(
                o.getString("id"),
                o.getString("session_token"),
                AuthChallenge.oAuthProvider.valueOf(o.getString("provider")),
                o.getLong("time")

        );
    }
    public String encode(AuthSession session) throws JSONException
    {
        JSONObject o = new JSONObject();
        o.put("id", session.getId());
        o.put("session_token", session.getToken());
        o.put("provider", session.getProvider());
        o.put("action", "VALIDATE");
        return o.toString();
    }


}
