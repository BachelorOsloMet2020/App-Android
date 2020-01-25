package no.dyrebar.dyrebar.classes;

public class SiginInChallenge
{
    private String id;
    private String token;
    private oAuthProvider provider;
    private Profile profile;

    public enum oAuthProvider
    {
        FACEBOOK,
        GOOGLE
    }

    public SiginInChallenge(String id, String token, oAuthProvider provider, Profile profile)
    {
        this.id = id;
        this.token = token;
        this.provider = provider;
        this.profile = profile;
    }

    public String getToken()
    {
        return token;
    }

    public Profile getProfile()
    {
        return profile;
    }
}
