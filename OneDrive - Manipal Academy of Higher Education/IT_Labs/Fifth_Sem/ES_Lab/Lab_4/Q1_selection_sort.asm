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
	LDR R0,=ARR
	MOV R1,#9; indicates outer loop counter
	MOV R2,#0; indicates i
	MOV R3,#0; indicates j
	
INNER
	RSB R5,R2,#9
UP	LDR R4,[R0,R3]
	ADD R3,#4
	LDR R6,[R0,R3]
	CMP R4,R6
	BLT down
	STR R4,[R0,R3]
	SUB R3,#4
	STR R6,[R0,R3]
	B down2
down	
	SUB R3,#4
down2	
	ADD R3,#4
	SUBS R5,#1
	BNE UP
	
	ADD R2,#1
	MOV R3,#0
	SUBS R1,#1
	BNE INNER
STOP
	B STOP
	AREA mydata, DATA, READWRITE
ARR DCD 0x000000004,0x00000002,0x00000001,0x00000003,0x00000007,0x00000009,0x000000010,0x00000005,0x00000006,0x00000008
	END