package no.dyrebar.dyrebar.classes;

import java.io.Serializable;

public class Missing extends ProfileAnimal implements Serializable
{
    private int _ID;
    private double lat;
    private double lng;
    private long time;
    private String area;
    private String mdesc;

    /**
     * Constructor with super
     * @param missingId
     * @param lat
     * @param lng
     * @param timeDate
     * @param animalId
     * @param userId
     * @param image
     * @param idTag
     * @param name
     * @param animalType
     * @param animalTypeExtras
     * @param sex
     * @param sterilized
     * @param color
     * @param furLength
     * @param furPattern
     * @param description
     */
    public Missing(int missingId, double lat, double lng, long timeDate, int animalId, int userId, String image, String idTag, String name, int animalType, String animalTypeExtras, int sex, int sterilized, String color, int furLength, int furPattern, String description, String mdesc)
    {
        super(animalId, userId, idTag, image, name, animalType, animalTypeExtras, sex, sterilized, color, furLength, furPattern, description);
        this._ID = missingId;
        this.lat = lat;
        this.lng = lng;
        this.time = timeDate;
        this.mdesc = mdesc;
    }

    /**
     * This constructor is recommended for RecyclerView or other ListViews
     * @param missingId
     * @param lat
     * @param lng
     * @param timeDate
     * @param animalId
     * @param name
     * @param animalType
     * @param animalTypeExtras
     * @param area
     */
    public Missing(int missingId, double lat, double lng, long timeDate, int animalId, String name, String image, int animalType, String animalTypeExtras, String color, String area, String mdesc)
    {
        this._ID = missingId;
        this.lat = lat;
        this.lng = lng;
        this.time = timeDate;
        this.area = area;
        this.mdesc = mdesc;
        super.setName(name);
        super.setImage(image);
        super.set_ID(animalId);
        super.setAnimalType(animalType);
        super.setColor(color);
        super.setExtras(animalTypeExtras);
    }

    /**
     * @Warning Only for posting missing
     * @param lat
     * @param lng
     * @param timeDate
     * @param area
     */
    public Missing(int animalId, int userId, double lat, double lng, long timeDate, String area, String mdesc)
    {
        super.set_ID(animalId);
        super.setUser_ID(userId);
        this.lat = lat;
        this.lng = lng;
        this.time = timeDate;
        this.area = area;
        this.mdesc = mdesc;
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

    public String getMdesc()
    {
        return mdesc;
    }
}
