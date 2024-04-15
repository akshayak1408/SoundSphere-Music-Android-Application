
/*#include <LPC17xx.h>
void initPWM(void);
void updatePulseWidth();
void delayMS(unsigned int milliseconds);
void scan(void);
unsigned char row, var, flag;
unsigned long int i, key, var1, temp, temp1, temp2, temp3;

int main(void){
	int asci_code[4] = {3000, 7500, 15000, 30000};
	int scan_code[4] = {0x11, 0x21, 0x41, 0x81};
	LPC_PINCON ->PINSEL0 = 0X0;
	LPC_GPIO0 -> FIODIR = 0XF0<<4; //0.4-0.7 = columns i/p, 0.8-0.11 = rows o/p
	initPWM(); //Initialize PWM
	while(1){
		
			while(1){
				var1 = 0x100;
				temp = var1;
				LPC_GPIO0 -> FIOCLR = 0XF00;
				LPC_GPIO0 -> FIOSET = var1;
				flag = 0;
				scan();
				if(flag ==1)
					break;
			}
			
		for(i=0; i<4; i++){
			if(key == scan_code[i])
				key = asci_code[i];
			updatePulseWidth(); //Update LED Pulse Width
			delayMS(10000);
		}
	}
}

void initPWM(void){
	LPC_PINCON->PINSEL3 |= 0x8000; //Select PWM1.4 output for Pin1.23, function 2
	LPC_PWM1->PCR = 0x1000; //enable PWM1.4, by default it is single Edged 
	LPC_PWM1->PR = 0; 
	LPC_PWM1->MR0 = 30000; //period=10ms if pclk=cclk/4
	LPC_PWM1->MCR = (1<<1); //Reset PWM TC on PWM1MR0 match
	LPC_PWM1->LER = 0xff; //update values in MR0 and MR1
	LPC_PWM1->TCR = 0x00000002; //RESET COUNTER TC and PC
	LPC_PWM1->TCR = 0x00000009; //enable TC and PC
}
void updatePulseWidth(){
	LPC_PWM1->MR4 = key; //Update MR4 with new value
	LPC_PWM1->LER = 0xff; //Load the MR4 new value at start of next cycle
}

void scan(void){
	temp3 = LPC_GPIO0->FIOPIN;
	temp3 &= 0x00000F0; //check if any key pressed in the enabled row
	if(temp3 != 0x00000000){
		flag = 1;
		temp3 >>= 0;//Shifted to come at HN of byte
		temp >>= 8; //shifted to come at LN of byte
		key = temp3|temp; //get SCAN_CODE
	}//if(temp3 != 0x00000000)
}//end scan

void delayMS(unsigned int milliseconds){
	LPC_TIM0->CTCR = 0x0; //Timer mode
	LPC_TIM0->PR = 2; //Increment TC at every 3 pclk
	LPC_TIM0->TCR = 0x02; //Reset Timer
	LPC_TIM0->TCR = 0x01; //Enable timer
	while(LPC_TIM0->TC < milliseconds); //wait until timer counter reaches the desired delay
	LPC_TIM0->TCR = 0x00; //Disable timer
}*/

#include<LPC17xx.h>

void initpwm(void);
void updatepulsewidth(unsigned int pulsewidth);
void delayms(unsigned int milliseconds);
int scan(void);
unsigned int temp3;
int pulsewidth[]={0,2500,5000,10000};
int dir=0;

int main(void)
{
	LPC_GPIO0->FIODIR = 0xf00;
	initpwm();
	while(1)
	{
		int count=scan();
		if(count==0x1)
			updatepulsewidth(pulsewidth[0]);
		else if(count==0x2)
			updatepulsewidth(pulsewidth[1]);
		else if(count==0x4)
			updatepulsewidth(pulsewidth[2]);
		else if(count==0x8)
			updatepulsewidth(pulsewidth[3]);
		delayms(100000);
	}

}

void initpwm(void)
{
	LPC_PINCON->PINSEL3|=0x8000;
	LPC_PWM1->PCR=0x1000;
	LPC_PWM1->PR=0;
	LPC_PWM1->MR0=10000;
	LPC_PWM1->MCR=2;
	LPC_PWM1->LER=0xff;
	LPC_PWM1->TCR=0x2;
	LPC_PWM1->TCR=0x9;
}

void updatepulsewidth(unsigned int pulsewidth)
{
	LPC_PWM1->MR4=pulsewidth;
	LPC_PWM1->LER=0xff;
}

void delayms(unsigned int milliseconds)
{
	LPC_TIM0->CTCR=0x0;
	LPC_TIM0->PR=2;
	LPC_TIM0->TCR=0x02;
	LPC_TIM0->TCR=0x1;
	while(LPC_TIM0->TC<milliseconds);
	LPC_TIM0->TCR=0;
}

int scan(void)
{
	LPC_GPIO0->FIOSET = 0x100;
	temp3 = LPC_GPIO0->FIOPIN;
	temp3 &= 0xf0;
	if(temp3!=0)
		return temp3>>4;
	return 1;
}

