package no.dyrebar.dyrebar.classes;

public class PublicProfile
{
    private String id;
    private String FirstName;
    private String LastName;
    private String Email;
    private String Tlf;

    private String Image;

    public PublicProfile(String id, String FirstName, String LastName, String Email, String Tlf, String Image)
    {
        this.id = id;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Email = Email;
        this.Tlf = Tlf;
        this.Image = Image;
    }

    public String getId()
    {
        return id;
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

    public String getImage()
    {
        return Image;
    }
}
