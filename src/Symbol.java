/**
 * Created by Zhou on 2017/1/2.
 */

public class Symbol {
    private String name;

    private ClassDef type;

    private boolean isField;

    public boolean isField(){
        return isField;
    }

    public Symbol(String name, boolean isField) {
        this.name = name;
        this.isField = isField;
    }

    public Symbol(String name, ClassDef type, boolean isField) {
        this(name, isField);
        this.type = type;
    }

    public ClassDef getType(){return type;}

    public String getName() { return name; }

    public String toString() {
        if ( type!=null ) return '<'+getName()+":"+type+'>';
        return getName();
    }
}
