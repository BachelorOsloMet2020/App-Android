package no.dyrebar.dyrebar.handler;

import android.content.Context;
import android.util.Pair;

import java.util.ArrayList;

import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.classes.Missing;
import no.dyrebar.dyrebar.classes.Profile;
import no.dyrebar.dyrebar.classes.ProfileAnimal;

public class DatasetHandler<T>
{

    public ArrayList<ProfileAnimal> searchInList(String query, ArrayList<ProfileAnimal> items)
    {
        ArrayList<ProfileAnimal> mi = new ArrayList<>();
        for (ProfileAnimal pa : items)
        {
            if (isRelevant(query, pa))
                mi.add(pa);
        }
        return mi;
    }

    public ArrayList<Missing> searchInMissingList(String query, ArrayList<Missing> items)
    {
        ArrayList<Missing> mi = new ArrayList<>();
        for (Missing pa : items)
        {
            if (isRelevantMissing(query, pa))
                mi.add(pa);
        }
        return mi;
    }

    private boolean isRelevant(String query, ProfileAnimal animal)
    {
        query = query.toLowerCase();
        String[] qi = query.split("[, .!-]");

        for (int i = 0; i < qi.length; i++)
        {
            String q = qi[i];
            if (animal.getName() != null && (q.equalsIgnoreCase(animal.getName()) || (animal.getName().toLowerCase().matches(q))))
                return true;
            else if (animal.getExtras() != null && (q.equalsIgnoreCase(animal.getExtras()) || (animal.getExtras()).toLowerCase().matches(q)))
                return true;
            else if (animal.getDescription() != null && (q.equalsIgnoreCase(animal.getDescription()) || (animal.getDescription().toLowerCase().matches(q))))
                return true;
        }
        return false;
    }

    //https://stackoverflow.com/questions/41359615/how-to-find-particular-word-from-string-android
    private boolean isRelevantMissing(String query, Missing missing)
    {
        query = query.toLowerCase();
        String[] qi = query.split("[, .!-/]");

        for (int i = 0; i < qi.length; i++)
        {
            String q = qi[i];
            if (missing.getName() != null)
                if (stringQueryCompare(q, missing.getName()))
                    return true;
            if (missing.getExtras() != null)
                if (stringQueryCompare(q, missing.getExtras()))
                    return true;
            if (missing.getDescription() != null)
                if (stringQueryCompare(q, missing.getDescription()))
                    return true;
            if (missing.getArea() != null)
                if (stringQueryCompare(q, missing.getArea()))
                    return true;
            if (missing.getMdesc() != null)
                if (stringQueryCompare(q, missing.getMdesc()))
                    return true;
            if (missing.getColor() != null)
                if (stringQueryCompare(q, missing.getColor()))
                    return true;
        }
        return false;
    }


    private boolean stringQueryCompare(String query, String data)
    {
        data = data.toLowerCase();
        query = query.toLowerCase();
        if (query.matches(data))
            return true;
        if (data.contains(query))
            return true;
        if (data.indexOf(query) > -1)
            return true;
        return false;
    }

    public ArrayList<Pair<Integer, String>> getAnimalTypeFilterForPoster(Context context)
    {
        return new ArrayList<Pair<Integer, String>>(){{
            add(new Pair<Integer, String>(-1, context.getString(R.string.all)));
            add(new Pair<Integer, String>(0, context.getString(R.string.create_animal_profile_animal_type_dog)));
            add(new Pair<Integer, String>(1, context.getString(R.string.create_animal_profile_animal_type_cat)));
            add(new Pair<Integer, String>(2, context.getString(R.string.create_animal_profile_animal_type_rabbit)));
            add(new Pair<Integer, String>(3, context.getString(R.string.create_animal_profile_animal_type_bird)));
            add(new Pair<Integer, String>(4, context.getString(R.string.create_animal_profile_animal_type_other)));
        }};
    }




    private int filter_animal_type = -1;
    private boolean filter_sex_male;
    private boolean filter_sex_female;
    private boolean filter_sex_unknown;

    public void resetFilter()
    {
        filter_animal_type = -1;
        filter_sex_male = false;
        filter_sex_female = false;
        filter_sex_unknown = false;
    }

    public void setFilter_animal_type(int id)
    {
        this.filter_animal_type = id;
    }

    public void setFilter_sex_male(boolean filter_sex_male)
    {
        this.filter_sex_male = filter_sex_male;
    }

    public void setFilter_sex_female(boolean filter_sex_female)
    {
        this.filter_sex_female = filter_sex_female;
    }

    public void setFilter_sex_unknown(boolean filter_sex_unknown)
    {
        this.filter_sex_unknown = filter_sex_unknown;
    }

    public ArrayList<T> getFiltered(ArrayList<T> items)
    {
        ArrayList<T> res = new ArrayList<>();
        for(T item : items)
        {
            if (item instanceof ProfileAnimal)
            {
                if (isRelevantToFilter(item))
                    res.add(item);
            }
        }


        return res;
    }

    private boolean isRelevantToFilter(T item)
    {
        if (filter_animal_type != -1)
        {
            if (((ProfileAnimal)item).getAnimalType() != filter_animal_type)
                return false;
        }
        if (filter_sex_male || filter_sex_female || filter_sex_unknown)
        {
            if (((ProfileAnimal)item).getSex() == 0 && !filter_sex_male)
                return false;
            else if (((ProfileAnimal)item).getSex() == 1 && !filter_sex_female)
                return false;
            else if (((ProfileAnimal)item).getSex() == 2 && !filter_sex_unknown)
                return false;
        }
        return true;
    }





}
