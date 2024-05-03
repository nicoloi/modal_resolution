package formula;

import clauses.ClauseSet;
// import clauses.LocalClause;
// import literal.PropAtom;

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
    public abstract ClauseSet toClauseSet();
}