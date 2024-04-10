package formula;

import connective.Connective;
import static connective.Connective.*;
import clauses.ClauseSet;

import java.util.Arrays;
import java.util.Objects;

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
        subFormulas = new Formula[2];

        switch (mainConnective) {
            case OR:
                subFormulas = new Formula[2];
                subFormulas[0] = f1;
                subFormulas[1] = f2;
                this.mainConnective = OR;
                break;
            case AND:
                //(f1 & f2) becomes ~(~f1 | ~f2)
                subFormulas = new Formula[1];
                subFormulas[0] = new CompoundFormula(OR, new CompoundFormula(NOT, f1),
                    new CompoundFormula(NOT, f2));
                this.mainConnective = NOT;
                break;
            case IMPLIES:
                //(f1 -> f2) becomes (~f1 | f2)
                subFormulas = new Formula[2];
                subFormulas[0] = new CompoundFormula(NOT, f1);
                subFormulas[1] = f2;
                this.mainConnective = OR;
                break;
            case IFF:
                //(f1 <-> f2) becomes ~(~(~f1 | f2) | ~(f1 | ~f2))
                CompoundFormula left = new CompoundFormula(NOT, new CompoundFormula(OR, 
                new CompoundFormula(NOT, f1), f2));
                
                CompoundFormula right = new CompoundFormula(NOT, new CompoundFormula(OR, 
                f1, new CompoundFormula(NOT, f2)));
                
                subFormulas = new Formula[1];
                subFormulas[0] = new CompoundFormula(OR, left, right);
                this.mainConnective = NOT;
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
        subFormulas = new Formula[1];
        subFormulas[0] = f;

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toClauseSet'");
    }

}
