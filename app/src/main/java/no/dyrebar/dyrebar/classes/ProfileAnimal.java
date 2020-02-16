package no.dyrebar.dyrebar.classes;


import java.io.Serializable;

public class ProfileAnimal implements Serializable
{
    private int _ID;
    private int user_ID;
    private String tag_ID;
    private String image;
    private String name;
    private int animalType;
    private String extras; // Rase // eventuelt hvis annet er valgt
    private int sex;
    private int sterilized;
    private String color;
    private int furLength;
    private int furPattern;
    private String description;

    private String imageType;

    public ProfileAnimal()
    {
    }

    public ProfileAnimal(int _ID, int user_ID, String tag_ID, String image, String name, int animalType, String extra, int sex, int sterilized, String color, int furLength, int furPattern, String description)
    {
        this._ID = _ID;
        this.user_ID = user_ID;
        this.tag_ID = tag_ID;
        this.image = image;
        this.name = name;
        this.animalType = animalType;
        this.extras = extra;
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

    public String getTag_ID()
    {
        return tag_ID;
    }

    public void setTag_ID(String tag_ID)
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

    public int getAnimalType()
    {
        return animalType;
    }

    public void setAnimalType(int animalType)
    {
        this.animalType = animalType;
    }

    public int getSex()
    {
        return sex;
    }

    public void setSex(int sex)
    {
        this.sex = sex;
    }

    public int getSterilized()
    {
        return sterilized;
    }

    public void setSterilized(int sterilized)
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

    public int getFurLength()
    {
        return furLength;
    }

    public void setFurLength(int furLength)
    {
        this.furLength = furLength;
    }

    public int getFurPattern()
    {
        return furPattern;
    }

    public void setFurPattern(int furPattern)
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

    public String getExtras()
    {
        return extras;
    }

    public void setExtras(String extras)
    {
        this.extras = extras;
    }

    public void setImageType(String imageType)
    {
        this.imageType = imageType;
    }

    public String getImageType()
    {
        return imageType;
    }

    public void fromAnimalBasic(AnimalBasic basic)
    {
        this.name = basic.getName();
        this.tag_ID = basic.getIdTag();
        this.animalType = basic.getAnimalType();
        this.extras = basic.getExtras();
    }

    public void fromAnimalExt(AnimalExt ext)
    {
        this.sex = ext.getSex();
        this.sterilized = ext.getSterilized();
    }

    public void fromAnimalFur(AnimalFur fur)
    {
        this.color = fur.getColor();
        this.furLength = fur.getFurLength();
        this.furPattern = fur.getFurPattern();
    }

}
