import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import java.io.*;
import java.util.*;



public class MiniJavaMain {

    private static String inputFile = null;

    public static void main(String[] args) throws IOException {
        inputFile = args[0];
        InputStream is = new FileInputStream(inputFile);

        ANTLRInputStream input = new ANTLRInputStream(is);
        MiniJavaLexer lexer = new MiniJavaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MiniJavaParser parser = new MiniJavaParser(tokens);

        parser.removeErrorListeners();
        parser.addErrorListener(new DiagnosticErrorListener());
        parser.getInterpreter()
                .setPredictionMode(PredictionMode.LL_EXACT_AMBIG_DETECTION);
        parser.addErrorListener(new UnderlineErrorListener());

        ParseTree tree = parser.goal();
        ErrorPrinter.exitIfError();

        Map<String, ClassDef> classes = new HashMap<>();
        ClassNameChecker classNameChecker = new ClassNameChecker(parser, classes);
        ParseTreeWalker.DEFAULT.walk(classNameChecker, tree);
        ErrorPrinter.exitIfError();

        ParseTreeProperty<Scope> scopes = new ParseTreeProperty<>();
        AssignmentChecker assignmentChecker = new AssignmentChecker(classes, scopes, parser);
        ParseTreeWalker.DEFAULT.walk(assignmentChecker, tree);
        ErrorPrinter.exitIfError();

        System.out.println("Successfully compiled!");
        System.out.println("The AST:");
        System.out.println(tree.toStringTree(parser));
    }

    public static String getFileName() {
        return inputFile;
    }
}