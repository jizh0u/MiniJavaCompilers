/**
 * Created by Zhou on 2017/1/2.
 */
import org.antlr.v4.runtime.*;

import java.rmi.registry.Registry;
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

    public static void printErrorMessage(Recognizer recognizer, Token offendingToken, String message) {
        ErrorPrinter.printFileNameAndLineNumber(offendingToken);
        System.err.println(message);
        ErrorPrinter.underlineError(recognizer, offendingToken);
    }

    public static void printDuplicateClassError(Recognizer recognizer, Token offendingToken, String className) {
        String message = "error: duplicate class: " + className;
        ErrorPrinter.printErrorMessage(recognizer, offendingToken, message);
        System.err.println();
    }

    public static void printNoSuperClassError(Recognizer recognizer, Token offendingToken, String className) {
        String message = "error: can not find symbol.";
        ErrorPrinter.printErrorMessage(recognizer, offendingToken, message);
        System.err.println("Symbol: Class " + className);
        System.err.println();
    }

    public static void printCanNotFindSymbolError(Recognizer recognizer, Token offendingToken, String symbolName,
                                                  String className) {
        String message = "error: can not find symbol.";
        ErrorPrinter.printErrorMessage(recognizer, offendingToken, message);
        System.err.println("Symbol: Class " + symbolName);
        System.err.println("Location: Class " + className);
        System.err.println();
    }

    public static void printSymbolAlreadyDefinedError(Recognizer recognizer, Token offendingToken, String symbolType,
                                                      String symbol, String className) {
        String message = "error: " + symbolType + " " + symbol + " already defined in class " + className;
        ErrorPrinter.printErrorMessage(recognizer, offendingToken, message);
        System.err.println();
    }
}
