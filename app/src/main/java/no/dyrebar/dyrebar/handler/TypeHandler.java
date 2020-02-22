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

    public String getSterilized(Context context, int val)
    {
        switch (val)
        {
            case 0:
                return context.getString(R.string.no);
            case 1:
                return context.getString(R.string.yes);
            default:
                return context.getString(R.string.unknown);
        }
    }

    public String getFurLength(Context context, int val)
    {
        switch (val)
        {
            case 1:
                return context.getString(R.string.create_animal_profile_animal_fur_length_short);
            case 2:
                return context.getString(R.string.create_animal_profile_animal_fur_length_half);
            case 3:
                return context.getString(R.string.create_animal_profile_animal_fur_length_long);
            default:
                return context.getString(R.string.create_animal_profile_animal_fur_length_unknown);
        }
    }

    public String getFurPattern(Context context, int val)
    {
        switch (val)
        {
            case 1:
                return context.getString(R.string.animal_profile_animal_fur_pattern_single);
            case 2:
                return context.getString(R.string.animal_profile_animal_fur_pattern_duo);
            case 3:
                return context.getString(R.string.animal_profile_animal_fur_pattern_trio);
            case 4:
                return context.getString(R.string.animal_profile_animal_fur_pattern_tiger_stripes);
            case 5:
                return context.getString(R.string.animal_profile_animal_fur_pattern_turtle);
            case 6:
                return context.getString(R.string.animal_profile_animal_fur_pattern_variegated);
            default:
                return context.getString(R.string.animal_profile_animal_fur_pattern_other);
        }
    }


}
