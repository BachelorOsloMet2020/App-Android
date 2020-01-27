package no.dyrebar.dyrebar.json;

import org.json.JSONException;
import org.json.JSONObject;

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
}
