package no.dyrebar.dyrebar.handler;

import android.content.Context;

import no.dyrebar.dyrebar.R;

public class TypeHandler
{

    public String getAnimalType(Context context, int val)
    {
        switch (val)
        {
            case 0:
                return context.getString(R.string.create_animal_profile_animal_type_dog);
            case 1:
                return context.getString(R.string.create_animal_profile_animal_type_cat);
            case 2:
                return context.getString(R.string.create_animal_profile_animal_type_rabbit);
            case 3:
                return context.getString(R.string.create_animal_profile_animal_type_bird);
            default:
                return context.getString(R.string.create_animal_profile_animal_type_other);
        }
    }

    public String getSex(Context context, int val)
    {
        switch (val)
        {
            case 0:
                return context.getString(R.string.create_animal_profile_animal_sex_male);
            case 1:
                return context.getString(R.string.create_animal_profile_animal_sex_female);
            default:
                return context.getString(R.string.create_animal_profile_animal_sex_unknown);
        }
    }


}
