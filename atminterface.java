//importing required packages
import java.io.*;
import java.util.*;
import java.sql.*;
//start of class
class atminterface
{
 public static void main(String args[])
 {
	//variable declarations 
   int ch,bal=0,amt,c=1,balance=0;	
   String name=" ",pass,con_pass,n,id;
   boolean result;   
   //try start   
   try
   {  
     //connecting with database
     Class.forName("com.mysql.cj.jdbc.Driver");
	 Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/atm","root","");
	 Statement stmt=con.createStatement();
	 System.out.println("WELCOME");
	 System.out.println("1.login");
	 System.out.println("2.Register");
	 System.out.println("Enter your option:");
	 Scanner s=new Scanner(System.in);
	 ch=s.nextInt();
	 System.out.println("choice:"+ch);
	 System.out.println();
	 //login
	 if(ch==1)
     {
       System.out.println("enter the username:");
       name=s.next();  
	   System.out.println("enter the password:");
	   pass=s.next();
	   ResultSet rs=stmt.executeQuery("SELECT * FROM USERDETAILS WHERE name=\""+name+"\"and password=\""+pass+"\";");  
       result=rs.next();	  
   	   if(result==true)
	   {
		   System.out.println("WARM WELCOME TO YOU!");
	   }
	   else
	   {
		   
		System.out.println("User doesnot exists!kindly do register");
		System.out.println("1.login");
		System.out.println("2.Register");
		System.out.println("Enter your option:");
		ch=s.nextInt();
		if(ch==1)
	    {
		  System.out.println("User doesnot exists!kindly do register");
		  System.out.println("1.login");
		  System.out.println("2.Register");
		  System.out.println("Enter your option:");
		  ch=s.nextInt();	
		}       			
	   }   
    }  
	//Registration (when user is new)
    if(ch==2)
    {
	 System.out.println("enter the username:");
     n=s.next();
     System.out.println("enter your account no:");
     id=s.next();
	 if(id.length()<9||id.length()>9)
	 {
       System.out.println("account no should contain 9 digits only");
       id=s.next();	   
	 }
     System.out.println("enter your password:");
     pass=s.next();
	 if(pass.length()>8||pass.length()<8)
     {
       System.out.println("password should contain maximum 8 characters");	
	   pass=s.next();	
     }	   
     System.out.println("confirm the password(re-enter):");
     con_pass=s.next();	
	 // The user should have unique pin so checking whether the pin already used by other users
	 System.out.println("Enter Your 4 Digit PIN:");
	 int pin=s.nextInt();
	 ResultSet rs2=stmt.executeQuery("SELECT PIN FROM CUSMONEY WHERE pin=\""+pin+"\";");
     boolean exist=rs2.next();
	 // If pin already in usage obtaining new pin from user and updating DB
	 if(exist)
     {
		System.out.println("your pin must be unique ..Try another pin");
        System.out.println("Enter Your 4 Digit PIN:");
	    pin=s.nextInt();		
		String insert="INSERT INTO USERDETAILS VALUES(\""+n+"\",\""+id+"\",\""+pass+"\",\""+con_pass+"\",\""+pin+"\");";
        //System.out.println(insert);
        int ins=stmt.executeUpdate(insert);  
	    String in="INSERT INTO CUSMONEY VALUES(\""+pin+"\",\""+bal+"\");";
	    int up=stmt.executeUpdate(in);
	    System.out.println("User registered successfully");
     }	
	 // Otherwise the DB updated directly
	 else
     { 		 
      String insert="INSERT INTO USERDETAILS VALUES(\""+n+"\",\""+id+"\",\""+pass+"\",\""+con_pass+"\",\""+pin+"\");";
      //System.out.println(insert);
      int ins=stmt.executeUpdate(insert);  
	  String in="INSERT INTO CUSMONEY VALUES(\""+pin+"\",\""+bal+"\");";
	  int up=stmt.executeUpdate(in);
	  System.out.println("User registered successfully");
	 } 
	}
	System.out.println();
    //displays list of operation when the choice variable c is equal to 1
	while(c!=0)
    {		
     System.out.println("1.Past transaction histroy");	
	 System.out.println("2.Withdraw");	
	 System.out.println("3.Deposit");	
	 System.out.println("4.Check Balance");
	 System.out.println("5.Tranfer Amount");	
	 System.out.println("6.Quit");
	 System.out.println("Choose your choice:");
	 int choice=s.nextInt();
	 switch(choice)
	 {
		//case 1 - transaction history 
		case 1:
		{
		  System.out.println();	
		  System.out.println("TRANSACTION HISTORY");
          System.out.println("enter your pin no:");
          int pin=s.nextInt();
	      ResultSet rs1=stmt.executeQuery("SELECT * FROM USERDETAILS WHERE PIN=\""+pin+"\";");
          boolean user=rs1.next();   		  
		  if(user)//performs operation if the pin correct
		  {		
		     System.out.println("kindly re-enter your pin no:");
             pin=s.nextInt();
	         ResultSet rs2=stmt.executeQuery("SELECT NAME,AMOUNT FROM TRANSACTION WHERE PIN=\""+pin+"\";");
			 //displaying transaction details		 
			  while(rs2.next())
		      {
               String r_name=rs2.getString("name");
               int r_amt=rs2.getInt("amount");
               System.out.println(r_amt+" Rs transferred to "+r_name);
              } 		 
          }
          else if(!user)//when the pin is incorrect
          {
            System.out.println("Invalid! kindly check your pin number");
          }
		  System.out.println();
		  System.out.println("do you want to continue(enter 1 to continue 0 to exit):");
	      c=s.nextInt();
          break;
		}//case 1 end  
		//case 2 - withdraw 
		case 2:
		{
		  System.out.println();	
		  System.out.println("WITHDRAW");
          System.out.println("enter your pin no:");
          int pin=s.nextInt();
	      ResultSet rs1=stmt.executeQuery("SELECT * FROM USERDETAILS WHERE pin=\""+pin+"\";");
          boolean user=rs1.next();   		  
		  if(user)//performs operation if the pin correct
		  {
			System.out.println("how much do you want to withdraw:");
            amt=s.nextInt();
            ResultSet rs2=stmt.executeQuery("SELECT BALANCE FROM CUSMONEY WHERE pin=\""+pin+"\";");
            while(rs2.next())
            {				  
		      int bal_amt=rs2.getInt("balance");
			  //System.out.println("bal:"+bal_amt);
			  if(bal_amt<1000)
			    System.out.println("You don't have enough amount in your account to withdraw");
		      else
			  { 
		        //balance calculation
			    balance=bal_amt-amt; 
				System.out.println();	
			    System.out.println("withdraw successfull!!"); 
			  } 	 
		   } 	   
		   //balance updation
           String update="UPDATE CUSMONEY SET BALANCE=\""+balance+"\"WHERE PIN=\""+pin+"\";";
           stmt.executeUpdate(update); 		   
	      }
		  else if(!user)//when the pin is incorrect
		  {	   
			   System.out.println("Invalid! kindly check your pin");
		  }
		  System.out.println();
		  System.out.println("do you want to continue(enter 1 to continue 0 to exit):");
	      c=s.nextInt();	
	      break;
	      
		} //case 2 end 
		//case 3 - deposit 
		case 3:
		{
		  System.out.println();		
		  System.out.println("DEPOSIT");
          System.out.println("enter your pin no:");
          int pin=s.nextInt();
	      ResultSet rs1=stmt.executeQuery("SELECT * FROM USERDETAILS WHERE pin=\""+pin+"\";");
          boolean user=rs1.next();   		  
		  if(user)//performs operation if the pin correct
		  {
			System.out.println("how much do you want to deposit:");
            amt=s.nextInt();
            ResultSet rs2=stmt.executeQuery("SELECT BALANCE FROM CUSMONEY WHERE pin=\""+pin+"\";");
            while(rs2.next())
            {				  
		      int bal_amt=rs2.getInt("balance");
			  //System.out.println("bal:"+bal_amt);
			  if(amt>10000)
			    System.out.println("kindly deposit 10,000 below");
		      else
			  { 
		        //balance calculation
			    balance=bal_amt+amt; 
				System.out.println();	
			    System.out.println("Deposit successfull!!"); 
			  } 	 
		   } 
		   //balance updation	   
           String update="UPDATE CUSMONEY SET BALANCE=\""+balance+"\"WHERE PIN=\""+pin+"\";";
           stmt.executeUpdate(update); 		   
	      }
		  else if(!user)//when the pin is incorrect
		  {	   
			   System.out.println("Invalid! kindly check your pin number");
		  } 
		  System.out.println();
		  System.out.println("do you want to continue(enter 1 to continue 0 to exit):");
	      c=s.nextInt();	
	      break;
	    }//case 3 end
		//case 4 - balance amount
        case 4:
		{
	      System.out.println();		
		  System.out.println("BALANCE AMOUNT");
          System.out.println("enter your pin no:");
          int pin=s.nextInt();
	      ResultSet rs1=stmt.executeQuery("SELECT * FROM USERDETAILS WHERE pin=\""+pin+"\";");
          boolean user=rs1.next();   		  
		  if(user)//performs operation if the pin correct
		  {
            ResultSet rs2=stmt.executeQuery("SELECT BALANCE FROM CUSMONEY WHERE pin=\""+pin+"\";");
            while(rs2.next())
            {				  
		      //collecting balance
		      int bal_amt=rs2.getInt("balance");
			  System.out.println();	
			  System.out.println("Your account Balance is:"+bal_amt);  
		    }   
	      }
		  else if(!user)//when the pin is incorrect
          {			  
			   System.out.println("Invalid! kindly check your pin number");
	      }		   
		  System.out.println();
		  System.out.println("do you want to continue(enter 1 to continue 0 to exit):");
	      c=s.nextInt();	
	      break;
	    }//case 4 end
        //case 5 - transfer amount 		
        case 5:
		{
		  System.out.println();	
		  System.out.println("TRANSFER AMOUNT");
          System.out.println("enter your pin no:");
          int pin=s.nextInt();
	      ResultSet rs1=stmt.executeQuery("SELECT * FROM USERDETAILS WHERE pin=\""+pin+"\";");
          boolean user=rs1.next();   		  
		  if(user)//performs operation if the pin correct
		  {
			System.out.println("Enter recepients name:");
            String recep=s.next();
            System.out.println("Enter recipients account number:");
            String recep_acc=s.next();
            if(recep_acc.length()>9 || recep_acc.length()<9)
            {
               System.out.println("Enter valid account number");
			   recep_acc=s.next();
            }			   
			
			//tranferring amount and updating balance
			System.out.println("how much do you want to transfer:");
            amt=s.nextInt();
			
            ResultSet rs2=stmt.executeQuery("SELECT BALANCE FROM CUSMONEY WHERE pin=\""+pin+"\";");
            while(rs2.next())
            {				  
		      int bal_amt=rs2.getInt("balance");
			  //System.out.println("bal:"+bal_amt);
			  if(bal_amt<1000)
			    System.out.println("You don't have enough amount in your account to perform transaction");
		      else
			  { 
		        //collecting balance
			    balance=bal_amt-amt; 
				System.out.println();	
			    System.out.println("Amount transferred to "+recep);
			  } 	 
		   } 	   
		   //Transaction table updation 
		   String trans="INSERT INTO TRANSACTION VALUES(\""+pin+"\",\""+recep+"\",\""+amt+"\");";
	       int up=stmt.executeUpdate(trans);
		   //balance updation
           String update="UPDATE CUSMONEY SET BALANCE=\""+balance+"\"WHERE PIN=\""+pin+"\";";
           stmt.executeUpdate(update); 		   
	      }
		  else if(!user)//when the pin is incorrect
		  {	   
			   System.out.println("Invalid! kindly check your pin");
		  }
		  System.out.println();
		  System.out.println("do you want to continue(enter 1 to continue 0 to exit):");
	      c=s.nextInt();	
	      break;   
		} //case 5 end 
		//case 6 - quit
		case 6:
		{
			c=0;
			break;
	    }		
		//default
		default:
		   System.out.println("CHOICE NOT AVAILABLE , CHOOSE CORRECTLY");
		   System.out.println();
		   System.out.println("do you want to continue(enter 1 to continue 0 to exit):");
	       c=s.nextInt();
		   break;
	}//switch end
  }//while end	  
 }
 //catch block
  catch(Exception ex)
  {
     System.out.println("database not connected");
     System.out.println(ex.toString());
  }
}//main method end   
}//class end