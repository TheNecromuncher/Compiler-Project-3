import org.antlr.v4.runtime.*;
import java.util.*;
import java.io.*;

public class SymbolTableExtractor extends LittleBaseListener{

@Override
public void enterString_decl(LittleParser.String_declContext ctx){

}

@Override
public void exitString_decl(LittleParser.String_declContext ctx){
String text1 = ctx.getText();
String text2 = text1.substring(6, text1.length()-1);
String[] tokens = text2.split(":=");
String name = tokens[0];
String value = tokens[1];
System.out.printf("name %s type STRING value %s\n", name, value);

}

public class SymbolTable {
        String name;
        String type;
        String value;
        Hashtable<Integer, SymbolTable> ht = new Hashtable<>();
}

}
