package formula;

import java.util.HashMap;
import java.util.Map;
import literal.PropAtom;
import static connective.Connective.*;

/**
 * The Eta class maps a logical formula and its subformulas to propositional
 * variables.
 */
public class Eta {

    //STATIC FIELDS
    private static int i = 0;

    //FIELDS
    private Map<Formula, PropAtom> m;


    //CONSTRUCTORS

    /**
     * Constructs an Eta object from the given formula.
     * @param f The logical formula from which to create the Eta object.
     */
    public Eta(Formula f) {
        m = new HashMap<>();
        this.createMap(f);
    }

    //METHODS

     /**
     * Creates the map by associating the given formula and its subformulas
     * with propositional variables.
     * @param f The logical formula from which to create the map.
     */
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

    /**
     * Returns the propositional variable associated with the given formula.
     * @param f The logical formula for which to obtain the propositional variable.
     * @return The propositional variable associated with the formula.
     * @throws NullPointerException If the given formula has no association.
     */
    public PropAtom getPropVariable(Formula f) {
        if (m.get(f) == null) 
            throw new NullPointerException("Formula " + f + " has no association");

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
