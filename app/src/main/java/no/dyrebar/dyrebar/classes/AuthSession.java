package no.dyrebar.dyrebar.classes;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Map;

public class AuthSession
{
    private String id;
    private int authId;
    private AuthChallenge.oAuthProvider provider;
    private long time;
    private String token;

    public AuthSession(String id, int authId, String token, AuthChallenge.oAuthProvider provider, long time)
    {
        this.id = id;
        this.authId = authId;
        this.token = token;
        this.provider = provider;
        this.time = time;
    }

    public AuthSession(Map<String, ?> map)
    {
        this.id = (String) map.get("id");
        this.authId = (Integer) map.get("authId");
        this.token = (String) map.get("token");
        if (map.get("time") instanceof Long)
            this.time = (Long) map.get("time");
        this.provider = AuthChallenge.oAuthProvider.valueOf((String)map.get("provider"));
    }

    public String getId()
    {
        return id;
    }

    public int getAuthId()
    {
        return authId;
    }

    public AuthChallenge.oAuthProvider getProvider()
    {
        return provider;
    }

    public String getToken()
    {
        return token;
    }

    public long getTime()
    {
        return time;
    }

    public ArrayList<Pair<String, ?>> asList()
    {
        ArrayList<Pair<String, ?>> ls = new ArrayList<Pair<String, ?>>()
        {{
            add(new Pair<>("id", id));
            add(new Pair<>("authId", authId));
            add(new Pair<>("token", token));
            add(new Pair<>("time", time));
            add(new Pair<>("provider", provider.toString()));
        }};
        return ls;
    }

    public boolean objVal()
    {
        return this.id != null && this.id.length() > 0
                && this.authId > 0
                && this.token != null && this.token.length() > 0
                && this.time > 0
                && this.provider != null;
    }
}
