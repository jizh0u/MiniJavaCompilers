/**
 * Created by Zhou on 2017/1/2.
 */

import org.antlr.v4.runtime.*;

public class UnderlineErrorListener extends BaseErrorListener {
    @Override
    public void syntaxError(Recognizer<?,?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
                            String msg, RecognitionException e) {
        ErrorPrinter.printFileNameAndLineNumber((Token)offendingSymbol);
        System.err.println("line " + line + ":" + charPositionInLine + " " + msg);
        ErrorPrinter.underlineError(recognizer, (Token)offendingSymbol);
    }
}
