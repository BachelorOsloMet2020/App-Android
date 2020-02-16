package no.dyrebar.dyrebar.classes;

public class AnimalBasic
{
    private String idTag;
    private String name;
    private int animalType;
    private String extras;

    public AnimalBasic(String name, String idTag, int animalType, String extras)
    {
        this.name = name;
        this.idTag = idTag;
        this.animalType = animalType;
        this.extras = extras;
    }

    public String getName()
    {
        return name;
    }

    public String getIdTag()
    {
        return idTag;
    }

    public int getAnimalType()
    {
        return animalType;
    }

    public String getExtras()
    {
        return extras;
    }
}
