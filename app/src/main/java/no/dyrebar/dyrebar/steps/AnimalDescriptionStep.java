package no.dyrebar.dyrebar.steps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

import ernestoyaquello.com.verticalstepperform.Step;
import no.dyrebar.dyrebar.R;

public class AnimalDescriptionStep extends Step<String>
{
    private Context context;
    private View v;

    public AnimalDescriptionStep(Context context, String stepTitle)
    {
        super(stepTitle);
        this.context = context;
    }


    @Override
    protected View createStepContentLayout()
    {
        v = LayoutInflater.from(context).inflate(R.layout.stepper_animal_description, null);

        return v;
    }
    @Override
    protected IsDataValid isStepDataValid(String stepData)
    {
        return null;
    }

    @Override
    public String getStepData()
    {
        String desc = ((TextInputEditText)v.findViewById(R.id.create_animal_profile_animal_description_text)).getText().toString();
        return desc;
    }

    @Override
    public String getStepDataAsHumanReadableString()
    {
        String desc = ((TextInputEditText)v.findViewById(R.id.create_animal_profile_animal_description_text)).getText().toString();
        return desc;
    }

    @Override
    protected void onStepOpened(boolean animated)
    {

    }

    @Override
    protected void onStepClosed(boolean animated)
    {

    }

    @Override
    protected void onStepMarkedAsCompleted(boolean animated)
    {

    }

    @Override
    protected void onStepMarkedAsUncompleted(boolean animated)
    {

    }

    @Override
    public void restoreStepData(String data)
    {
        if (data != null)
            ((TextInputEditText)v.findViewById(R.id.create_animal_profile_animal_description_text)).setText(data);
    }



}
