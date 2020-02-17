package no.dyrebar.dyrebar.steps;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import ernestoyaquello.com.verticalstepperform.Step;
import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.adapter.AnimalTypeAdapter;
import no.dyrebar.dyrebar.classes.AnimalFur;

public class AnimalFurStep extends Step<AnimalFur>
{

    private Context context;
    private View v;

    public AnimalFurStep(Context context, String stepTitle)
    {
        super(stepTitle);
        this.context = context;
    }

    @Override
    protected View createStepContentLayout()
    {
        v = LayoutInflater.from(context).inflate(R.layout.stepper_animal_fur, null);
        ((Spinner)v.findViewById(R.id.create_animal_profile_animal_fur_length_spinner)).setAdapter(new AnimalTypeAdapter(context, new ArrayList<Pair<Integer, String>>(){{
            add(new Pair<>(-1, context.getString(R.string.spinner_please_select)));
            add(new Pair<>(1, context.getString(R.string.create_animal_profile_animal_fur_length_short)));
            add(new Pair<>(2, context.getString(R.string.create_animal_profile_animal_fur_length_half)));
            add(new Pair<>(3, context.getString(R.string.create_animal_profile_animal_fur_length_long)));

            add(new Pair<>(0, context.getString(R.string.create_animal_profile_animal_fur_length_unknown)));
        }}));

        ((Spinner)v.findViewById(R.id.create_animal_profile_animal_fur_pattern_spinner)).setAdapter(new AnimalTypeAdapter(context, new ArrayList<Pair<Integer, String>>(){{
            add(new Pair<>(-1, context.getString(R.string.spinner_please_select)));
            add(new Pair<>(1, context.getString(R.string.animal_profile_animal_fur_pattern_single)));
            add(new Pair<>(2, context.getString(R.string.animal_profile_animal_fur_pattern_duo)));
            add(new Pair<>(3, context.getString(R.string.animal_profile_animal_fur_pattern_trio)));
            add(new Pair<>(4, context.getString(R.string.animal_profile_animal_fur_pattern_tiger_stripes)));
            add(new Pair<>(5, context.getString(R.string.animal_profile_animal_fur_pattern_turtle)));
            add(new Pair<>(6, context.getString(R.string.animal_profile_animal_fur_pattern_variegated)));

            add(new Pair<>(0, context.getString(R.string.animal_profile_animal_fur_pattern_other)));
        }}));

        ((Spinner)v.findViewById(R.id.create_animal_profile_animal_fur_length_spinner)).setOnItemSelectedListener(oisl);
        ((Spinner)v.findViewById(R.id.create_animal_profile_animal_fur_pattern_spinner)).setOnItemSelectedListener(oisl);
        ((TextInputEditText)v.findViewById(R.id.create_animal_profile_animal_fur_color_text)).addTextChangedListener(textWatcher);

        return v;
    }

    @Override
    protected IsDataValid isStepDataValid(AnimalFur stepData)
    {
        if (stepData.getColor().length() == 0)
        {
            return new IsDataValid(false, context.getString(R.string.create_animal_profile_error_furColor));
        }
        else if (stepData.getFurLength() == -1)
        {
            return new IsDataValid(false, context.getString(R.string.create_animal_profile_error_furLength));
        }
        else if (stepData.getFurPattern() == -1)
        {
            return new IsDataValid(false, context.getString(R.string.create_animal_profile_error_furPattern));
        }

        return new IsDataValid(true, "");
    }

    @Override
    public AnimalFur getStepData()
    {
        String color = ((TextInputEditText)v.findViewById(R.id.create_animal_profile_animal_fur_color_text)).getText().toString();


        Pair<Integer, String> furLength_selectedIndex = (Pair<Integer, String>) ((Spinner)v.findViewById(R.id.create_animal_profile_animal_fur_length_spinner)).getSelectedItem();
        Pair<Integer, String> furPattern_selectedIndex = (Pair<Integer, String>) ((Spinner)v.findViewById(R.id.create_animal_profile_animal_fur_pattern_spinner)).getSelectedItem();

        AnimalFur fur = new AnimalFur(
                color,
                furLength_selectedIndex.first,
                furPattern_selectedIndex.first
        );

        return fur;
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
    public void restoreStepData(AnimalFur data)
    {
        // TODO: add restore
        ((TextInputEditText)v.findViewById(R.id.create_animal_profile_animal_fur_color_text)).setText(data.getColor());

        AnimalTypeAdapter aat = (AnimalTypeAdapter) ((Spinner)v.findViewById(R.id.create_animal_profile_animal_fur_length_spinner)).getAdapter();
        for (int i = 0; i < aat.getCount(); i++)
        {
            Pair<Integer, String> p = (Pair<Integer, String>) aat.getItem(i);
            if (p.first == data.getFurLength())
                ((Spinner)v.findViewById(R.id.create_animal_profile_animal_fur_length_spinner)).setSelection(i, true);
        }

        AnimalTypeAdapter aat2 = (AnimalTypeAdapter) ((Spinner)v.findViewById(R.id.create_animal_profile_animal_fur_pattern_spinner)).getAdapter();
        for (int i = 0; i < aat.getCount(); i++)
        {
            Pair<Integer, String> p = (Pair<Integer, String>) aat2.getItem(i);
            if (p.first == data.getFurLength())
                ((Spinner)v.findViewById(R.id.create_animal_profile_animal_fur_pattern_spinner)).setSelection(i, true);
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
