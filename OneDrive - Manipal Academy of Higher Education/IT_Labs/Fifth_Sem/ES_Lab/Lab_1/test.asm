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
	MOV R0,#10
	MOV R1,#0x1
	MOV R2,#2_1010101010
	MOV R3,#-1
	LDR R4,=0x12345678
	END                     