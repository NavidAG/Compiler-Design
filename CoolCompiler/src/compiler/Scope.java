package compiler;

public class Scope {

    public Scope() {
//        parent = new Scope();
        symbolTable = new SymbolTable();
        Class class1 = new Class();
        class1.setName("Int");
        symbolTable.add(class1.getName(), class1);
        Class class2 = new Class();
        class2.setName("String");
        symbolTable.add(class2.getName(), class2);
        Class class3 = new Class();
        class3.setName("Bool");
        symbolTable.add(class3.getName(), class3);
        Class class4 = new Class();
        class4.setName("SELF_TYPE");
        symbolTable.add(class4.getName(), class4);
        Class class5 = new Class();
        class5.setName("IO");
        symbolTable.add(class5.getName(), class5);
        Class class6 = new Class();
        class6.setName("var");
        symbolTable.add(class6.getName(), class6);
        Class class7 = new Class();
        class7.setName("Object");
        symbolTable.add(class7.getName(), class7);
    }

    Scope parent;
    SymbolTable symbolTable;
}
