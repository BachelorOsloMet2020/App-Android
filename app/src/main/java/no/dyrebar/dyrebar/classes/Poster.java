package no.dyrebar.dyrebar.classes;

public class Poster
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

    public int get_ID()
    {
        return _ID;
    }

    public void set_ID(int _ID)
    {
        this._ID = _ID;
    }

    public double getLat()
    {
        return lat;
    }

    public void setLat(double lat)
    {
        this.lat = lat;
    }

    public double getLng()
    {
        return lng;
    }

    public void setLng(double lng)
    {
        this.lng = lng;
    }

    public long getTime()
    {
        return time;
    }

    public void setTime(long time)
    {
        this.time = time;
    }
}
