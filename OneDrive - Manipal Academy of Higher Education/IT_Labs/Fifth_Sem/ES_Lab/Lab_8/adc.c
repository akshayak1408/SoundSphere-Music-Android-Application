#include <lpc17xx.h>
#include <math.h>
#include <stdio.h>
#define RS_CTRL  0x08000000  //P0.27, 1<<27
#define EN_CTRL  0x10000000  //P0.28, 1<<28
#define DT_CTRL  0x07800000  //P0.23 to P0.26 data lines, F<<23

 

unsigned long int temp1=0, temp2=0,i,j,r;
unsigned char flag1 =0, flag2 =0;
unsigned char msg[] = {"VOLTAGE DIFF"};
  char diff[16];
int digital_op4,digital_op5;
float analog_eq4,analog_eq5,analog_diff;

 

void lcd_write(void);
void port_write(void);
void delay_lcd(unsigned int);
unsigned long int init_command[] = {0x30,0x30,0x30,0x20,0x28,0x01 ,0x06,0x0c,0x80};
int main(void)
{
  SystemInit();
  SystemCoreClockUpdate();
  LPC_PINCON->PINSEL1=0;
  LPC_GPIO0->FIODIR = DT_CTRL | RS_CTRL | EN_CTRL; //0xf<<23 | 1<<27 | 1<<28;
  flag1 =0;
  for (i=0; i<9;i++)
                    {
      temp1 = init_command[i];
          lcd_write();
                    }
  flag1 =1; //DATA MODE
  for(i=0;msg[i]!='\0';i++){
    temp1=msg[i];
    lcd_write();
  }
  LPC_SC->PCONP = (1<<15);
  LPC_SC->PCONP |=1<<12;
  LPC_PINCON->PINSEL3=3<<30 | 3<<28;

 

  while(1){
    LPC_ADC->ADCR=1<<4 | 1<<21 | 1<<24;
    while(!(LPC_ADC->ADDR4>>31 & 0X1));
    digital_op4=(LPC_ADC->ADDR4>>4) & 0XFFF;
    analog_eq4=(float)digital_op4*(3.3/pow(2,12));
    LPC_ADC->ADCR=1<<5 | 1<<21 | 1<<24;
    while(!(LPC_ADC->ADDR5>>31 & 0X1));
    digital_op5=(LPC_ADC->ADDR5>>4) & 0XFFF;
    analog_eq5=(float)digital_op5*(3.3/pow(2,12));
    analog_diff=analog_eq4-analog_eq5;
    sprintf(diff,"%3.2fV",analog_diff);
    flag1=0;
    temp1=0xC0;
    lcd_write();
    flag1=1;
    for(i=0;diff[i]!='\0';i++){
      temp1=diff[i];
      lcd_write();
  }
  for(i=0;i<2000000;i++);
}
}

 

  void lcd_write(void)
                 { 
                  temp2 = temp1 & 0xf0;// 4 - Bits to get it to least significant digit place
  temp2 = temp2>>4;
  port_write();
                 if (!((flag1==0)&&((temp1==0x20)||(temp1==0x30)))) //send least significant 4 bits only when it is data/command other than 0x30/0x20
                  {
     temp2 = temp1 & 0x0f; 
     temp2 = temp2 ;
     port_write();
                   }
                 }

 

 

void port_write(void)

 

{

  LPC_GPIO0->FIOPIN = temp2<<23; // sending the ascii code
          if (flag1 == 0)
                  LPC_GPIO0->FIOCLR = RS_CTRL; // if command
          else
                    LPC_GPIO0->FIOSET = RS_CTRL; //if data

 

  LPC_GPIO0->FIOSET = EN_CTRL; //sending a low high edge on enable input
  for(r=0;r<25;r++);
  LPC_GPIO0->FIOCLR = EN_CTRL;
    for(r=0;r<30000;r++);
  }