package edu.pdx.CS410J.minh9;

import edu.pdx.cs410J.AbstractPhoneBill;

import javax.xml.stream.events.EndDocument;
import java.util.Collection;

/**
 * The main class for the CS410J Phone Bill Project
 * @param Customer
 * @param  Callee
 * @param Caller
 * @param startTime
 * @param endTime
 */
public class Project1 {

  private static  String Customer;
  private static String Callee;
  private static String Caller;
  private static String startTime;
  private  static String endTime;

  /**
   * This is the main method of this class
   * @param args
   * This function will firstly check the length of arguments
   * If argument's length is 0, then throw exception
   * if argument's length is 1 and argument is "-README" then call the readme() function
   * if argument's length less than 7, throw exception
   * if argument's length less than 10,create a phone call, then phone bill. Add phone call to the phone bill
   * if the 8th argument is -README then , call the readme() function, else call the printOption() function
   * if the 9th argument is -README then, call the readme() function, else call the printOption() function
   *
   */

  public static void main(String[] args) {


    try {

      if (args.length == 0) {
        throw new IllegalAccessException();
      } else if (args.length == 1 && args[0].equals("-README")) {
        readme();

      } else if (args.length <7) {
        throw new IllegalAccessException();
      } else if (args.length <10 ) {
        try {
          setCustomer(args[0]);
          setCaller(args[1]);
          setCallee(args[2]);
          setStartTime(args[3], args[4]);
          setEndTime(args[5], args[6]);
          if(startTime.contains("\"")||endTime.contains("\""))
            throw new IllegalArgumentException("Date and time cannot contain quotes ");
          if (!Caller.matches("\\d{3}-\\d{3}-\\d{4}") || !Callee.matches("\\d{3}-\\d{3}-\\d{4}"))
            throw new IllegalArgumentException("Phone numbers must contain exactly 10 digits plus two dashes");
          if (!args[3].matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)") || !args[5].matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)"))
            throw new IllegalArgumentException("Date format must follow mm/dd/yyyy");
          if (!args[4].matches("([01]?[0-9]|2[0-3]):[0-5][0-9]") || !args[6].matches("([01]?[0-9]|2[0-3]):[0-5][0-9]"))
            throw new IllegalArgumentException("Time format must follow mm:hh (24 hour time)");

        } catch (IllegalArgumentException ex) {
          System.out.println(ex.getMessage());
          System.exit(1);
        }
        PhoneCall call=  new PhoneCall(Caller, Callee, startTime, endTime);
        PhoneBill bill = new PhoneBill(Customer, call);
        if (args.length>7 &&args.length<9 && !args[7].equals("")) {
          if(args[7].equals("-README"))
          {
            readme();
          } else {
            printOption(args[7], bill, call);
          }

        }
         if(args.length>8 && !args[8].equals(""))
        {
          if(args[8].equals("-README"))
          {
            readme();
          } else {
            printOption(args[8], bill, call);
          }

        }

      }
      else
      {
        throw new IllegalAccessException();
      }
    } catch (IllegalAccessException ex) {
        System.out.println("Something is missing from the command line");
    }
    System.exit(1);
  }

  /**
   * This is the readme() method.
   * This has been called whenever the argment -README appears in the arguments list
   */
  public static void readme() {
    System.out.println("Project 1");
    System.out.println("Please provide customer name, caller number, callee number, start time, and end time");
    System.out.println();
    System.out.println("Usage: java edu.pdx.cs410J.<login-id>.Project1 [options] <args>\n" +
            "   args are (in this order):\n" +
            "       customer               Person whose phone bill we’re modeling\n" +
            "       callerNumber           Phone number of caller.CallerNumber format: xxx-xxx-xxxx\n" +
            "       calleeNumber           Phone number of person who was called.CallerNumber format: xxx-xxx-xxxx\n" +
            "       startTime              Date and time call began (24-hour time)\n" +
            "       endTime                Date and time call ended (24-hour time)\n" +
            "   options are (options may appear in any order):\n" +
            "       -print                 Prints a description of the new phone call\n" +
            "       -README                Prints a README for this project and exits\n" +
            "   Date and time should be in the format: mm/dd/yyyy hh:mm");
    System.exit(1);
  }

  /**
   * This function is set the customer name
   * @param _Customer
   */
  private static void setCustomer(String _Customer)
  {
    Customer=_Customer;
  }
  /**
   * This function to set the callee
   */
  public static void setCallee(String _Callee)
  {
    Callee =_Callee;
  }

  /**
   * This function to set the caller
   * @param _Caller
   */
  public static void setCaller(String _Caller)
  {
    Caller =_Caller;
  }

  /**
   * This function to set the start timer
   * @param startDate
   * @param startHour_Minute
   */
  public static void setStartTime(String startDate, String startHour_Minute)
  {
    startTime= startDate + " " + startHour_Minute;
  }

  /**
   * this function to set the end time
   * @param endDate
   * @param endHour_Minute
   */
  public static void setEndTime(String endDate, String endHour_Minute)
  {
    endTime =  endDate + " " + endHour_Minute;
  }

  /**
   * This function to print out the content of this phone bill when the option -print appears in the argument lists
   * @param option
   * @param bill
   * @param call
   */
  public static void printOption (String option, PhoneBill bill, PhoneCall call)
  {
    try {
      switch (option) {

        case "-print":

          System.out.println("Customer: " + bill.getCustomer() + " " + call.toString());
          break;
        default:
          throw new IllegalArgumentException();
      }
    }
    catch (IllegalArgumentException ex)
    {
        System.out.println("Argument not found "+ option);
    }
  }
}