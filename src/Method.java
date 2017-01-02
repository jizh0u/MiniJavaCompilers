/**
 * Created by Zhou on 2017/1/2.
 */
import java.util.*;

public class Method extends Symbol implements Scope {
    private Map<String, Symbol> parameters = new LinkedHashMap<>();
    private Scope owner;
    private Scope body;
    private Map<String, Symbol> locals = new HashMap<>();
    private Map<String, Symbol> initializedVariables = new HashMap<>();

    public Method(ClassDef returnType, String name, Scope owner){
        super(name, returnType, true);
        this.owner=owner;
    }

    @Override
    public String getScopeName() {
        return this.name;
    }

    @Override
    public Scope getEnclosingScope() {
        return owner;
    }

    @Override
    public void define(Symbol sym) {
        locals.put(sym.getName(), sym);
    }

    @Override
    public void initialize(Symbol sym) {
        initializedVariables.put(sym.getName(), sym);
    }

    @Override
    public Symbol lookup(String name) {
        if (parameters.containsKey(name)) {
            return parameters.get(name);
        } else if (locals.containsKey(name)) {
            return locals.get(name);
        } else {
            return this.getEnclosingScope().lookup(name);
        }
    }

    @Override
    public Symbol lookupLocally(String name) {
        if (parameters.containsKey(name)) {
            return parameters.get(name);
        } else {
            return locals.get(name);
        }
    }

    @Override
    public boolean hasBeenInitialized(String name) {
        if (initializedVariables.containsKey(name) || parameters.containsKey(name)) {
            return true;
        } else {
            return this.getEnclosingScope().hasBeenInitialized(name);
        }
    }

    @Override
    public Set<Symbol> getInitializedVariables() {
        return new HashSet<Symbol>(this.initializedVariables.values());
    }

    public void addParameter(Symbol parameter) {
        parameters.put(parameter.getName(), parameter);
    }

    public List<Symbol> getParameterList() {
        return new ArrayList<Symbol>(parameters.values());
    }

    public List<ClassDef> getParameterListDefinition() {
        List<Symbol> parameterList = getParameterList();
        List<ClassDef> parameterListDefinition = new ArrayList<>();
        for (Symbol var: parameterList) {
            parameterListDefinition.add(var.getType());
        }
        return parameterListDefinition;
    }

    public String toString() {
        return name;
    }

    public String fullName() {
        String fullName = this.getType().toString() + " ";
        fullName += name;
        fullName = fullName.substring(0, fullName.length() - 1);
        boolean hasParameter = false;
        for (Symbol parameter : parameters.values()) {
            fullName += parameter.getType().getScopeName() + ", ";
            hasParameter=true;
        }
        if (hasParameter) {
            fullName = fullName.substring(0, fullName.length() - 2);
        }
        fullName += ")";
        return fullName;
    }
}
