grammar FormulaExpression;

start : formula EOF;

formula : atomic_formula
        | '(' formula ')' 
        | unary_connective formula
        | formula binary_connective formula
        ;

atomic_formula : LITERAL ;

unary_connective : NOT | BOX;

binary_connective : AND | OR | IMPLIES | IFF;

//token
AND : '&' ;
OR : '|' ;
IMPLIES : '->' ;
NOT : '~' ;
IFF : '<->';
BOX : '#';

LITERAL : [a-z]+ ;
WS : [ \t\r\n]+ -> skip ;


