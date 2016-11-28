#include<stdio.h>

 int main(int argc, char *argv[])
{
if(argc==3)
	{
		int i,count;
		count=atoi(argv[2]);
		for(i=0;i<count;i++)
		{
		 printf("Hello, %s!\n",argv[1]);
		}
	}
if(argc>3)
	{
		printf("Too many arguments!\n");
	}
if(argc<3)
	{
		printf("Not enough arguments!\n");
	}
return(0);
}
