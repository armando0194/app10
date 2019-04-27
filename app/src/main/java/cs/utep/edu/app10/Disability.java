package cs.utep.edu.app10;

import java.util.List;

public class Disability {
    private String name;
    private String description;
    private int resourceID;


    public Disability(){}

    public Disability(String name,String description, int resourceID){
        this.name = name;
        this.description = description;
        this.resourceID = resourceID;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }
}
