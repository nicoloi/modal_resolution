package antlr4;

import formula.*;
import org.antlr.v4.runtime.*;

public class ParseFormula {

    /**
     * Parses the string argument as a formula.
     * 
     * @param formulaStr the string to be parsed.
     * @return the formula obtained by parsing the specified string.
     *         Return null, if the specified string is a non valid formula.
     */
    public static Formula parse(String formulaStr) {
        CharStream input = CharStreams.fromString(formulaStr);
        FormulaExpressionLexer lexer = new FormulaExpressionLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        FormulaExpressionParser parser = new FormulaExpressionParser(tokens);

        //create listener
        FormulaListenerImplementation listener = new FormulaListenerImplementation();

        //adds the listener to the parser
        parser.addParseListener(listener);

        try {
            //analyze the input and get the corresponding formula
            parser.start();
            return listener.getFormula();
        } catch (Exception e) {
            return null;
        }
    }
}
