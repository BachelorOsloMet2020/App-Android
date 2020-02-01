package no.dyrebar.dyrebar.classes;


import java.io.Serializable;

public class ProfileAnimal implements Serializable
{
    private int _ID;
    private int user_ID;
    private int tag_ID;
    private String image;
    private String name;
    private String animalType;
    private String sex;
    private String sterilized;
    private String color;
    private String furLength;
    private String furPattern;
    private String description;

    public ProfileAnimal()
    {
    }

    public ProfileAnimal(int _ID, int user_ID, int tag_ID, String image, String name, String animalType, String sex, String sterilized, String color, String furLength, String furPattern, String description)
    {
        this._ID = _ID;
        this.user_ID = user_ID;
        this.tag_ID = tag_ID;
        this.image = image;
        this.name = name;
        this.animalType = animalType;
        this.sex = sex;
        this.sterilized = sterilized;
        this.color = color;
        this.furLength = furLength;
        this.furPattern = furPattern;
        this.description = description;
    }

    public int get_ID()
    {
        return _ID;
    }

    public void set_ID(int _ID)
    {
        this._ID = _ID;
    }

    public int getUser_ID()
    {
        return user_ID;
    }

    public void setUser_ID(int user_ID)
    {
        this.user_ID = user_ID;
    }

    public int getTag_ID()
    {
        return tag_ID;
    }

    public void setTag_ID(int tag_ID)
    {
        this.tag_ID = tag_ID;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAnimalType()
    {
        return animalType;
    }

    public void setAnimalType(String animalType)
    {
        this.animalType = animalType;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getSterilized()
    {
        return sterilized;
    }

    public void setSterilized(String sterilized)
    {
        this.sterilized = sterilized;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public String getFurLength()
    {
        return furLength;
    }

    public void setFurLength(String furLength)
    {
        this.furLength = furLength;
    }

    public String getFurPattern()
    {
        return furPattern;
    }

    public void setFurPattern(String furPattern)
    {
        this.furPattern = furPattern;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
