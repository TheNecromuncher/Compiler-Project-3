import org.antlr.v4.runtime.*;
import java.util.*;
import java.io.*;


public class SymbolTableExtractor extends LittleBaseListener{

private int blockNum = 1;

LinkedList<SymbolTable> SymTabList = new LinkedList<>();
LinkedList<SymbolTable> Queue = new LinkedList<>();

public boolean declCheckl(String name){
Hashtable current = Queue.getFirst().ht;

if( stringCompare(current.get(name.charAt(0)%10).name, name) == 0){
	System.out.printf("DECLARATION ERROR %s", name);
	throw new Exception();
}

}

@Override
public void enterProgram(LittleParser.ProgramContext ctx){

System.out.printf("Symbol table GLOBAL\n");
SymbolTable ST = new SymbolTable("GLOBAL", null, null);
SymTabList.add(ST);
Queue.add(ST);
}

@Override
public void enterString_decl(LittleParser.String_declContext ctx){
String text1 = ctx.getText();
String text2 = text1.substring(6, text1.length()-1);
String[] tokens = text2.split(":=");
String name = tokens[0];
String value = tokens[1];
System.out.printf("name %s type STRING value %s\n", name, value);
SymbolTable newValue = new SymbolTable(name, "STRING", value);
Queue.getLast().ht.put(value.charAt(1)%10, newValue);
//System.out.printf("\n\nTESTO TESTO:" + Queue.getLast().ht.get(value.charAt(1)%10).value + "\n");
}

@Override
public void enterWhile_stmt(LittleParser.While_stmtContext ctx){
String blockStr = "BLOCK ";
blockStr.concat(String.valueOf(blockNum));
SymbolTable ST = new SymbolTable(blockStr, null, null);
SymTabList.add(ST);
Queue.add(ST);
System.out.printf("\nSymbol Table BLOCK %d\n", blockNum++);
}

@Override
public void exitWhile_stmt(LittleParser.While_stmtContext ctx){
Queue.removeLast();
}

@Override
public void enterIf_stmt(LittleParser.If_stmtContext ctx){
String blockStr = "BLOCK ";
blockStr.concat(String.valueOf(blockNum));
SymbolTable ST = new SymbolTable(blockStr, null, null);
SymTabList.add(ST);
Queue.add(ST);
System.out.printf("\nSymbol Table BLOCK %d\n", blockNum++);
}

@Override
public void exitIf_stmt(LittleParser.If_stmtContext ctx){
Queue.removeLast();
}

@Override
public void enterElse_part(LittleParser.Else_partContext ctx){
if(ctx.getChildCount() == 0){}
else{
String blockStr = "BLOCK ";
blockStr.concat(String.valueOf(blockNum));
SymbolTable ST = new SymbolTable(blockStr, null, null);
SymTabList.add(ST);
Queue.add(ST);
System.out.printf("\nSymbol Table BLOCK %d\n", blockNum++);
}
}

@Override
public void exitElse_part(LittleParser.Else_partContext ctx){
Queue.removeLast();
}

@Override
public void enterFunc_decl(LittleParser.Func_declContext ctx){
String functionName = ctx.getChild(2).getText();
System.out.printf("\nSymbol table %s\n", functionName);
SymbolTable ST = new SymbolTable(functionName, null, null);
SymTabList.add(ST);
Queue.add(ST);
}

@Override
public void exitFunc_decl(LittleParser.Func_declContext ctx){
Queue.removeLast();
}

@Override
public void enterParam_decl(LittleParser.Param_declContext ctx){
String varType = ctx.getChild(0).getText();
String varName = ctx.getChild(1).getText();
System.out.printf("name %s type %s\n", varName, varType);
SymbolTable newValue = new SymbolTable(varName, varType, null);
Queue.getLast().ht.put(varName.charAt(0)%10, newValue);
}


@Override
public void enterVar_decl(LittleParser.Var_declContext ctx){
String varType = ctx.getChild(0).getText();
String varNames = ctx.getChild(1).getText();
String[] varNameList = varNames.split(",");
int i = 0;
while(i < varNameList.length){
	System.out.printf("name %s type %s\n", varNameList[i], varType);
	SymbolTable newValue = new SymbolTable(varNameList[i], varType, null);
	Queue.getLast().ht.put(varNameList[i].charAt(0)%10, newValue);
	i++;
}
}

public class SymbolTable {
        String name;
        String type;
        String value;
        Hashtable<Integer, SymbolTable> ht = new Hashtable<>(10);

	SymbolTable(String name, String type, String value){
		this.name = name;
		this.type = type;
		this.value = value;
	}
}

}
