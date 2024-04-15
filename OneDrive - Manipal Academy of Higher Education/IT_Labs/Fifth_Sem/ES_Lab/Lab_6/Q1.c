//Connect Switch CNB1 to CND (2.0)
//multiplexd 7 segment code
#include<LPC17xx.h>
#include<stdio.h>
unsigned int dig_count=0;
unsigned int digit_value[4]={0,0,0,0};
unsigned int select_segment[4]={3<<23,2<<23,1<<23,0<<23};
unsigned char seven_seg[16]={0x3F,0x06,0x5B,0x4F,0x66,0x6D,0x7D,0x07,0x7F,0x67};/0x77,0x7C,0x39,0x5E,0x79,0x71};
long temp1,temp2,i=0,idx,count=0,flag,flag1;
void delay()
{
   for(i=0;i<500;i++);
   if(count==1000)
   {
      flag=0xFF;
	  count=0;
   }
   else count++;
}
void change()
{
  int p=1,sum=0;
  for(i=3;i>=0;i--)
  {
    sum+=digit_value[i]*p;
	  p*=10;
  }
	if(!(LPC_GPIO2->FIOPIN &1))sum--;
	else sum++;
	if(sum==0)
	sum=9999;
	if(sum==9999)
  sum=0;
	idx=3;
  while(sum>0)
  {
     digit_value[idx]=sum%10;
	   sum/=10;
	   idx--;
  }
}
void display()
{
  LPC_GPIO1->FIOPIN=select_segment[dig_count];
  LPC_GPIO0->FIOPIN=seven_seg[digit_value[dig_count]]<<4;
  for(i=0;i<500;i++);
  LPC_GPIO0->FIOCLR=0x00000FF0;
}
int main()
{
	SystemInit();
	SystemCoreClockUpdate();
	LPC_PINCON->PINSEL0&=0xFF0000FF;
	LPC_PINCON->PINSEL3&=0xFFC03FFF;
	LPC_PINCON->PINSEL4&=0xFFFFFFFC;
	LPC_GPIO0->FIODIR|=0xFF<<4;
	LPC_GPIO1->FIODIR|=0xF<<23;
	LPC_GPIO2->FIODIR=0xFFFFFFFE;
	while(1)
	{
		delay();
		display();
		dig_count++;
		if(dig_count==0x04)
		{
		   dig_count=0x00;
		}
		if(flag==0xFF)
		{
		  flag=0x0;
			change();
		}
	}
}