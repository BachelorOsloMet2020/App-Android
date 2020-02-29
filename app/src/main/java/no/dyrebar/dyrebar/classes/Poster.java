package no.dyrebar.dyrebar.classes;

public class Poster extends ProfileAnimal
{
    private int Id;
    private int userId;
    private double lat;
    private double lng;
    private long time;

    public Poster()
    {
    }

    public Poster(int _ID, int userId, double lat, double lng, long time)
    {
        this.Id = _ID;
        this.userId = userId;
        this.lat = lat;
        this.lng = lng;
        this.time = time;
    }


}
