package formula;

import literal.*;
import clauses.*;

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
    public ClauseSet toClauseSet() {
        //TODO
        return null;
    }
}
