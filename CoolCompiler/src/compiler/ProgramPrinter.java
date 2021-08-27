package compiler;

import gen.*;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.Trees;

import java.util.ArrayList;

public class ProgramPrinter  implements CoolListener{
    public Tree tree;
    public int errorNummber = 100;
    public Scope currentScope;
    public ArrayList<Object> classNameArrayList = new ArrayList<>();

    @Override
    public void enterProgram(CoolParser.ProgramContext ctx) {
        System.out.println("program start{");
        tree = new Tree("object");


        Scope programScope = new Scope();
        programScope.parent = currentScope;
        currentScope = programScope;

//        System.out.println(currentScope.symbolTable.toString());
    }

    @Override
    public void exitProgram(CoolParser.ProgramContext ctx) {
//        System.out.println("}");
//        System.out.println(tree.toString());

        for(int i=0; i<classNameArrayList.size(); i++){
            if(currentScope.symbolTable.lookup(((Token) classNameArrayList.get(i)).getText()) == null){
                System.err.println("Error " + (errorNummber++) + ": in line ["+((Token) classNameArrayList.get(i)).getLine()+":"+((Token) classNameArrayList.get(i)).getCharPositionInLine()+"], cannot find class " + ((Token) classNameArrayList.get(i)).getText());
            }
        }
        currentScope = currentScope.parent;

    }

    @Override
    public void enterClasses(CoolParser.ClassesContext ctx) {

    }

    @Override
    public void exitClasses(CoolParser.ClassesContext ctx) {

    }

    @Override
    public void enterEof(CoolParser.EofContext ctx) {

    }

    @Override
    public void exitEof(CoolParser.EofContext ctx) {

    }

    @Override
    public void enterClassDefine(CoolParser.ClassDefineContext ctx) {
        Class aClass = new Class();
        aClass.setName(ctx.className.getText());

        if(currentScope.symbolTable.lookup(aClass.getName()) != null){
            System.err.println("Error " + (errorNummber++) + ": in line ["+ctx.className.getLine()+":"+ctx.className.getCharPositionInLine()+"], class "+ aClass.getName()+" has been defined already");
        }
//        System.out.println(aClass.getName());
        classNameArrayList.add(ctx.className);
        if(ctx.parentClass != null){
            classNameArrayList.add(ctx.parentClass);
//            System.out.println(ctx.parentClass.getText());
        } // else


        currentScope.symbolTable.add(aClass.getName(), aClass);
        Scope classScope = new Scope();
        classScope.parent = currentScope;
        currentScope = classScope;

//        System.out.println(currentScope.symbolTable.toString());
//        if(ctx.parentClass != null) {
//            System.out.println("\tclass: " + ctx.className.getText() + "/ class parents: " + ctx.parentClass.getText() + ", " + "{");
//            tree.addLeaf(ctx.parentClass.getText(), ctx.className.getText());
//        }else {
//            System.out.println("\tclass: " + ctx.className.getText() + "/ class parents: " + "object, " + "{");
//            tree.addLeaf("object", ctx.className.getText());
//        }


    }

    @Override
    public void exitClassDefine(CoolParser.ClassDefineContext ctx) {
//        System.out.println("\t}");
        currentScope = currentScope.parent;
    }

    @Override
    public void enterMethod(CoolParser.MethodContext ctx) {
//        System.out.println("\t\tclass method: " + ctx.methodName.getText() + "/ return type=" + ctx.returnType.getText() + "{");
        Method aMethod = new Method();
        aMethod.setName(ctx.methodName.getText());
        aMethod.setReturnValue(new Class(ctx.returnType.getText()));

        if(currentScope.symbolTable.lookup(aMethod.getName()) != null){
            System.err.println("Error " + (errorNummber++) + ": in line ["+ctx.methodName.getLine()+":"+ctx.methodName.getCharPositionInLine()+"], method "+ aMethod.getName()+" has been defined already");
        } // else

        classNameArrayList.add(ctx.returnType);
        currentScope.symbolTable.add(aMethod.getName(), aMethod);
        System.out.println("\t" + aMethod);
//        System.out.println(currentScope.symbolTable.toString());
        Scope methodScope = new Scope();
        methodScope.parent = currentScope;
        currentScope = methodScope;
    }

    @Override
    public void exitMethod(CoolParser.MethodContext ctx) {
//        System.out.println("\t\t}");
        currentScope = currentScope.parent;
    }

    @Override
    public void enterProperty(CoolParser.PropertyContext ctx) {
        Property aProperty = new Property();
        aProperty.setName(ctx.propertyName.getText());
        aProperty.setType(ctx.propertyType.getText());
//        if(globalScope.symbolTable.lookup(ctx.propertyType.getText()) == null){
//            System.err.println("Error " + (errorNummber++) + ": in line [line:column], cannot find class " + ctx.propertyType.getText());
//        } else {
//            aProperty.setType(new Class(ctx.propertyType.getText()));
//        }

        if(currentScope.symbolTable.lookup(aProperty.getName()) != null){
            System.err.println("Error " + (errorNummber++) + ": in line ["+ctx.propertyName.getLine()+":"+ctx.propertyName.getCharPositionInLine()+"], property "+ aProperty.getName()+" has been defined already");
        } else {
            currentScope.symbolTable.add(aProperty.getName(), aProperty);
            System.out.print(ctx.TYPEID().getText()+": ");
            System.out.print(aProperty.getName());

        }
        classNameArrayList.add(ctx.propertyType);
//        currentScope.symbolTable.add(aVarable.getName(), aVarable);
        System.out.println("\t" + aProperty);
//        System.out.println(currentScope.symbolTable.toString());
    }

    @Override
    public void exitProperty(CoolParser.PropertyContext ctx) {

    }

    @Override
    public void enterAtribute(CoolParser.AtributeContext ctx) {
//        if(!ctx.formal().isEmpty()) {
//            System.out.print("\t\t\tparameters list= [");
//        }
    }

    @Override
    public void exitAtribute(CoolParser.AtributeContext ctx) {
//        if(!ctx.formal().isEmpty()) {
//            System.out.println("]");
//        }
    }

    @Override
    public void enterFormal(CoolParser.FormalContext ctx) {
        Property aProperty = new Property();
        aProperty.setName(ctx.formalName.getText());
        aProperty.setType(ctx.formalName.getText());
        classNameArrayList.add(ctx.formalType);

        if(currentScope.symbolTable.lookup(ctx.formalName.getText()) == null){

            currentScope.symbolTable.add(ctx.formalName.getText(), aProperty);
        } else {
            System.err.println("Error " + (errorNummber++) + ": in line ["+ctx.formalName.getLine()+":"+ctx.formalName.getCharPositionInLine()+"], property "+ ctx.formalName.getText()+" has been defined already");
        }
//        if(ctx.formalName != null && ctx.formalType != null) {
//            System.out.print(ctx.formalType.getText() + " " + ctx.formalName.getText() + ", ");
//        }
    }

    @Override
    public void exitFormal(CoolParser.FormalContext ctx) {

    }

    @Override
    public void enterLetIn(CoolParser.LetInContext ctx) {
        ctx.TYPEID().get(0).getSymbol();
        System.out.println(ctx.TYPEID().get(0).getClass().getSimpleName());
//        System.out.println("\t\t\tfield: " + ctx.letName.getText() + "/ type=" + ctx.letType.getText());
    }

    @Override
    public void exitLetIn(CoolParser.LetInContext ctx) {

    }

    @Override
    public void enterMinus(CoolParser.MinusContext ctx) {

    }

    @Override
    public void exitMinus(CoolParser.MinusContext ctx) {

    }

    @Override
    public void enterString(CoolParser.StringContext ctx) {

    }

    @Override
    public void exitString(CoolParser.StringContext ctx) {

    }

    @Override
    public void enterIsvoid(CoolParser.IsvoidContext ctx) {

    }

    @Override
    public void exitIsvoid(CoolParser.IsvoidContext ctx) {

    }

    @Override
    public void enterWhile(CoolParser.WhileContext ctx) {
//        int counter = 0;
//        for(int i=0; i<ctx.expression().size(); i++){
//            if(ctx.expression(1).getText().contains("if")||ctx.expression(1).getText().contains("while")){
//                counter++;
//            }
//
//        }
//
//        if(counter>1){
//            System.out.println("\t"+"\t"+"\t"+"nested statement{\n\t\t\t}");
//        } else {
////            System.out.println("\t"+"\t"+"\t"+ctx.WHILE()+" "+ctx.expression(0).getText()+" loop {");
////            System.out.println("\t"+"\t"+"\t"+"\t"+ctx.expression(1).getText());
////            System.out.println("\t"+"\t"+"\t}");
//        }
    }

    @Override
    public void exitWhile(CoolParser.WhileContext ctx) {

    }

    @Override
    public void enterDivision(CoolParser.DivisionContext ctx) {

    }

    @Override
    public void exitDivision(CoolParser.DivisionContext ctx) {

    }

    @Override
    public void enterNegative(CoolParser.NegativeContext ctx) {

    }

    @Override
    public void exitNegative(CoolParser.NegativeContext ctx) {

    }

    @Override
    public void enterBoolNot(CoolParser.BoolNotContext ctx) {

    }

    @Override
    public void exitBoolNot(CoolParser.BoolNotContext ctx) {

    }

    @Override
    public void enterLessThan(CoolParser.LessThanContext ctx) {

    }

    @Override
    public void exitLessThan(CoolParser.LessThanContext ctx) {

    }

    @Override
    public void enterBlock(CoolParser.BlockContext ctx) {

    }

    @Override
    public void exitBlock(CoolParser.BlockContext ctx) {

    }

    @Override
    public void enterId(CoolParser.IdContext ctx) {
    }

    @Override
    public void exitId(CoolParser.IdContext ctx) {

    }

    @Override
    public void enterMultiply(CoolParser.MultiplyContext ctx) {

    }

    @Override
    public void exitMultiply(CoolParser.MultiplyContext ctx) {

    }

    @Override
    public void enterIf(CoolParser.IfContext ctx) {

//        int counter = 0;
//        for(int i=0; i<ctx.expression().size(); i++){
////            System.out.println(ctx.expression().get(i).getText());
//            if(ctx.expression(1).getText().contains("if")||ctx.expression(1).getText().contains("while")){
//                counter++;
//            }
//
//        }
//
//        if(counter>1){
//            System.out.println("\t"+"\t"+"\t"+"nested statement{\n\t\t\t}");
//        } else {
////            System.out.println("\t"+"\t"+"\t"+ctx.IF()+" "+ctx.expression(0).getText()+" then");
////            System.out.println("\t"+"\t"+"\t"+"\t"+ctx.expression(1).getText());
////            System.out.println("\t"+"\t"+"\t"+ctx.ELSE());
////            System.out.println("\t"+"\t"+"\t"+"\t"+ctx.expression(2).getText());
////            System.out.println("\t"+"\t"+"\t"+ctx.FI());
//        }
    }

    @Override
    public void exitIf(CoolParser.IfContext ctx) {

    }

    @Override
    public void enterCase(CoolParser.CaseContext ctx) {

    }

    @Override
    public void exitCase(CoolParser.CaseContext ctx) {

    }

    @Override
    public void enterOwnMethodCall(CoolParser.OwnMethodCallContext ctx) {

    }

    @Override
    public void exitOwnMethodCall(CoolParser.OwnMethodCallContext ctx) {

    }

    @Override
    public void enterAdd(CoolParser.AddContext ctx) {

    }

    @Override
    public void exitAdd(CoolParser.AddContext ctx) {

    }

    @Override
    public void enterNew(CoolParser.NewContext ctx) {

    }

    @Override
    public void exitNew(CoolParser.NewContext ctx) {

    }

    @Override
    public void enterParentheses(CoolParser.ParenthesesContext ctx) {

    }

    @Override
    public void exitParentheses(CoolParser.ParenthesesContext ctx) {

    }

    @Override
    public void enterAssignment(CoolParser.AssignmentContext ctx) {
    }

    @Override
    public void exitAssignment(CoolParser.AssignmentContext ctx) {

    }

    @Override
    public void enterFalse(CoolParser.FalseContext ctx) {

    }

    @Override
    public void exitFalse(CoolParser.FalseContext ctx) {

    }

    @Override
    public void enterInt(CoolParser.IntContext ctx) {

    }

    @Override
    public void exitInt(CoolParser.IntContext ctx) {

    }

    @Override
    public void enterEqual(CoolParser.EqualContext ctx) {

    }

    @Override
    public void exitEqual(CoolParser.EqualContext ctx) {

    }

    @Override
    public void enterTrue(CoolParser.TrueContext ctx) {

    }

    @Override
    public void exitTrue(CoolParser.TrueContext ctx) {

    }

    @Override
    public void enterLessEqual(CoolParser.LessEqualContext ctx) {

    }

    @Override
    public void exitLessEqual(CoolParser.LessEqualContext ctx) {

    }

    @Override
    public void enterMethodCall(CoolParser.MethodCallContext ctx) {

    }

    @Override
    public void exitMethodCall(CoolParser.MethodCallContext ctx) {

    }

    @Override
    public void visitTerminal(TerminalNode terminalNode) {

    }

    @Override
    public void visitErrorNode(ErrorNode errorNode) {

    }

    @Override
    public void enterEveryRule(ParserRuleContext parserRuleContext) {

    }

    @Override
    public void exitEveryRule(ParserRuleContext parserRuleContext) {

    }
}
