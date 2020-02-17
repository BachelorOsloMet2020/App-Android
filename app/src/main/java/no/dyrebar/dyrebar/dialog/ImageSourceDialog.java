package no.dyrebar.dyrebar.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import no.dyrebar.dyrebar.R;

public class ImageSourceDialog
{
    private Activity activity;
    private Dialog dialog;
    private View v;

    public ImageSourceDialog(Activity activity, String title, int ARC_Camera, int ARC_Gallery, Uri output)
    {
        this.activity = activity;
        dialog = make();
        ((TextView)v.findViewById(R.id.dialog_image_source_title)).setText(title);
        v.findViewById(R.id.dialog_image_source_camera).setOnClickListener(v -> {
            Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            camera.putExtra(MediaStore.EXTRA_OUTPUT, output);
            activity.startActivityForResult(camera, ARC_Camera);
            Hide();
        });
        v.findViewById(R.id.dialog_image_source_gallery).setOnClickListener(v -> {
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activity.startActivityForResult(gallery, ARC_Gallery);
            Hide();
        });
    }

    private Dialog make()
    {
        v = View.inflate(activity, R.layout.dialog_image_source, null);
        Dialog dialog = new Dialog(activity, R.style.DialogIndicator);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(v);
        return dialog;
    }

    public void Show()
    {
        if (dialog != null)
            dialog.show();
    }

    public void Hide()
    {
        if (dialog != null)
            dialog.hide();
    }

    public boolean isVisible()
    {
        if (dialog == null)
            return false;
        return dialog.isShowing();
    }

    public void Destroy()
    {
        dialog.dismiss();
    }
}
