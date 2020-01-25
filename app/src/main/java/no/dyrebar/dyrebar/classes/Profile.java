package no.dyrebar.dyrebar.classes;

public class Profile
{
    private String FirstName;
    private String LastName;
    private String Email;
    private String Tlf;
    private String Address;

    /**
     * Only to be used for SignIn Channelge
     *
     * @param FirstName
     * @param LastName
     * @param Email
     */
    public Profile(String FirstName, String LastName, String Email)
    {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Email = Email;
    }

    public Profile(String FirstName, String LastName, String Email, String Tlf, String Address)
    {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Email = Email;
        this.Tlf = Tlf;
        this.Address = Address;
    }


    public String getFirstName()
    {
        return FirstName;
    }

    public String getLastName()
    {
        return LastName;
    }

    public String getEmail()
    {
        return Email;
    }

    public String getTlf()
    {
        return Tlf;
    }

    public String getAddress()
    {
        return Address;
    }
}
