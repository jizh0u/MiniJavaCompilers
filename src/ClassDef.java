/**
 * Created by Zhou on 2017/1/2.
 */
import java.util.*;

public class ClassDef implements Scope {
    private ClassDef superClass;

    private String name;

    private Map<String, Symbol> symTable = new HashMap<String, Symbol>();

    public ClassDef(String name) {
        this.name = name;
    }

    public void setSuperClass(ClassDef superKlass) {
        this.superClass = superKlass;
    }

    public ClassDef getSuperClass() {
        return this.superClass;
    }

    @Override
    public String getScopeName() {
        return name;
    }

    @Override
    public Scope getEnclosingScope() {
        return null;
    }

    @Override
    public void define(Symbol sym) {
        symTable.put(sym.getName(), sym);
    }

    @Override
    public void initialize(Symbol sym) {
        assert false;
    }

    public boolean isDescendantOf(ClassDef other) {
        if (this.superClass == null && other != this) {
            return false;
        } else if (other == this) {
            return true;
        } else {
            return this.superClass.isDescendantOf(other);
        }
    }

    @Override
    public Symbol lookup(String name) {
        Symbol symbol = null;
        for (ClassDef classDef = this; symbol == null && classDef != null; classDef=classDef.getSuperClass()) {
            symbol = classDef.symTable.get(name);
        }
        return symbol;
    }

    @Override
    public Symbol lookupLocally(String name) {
        return symTable.get(name);
    }

    @Override
    public boolean hasBeenInitialized(String name) {
        return this.lookup(name) != null;
    }

    @Override
    public Set<Symbol> getInitializedVariables() {
        assert false;
        return null;
    }

    public String toString() {
        return name;
    }
}
