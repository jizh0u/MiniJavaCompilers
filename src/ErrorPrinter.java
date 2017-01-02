/**
 * Created by Zhou on 2017/1/2.
 */
import org.antlr.v4.runtime.*;
import java.util.*;

public final class ErrorPrinter {
    private static boolean hasError = false;
    private static int errorCount = 0;
    public static boolean noErrors() {
        return !hasError;
    }
    public static void reportError() {
        hasError = true;
        ++errorCount;
    }

    public static int getErrorCount() {
        return errorCount;
    }

    public static void exitIfError() {
        if(!ErrorPrinter.noErrors()) {
            System.err.println(ErrorPrinter.getErrorCount() + " errors.");
            System.exit(1);
        }
    }

    public static void printFileNameAndLineNumber(Token offendingToken) {
        reportError();
        System.err.print(MiniJavaMain.getFileName() + ":" + offendingToken.getLine() + ": ");
    }

    public static void underlineError(Recognizer recognizer, Token offendingToken) {
        int line = offendingToken.getLine();
        int charPositionInLine = offendingToken.getCharPositionInLine();
        CommonTokenStream tokens = (CommonTokenStream)recognizer.getInputStream();
        String input = tokens.getTokenSource().getInputStream().toString();
        String[] lines = input.split("\n");
        String errorLine = lines[line - 1];
        System.err.println(errorLine);

        for (int i = 0; i < charPositionInLine; i++) {
            if (errorLine.charAt(i) == '\t') {
                System.err.print("\t");
            } else {
                System.err.print(" ");
            }
        }

        int start = offendingToken.getStartIndex();
        int stop = offendingToken.getStopIndex();
        for (int i = start; i <= stop; i++) {
                System.err.print("~");
        }
        System.err.println();
    }

}
