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
        this.atm = new PropAtom(name);
    }

    public AtomicFormula(PropAtom atm) {
        this.atm = atm;
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
    public int hashCode() {
        return atm.getName().hashCode();
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public ClauseSet toClauseSet() {

        // eta(this) = this
        // eta = new Eta(this);
        //TODO mettere: return this.classicClausification();
        Literal t = this.toLiteral();
        ClauseSet cs = new ClauseSet(new LocalClause(t));
        return cs;
    }


    @Override
    protected ClauseSet classicClausification() {
        return new ClauseSet(new GlobalClause(atm));
    }
}
