#include <lpc17xx.h>

#include <stdio.h>

#include <stdlib.h>

#include <string.h>

#include <time.h>

 

char temp, msg1, str[50], randchar;

unsigned int k, x;

unsigned int i=0, flag1, j, randint;

 

#define randnum(min, max)\

((rand() % (int)(((max) + 1) - (min))) + (min))

void delay(int k)

{

  for (j=0; j<k; j++);

}

void lcd_port()

{

  LPC_GPIO0->FIOPIN=msg1<<23;

  if (flag1==0)

  {

    LPC_GPIO0->FIOCLR=1<<27;

  }

  else

  {

    LPC_GPIO0->FIOSET=1<<27;

  }

  LPC_GPIO0->FIOSET=1<<28;

  delay(50);

  LPC_GPIO0->FIOCLR=1<<28;

  delay(100000);

 

 

}

void lcd_wr()

{

  msg1=temp&0xF0;

  msg1=msg1>>4;

  lcd_port();

  if ((i>3)|(flag1==1))

  {

    msg1=temp&0x0F;

    lcd_port();

  }

}

int main(){

  char msg[50]="Dice Result: ";

  char init_command[]={0x30, 0x30, 0x30, 0x20, 0x28, 0x0C, 0x01, 0x06, 0x80};

 

  LPC_PINCON->PINSEL0 &=  0xFF0000FF;

  LPC_PINCON->PINSEL3 &= 0xFFC03FFF;

  LPC_PINCON->PINSEL1&=0xFC003FFF;

  LPC_GPIO0->FIODIR|=0X3F<<23;

 

  while(1){

    x = LPC_GPIO2->FIOPIN & (1<<12);

    //printf("%d", x);

    if(!x){

      flag1=0;

      for (i=0; i<9; i++)

      {

        temp=init_command[i];

        lcd_wr();

      }

      flag1=1;

 

      randint = (rand() % 6) + 1;

 

      //str = randnum(1,6);

      //str += 0x30;

      sprintf(str, "%d", randint);

      //tostring(randint, str);

      strcat(msg, str);

      i=0;

      while(msg[i]!='\0')

      {

        temp=msg[i];

        lcd_wr();

        i++;

      }

      strcpy(msg, "Dice Result: ");

    }

    x = 0;

 

}

}