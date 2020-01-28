package no.dyrebar.dyrebar.classes;

public class AuthChallenge
{
    private String id;
    private String email;
    private String token;
    private oAuthProvider provider;
    private Profile profile;
    private String DeviceId;

    private String password;

    public enum oAuthProvider
    {
        FACEBOOK,
        GOOGLE,
        DYREBAR
    }

    /**
     * AuthChallenge for oAuth providers, Facebook and Google
     * @param id
     * @param email
     * @param token
     * @param provider
     * @param profile
     * @param DeviceId
     */
    public AuthChallenge(String id, String email, String token, oAuthProvider provider, Profile profile, String DeviceId)
    {
        this.id = id;
        this.email = email;
        this.token = token;
        this.provider = provider;
        this.profile = profile;
        this.DeviceId = DeviceId;
    }

    /**
     * AuthChallenge for Dyrebar Sign in
     * @param email
     * @param password
     * @param provider
     */
    public AuthChallenge(String email, String password, oAuthProvider provider)
    {
        this.email = email;
        this.password = password;
        this.provider = provider;
    }

    public String getId()
    {
        return id;
    }

    public String getEmail()
    {
        return email;
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

    public String getPassword()
    {
        return password;
    }
}
