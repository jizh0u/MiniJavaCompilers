import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import java.io.*;
import java.util.*;



public class MiniJavaMain {

    public static void main(String[] args) throws IOException, FileNotFoundException {
        ANTLRInputStream input = new ANTLRInputStream(System.in);
        MiniJavaLexer lexer = new MiniJavaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MiniJavaParser parser = new MiniJavaParser(tokens);

        ParseTree tree = parser.goal();
        System.out.println(tree.toStringTree(parser));
    }
}