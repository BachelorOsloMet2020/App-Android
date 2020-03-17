package no.dyrebar.dyrebar.CustomControls;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;

public class DrawerLayoutOverride extends DrawerLayout
{
    public DrawerLayoutOverride(@NonNull Context context)
    {
        super(context);
    }

    public DrawerLayoutOverride(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public DrawerLayoutOverride(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
