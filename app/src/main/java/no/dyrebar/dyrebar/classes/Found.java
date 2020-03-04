package no.dyrebar.dyrebar.classes;

import java.io.Serializable;

public class Found extends ProfileAnimal implements Serializable
{
    private int _ID;
    private double lat;
    private double lng;
    private long time;
    private String area;
    private String fdesc;

    public Found(int animalId, int userId, double lat, double lng, long timeDate, String area, String fdesc)
    {
        super.set_ID(animalId);
        super.setUser_ID(userId);
        this.lat = lat;
        this.lng = lng;
        this.time = timeDate;
        this.area = area;
        this.fdesc = fdesc;
    }


    public Found(int missingId, double lat, double lng, long timeDate, String name, String image, int animalType, String animalTypeExtras, String color, String area, String fdesc)
    {
        this._ID = missingId;
        this.lat = lat;
        this.lng = lng;
        this.time = timeDate;
        this.area = area;
        this.fdesc = fdesc;
        super.setName(name);
        super.setImage(image);
        super.setAnimalType(animalType);
        super.setColor(color);
        super.setExtras(animalTypeExtras);
    }


    public Found(int foundId, double lat, double lng, long timeDate, int animalId, int userId, String image, String idTag, String name, int animalType, String animalTypeExtras, int sex, int sterilized, String color, int furLength, int furPattern, String description, String fdesc)
    {
        super(animalId, userId, idTag, image, name, animalType, animalTypeExtras, sex, sterilized, color, furLength, furPattern, description);
        this._ID = foundId;
        this.lat = lat;
        this.lng = lng;
        this.time = timeDate;
        this.fdesc = fdesc;
    }


    public String getArea()
    {
        return area;
    }

    public String getName()
    {
        return super.getName();
    }

    public int getAnimalId()
    {
        return super.get_ID();
    }

    public int getAnimalType()
    {
        return super.getAnimalType();
    }

    public String getImage()
    {
        return super.getImage();
    }

    public String getAnimalTypeExtras()
    {
        return super.getExtras();
    }

    public int get_ID()
    {
        return _ID;
    }


    public double getLat()
    {
        return lat;
    }

    public double getLng()
    {
        return lng;
    }

    public long getTime()
    {
        return time;
    }

    public String getFdesc()
    {
        return fdesc;
    }
}
