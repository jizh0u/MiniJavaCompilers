/**
 * Created by Zhou on 2017/1/2.
 */
import java.util.Map;

import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.ParserRuleContext;

public class AssignmentChecker extends MiniJavaBaseListener {
    MiniJavaParser parser;

    Map<String, ClassDef> classes;

    ParseTreeProperty<Scope> scopes;

    Scope currentScope = null;

    public AssignmentChecker(Map<String, ClassDef> classes, ParseTreeProperty<Scope> scopes, MiniJavaParser parser){
        this.scopes = scopes;
        this.classes = classes;
        this.parser = parser;
    }

    private void saveScope(ParserRuleContext ctx, Scope scope) {
        scopes.put(ctx, scope);
    }

    @Override
    public void enterMainClassDeclaration(MiniJavaParser.MainClassDeclarationContext ctx) {
        ClassDef classDef = classes.get(ctx.ID().getText());
        currentScope = classDef;
        saveScope(ctx, classDef);
    }

    @Override
    public void enterClassDeclaration(MiniJavaParser.ClassDeclarationContext ctx) {
        ClassDef classDef = classes.get(ctx.ID().getText());
        currentScope = classDef;
        saveScope(ctx, classDef);

        ClassDef superClass = null;
        if (ctx.type() != null) {
            superClass = classes.get(ctx.type().getText());
            if (superClass == null) {
                ErrorPrinter.printNoSuperClassError(parser, ctx.type().ID().getSymbol(), ctx.type().getText());
            }
        }

        classDef.setSuperClass(superClass);

        //TODO: Check cyclic inheritance
    }

    @Override
    public void exitClassDeclaration(MiniJavaParser.ClassDeclarationContext ctx) {
        currentScope = currentScope.getEnclosingScope();
    }

    @Override
    public void enterMainMethod(MiniJavaParser.MainMethodContext ctx) {
        String methodName = "main()";
        Scope owner = currentScope;
        Method method = new Method(null, methodName, owner);

        currentScope.define(method);
        currentScope = method;

        saveScope(ctx, currentScope);
    }

    @Override
    public void exitMainMethod(MiniJavaParser.MainMethodContext ctx) {
        currentScope = currentScope.getEnclosingScope();
    }

    @Override
    public void enterMainMethodDeclaration(MiniJavaParser.MainMethodDeclarationContext ctx) {
        //TODO: Define String args
    }

    @Override
    public void enterMethodDeclaration(MiniJavaParser.MethodDeclarationContext ctx) {
        ClassDef returnType = null;

        if (ctx.type() != null) {
            returnType = classes.get(ctx.type().getText());
            if (returnType == null) {
                ErrorPrinter.printCanNotFindSymbolError(parser, ctx.type().ID().getSymbol(),
                        ctx.type().getText(), currentScope.getScopeName());
            }
        }

        String methodName = ctx.ID().getText() + "()";
        if (currentScope.lookupLocally(methodName) != null) {
            ErrorPrinter.printSymbolAlreadyDefinedError(parser, ctx.ID().getSymbol(), "method", methodName,
                    currentScope.getScopeName());
        }

        Scope owner = currentScope;
        Method method = new Method(returnType, methodName, owner);

        currentScope.define(method);
        currentScope = method;

        saveScope(ctx, currentScope);
    }

    @Override
    public void exitMethodDeclaration(MiniJavaParser.MethodDeclarationContext ctx) {
        currentScope = currentScope.getEnclosingScope();
    }

    @Override
    public void enterVarDeclaration(MiniJavaParser.VarDeclarationContext ctx) {
        String typeName = ctx.type().getText();
        String varName = ctx.ID().getText();
        if (currentScope.lookupLocally(varName) != null) {
            ErrorPrinter.printSymbolAlreadyDefinedError(parser, ctx.ID().getSymbol(), "variable", varName,
                    currentScope.getScopeName());
        }
        currentScope.define(new Symbol(varName, classes.get(typeName), false));
    }

    @Override
    public void enterFieldDeclaration(MiniJavaParser.FieldDeclarationContext ctx) {
        String typeName = ctx.type().getText();
        String varName = ctx.ID().getText();
        if (currentScope.lookupLocally(varName) != null) {
            ErrorPrinter.printSymbolAlreadyDefinedError(parser, ctx.ID().getSymbol(), "variable", varName,
                    currentScope.getScopeName());
        }
        currentScope.define(new Symbol(varName, classes.get(typeName), true));
    }

    @Override
    public void enterFormalParameter(MiniJavaParser.FormalParameterContext ctx) {
        ClassDef parameterType = null;

        if (ctx.type() != null) {
            parameterType = classes.get(ctx.type().getText());
            if (parameterType == null) {
                ErrorPrinter.printCanNotFindSymbolError(parser, ctx.type().ID().getSymbol(),
                        ctx.type().getText(), currentScope.getScopeName());
            }
        }

        Symbol parameter = new Symbol(ctx.ID().getText(), parameterType, false);

        ((Method) currentScope).addParameter(parameter);
    }
}
