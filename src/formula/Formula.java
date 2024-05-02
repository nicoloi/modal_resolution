package formula;

import clauses.ClauseSet;
import clauses.LocalClause;
import literal.PropAtom;

/**
 * This abstract class represents a logical formula in modal logics.
 * Subclasses of Formula define atomic formulas and compound formulas.
 */
public abstract class Formula {

    protected static Eta eta;

    /**
     * converts this formula to a set of clauses.
     * 
     * @return the representation of the formula as a set of clauses.
     */
    public ClauseSet toClauseSet() {
        eta = new Eta(this);

        // System.out.println("\n" + eta);

        PropAtom t = eta.getPropVariable(this);
        ClauseSet cs = new ClauseSet(new LocalClause(t)); // {eta(phi)}
        return cs.union(this.R(t)); // {eta(phi)}  U  R(G(eta(phi) <-> phi))
    }

    /**
     * Applies the function R(G(eta(this) <-> this))
     * 
     * @param t The propositional variable that represent the value of eta(this).
     * @return The set of clauses generated by applying the R function.
     */
    protected abstract ClauseSet R(PropAtom t);
}