package no.dyrebar.dyrebar.json;

import org.json.JSONException;
import org.json.JSONObject;

import no.dyrebar.dyrebar.classes.Err;

public class jStatus
{
    public boolean getStatus(String json)
    {
        boolean status = false;
        try
        {
            JSONObject o = new JSONObject(json);
            status = o.getBoolean("status");
        }
        catch (JSONException | NullPointerException e)
        {
            e.printStackTrace();
        }
        return status;
    }

    public Err getError(String json)
    {
        try
        {
            JSONObject o = new JSONObject(json);
            if (o.has("err"))
            {
                o = o.getJSONObject("err");
                return new Err(o.getString("errc"), o.getString("errm"));
            }
            else
            {
                return new Err(o.getString("errc"), o.getString("errm"));
            }

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
