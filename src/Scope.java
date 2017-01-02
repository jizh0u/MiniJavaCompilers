/**
 * Created by Zhou on 2017/1/2.
 */

import java.util.*;

public interface Scope {
    public String getScopeName();

    public Scope getEnclosingScope();

    public void define(Symbol sym);

    public void initialize(Symbol sym);

    public Symbol lookup(String name);

    public Symbol lookupLocally(String name);

    public boolean hasBeenInitialized(String name);

    public Set<Symbol> getInitializedVariables();
}
