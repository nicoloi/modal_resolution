package resolution;

import clauses.*;
import literal.Literal;

/**
 * this class represents a resolution step, used to track how the resolution method works
 */
public class Step {

    //STATIC FIELDS
    private static int count = 1;

    //FIELDS
    private int stepNumber; //the number of this step.
    private Clause premise1;
    private Clause premise2;
    private Clause conclusion;
    private Literal lit1; //the literal used by all inference rules.
    private Literal lit2; //the literal used by LERES and GERES rules.
    private String inferenceRule;
    private boolean isTautology; //is true if the conclusion is a tautology
    private boolean isAlreadyPresent; //is true if the conclusion is already present in the set. 

    //CONSTRUCTORS

    //constructor for GRES and LRES rules
    public Step(Clause premise1, Clause premise2, Clause conclusion, Literal lit1, String rule) {
        this.premise1 = premise1;
        this.premise2 = premise2;
        this.conclusion = conclusion;
        this.lit1 = lit1;
        this.lit2 = null;
        this.inferenceRule = rule;
        
        this.stepNumber = count;
        this.isTautology = false;
        this.isAlreadyPresent = false;
        count++;
    }
    
    //constructor for GERES and LERES rules
    public Step(Clause premise1, Clause premise2, Clause conclusion, Literal lit1, Literal lit2, String rule) {
        this.premise1 = premise1;
        this.premise2 = premise2;
        this.conclusion = conclusion;
        this.lit1 = lit1;
        this.lit2 = lit2;
        this.inferenceRule = rule;

        this.stepNumber = count;
        this.isTautology = false;
        this.isAlreadyPresent = false;
        count++;
    }

    //METHODS

    /**
     * use this method to indicate that the conclusion of the resolution step is a tautology
     */
    public void setTautology() {
        this.isTautology = true;
    }

    /**
    * use this method to indicate that the conclusion is already present 
    * in the set considered by the resolution method
    */
    public void setAlreadyPresent() {
        this.isAlreadyPresent = true;
    }

    @Override
    public String toString() {

        StringBuilder res = new StringBuilder();

        res.append("STEP NUMBER " + stepNumber + ":\n");
        res.append("First premise: " + premise1.toString() + "\n");
        res.append("Second premise: " + premise2.toString() + "\n");
        res.append("Conclusion: " + conclusion.toString() + "\n");

        if (inferenceRule.equals("LRES") || inferenceRule.equals("GRES")) {
            res.append("obtained by removing the literal \"" + lit1 + "\" and its opposite.\n");
        } else {
            res.append("obtained by removing the literals \"" + lit1 + "\" and \"" + lit2 + "\".\n");
        }
        
        res.append("Rule applied: " + inferenceRule + "\n");

        if (isTautology) {
            res.append("The conclusion is DISCARDED because it is a tautology.\n");
        }

        if (isAlreadyPresent) {
            res.append("The conclusion is DISCARDED because it is already present in the set.\n");
        }

        res.append("__________________________________________________________\n");

        return res.toString();
    }
}
