package emulator;
import java.util.HashMap;
import java.util.Scanner;
//Class for Register
class Registers {
    byte  a; // accumulator
    byte b; // B register
    byte c; // C register
    byte d; // D register
    byte e; // E register
    byte h; // H register
    byte l; // L register
    int sp; // stack pointer
    int pc; // program counter

    public Registers() {
        a = 0;
        b = 0;
        c = 0;
        d = 0;
        e = 0;
        h = 0;
        l = 0;
        sp = 0;
        pc = 0;
    }

    
}
//Class for Flag Register
class FlagRegister {
    boolean sign; // sign flag
    boolean zero; // zero flag
    boolean auxCarry; // auxiliary carry flag
    boolean parity; // parity flag
    boolean carry; // carry flag

    public FlagRegister() {
        sign = false;
        zero = false;
        auxCarry = false;
        parity = false;
        carry = false;
    }

}
// Public Class for execution of Emulator 
public class Emulator {
    HashMap<Integer, Byte> memory;

    public Emulator() {
        memory = new HashMap<Integer, Byte>();
        for (int i = 0; i < 65536; i++) {
            memory.put(i, (byte) 0);
        }
    }

    public void writeByte(int address, byte value) {
        memory.put(address, value);
    }

    public byte readByte(int address) {
        return memory.get(address);
    }

    public static void main(String[] args) {
    	
        Emulator m=new Emulator();
        Registers r=new Registers();
        FlagRegister f=new FlagRegister();
        Scanner scanner = new Scanner(System.in);
        System.out.println("**********************************WELCOME***********************************************");
        System.out.println("**********************************8085 EMULATOR BY PRAVEEN DHOUNDIYAL ****************************************");
        int choice=-1;
        int chm=-1;
        int chp=-1;
        
        do{
        	try {
            System.out.println("Enter 0 Memory Access Mode\nEnter 1 8085 Programming Mode\nEnter 2 Exit\n ");
            choice=scanner.nextInt();
            if(choice ==2){
                System.out.println("*****************************Thanks for using Microprocessor***************************************************\n");
                System.out.println("*****************************Exiting the Microprocessor********************************************************");
                break;
            }
            else if(choice==0){//Accessing for memory
                System.out.println("*****************************Welcome to Memory Access Mode*****************************************************");
                do{
                System.out.println("Enter 0 Read Mode\nEnter 1 Write Mode\nEnter 2 Exit");
                chm=scanner.nextInt();
                if(chm==2){
                    System.out.println("****************************Exiting Memory Access Mode*****************************************************\n");
                    break;
                }
                else if(chm==1){
                    System.out.println("Enter the Memory Address \n");
                    int address=scanner.nextInt();
                    System.out.println("Enter the Value \n");
                    byte value=(byte)scanner.nextInt();
                    m.writeByte(address,value);
                }
                else if(chm==0){
                    System.out.println("Enter the Memory Address\n ");
                    int address=scanner.nextInt();
                    System.out.println("Value at "+address+" is "+m.readByte(address));
                }
                else{
                    System.out.println("Invalid input  \n");
                }
                }while(chm!=2);
                
               
                
            }
            else if(choice==1){
                int brk=-1;
                System.out.println("*****************************Welcome to Programing Mode*****************************************************");
                do{
               System.out.println("Enter 0 debug\nEnter 1 execution\nEnter 2 Print Architecture\nEnter 3 Breakpoint \nEnter 4 Exit");
               chp=scanner.nextInt();
               if(chp==4){
                   System.out.println("***************Exiting Programing Mode *************");
                   break;
               }
               else if(chp==2){// Printing Architecture
            	   System.out.println("*************Registers***********");
                   System.out.println("*   Register A : "+r.a+"              *");
                   System.out.println("*   Register B : "+r.b+"              *");
                   System.out.println("*   Register C : "+r.c+"              *");
                   System.out.println("*   Register D : "+r.d+"              *");
                   System.out.println("*   Register E : "+r.e+"              *");
                   System.out.println("*   Register H : "+r.h+"              *");
                   System.out.println("*   Register L : "+r.l+"              *");
                   System.out.println("*********************************");
                   System.out.println("*************Flags***************");
                   System.out.println("*   carry    : "+f.carry+"            *");
                   System.out.println("*   parity   : "+f.parity+"            *");
                   System.out.println("*   sign     : "+f.sign+"            *");
                   System.out.println("*   auxCarry : "+f.auxCarry+"            *");
                   System.out.println("*   zero     : "+f.zero+"            *");
                   System.out.println("*********************************");
            
            }
            else if(chp==1||chp==0||chp==3){
        String inputCode = "";
        String codeLine;
        System.out.println("Enter 8085 Program\n");
        while (true) {
            codeLine = scanner.nextLine();
            inputCode += codeLine + "\n"; // Append the code line to inputCode with a newline character
            if (codeLine.trim().toUpperCase().equals("HLT")) { // Check for HLT instruction
                break;
            }
        }
        String[] lines = inputCode.toString().split("\n");
        if(chp==3){
                   System.out.println("Enter breakpoint instruction number");
                   brk=scanner.nextInt();
                   if(brk>lines.length-1){
                       System.out.println("Invaid Breakpoint");
                   }
                   
               }
        outer: for (int i = 0; i < lines.length; ++i) {
        String[] words = lines[i].split("\\s+");
            // code for LDA 
                if(words[0].equals("LDA")){
                    int address = Integer.parseInt(words[1]);
                    r.a = m.readByte(address);
                    r.pc+=3;
                }
            // Code for STA
                else if(words[0].equals("STA")){
                    int address = Integer.parseInt(words[1]);
                    m.writeByte(address,r.a);
                    r.pc+=3;
                }
            // Code for MOV
                else if(words[0].equals("MOV")){
                    String registers[] = words[1].split(",");
                    String destination=registers[0];
                    String source =registers[1];
                    r.pc+=1;
                    if(destination.equals("B")){
                        if(source.equals("A")){
                            r.b=r.a;
                        }
                        else if(source.equals("C")){
                            
                            r.b=r.c;
                        }
                        else if(source.equals("D")){
                            r.b=r.d;
                        }
                        else if(source.equals("E")){
                           r.b=r.e;
                        }
                        else if(source.equals("H")){
                            r.b=r.h;
                        }
                        else if(source.equals("L")){
                           r.b=r.l;
                        }
                        else{
                            System.out.println("Invalid instruction");
                        }
                    }
                    else if(destination.equals("C")){
                        if(source.equals("A")){
                            r.c=r.a;
                        }
                        else if(source.equals("B")){
                            
                            r.c=r.b;
                        }
                        else if(source.equals("D")){
                            r.c=r.d;
                        }
                        else if(source.equals("E")){
                           r.c=r.e;
                        }
                        else if(source.equals("H")){
                            r.c=r.h;
                        }
                        else if(source.equals("L")){
                           r.c=r.l;
                        }
                        else{
                            System.out.println("Invalid instruction");
                        }
                    }
                    else if(destination.equals("D")){
                        if(source.equals("A")){
                            r.d=r.a;
                        }
                        else if(source.equals("C")){
                            
                            r.d=r.c;
                        }
                        else if(source.equals("B")){
                            r.d=r.b;
                        }
                        else if(source.equals("E")){
                           r.d=r.e;
                        }
                        else if(source.equals("H")){
                            r.d=r.h;
                        }
                        else if(source.equals("L")){
                           r.d=r.l;
                        }
                        else{
                            System.out.println("Invalid instruction");
                        }
                    }
                    else if(destination.equals("E")){
                        if(source=="A"){
                            r.e=r.a;
                        }
                        else if(source.equals("C")){
                            
                            r.e=r.c;
                        }
                        else if(source.equals("D")){
                            r.e=r.d;
                        }
                        else if(source.equals("B")){
                           r.e=r.b;
                        }
                        else if(source.equals("H")){
                            r.e=r.h;
                        }
                        else if(source.equals("L")){
                           r.e=r.l;
                        }
                        else{
                            System.out.println("Invalid instruction");
                        }
                    }
                    else if(destination.equals("H")){
                        if(source.equals("A")){
                            r.h=r.a;
                        }
                        else if(source.equals("C")){
                            
                            r.h=r.c;
                        }
                        else if(source.equals("D")){
                            r.h=r.d;
                        }
                        else if(source.equals("E")){
                           r.h=r.e;
                        }
                        else if(source.equals("B")){
                            r.h=r.b;
                        }
                        else if(source.equals("L")){
                           r.h=r.l;
                        }
                        else{
                            System.out.println("Invalid instruction");
                        }
                    }
                    else if(destination.equals("L")){
                        if(source.equals("A")){
                            r.l=r.a;
                        }
                        else if(source.equals("C")){
                            
                            r.l=r.c;
                        }
                        else if(source.equals("D")){
                            r.l=r.d;
                        }
                        else if(source.equals("E")){
                           r.l=r.e;
                        }
                        else if(source.equals("H")){
                            r.l=r.h;
                        }
                        else if(source.equals("B")){
                           r.l=r.b;
                        }
                        else{
                            System.out.println("Invalid instruction");
                        }
                    }
                    else if(destination.equals("A")){
                        if(source=="L"){
                            r.a=r.l;
                        }
                        else if(source.equals("C")){
                            
                            r.a=r.c;
                        }
                        else if(source.equals("D")){
                            r.a=r.d;
                        }
                        else if(source.equals("E")){
                           r.a=r.e;
                        }
                        else if(source.equals("H")){
                            r.a=r.h;
                        }
                        else if(source.equals("B")){
                           r.a=r.b;
                        }
                        else{
                            System.out.println("Invalid instruction");
                        }
                    }
                    
                }
            // Code for ADD
                 else if(words[0].equals("ADD")){
                    if(words[1].equals("B")){
                        r.a+=r.b;
                    }
                    else if(words[1].equals("C")){
                        r.a+=r.c;
                    }
                    else if(words[1].equals("D")){
                        r.a+=r.d;
                    }
                    else if(words[1].equals("E")){
                        r.a+=r.e;
                    }
                    else if(words[1].equals("H")){
                        r.a+=r.h;
                    }
                    else if(words[1].equals("L")){
                        r.a+=r.l;
                    }
                    else{
                        System.out.println("Invalid instruction");
                    }
                    if(r.a<0){
                        f.carry=true;
                    }
                    r.pc+=1;
                }
            // Code for SUB
                else if(words[0].equals("SUB")){
                    if(words[1].equals("B")){
                        r.a-=r.b;
                    }
                    else if(words[1].equals("C")){
                        r.a-=r.c;
                    }
                    else if(words[1].equals("D")){
                        r.a-=r.d;
                    }
                    else if(words[1].equals("E")){
                        r.a-=r.e;
                    }
                    else if(words[1].equals("H")){
                        r.a-=r.h;
                    }
                    else if(words[1].equals("L")){
                        r.a-=r.l;
                    }
                    else{
                        System.out.println("Invalid instruction");
                    }
                    if(r.a<-127){
                        f.carry=true;
                    }
                    if(r.a==0){
                        f.zero=true;
                    }
                    r.pc+=1;
                }
            // Code MVI
                else if(words[0].equals("MVI")){
                    byte value=(byte)Integer.parseInt(words[2]);
                    if(words[1].equals("A")){
                        r.a=value;
                    }
                    else if(words[1].equals("B")){
                        r.b=value;
                    }
                    else if(words[1].equals("C")){
                        r.c=value;
                    }
                    else if(words[1].equals("D")){
                        r.d=value;
                    }
                    else if(words[1].equals("E")){
                        r.e=value;
                    }
                    else if(words[1].equals("H")){
                        r.h=value;
                    }
                    else if(words[1].equals("L")){
                        r.l=value;
                    }
                    else{
                        System.out.println("Invalid instruction");
                    }
                    r.pc+=2;
                }
            // Code for INR
                else if(words[0].equals("INR")){
                     if(words[1].equals("A")){
                        ++r.a;
                    }
                    else if(words[1].equals("B")){
                        ++r.b;
                    }
                    else if(words[1].equals("C")){
                        ++r.c;
                    }
                    else if(words[1].equals("D")){
                        ++r.d;
                    }
                    else if(words[1].equals("E")){
                        ++r.e;
                    }
                    else if(words[1].equals("H")){
                        ++r.h;
                    }
                    else if(words[1].equals("L")){
                        ++r.l;
                    }
                    else{
                        System.out.println("Invalid instruction");
                    }
                    if(r.a<0){
                        f.carry=true;
                    }
                    r.pc+=1;
                        

                }
            // Code for DCR
                else if(words[0].equals("DCR")){
                     if(words[1].equals("A")){
                        --r.a;
                    }
                    else if(words[1].equals("B")){
                        --r.b;
                    }
                    else if(words[1].equals("C")){
                        --r.c;
                    }
                    else if(words[1].equals("D")){
                        --r.d;
                    }
                    else if(words[1].equals("E")){
                        --r.e;
                    }
                    else if(words[1].equals("H")){
                        --r.h;
                    }
                    else if(words[1].equals("L")){
                        --r.l;
                    }
                    else{
                        System.out.println("Invalid instruction");
                    }
                    if(r.a<-127){
                        f.carry=true;
                    }
                    if(r.a==0){
                        f.zero=true;
                    }
                    r.pc+=1;
                }
                // Code for ADI
                else if(words[0].equals("ADI")){
                    byte value =(byte)Integer.parseInt(words[1]);
                    r.a+=value;
                    if(r.a<0){
                        f.carry=true;
                    }
                    r.pc+=2;
                    
                }
                 // Code for SBI
                else if(words[0].equals("SBI")){
                    byte value =(byte)Integer.parseInt(words[1]);
                    r.a-=value;
                    if(r.a<-127){
                        f.carry=true;
                    }
                    if(r.a==0){
                        f.zero=true;
                    }
                    r.pc+=2;
                }
                // Code ADC
                else if(words[0].equals("ADC")){
                    if(words[1].equals("B")){
                        r.a+=r.b;
                    }
                    else if(words[1].equals("C")){
                        r.a+=r.c;
                    }
                    else if(words[1].equals("D")){
                        r.a+=r.d;
                    }
                    else if(words[1].equals("E")){
                        r.a+=r.e;
                    }
                    else if(words[1].equals("H")){
                        r.a+=r.h;
                    }
                    else if(words[1].equals("L")){
                        r.a+=r.l;
                    }
                    else{
                        System.out.println("Invalid instruction");
                    }
                    
                    if(f.carry==true){
                        r.a+=1;
                    }
                    if(r.a<0){
                        f.carry=true;
                    }
                    r.pc+=1;
                }
                // Code for SBC
                else if(words[0].equals("SBC")){
                    if(words[1].equals("B")){
                        r.a-=r.b;
                    }
                    else if(words[1].equals("C")){
                        r.a-=r.c;
                    }
                    else if(words[1].equals("D")){
                        r.a-=r.d;
                    }
                    else if(words[1].equals("E")){
                        r.a-=r.e;
                    }
                    else if(words[1].equals("H")){
                        r.a-=r.h;
                    }
                    else if(words[1].equals("L")){
                        r.a-=r.l;
                    }
                    else{
                        System.out.println("Invalid instruction");
                    }
                    if(f.carry==true){
                        r.a-=1;
                    }
                    if(r.a<-127){
                        f.carry=true;
                    }
                    if(r.a==0){
                        f.zero=true;
                    }
                    r.pc+=1;
                }
                // code for JNC
                else if(words[0].equals("JNC")){
                    int nji=Integer.parseInt(words[1]);
                    if(f.carry==false){
                        i+=nji;
                        continue outer;
                    }
                }
                // Code for JC
                else if(words[0].equals("JC")){
                    int nji=Integer.parseInt(words[1]);
                    if(f.carry==true){
                        i+=nji;
                        continue outer;
                    }
                }
                // Code for JNZ
                else if(words[0].equals("JNZ")){
                    int nji=Integer.parseInt(words[1]);
                    if(f.zero==false){
                        i+=nji;
                        continue outer;
                    }
                }
                // Code for JZ
                else if(words[0].equals("JZ")){
                    int nji=Integer.parseInt(words[1]);
                    if(f.zero==true){
                        i+=nji;
                        continue outer;
                    }
                }
                // updating flags
            if(r.a==0){
                f.zero=true;
            }
            if(r.a<0){
                f.sign=true;
            }
            int num = r.a;  // the number to count ones in
            int count = 0;
            while (num != 0) {
                if ((num & 1) == 1) {
                    count++;
                }
                num >>= 1;  // right shift num by 1 bit
            }
            if(count%2==0){
                f.parity=true;
            }
            if(chp==3){//Breakpoint
                if(brk==i){
                    System.out.println(lines[i]);
                    System.out.println("*************Registers***********");
                    System.out.println("*   Register A : "+r.a+"              *");
                    System.out.println("*   Register B : "+r.b+"              *");
                    System.out.println("*   Register C : "+r.c+"              *");
                    System.out.println("*   Register D : "+r.d+"              *");
                    System.out.println("*   Register E : "+r.e+"              *");
                    System.out.println("*   Register H : "+r.h+"              *");
                    System.out.println("*   Register L : "+r.l+"              *");
                    System.out.println("*********************************");
                    System.out.println("*************Flags***************");
                    System.out.println("*   carry    : "+f.carry+"            *");
                    System.out.println("*   parity   : "+f.parity+"             *");
                    System.out.println("*   sign     : "+f.sign+"            *");
                    System.out.println("*   auxCarry : "+f.auxCarry+"            *");
                    System.out.println("*   zero     : "+f.zero+"             *");
                    System.out.println("*********************************");
                    break outer;
                }
            }
            if(chp==0){
            System.out.println(lines[i]);
            System.out.println("*************Registers***********");
            System.out.println("*   Register A : "+r.a+"              *");
            System.out.println("*   Register B : "+r.b+"              *");
            System.out.println("*   Register C : "+r.c+"              *");
            System.out.println("*   Register D : "+r.d+"              *");
            System.out.println("*   Register E : "+r.e+"              *");
            System.out.println("*   Register H : "+r.h+"              *");
            System.out.println("*   Register L : "+r.l+"              *");
            System.out.println("*********************************");
            System.out.println("*************Flags***************");
            System.out.println("*   carry    : "+f.carry+"            *");
            System.out.println("*   parity   : "+f.parity+"             *");
            System.out.println("*   sign     : "+f.sign+"            *");
            System.out.println("*   auxCarry : "+f.auxCarry+"            *");
            System.out.println("*   zero     : "+f.zero+"             *");
            System.out.println("*********************************");
            
            }
            
                
            }
            
        }
        else{
                System.out.println("Invalid Input");
            }
            
        
        
            }while(chp!=4);
            }
        	  }
        	catch(Exception e) {
        		System.out.println("Error");
        	}
        }while(choice!=2);
        
    	 
    }
    	
    
}