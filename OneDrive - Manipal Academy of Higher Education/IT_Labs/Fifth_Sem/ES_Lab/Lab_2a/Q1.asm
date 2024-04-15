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
	LDR R0,=SRC
	LDR R1,=RES
	MOV R2,#10
	
UP	LDR R3,[R0],#4
	ADDS R4,R3
	ADC R5,#0
	SUBS R2,#1
	BNE UP
	STR R4,[R1]
	STR R5,[R1,#4]
STOP B STOP	

SRC DCD 0x00000012, 0x00000034, 0x00000056, 0x00000078, 0x00000091, 0x00000014, 0x00000016, 0x00000024, 0xEEEEEE78, 0xFFFFFFFF
	AREA mydata, DATA, READWRITE
RES DCD 0x0
	END