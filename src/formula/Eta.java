package formula;

import java.util.HashMap;
import java.util.Map;
import literal.PropAtom;
import static connective.Connective.*;

public class Eta {

    //STATIC FIELDS
    private static int i = 1;

    //FIELDS
    private Map<Formula, PropAtom> m;


    //CONSTRUCTORS
    public Eta(Formula f) {
        m = new HashMap<>();
        this.createMap(f);
    }

    //METHODS

    private void createMap(Formula f) {
        PropAtom propVar = new PropAtom("$p" + i);  // $p1, $p2, etc...
        i++;

        if (!m.containsKey(f)) {
            m.put(f, propVar);
        }

        if (f instanceof CompoundFormula) {
            CompoundFormula cf = (CompoundFormula) f;
            if (cf.getMainConnective() == NOT || cf.getMainConnective() == BOX) {
                this.createMap(cf.getLeftSubformula());
            } else {
                this.createMap(cf.getLeftSubformula());
                this.createMap(cf.getRightSubformula());
            }
        }
    }

    
    public PropAtom getPropVariable(Formula f) {
        return m.get(f);
    }

    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Formula k : m.keySet()) {
            sb.append(k + ":  " + m.get(k) + "\n");
        }

        return sb.toString();
    }
}
