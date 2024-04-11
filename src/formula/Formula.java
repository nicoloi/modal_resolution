package formula;

import clauses.ClauseSet;
import clauses.LocalClause;
import literal.PropAtom;

public abstract class Formula {

    protected static Eta eta;

    /**
     * converts this formula to a set of clauses.
     * 
     * @return the representation of the formula as a set of clauses.
     */
    public ClauseSet toClauseSet() {
        eta = new Eta(this);

        System.out.println(eta);

        PropAtom t = eta.getPropVariable(this);
        ClauseSet cs = new ClauseSet(new LocalClause(t)); // {eta(phi)}
        return cs.union(this.R(t)); // {eta(phi)}  U  R(G(eta(phi) <-> phi))
    }

    protected abstract ClauseSet R(PropAtom t);
}