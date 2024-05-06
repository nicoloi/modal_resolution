package formula;

import literal.*;
import clauses.*;
import static connective.Connective.*;
import connective.Connective;
import java.util.Arrays;
import java.util.Objects;

/**
 * this class represents a compound formula. which consists of a formula
 * obtained using a modal connective or propositional connective on other
 * formulas, which can be atomic or themselves composite.
 */
public class CompoundFormula extends Formula {

    //FIELDS
    private final Connective mainConnective;
    private Formula[] subFormulas;


    //CONSTRUCTORS
    
    /**
     * 
     * Use this constructor only for binary connectives.
     * 
     * @param mainConnective the main connective of this
     * @param f1 the subformula that is located to the left of the connective
     * @param f2 the subformula that is located to the right of the connective
     * @throws IllegalArgumentException if mainConnective is unary
     * @throws NullPointerException if the formulas f1 or f2 are null
     */
    public CompoundFormula(Connective mainConnective, Formula f1, Formula f2) {

        Objects.requireNonNull(f1, "one or both formulas are null.");
        Objects.requireNonNull(f2, "one or both formulas are null.");  
        this.subFormulas = new Formula[2];

        switch (mainConnective) {
            case OR:
                this.subFormulas = new Formula[2];
                this.subFormulas[0] = f1;
                this.subFormulas[1] = f2;
                this.mainConnective = OR;
                break;
            case AND:
                //(f1 & f2) becomes ~(~f1 | ~f2)
                // this.subFormulas = new Formula[1];
                // this.subFormulas[0] = new CompoundFormula(OR, new CompoundFormula(NOT, f1),
                //     new CompoundFormula(NOT, f2));
                // this.mainConnective = NOT;
                this.subFormulas = new Formula[2];
                this.subFormulas[0] = f1;
                this.subFormulas[1] = f2;
                this.mainConnective = AND;
                break;
            case IMPLIES:
                //(f1 -> f2) becomes (~f1 | f2)
                this.subFormulas = new Formula[2];
                this.subFormulas[0] = new CompoundFormula(NOT, f1);
                this.subFormulas[1] = f2;
                this.mainConnective = OR;
                break;
            case IFF:
                //(f1 <-> f2) becomes (~f1 | f2) & (f1 | ~f2)
                CompoundFormula left = new CompoundFormula(IMPLIES, f1, f2);
                
                CompoundFormula right = new CompoundFormula(IMPLIES, f2, f1);
                
                this.subFormulas = new Formula[2];
                this.subFormulas[0] = left;
                this.subFormulas[1] = right;
                this.mainConnective = AND;
                break;
            default:
                //case of unary connective
                throw new IllegalArgumentException("You must call this constructor only for binary connective."); 
        }
    }

    /**
     * Use this constructor only for unary connective.
     * 
     * @param mainConnective the main connective 
     * @param f the formula on which to apply the unary connective
     * @throws IllegalArgumentException if mainConnective is a binary connective.
     * @throws NullPointerException if formula f is null
     */
    public CompoundFormula(Connective mainConnective, Formula f) {

        if (mainConnective != NOT && mainConnective != BOX) {
            throw new IllegalArgumentException("You must call this constructor only for unary connective.");
        }

        Objects.requireNonNull(f, "The formula is null");
        this.subFormulas = new Formula[1];
        this.subFormulas[0] = f;

        this.mainConnective = mainConnective;
    }


    //METHODS

    /**
     * 
     * @return an array that contains the subformulas of the formula this.
     */
    public Formula[] getSubformulas() {
        return subFormulas;
    }

    /**
     *
     * @return the left subformula of this compound formula.
     */
    public Formula getLeftSubformula() {
        return this.subFormulas[0];
    }

    /**
     * 
     * @return the right subformula of this compound formula.
     * @throws UnsupportedOperationException if main connective is unary.
     */
    public Formula getRightSubformula() {
        if (this.mainConnective == NOT || this.mainConnective == BOX) {
            throw new UnsupportedOperationException("this method is not supported for unary connective");
        }

        return this.subFormulas[1];
    }

    /**
     * 
     * @return the main connective of the formula this.
     */
    public Connective getMainConnective() {
        return mainConnective;
    }

    
    @Override
    public String toString() {

        StringBuilder res = new StringBuilder();

        if (this.mainConnective == NOT) {
            res.append("~" + this.getLeftSubformula().toString());
        } else if (this.mainConnective == BOX) {
            res.append("#" + this.getLeftSubformula().toString());
        } else {
            res.append("(" + subFormulas[0].toString() + " " + this.mainConnective.toString() 
                + " " + subFormulas[1].toString() + ")");
        }

        return res.toString();
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof CompoundFormula)) {
            return false;
        }

        CompoundFormula other = (CompoundFormula) obj;
        if (this.mainConnective != other.mainConnective) {
            return false;
        }

        if (this.subFormulas.length == 1) {
            //case of unary connective
            return Arrays.equals(this.subFormulas, other.subFormulas);
        }

        //case of binary connective
        if (!Arrays.equals(this.subFormulas, other.subFormulas)) {
            // If not equal, try comparing with subFormulas in reversed order
            Formula[] reversed = new Formula[2];
            reversed[0] = other.subFormulas[1];
            reversed[1] = other.subFormulas[0];
            if (!Arrays.equals(this.subFormulas, reversed)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return 1;
    }






    @Override
    public ClauseSet toClauseSet() {
        eta = new Eta(this);
    
        System.out.println("\n" + eta);
    
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
    private ClauseSet R(PropAtom t) {

        if ((this.mainConnective == NOT) && (this.getLeftSubformula() instanceof CompoundFormula)) {
            CompoundFormula sub = (CompoundFormula) this.getLeftSubformula();

            if (sub.mainConnective == NOT) {
                //double negation

                if (sub.getLeftSubformula() instanceof CompoundFormula) {
                    CompoundFormula sub_sub = (CompoundFormula) sub.getLeftSubformula();
                    return sub_sub.R(eta.getPropVariable(sub_sub));
                } else {
                    return new ClauseSet();
                }
            }
        }

        Formula psi = this.getLeftSubformula(); // psi
        PropAtom etaPsi = eta.getPropVariable(psi); // eta(psi)
        ClauseSet res = new ClauseSet();

        switch (this.mainConnective) {
            case NOT:
                // {G({~t, ~eta(psi)}), G({t, eta(psi)})}  U  R(G(eta(psi) <-> psi))

                GlobalClause gc1 = new GlobalClause(); // G({~t, ~eta(psi)})
                gc1.add(t.getOpposite());
                gc1.add(etaPsi.getOpposite());
                res.add(gc1);

                GlobalClause gc2 = new GlobalClause(); // G({t, eta(psi)})
                gc2.add(t);
                gc2.add(etaPsi);
                res.add(gc2);

                if (psi instanceof CompoundFormula) {
                    CompoundFormula cf_psi = (CompoundFormula) psi;
                    return res.union(cf_psi.R(etaPsi)); // res  U  R(G(eta(psi) <-> psi))
                }

                return res;
            case OR:
                // {G({~t, eta(psi), eta(psi_prime)}), G({t, ~eta(psi)}), 
                // G({t, ~eta(psi_prime)})}  U  R(G(eta(psi) <-> psi)) 
                // U  R(G(eta(psi_prime) <-> psi_prime))
                Formula psi_prime = this.getRightSubformula();
                PropAtom etaPsi_prime = eta.getPropVariable(psi_prime);

                gc1 = new GlobalClause(); // G({~t, eta(psi), eta(psi_prime)})
                gc1.add(t.getOpposite());
                gc1.add(etaPsi);
                gc1.add(etaPsi_prime);
                res.add(gc1);

                gc2 = new GlobalClause(); // G({t, ~eta(psi)})
                gc2.add(t);
                gc2.add(etaPsi.getOpposite());
                res.add(gc2);

                GlobalClause gc3 = new GlobalClause(); // G({t, ~eta(psi_prime)})}
                gc3.add(t);
                gc3.add(etaPsi_prime.getOpposite());
                res.add(gc3);

                if ((psi instanceof CompoundFormula) && (psi_prime instanceof CompoundFormula)) {
                    CompoundFormula cf_psi = (CompoundFormula) psi;
                    CompoundFormula cf_psi_prime = (CompoundFormula) psi_prime;

                    // res  U  R(G(etaPsi <-> psi))  U  R(G(etaPsi_prime <-> psi_prime))
                    return (res.union(cf_psi.R(etaPsi))).union(cf_psi_prime.R(etaPsi_prime));
                } else if (psi instanceof CompoundFormula) {
                    CompoundFormula cf_psi = (CompoundFormula) psi;

                    //res   U   R(G(etaPsi <-> psi))
                    return res.union(cf_psi.R(etaPsi));
                } else if (psi_prime instanceof CompoundFormula) {
                    CompoundFormula cf_psi_prime = (CompoundFormula) psi_prime;

                    // res   U   R(G(etaPsi_prime <-> psi_prime))
                    return res.union(cf_psi_prime.R(etaPsi_prime));
                }

                return res;  //psi and psi_prime are both atomic formulas.
            case BOX:
                // {G({~t, #eta(psi)}), G({t, ~#eta(psi)})}  U  R(G(eta(psi) <-> psi))
                ModalAtom boxEtaPsi = new ModalAtom(etaPsi.getName());

                gc1 = new GlobalClause(); // G({~t, #eta(psi)})
                gc1.add(t.getOpposite());
                gc1.add(boxEtaPsi);
                res.add(gc1);

                gc2 = new GlobalClause(); // G({t, ~#eta(psi)})
                gc2.add(t);
                gc2.add(boxEtaPsi.getOpposite());
                res.add(gc2);

                if (psi instanceof CompoundFormula) {
                    CompoundFormula cf_psi = (CompoundFormula) psi;
                    return res.union(cf_psi.R(etaPsi)); // res  U  R(G(eta(psi) <-> psi))
                }

                return res; //psi is an atomic formula
            case AND:
                psi_prime = this.getRightSubformula();
                etaPsi_prime = eta.getPropVariable(psi_prime);

                gc1 = new GlobalClause(); // G({t, ~eta(psi), ~eta(psi_prime)})
                gc1.add(t);
                gc1.add(etaPsi.getOpposite());
                gc1.add(etaPsi_prime.getOpposite());
                res.add(gc1);

                gc2 = new GlobalClause(); // G({~t, eta(psi)})
                gc2.add(t.getOpposite());
                gc2.add(etaPsi);
                res.add(gc2);

                gc3 = new GlobalClause(); // G({~t, eta(psi_prime)})}
                gc3.add(t.getOpposite());
                gc3.add(etaPsi_prime);
                res.add(gc3);


                if ((psi instanceof CompoundFormula) && (psi_prime instanceof CompoundFormula)) {
                    CompoundFormula cf_psi = (CompoundFormula) psi;
                    CompoundFormula cf_psi_prime = (CompoundFormula) psi_prime;

                    // res  U  R(G(etaPsi <-> psi))  U  R(G(etaPsi_prime <-> psi_prime))
                    return (res.union(cf_psi.R(etaPsi))).union(cf_psi_prime.R(etaPsi_prime));
                } else if (psi instanceof CompoundFormula) {
                    CompoundFormula cf_psi = (CompoundFormula) psi;

                    //res   U   R(G(etaPsi <-> psi))
                    return res.union(cf_psi.R(etaPsi));
                } else if (psi_prime instanceof CompoundFormula) {
                    CompoundFormula cf_psi_prime = (CompoundFormula) psi_prime;

                    // res   U   R(G(etaPsi_prime <-> psi_prime))
                    return res.union(cf_psi_prime.R(etaPsi_prime));
                }

                return res;
            default:
                throw new UnsupportedOperationException("the formula is not valid");
        }
    }





}
