package no.dyrebar.dyrebar.classes;

public class AnimalFur
{
    private String color;
    private int furLength;
    private int furPattern;

    public AnimalFur(String color, int furLength, int furPattern)
    {
        this.color = color;
        this.furLength = furLength;
        this.furPattern = furPattern;
    }

    public int getFurLength()
    {
        return furLength;
    }

    public int getFurPattern()
    {
        return furPattern;
    }

    public String getColor()
    {
        return color;
    }
}
