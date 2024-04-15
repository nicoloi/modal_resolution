package antlr4;

// Generated from FormulaExpression.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link FormulaExpressionParser}.
 */
public interface FormulaExpressionListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link FormulaExpressionParser#start}.
	 * @param ctx the parse tree
	 */
	void enterStart(FormulaExpressionParser.StartContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormulaExpressionParser#start}.
	 * @param ctx the parse tree
	 */
	void exitStart(FormulaExpressionParser.StartContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormulaExpressionParser#formula}.
	 * @param ctx the parse tree
	 */
	void enterFormula(FormulaExpressionParser.FormulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormulaExpressionParser#formula}.
	 * @param ctx the parse tree
	 */
	void exitFormula(FormulaExpressionParser.FormulaContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormulaExpressionParser#atomic_formula}.
	 * @param ctx the parse tree
	 */
	void enterAtomic_formula(FormulaExpressionParser.Atomic_formulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormulaExpressionParser#atomic_formula}.
	 * @param ctx the parse tree
	 */
	void exitAtomic_formula(FormulaExpressionParser.Atomic_formulaContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormulaExpressionParser#unary_connective}.
	 * @param ctx the parse tree
	 */
	void enterUnary_connective(FormulaExpressionParser.Unary_connectiveContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormulaExpressionParser#unary_connective}.
	 * @param ctx the parse tree
	 */
	void exitUnary_connective(FormulaExpressionParser.Unary_connectiveContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormulaExpressionParser#binary_connective}.
	 * @param ctx the parse tree
	 */
	void enterBinary_connective(FormulaExpressionParser.Binary_connectiveContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormulaExpressionParser#binary_connective}.
	 * @param ctx the parse tree
	 */
	void exitBinary_connective(FormulaExpressionParser.Binary_connectiveContext ctx);
}