package no.dyrebar.dyrebar.handler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;

import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.classes.Err;
import no.dyrebar.dyrebar.json.jStatus;

public class ResponseHandler
{

    public boolean showDialogOnError(Activity activity, String json)
    {
        String message = null;

        jStatus jstat = new jStatus();
        boolean success = jstat.getStatus(json);
        if (success)
            return false;
        Err err = jstat.getError(json);
        if (err == null || err.getCode() == null || err.getMessage() == null)
            message = getLocalizedErrorMessage(activity, "0x4");
        else
            message = getLocalizedErrorMessage(activity, err.getCode());
        buildDialog(activity, getString(activity, R.string.error), (message == null) ? err.getMessage() : message);
        return true;
    }


    private void buildDialog(Activity activity, String title, String message)
    {
        if (Looper.myLooper() == Looper.getMainLooper())
        {
            showDialog(createDialog(activity, getString(activity, R.string.error), message));
        }
        else
        {
            new Handler(Looper.getMainLooper()).post(() -> {
                showDialog(createDialog(activity, getString(activity, R.string.error), message));
            });
        }
    }


    private void showDialog(AlertDialog dialog)
    {
        dialog.show();
    }



    private AlertDialog createDialog(Activity activity, String title, String message)
    {
        AlertDialog.Builder adb = new AlertDialog.Builder(activity);
        adb.setTitle(title);
        adb.setMessage(message);
        adb.setNeutralButton(activity.getString(R.string.ok), (dialog, which) -> dialog.dismiss());
        return adb.create();
    }


    private String getLocalizedErrorMessage(Context context, String code)
    {
        switch (code)
        {
            case "0x1":
                return getString(context, R.string._0x1);
            case "0x2":
                return getString(context, R.string._0x2);
            case "0x3":
                return getString(context, R.string._0x3);
            case "0x4":
                return getString(context, R.string._0x4);
            case "0x5":
                return getString(context, R.string._0x5);
            case "0x6":
                return getString(context, R.string._0x6);
            case "0x7":
                return getString(context, R.string._0x7);
            case "0x8":
                return getString(context, R.string._0x8);
            case "0x9":
                return getString(context, R.string._0x9);
            case "0x10":
                return getString(context, R.string._0x10);
            case "0x11":
                return getString(context, R.string._0x11);
            case "0x12":
                return getString(context, R.string._0x12);
            case "0x13":
                return getString(context, R.string._0x13);
            case "0x14":
                return getString(context, R.string._0x14);
            case "0x15":
                return getString(context, R.string._0x15);
            case "0x16":
                return getString(context, R.string._0x16);
            case "0x17":
                return getString(context, R.string._0x17);
            case "0x18":
                return getString(context, R.string._0x18);
            case "0x19":
                return getString(context, R.string._0x19);
            case "0x20":
                return getString(context, R.string._0x20);
            case "0x21":
                return getString(context, R.string._0x21);
            case "0x22":
                return getString(context, R.string._0x22);
            case "0x23":
                return getString(context, R.string._0x23);
            default:
                return null;
        }
    }

    private String getString(Context c, int id)
    {
        return c.getString(id);
    }

}
