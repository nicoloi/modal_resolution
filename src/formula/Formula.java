package formula;

import clauses.ClauseSet;

public abstract class Formula {

    /**
     * converts this formula to a set of clauses.
     * 
     * @return the representation of the formula as a set of clauses.
     */
    public abstract ClauseSet toClauseSet();
}