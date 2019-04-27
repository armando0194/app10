package cs.utep.edu.app10;

import java.util.ArrayList;
import java.util.List;

import cs.utep.edu.app10.Disability;

public class DisabilityManager {

    private List<Disability> disabilityList;

    public DisabilityManager(){
        disabilityList = new ArrayList<Disability>();
        Disability wheel = new Disability("Wheelchair","Wheelchair accessibility",R.drawable.ic_wheelchair_96);
        Disability toilet = new Disability("Toilet","Restrooms in this place have enough space",R.drawable.ic_toilet_96);
        Disability parking = new Disability("Parking","This place offers handicap parking",R.drawable.ic_parking_96);
        this.add(wheel);
        this.add(toilet);
        this.add(parking);

    }

    public void add(Disability d){
        disabilityList.add(d);
    }

    public void remove(Disability d){
        disabilityList.remove(d);
    }

    public Disability get(int id){
        return disabilityList.get(id);
    }

    public int size(){
        return disabilityList.size();
    }
}
