package no.dyrebar.dyrebar.steps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import ernestoyaquello.com.verticalstepperform.Step;
import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.classes.AnimalExt;

public class AnimalExtStep extends Step<AnimalExt>
{
    private Context context;
    private View v;
    private AnimalExt animalExt;

    public AnimalExtStep(Context context, String stepTitle)
    {
        super(stepTitle);
        this.context = context;
    }

    @Override
    protected View createStepContentLayout()
    {
        animalExt = new AnimalExt(-1,-1);
        v = LayoutInflater.from(context).inflate(R.layout.stepper_animal_ext, null);

        ((RadioButton)v.findViewById(R.id.stepper_animal_ext_sex_male)).setOnClickListener(onSexRadioClick);
        ((RadioButton)v.findViewById(R.id.stepper_animal_ext_sex_female)).setOnClickListener(onSexRadioClick);
        ((RadioButton)v.findViewById(R.id.stepper_animal_ext_sex_unknown)).setOnClickListener(onSexRadioClick);

        ((RadioButton)v.findViewById(R.id.stepper_animal_ext_sterilized_yes)).setOnClickListener(onSterilizedRadioClick);
        ((RadioButton)v.findViewById(R.id.stepper_animal_ext_sterilized_no)).setOnClickListener(onSterilizedRadioClick);
        ((RadioButton)v.findViewById(R.id.stepper_animal_ext_sterilized_unknown)).setOnClickListener(onSterilizedRadioClick);


        return v;
    }

    @Override
    protected IsDataValid isStepDataValid(AnimalExt stepData)
    {
        if (stepData.getSex() == -1)
        {
            return new IsDataValid(false, context.getString(R.string.create_animal_profile_error_sex));
        }
        else if (stepData.getSterilized() == -1)
        {
            return new IsDataValid(false, context.getString(R.string.create_animal_profile_error_sterilized));
        }
        return new IsDataValid(true, "");
    }

    @Override
    public AnimalExt getStepData()
    {
        return animalExt;
    }

    @Override
    public String getStepDataAsHumanReadableString()
    {
        return null;
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
    public void restoreStepData(AnimalExt data)
    {
        // TODO: add restore
        if (data.getSex() == 0)
            ((RadioButton)v.findViewById(R.id.stepper_animal_ext_sex_male)).setChecked(true);
        else if (data.getSex() == 1)
            ((RadioButton)v.findViewById(R.id.stepper_animal_ext_sex_female)).setChecked(true);
        else if (data.getSex() == 2)
            ((RadioButton)v.findViewById(R.id.stepper_animal_ext_sex_unknown)).setChecked(true);

        if (data.getSterilized() == 0)
            ((RadioButton)v.findViewById(R.id.stepper_animal_ext_sterilized_no)).setChecked(true);
        else if (data.getSterilized() == 1)
            ((RadioButton)v.findViewById(R.id.stepper_animal_ext_sterilized_yes)).setChecked(true);
        else if (data.getSterilized() == 2)
            ((RadioButton)v.findViewById(R.id.stepper_animal_ext_sterilized_unknown)).setChecked(true);
        animalExt = data;
        markAsCompletedOrUncompleted(true);

    }

    private View.OnClickListener onSexRadioClick = v ->
    {
        boolean checked = ((RadioButton)v).isChecked();
        switch (v.getId())
        {
            case R.id.stepper_animal_ext_sex_male:
            {
                if (checked)
                    animalExt.setSex(0);
                break;
            }
            case R.id.stepper_animal_ext_sex_female:
            {
                if (checked)
                    animalExt.setSex(1);
                break;
            }
            case R.id.stepper_animal_ext_sex_unknown:
            {
                if (checked)
                    animalExt.setSex(2);
                break;
            }
            default:
                animalExt.setSex(-1);
        }
        markAsCompletedOrUncompleted(true);
    };

    private View.OnClickListener onSterilizedRadioClick = v ->
    {
        boolean checked = ((RadioButton)v).isChecked();
        switch (v.getId())
        {
            case R.id.stepper_animal_ext_sterilized_no:
            {
                if (checked)
                    animalExt.setSterilized(0);
                break;
            }
            case R.id.stepper_animal_ext_sterilized_yes:
            {
                if (checked)
                    animalExt.setSterilized(1);
                break;
            }
            case R.id.stepper_animal_ext_sterilized_unknown:
            {
                if (checked)
                    animalExt.setSterilized(2);
                break;
            }
            default:
                animalExt.setSterilized(-1);
        }
        markAsCompletedOrUncompleted(true);
    };

}
