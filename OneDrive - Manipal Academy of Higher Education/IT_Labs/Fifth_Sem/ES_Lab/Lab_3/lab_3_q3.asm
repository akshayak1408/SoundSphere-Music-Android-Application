	AREA RESET, DATA, READONLY
	EXPORT __Vectors
__Vectors
	DCD 0x40001000
	DCD Reset_Handler
	ALIGN
	AREA mycode, CODE, READONLY
	ENTRY
	EXPORT Reset_Handler
Reset_Handler
	LDR R0,=HEX
	LDR R1,=BCD
	LDR R2,[R0];Initial hex value
	MOV R3,#0x10;To multiply with value
	MOV R4,#1;Multiplicate initialized to 1
	
	CMP R2,#0x0A
	MOV R6,R2
	BLT final
	MOV R6,#0
	
UP	CMP R2,#0
	BEQ final
	BL DIV
	MUL R5,R4
	ADD R6,R5
	MUL R4,R3
	MOV R2,R7
	MOV R7,#0
	B UP
final
	STR R6,[R1]
	
STOP B STOP
DIV CMP R2,#0x0A
	BCC down
	SUB R2,#0x0A
	ADD R7,#1
	B DIV
down
	MOV R5,R2
	BX LR
	
HEX DCD 0xAC
	AREA mydata, DATA, READWRITE
BCD DCD 0x0
	END