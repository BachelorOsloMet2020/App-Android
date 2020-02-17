package no.dyrebar.dyrebar.steps;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import ernestoyaquello.com.verticalstepperform.Step;
import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.adapter.AnimalTypeAdapter;
import no.dyrebar.dyrebar.classes.AnimalBasic;

public class AnimalBasicStep extends Step<AnimalBasic>
{
    private Context context;
    private View v;

    public AnimalBasicStep(Context context, String stepTitle)
    {
        super(stepTitle);
        this.context = context;
    }


    @Override
    protected View createStepContentLayout()
    {
        v = LayoutInflater.from(context).inflate(R.layout.stepper_animal_basic, null);
        ((Spinner)v.findViewById(R.id.create_animal_profile_animal_type_spinner)).setAdapter(new AnimalTypeAdapter(context, new ArrayList<Pair<Integer, String>>(){{
            add(new Pair<>(-1, context.getString(R.string.spinner_please_select)));
            add(new Pair<>(0, context.getString(R.string.create_animal_profile_animal_type_dog)));
            add(new Pair<>(1, context.getString(R.string.create_animal_profile_animal_type_cat)));
            add(new Pair<>(2, context.getString(R.string.create_animal_profile_animal_type_rabbit)));
            add(new Pair<>(3, context.getString(R.string.create_animal_profile_animal_type_bird)));
            add(new Pair<>(4, context.getString(R.string.create_animal_profile_animal_type_other)));
        }}));
        ((Spinner)v.findViewById(R.id.create_animal_profile_animal_type_spinner)).setOnItemSelectedListener(oisl);
        ((TextInputEditText)v.findViewById(R.id.create_animal_profile_name_text)).addTextChangedListener(textWatcher);

        return v;
    }

    @Override
    protected IsDataValid isStepDataValid(AnimalBasic stepData)
    {


        if (stepData.getName().length() == 0)
        {
            return new IsDataValid(false, context.getString(R.string.create_animal_profile_error_name));
        }
        else if (stepData.getAnimalType() == -1)
        {
            return new IsDataValid(false, context.getString(R.string.create_animal_profile_error_animalType));
        }


        return new IsDataValid(true, "");
    }

    @Override
    public AnimalBasic getStepData()
    {
        String name = ((TextInputEditText)v.findViewById(R.id.create_animal_profile_name_text)).getText().toString();
        String idTag = ((TextInputEditText)v.findViewById(R.id.create_animal_profile_idtag_text)).getText().toString();
        Pair<Integer, String> selectedIndex = (Pair<Integer, String>) ((Spinner)v.findViewById(R.id.create_animal_profile_animal_type_spinner)).getSelectedItem();
        int animalId = selectedIndex.first;
        String animalTypeExtra = ((TextInputEditText)v.findViewById(R.id.create_animal_profile_animal_type_extra_text)).getText().toString();

        AnimalBasic ab = new AnimalBasic(
            name,
            idTag,
            animalId,
            animalTypeExtra
        );

        return ab;
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
    public void restoreStepData(AnimalBasic data)
    {
        ((TextInputEditText)v.findViewById(R.id.create_animal_profile_name_text)).setText(data.getName());
        ((TextInputEditText)v.findViewById(R.id.create_animal_profile_idtag_text)).setText(data.getIdTag());
        ((TextInputEditText)v.findViewById(R.id.create_animal_profile_animal_type_extra_text)).setText(data.getExtras());

        AnimalTypeAdapter aat = (AnimalTypeAdapter) ((Spinner)v.findViewById(R.id.create_animal_profile_animal_type_spinner)).getAdapter();
        for (int i = 0; i < aat.getCount(); i++)
        {
            Pair<Integer, String> p = (Pair<Integer, String>) aat.getItem(i);
            if (p.first == data.getAnimalType())
                ((Spinner)v.findViewById(R.id.create_animal_profile_animal_type_spinner)).setSelection(i, true);
        }
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            markAsCompletedOrUncompleted(true);
        }

        @Override
        public void afterTextChanged(Editable s)
        {

        }
    };

    private AdapterView.OnItemSelectedListener oisl =  new AdapterView.OnItemSelectedListener()
    {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            markAsCompletedOrUncompleted(true);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {

        }
    };

}
