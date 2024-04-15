package formula;

import literal.*;
import clauses.*;

/**
 * This class represents an atomic formula. An atomic formula 
 * contains only a propositional literal.
 */
public class AtomicFormula extends Formula {

    //FIELDS
    private PropAtom atm;

    //CONSTRUCTORS
    public AtomicFormula(String name) {
        atm = new PropAtom(name);
    }


    //METHODS

    /**
     * 
     * @return the name of this atomic formula.
     */
    public String getName() {
        return this.atm.getName();
    }

    /**
     * 
     * @return the literal that represent this atomic formula.
     */
    public Literal toLiteral() {
        return atm;
    }

    private boolean equals(AtomicFormula af) {
        return this.atm.equals(af.atm);
    }
    
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof AtomicFormula) && this.equals((AtomicFormula)obj);
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    //base case of function R
    protected ClauseSet R(PropAtom t) {
        ClauseSet res = new ClauseSet();

        GlobalClause gc1 = new GlobalClause(); // G({~t, p})
        gc1.add(t.getOpposite());
        gc1.add(this.toLiteral());

        GlobalClause gc2 = new GlobalClause(); // G({t, ~p})
        gc2.add(t);
        gc2.add(this.toLiteral().getOpposite());

        res.add(gc1);
        res.add(gc2);

        return res;
    }
}
