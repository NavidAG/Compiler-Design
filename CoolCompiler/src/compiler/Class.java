package compiler;

import java.util.ArrayList;

public class Class extends Block {
    public Class() {
    }
    private String name;
    private ArrayList<Method> allMethods;
    private Class parents;

    public void setName(String name) {
        this.name = name;
    }

    public void setAllMethods(ArrayList<Method> allMethods) {
        this.allMethods = allMethods;
    }

    public void setParents(Class parents) {
        this.parents = parents;
    }

    public String getName() {
        return name;
    }


    public ArrayList<Method> getAllMethods() {
        return allMethods;
    }

    public Class getParents() {
        return parents;
    }

    public Class(String name) {
        this.name = name;
    }



}
