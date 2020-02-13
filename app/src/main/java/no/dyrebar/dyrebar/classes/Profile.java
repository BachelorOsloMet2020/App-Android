package no.dyrebar.dyrebar.classes;

import android.content.Intent;
import android.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Profile implements Serializable
{
    private String id;
    private int authId;
    private String FirstName;
    private String LastName;
    private String Email;
    private String Tlf;
    private String Address;
    private int postNumber;

    private String Image;
    private String imageType;
    private boolean isGuest;

    public Profile()
    {
    }


    /**
     * Only to be used for SignIn Channelge
     *
     * @param FirstName
     * @param LastName
     * @param Email
     */

    public Profile(String FirstName, String LastName, String Email, String Image)
    {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Email = Email;
        this.Image = Image;
    }


    public Profile(String id, int auhtId, String FirstName, String LastName, String Email, String Tlf, String Address, int postNumber, String Image)
    {
        this.id = id;
        this.authId = auhtId;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Email = Email;
        this.Tlf = Tlf;
        this.Address = Address;
        this.Image = Image;
        this.postNumber = postNumber;
    }

    public Profile(Map<String, ?> map)
    {
        this.id = (String) map.get("id");
        this.authId = (Integer) map.get("authId");
        this.FirstName = (String) map.get("firstName");
        this.LastName = (String) map.get("lastName");
        this.Email = (String) map.get("email");
        this.Tlf = (String) map.get("phoneNumber");
        this.Address = (String) map.get("address");
        this.postNumber = (Integer) map.get("postNumber");
        this.Image = (String) map.get("image");
    }

    public String getId()
    {
        return id;
    }

    public int getAuthId()
    {
        return authId;
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

    public int getpostNumber()
    {
        return postNumber;
    }

    public void setAddress(String address)
    {
        Address = address;
    }

    public void setAuthId(int authId)
    {
        this.authId = authId;
    }

    public void setEmail(String email)
    {
        Email = email;
    }

    public void setFirstName(String firstName)
    {
        FirstName = firstName;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setLastName(String lastName)
    {
        LastName = lastName;
    }

    public void setTlf(String tlf)
    {
        Tlf = tlf;
    }

    public void setpostNumber(int postNumber)
    {
        postNumber = postNumber;
    }

    public void setImage(String image)
    {
        Image = image;
    }

    public void setImageType(String imageType)
    {
        this.imageType = imageType;
    }

    public String getImageType()
    {
        return imageType;
    }

    public String getImage()
    {
        return Image;
    }

    public boolean isGuest()
    {
        return isGuest;
    }

    public void setGuest(boolean guest)
    {
        isGuest = guest;
    }

    public ArrayList<Pair<String, ?>> asList()
    {
        ArrayList<Pair<String, ?>> ls = new ArrayList<Pair<String, ?>>()
        {{
            add(new Pair<>("id", getId()));
            add(new Pair<>("authId", getAuthId()));
            add(new Pair<>("firstName", getFirstName()));
            add(new Pair<>("lastName", getLastName()));
            add(new Pair<>("email", getEmail()));
            add(new Pair<>("phoneNumber", getTlf()));
            add(new Pair<>("address", getAddress()));
            add(new Pair<>("postNumber", getpostNumber()));
            add(new Pair<>("image", getImage()));
        }};
        return ls;
    }


}
