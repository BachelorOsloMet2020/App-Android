package no.dyrebar.dyrebar.json;

import org.json.JSONException;
import org.json.JSONObject;

import no.dyrebar.dyrebar.classes.AuthSession;
import no.dyrebar.dyrebar.classes.AuthChallenge;

public class jAuthSession
{
    public AuthSession decode(String json) throws JSONException, IllegalArgumentException
    {
        JSONObject root = new JSONObject(json);
        JSONObject o = root.getJSONObject("data");
        return new AuthSession(
                o.getString("id"),
                o.getInt("authId"),
                o.getString("session_token"),
                AuthChallenge.oAuthProvider.valueOf(o.getString("provider")),
                o.getLong("time")

        );
    }
    public String encode(AuthSession session) throws JSONException
    {
        JSONObject o = new JSONObject();
        o.put("id", session.getId());
        o.put("authId", session.getAuthId());
        o.put("session_token", session.getToken());
        o.put("provider", session.getProvider());
        return o.toString();
    }


}
