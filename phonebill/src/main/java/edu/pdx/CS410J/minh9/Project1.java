package edu.pdx.CS410J.minh9;

import edu.pdx.cs410J.AbstractPhoneBill;
import java.util.Collection;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

  private String Customer;
  private String Callee;
  private String Caller;
  private String startTime;
  private String endTime;

  public static void main(String[] args) {

    PhoneCall call = new PhoneCall("test","test","test","test");  // Refer to one of Dave's classes so that we can be sure it is on the classpath

    PhoneBill bill = new PhoneBill("Test",call);
    bill.addPhoneCall(call);

    Collection<PhoneCall> phoneCalls = bill.getPhoneCalls();
    for (PhoneCall c : phoneCalls) {
      System.out.println(c);
    }

    System.err.println("Missing command line arguments");
    for (String arg : args) {
      System.out.println(arg);
    }
 try {

   if (args.length == 0 || args.length < 6) {
     throw new IllegalAccessException("Arguments are missing");
   } else if (args.length==1 && args[0].equals("-README")) {
       readme();
       System.exit(1);

   }
 }
  catch (IllegalAccessException ex )
  {

  }
    System.exit(1);
  }

  private static void readme() {
    System.out.println("README has been called");
    System.out.println("This program is a phonebill application which takes a very specific amount of arguments");
    System.out.println("You must provide a customer name, caller number, callee number, start time, and end time (mm/dd/yyyy mm:hh)");
    System.out.println();
    System.out.println("Usage: java edu.pdx.cs410J.<login-id>.Project1 [options] <args>\n" +
            "   args are (in this order):\n" +
            "       customer               Person whose phone bill we’re modeling\n" +
            "       callerNumber           Phone number of caller\n" +
            "       calleeNumber           Phone number of person who was called\n" +
            "       startTime              Date and time call began (24-hour time)\n" +
            "       endTime                Date and time call ended (24-hour time)\n" +
            "   options are (options may appear in any order):\n" +
            "       -print                 Prints a description of the new phone call\n" +
            "       -README                Prints a README for this project and exits\n" +
            "   Date and time should be in the format: mm/dd/yyyy hh:mm");
  }

}