package no.dyrebar.dyrebar.classes;

public class Poster extends ProfileAnimal
{
    private int _ID;
    private double lat;
    private double lng;
    private long time;

    public Poster()
    {
    }

    public Poster(int _ID, double lat, double lng, long time)
    {
        this._ID = _ID;
        this.lat = lat;
        this.lng = lng;
        this.time = time;
    }


}
