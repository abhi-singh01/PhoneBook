package phonebook;

import java.util.*;
import java.io.*;
public class PhoneBook
{
    //data members
    String nm;
    String phno;
    int rec_count;
    
    //member methods
    PhoneBook() //default constructor
    {
        nm="";
        phno="";
        rec_count=0;
    }
    
    void countrecords()throws Exception
    {
        //method to count the number of records existing in the file
        File tf=new File("Phdata.txt");
        if(tf.exists()) //checking whether the file exists or not
        {
            FileReader fr=new FileReader("Phdata.txt");
            BufferedReader br=new BufferedReader(fr);
            String tmp="";
            while((tmp=br.readLine())!= null)
            rec_count++;
            br.close();
        }
        return;
    }
    
    void show(String str, int sr)
    {
        //method to display the records in a formatted manner
        int li=str.lastIndexOf('\t');
        nm=str.substring(0,li);
        phno=str.substring(li+1);
        System.out.printf("%3d. %25s   :   %10s\n",sr,nm,phno);
    }
    
    void writeData() throws IOException
    {   //method to write name and number to the file.
        if(rec_count>=300)
        {
            System.out.println("CANNOT ADD RECORD. PHONEBOOK IS FULL.");
            return;
        }
        System.out.println("\f");   //clearing the screen
        //header
        System.out.println("=========================================================");
        System.out.println("      ==<<\u01A4\u0181>>== P H O N E   B O O K ==<<\u01A4\u0181>>==");
        System.out.println("---------------------------------------------------------");
        System.out.println("\t\t       Add Record");
        System.out.println("\t\t     ==============");
        
        //accepting input and writing it to the file
        Scanner sc=new Scanner(System.in);
        FileWriter fw=new FileWriter("Phdata.txt",true);
        BufferedWriter br=new BufferedWriter(fw);
        PrintWriter pw=new PrintWriter(br);
        System.out.print("\tEnter the Phone Number: ");
        phno=sc.nextLine();
        phno=phno.trim();
        System.out.print("\tEnter the Name: ");
        nm=sc.nextLine();
        nm=nm.trim();
        nm=nm.toUpperCase();
        
        System.out.println("---------------------------------------------------------");
        System.out.print("\tDo you want to save data (Y/N) ? ");
        char ch=(sc.nextLine()).charAt(0);
        if(ch=='Y' || ch=='y')
        {
            String tmp=nm+"\t"+phno;  //combining the data to facilitate storage in file
            pw.println(tmp);
            rec_count++;
            System.out.println("\n\tRecord Saved...");
        }
        else
        System.out.println("\n\tFAILED!! to save data...");
        for(int i=1;i<=999999999;i++);      //time delay loop
        pw.close();
    }
    
    void readData()throws Exception
    {
        //checking existence of file
        File tf=new File("Phdata.txt");
        if(tf.exists()==false) //checking whether the file exists or not
        {
            System.out.println("\tFile does not exist. Enter a record.");
            for(int i=0;i<=999999999;i++);   //time delay loop
            return;
        }
        
        //method to display all records stored in the file.
        FileReader fr=new FileReader("Phdata.txt");
        BufferedReader br=new BufferedReader(fr);
        
        System.out.println("\f");
        System.out.println("=========================================================");
        System.out.println("      ==<<\u01A4\u0181>>== P H O N E   B O O K ==<<\u01A4\u0181>>==");
        System.out.println("---------------------------------------------------------");
        String tmp;
        int ctr=0; //counter for serial number
        System.out.println("\t\tLIST OF NAMES + PHONE NOs");
        System.out.println("=========================================================");
        while((tmp=br.readLine())!= null)
        {
            ctr++;
            show(tmp,ctr);
        }
        br.close();
        
        if(ctr==0)
        System.out.println("\t\tNO Records Found.File Empty.");
        System.out.println("---------------------------------------------------------");
        System.out.printf("\t      Record Count :: %3d / 300\n",rec_count);
        System.out.println("=========================================================");
               
        Scanner sc=new Scanner(System.in);
        System.out.println("Type Y, then Press ENTER to CONTINUE....");
        String garb=sc.next();
    }
    
    void search() throws Exception
    {
        //method to search records in the file
        Scanner sc=new Scanner(System.in);
        String tar[]=new String[300];  //declaring array to store the extracted records
        
        System.out.println("\f");   //clearing screen
        //displaying header
        System.out.println("=========================================================");
        System.out.println("      ==<<\u01A4\u0181>>== P H O N E   B O O K ==<<\u01A4\u0181>>==");
        System.out.println("---------------------------------------------------------");
        
        //displaying search menu
        System.out.println("\t\t1. Search by Name.\n\t\t2. Search by Number.\n\t\t3. Cancel.");
        System.out.println("---------------------------------------------------------");
        System.out.print("\tEnter your choice: ");
        int chs=sc.nextInt();
        sc.nextLine();
        
        if(chs==3)
        return;
        System.out.println("\n");  //inserting blank rows for spacing
        
        switch(chs)
        {
            case 1: System.out.println("Enter the name/first few characters:");
                    String snm=sc.nextLine();
                    snm=(snm.trim()).toUpperCase();
                    
                    FileReader fr=new FileReader("Phdata.txt");
                    BufferedReader br=new BufferedReader(fr);
                    System.out.println("\t\tSearch Result");
                    System.out.println("=========================================================");
                    int ctr=0;  //serial counter
                    String tmp="";
                    while((tmp=br.readLine()) != null)
                    {
                        if(tmp.startsWith(snm))
                        {
                            tar[ctr]=tmp;
                            ctr++;
                            show(tmp,ctr);
                        }
                    }
                    if(ctr==0)
                    System.out.println("\t\tNO Matching Records Found");
                    br.close();
                    System.out.println("=========================================================");
                    System.out.print("To DELETE enter record number/Enter 0 to CANCEL : ");
                    int num=sc.nextInt();
                    sc.nextLine();
                    if(num>0 && num<=ctr)
                    {
                        delete(tar[num-1]);
                    }
                    break;
            case 2: System.out.println("Enter the number/first few digits:");
                    String spn=sc.nextLine();
                    
                    FileReader fr1=new FileReader("Phdata.txt");
                    BufferedReader br1=new BufferedReader(fr1);
                    System.out.println("\t\tSearch Result");
                    System.out.println("=========================================================");
                    int ctr1=0;  //serial counter
                    String tmp1="";
                    while((tmp1=br1.readLine()) != null)
                    {
                        if((tmp1.substring(tmp1.lastIndexOf('\t')+1)).startsWith(spn))
                        {
                            tar[ctr1]=tmp1;
                            ctr1++;
                            show(tmp1,ctr1);
                        }
                    }
                    if(ctr1==0)
                    System.out.println("\t\tNO Matching Records Found");
                    br1.close();
                    System.out.println("=========================================================");
                    System.out.print("To DELETE enter record number/Enter 0 to CANCEL : ");
                    int num1=sc.nextInt();
                    sc.nextLine();
                    if(num1>0 && num1<=ctr1)
                    {
                        delete(tar[num1-1]);
                    }
                    break;
        }
        System.out.println("Type Y, then Press ENTER to CONTINUE....");
        String garb=sc.next();
    }
    
    void delete(String str) throws Exception
    {
        FileReader fr=new FileReader("Phdata.txt");
        BufferedReader br=new BufferedReader(fr);   //for reading data from existing file
        
        FileWriter fw=new FileWriter("phtmp.txt",true);
        BufferedWriter bw=new BufferedWriter(fw);
        PrintWriter pw=new PrintWriter(bw); //for writing data to a temporary file
        
        String tmp="";
        int flag=0;
        while((tmp=br.readLine())!= null)
        {
            if(tmp.startsWith(str))
            {
                flag=1;
                rec_count--;
                continue;
            }
            pw.println(tmp);
        }
        pw.close();
        br.close();
        
        //code to replace files
        File f1=new File("Phdata.txt");
        File f2=new File("phtmp.txt");
        boolean test1=false,test2=false;
        test1=f1.delete();
        test2=f2.renameTo(f1);
        if(test1 && test2)
        System.out.println("\tRecord DELETED");
    }
    
    void starter() throws Exception
    {
        Scanner sc=new Scanner(System.in);
        countrecords();
        while(true)
        {
            System.out.println("\f");
            //displaying header
            System.out.println("=========================================================");
            System.out.println("      ==<<\u01A4\u0181>>== P H O N E   B O O K ==<<\u01A4\u0181>>==");
            System.out.println("---------------------------------------------------------");
            System.out.printf("\t      Record Count :: %3d / 300\n",rec_count);
            System.out.println("---------------------------------------------------------");
            //displaying main menu
            System.out.println("\t\t1. Add new Number.");
            System.out.println("\t\t2. Show ALL Numbers.");
            System.out.println("\t\t3. Search Record.");
            System.out.println("\t\t4. Exit");
            System.out.println("=========================================================");
            System.out.print("\t\tEnter your choice: ");
            int ch=sc.nextInt();
            
            switch(ch)
            {
                case 1: writeData();
                        break;
                case 2: readData();
                        break;
                case 3: search();
                        break;
                case 4: System.out.println("Closing the PHONEBOOK app...");
                        System.exit(0);
            }
        }
    }
    
    public static void main(String args[])throws Exception
    {
        PhoneBook pb=new PhoneBook();
        pb.starter();
    }
}