package compiler;

import java.util.ArrayList;

public class Method extends Block{
    private String name;
    private ArrayList<Class> arguments = new ArrayList<Class>();
    private Class returnValue = new Class();

    public String getName() {
        return name;
    }

//    public ArrayList<Class> getArguments() {
//        return arguments;
//    }

    public Class getReturnValue() {
        return returnValue;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public void setArguments(ArrayList<Class> arguments) {
//        this.arguments = arguments;
//    }

    public void setReturnValue(Class returnValue) {
        this.returnValue = returnValue;
    }
}
