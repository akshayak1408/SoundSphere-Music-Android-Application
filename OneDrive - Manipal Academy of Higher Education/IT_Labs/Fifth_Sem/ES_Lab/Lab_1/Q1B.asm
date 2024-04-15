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
	LDR R0,=(SRC+36)
	LDR R1,=(SRC+56)
	MOV R3,#10
	
UP	LDR R2,[R0],#4
	STR R2,[R1],#4
	SUBS R3,#1
	BNE UP
STOP B STOP
	
	AREA mydata, DATA, READWRITE
SRC DCD 0x12000000, 0x32000000, 0xEF000000, 0x12000000, 0xFF000000, 0xAB000000, 0x32000000, 0x1A000000, 0xEE000000, 0xBE000000
	END