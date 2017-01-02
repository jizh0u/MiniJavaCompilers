/**
 * Created by Zhou on 2017/1/2.
 */
import java.util.Map;

public class ClassNameChecker extends MiniJavaBaseListener {
    private Map<String, ClassDef> classes;
    private MiniJavaParser parser;

    public ClassNameChecker(MiniJavaParser parser, Map<String, ClassDef> classes) {
        this.parser = parser;
        this.classes = classes;
    }

    @Override
    public void enterClassDeclaration(MiniJavaParser.ClassDeclarationContext ctx) {
        ClassDef curClass = new ClassDef(ctx.ID().getText());
        if (classes.put(curClass.getScopeName(), curClass) != null) {
            ErrorPrinter.printDuplicateClassError(parser, ctx.ID().getSymbol(), curClass.getScopeName());
        }
    }

    @Override
    public void enterMainClassDeclaration(MiniJavaParser.MainClassDeclarationContext ctx) {
        ClassDef curClass = new ClassDef(ctx.ID().getText());
        classes.put(curClass.getScopeName(), curClass);

        curClass = new ClassDef("int[]");
        classes.put(curClass.getScopeName(), curClass);

        curClass = new ClassDef("int");
        classes.put(curClass.getScopeName(), curClass);

        curClass = new ClassDef("boolean");
        classes.put(curClass.getScopeName(), curClass);
    }
}
