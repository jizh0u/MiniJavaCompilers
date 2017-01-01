import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import java.io.*;
import java.util.*;



public class MiniJavaMain {

    private static String inputFile = null;

    public static void main(String[] args) throws IOException, FileNotFoundException {
        InputStream is = null;

        ANTLRInputStream input = new ANTLRInputStream(is);
        MiniJavaLexer lexer = new MiniJavaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MiniJavaParser parser = new MiniJavaParser(tokens);

        if (args.length > 0) {
            inputFile = args[0];
            is = new FileInputStream(inputFile);
        } else {
            is = System.in;
        }

        ParseTree tree = parser.goal();
    }
}