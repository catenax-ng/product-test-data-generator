grammar TestDataScenario;
prog:
			'Name' SPLIT ID SEPARATOR
			'Version' SPLIT VERSION SEPARATOR
			'Entities' SPLIT
				entity+
			(statement)*
	;
entity:
			'Entity' SPLIT 
				'Name' SPLIT ID 
				'Version' SPLIT VERSION 
				'As' SPLIT ID
				SEPARATOR
	;

	
statement:
			generation |
			instance |			
			assign |
			foreach |
			declare |
			include
	;
	
generation:
			'Generate' ID 'times' INT 'As' SPLIT ID SEPARATOR
	;	
	
instance:
			'Instance' ID 'As' SPLIT ID template? SEPARATOR
	;
	
declare:
			'Var' ID ASSIGN assignment SEPARATOR
	;
	
assign:
			'Set' ID'.'ID ASSIGN assignment SEPARATOR
	;
	
assignment:
			(retrieve |
			random |
			scalar) (concat)*
	;
	
concat:
			'+' assignment
	;
	
retrieve:
			ID ('.' ID)*
	;
	
random:
			'Random' ID ('between' INT 'and' INT)?
	;
	
scalar:
			ID |
			STRING |
			INT |
			DOUBLE
	;

foreach:
			'For Each' ID 'As' SPLIT ID 'Do' SEPARATOR
				(statement)+
			'Done' SEPARATOR
	;
	
include:
			'Include ' ID DOUBLE SEPARATOR
	;
	
template:
			'from template ' ID VERSION 
	;

VERSION: [0-9]* ('.'|',') [0-9]* (('.'|',') [0-9])? ;

SPLIT: ':';
ASSIGN: '=';
SEPARATOR: ';';
NEWLINE : [\r\n]+ ;

ID		: [a-zA-Z0-9_]+ ;
STRING : '"' .*? '"' ;
INT     : [0-9]+ ;
DOUBLE	: [0-9]* ('.'|',') [0-9]* ;

COMMENT : '/*' (~('\n'|'\r'))* '*/' -> skip;

WS 		: [ \t\r\n]+ -> skip ;


