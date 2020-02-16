package no.dyrebar.dyrebar.classes;

public class AnimalExt
{
    private int sex;
    private int sterilized;

    public AnimalExt(int sex, int sterilized)
    {
        this.sex = sex;
        this.sterilized = sterilized;
    }
    public AnimalExt() {}

    public void setSex(int sex)
    {
        this.sex = sex;
    }

    public void setSterilized(int sterilized)
    {
        this.sterilized = sterilized;
    }

    public int getSterilized()
    {
        return sterilized;
    }

    public int getSex()
    {
        return sex;
    }
}
