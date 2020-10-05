grammar Tac;

prog: instruction+;

instruction : (label)? assign NEWLINE
            | (label)? go NEWLINE
            | (label)? iffalse NEWLINE
            | (label)? iftrue NEWLINE
            | (label)? array NEWLINE
            | (label)? print NEWLINE
            | NEWLINE
            ;

label       : ID ':';

value       : ID    # Id
            | NUM   # Num
            ;

assign      : ID '=' value            # Set
            | ID '=' value '+' value  # Sum
            | ID '=' value '-' value  # Sub
            | ID '=' value '*' value  # Mul
            | ID '=' value '/' value  # Div
            | ID '=' value '<' value  # Le
            | ID '=' value '==' value # Eq
            | ID '=' value '&&' value # And
            | ID '=' value '||' value # Or
            ;

go          : 'goto' ID;

iffalse     : 'ifFalse' ID go;

iftrue      : 'ifTrue' ID go;

array       : ID '[' value ']' '=' value    # ArrVar
            | ID '=' ID '[' value ']'       # VarArr
            ;

print       : 'print(' value ')';

fragment UPPERCASE  : [A-Z];
fragment LOWERCASE  : [a-z];
fragment DIGIT      : [0-9];

ID      : (LOWERCASE | UPPERCASE | '_') (LOWERCASE | UPPERCASE | '_' | DIGIT)* ;
NUM     : DIGIT+;
NEWLINE : ('\r'? '\n' | '\r')+ ;
WS      : (' ' | '\t')+ -> skip;
