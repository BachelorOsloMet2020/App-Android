package no.dyrebar.dyrebar.classes;

public class SignInChallenge
{
    private String id;
    private String token;
    private oAuthProvider provider;
    private Profile profile;
    private String DeviceId;

    public enum oAuthProvider
    {
        FACEBOOK,
        GOOGLE
    }

    public SignInChallenge(String id, String token, oAuthProvider provider, Profile profile, String DeviceId)
    {
        this.id = id;
        this.token = token;
        this.provider = provider;
        this.profile = profile;
        this.DeviceId = DeviceId;
    }

    public String getId()
    {
        return id;
    }

    public String getToken()
    {
        return token;
    }

    public oAuthProvider getProvider()
    {
        return provider;
    }

    public Profile getProfile()
    {
        return profile;
    }

    public String getDeviceId()
    {
        return DeviceId;
    }
}
