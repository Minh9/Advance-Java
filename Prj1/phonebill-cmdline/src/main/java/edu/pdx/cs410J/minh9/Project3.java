package edu.pdx.cs410J.minh9;

import java.util.ArrayList;
import java.util.Collections;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Project3 {

    private static ArrayList<String> argumentList;
    private static PhoneBill bill;
    private static PhoneCall call;
    private static String filePath;
    private static TextDumper dump;
    private static TextParser par;
    private static String prettyPath;

    /**
     * This is the main class for Project 3
     * This program will first check whether -README -Print -textFile and argument list as arguments
     * If -README contain in the argument list then call readme
     * If not, the check the argument list for a call, if it's not right then print errors and exit
     * if -textFile is in the list,then create a new file in a specific folder
     * if the file is not existed, then create a new one and print the location of the file
     * then write the new phone call to the file
     * if file existed, first check the customer name, if it is duplicated,
     * then print out the message
     * else then loop through all the calls in the file and check whether input call is duplicated or not
     * if not then write it to the file
     * if it 's duplicated then print a message
     * if -pretty in the argument list and following argument is - or a valid file path then print out the bill to
     * the console or in the file
     * if -Print contains in the argument list and argument list of a call valid, then print that call
     * @param args
     */


    public static void main(String[] args) {

        if (args.length == 0) {
            System.err.println("Missing command line arguments");
            System.exit(1);
        }
        PrettyPrinter printer;
        call = new PhoneCall();
        bill = new PhoneBill();
        argumentList = new ArrayList<>();
        addArguments(argumentList, args);
        if (checkReadme(argumentList)) readme();
        boolean isFile;
        isFile = checkFile(argumentList);
        boolean isPrint;
        isPrint = checkPrint(argumentList);
        boolean isPretty;
        isPretty =checkPretty(argumentList);
        boolean is_Customer_StartDate_EndDate;
        is_Customer_StartDate_EndDate = checkCustomerStartEnd(argumentList, 9);
        if (!is_Customer_StartDate_EndDate)
        {
            System.out.println("Invalid arguments \n");
            System.exit(1);
        }



        if (isFile) {
            try {
                par = new TextParser(filePath);
                bill = (PhoneBill) par.parse();
            }
            catch (Exception ex)
            {
                System.out.println(ex.getMessage());
                System.exit(1);
            }
        }
        if (!isFile && is_Customer_StartDate_EndDate) {
            createBill(bill, call, argumentList);
        }




        if (!is_Customer_StartDate_EndDate && !isFile) {
            System.out.println("Invalid Argument");
            System.exit(1);
        }




        if (isFile &&!is_Customer_StartDate_EndDate )
        {
            System.out.println("Invalid arguments \n");
            System.exit(1);
        }
        if (isFile && is_Customer_StartDate_EndDate) {



            if (bill != null) {
                if (bill.getCustomer().equals(argumentList.get(0)) && (!bill.getCustomer().equals(""))) {
                    if (!checkDuplicate(bill,argumentList)) {
                        createBill(bill, call, argumentList);
                    } else {
                        System.out.println(" Call is duplicated \n");
                        System.exit(1);
                    }
                    try {
                        new TextDumper(filePath).dump(bill);
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                        System.exit(1);
                    }
                } else {
                    System.out.println("Provided Customer does not own the file in the filepath\n");
                    System.exit(1);
                }
            } else {
                bill = new PhoneBill();
                createBill(bill, call, argumentList);
                try {
                    new TextDumper(filePath).dump(bill);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                    System.exit(1);
                }

            }

        }
        if(isPretty)
        {
            if (prettyPath.equals("-")) {
                printer = new PrettyPrinter();
                printer.dumpOut(bill);
            } else {
                try {
                    printer = new PrettyPrinter(prettyPath);
                    printer.dump(bill);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        if (isPrint) {
            if (is_Customer_StartDate_EndDate) {
                createBill(bill, call, argumentList);
                printOption(bill, call);
            } else {
                System.out.println("Please provide arguments of the Phone Call to print \n");
                System.exit(1);
            }


        }



    }

    /**
     * This function to check the -textFile in the argument list
     * @param argumentList
     * @return boolean
     */

    private static boolean checkFile(ArrayList<String> argumentList) {
        int fileIndex;
        if (argumentList.contains("-textFile")) {
            fileIndex = argumentList.indexOf("-textFile") + 1;
            filePath = argumentList.get(fileIndex);
            argumentList.remove("-textFile");
            argumentList.remove(filePath);

            return true;

        } else {
            return false;
        }
    }

    /**
     * This function to create a new phone call and add to bill
     * @param bill
     * @param call
     * @param callDetail
     */

    public static void createBill(PhoneBill bill, PhoneCall call, ArrayList<String> callDetail) {
        try {
            String Customer;
            String Callee;
            String Caller;
            String startTime;
            String endTime;
            Customer = callDetail.get(0);
            Caller = callDetail.get(1);
            Callee = callDetail.get(2);

            startTime = callDetail.get(3) + " " + callDetail.get(4) + " "+callDetail.get(5);

            endTime = callDetail.get(6) + " " + callDetail.get(7) + " "+ callDetail.get(8);

            if (!Caller.matches("\\d{3}-\\d{3}-\\d{4}") || !Callee.matches("\\d{3}-\\d{3}-\\d{4}"))
                throw new IllegalArgumentException("Phone number format: 10 digits plus two dashes");
            if (!callDetail.get(3).matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)") || !callDetail.get(6).matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)"))
                throw new IllegalArgumentException("Date format : mm/dd/yyyy");
            if(!callDetail.get(4).matches("(1[0-2]|0?[1-9]):([0-5][0-9])")||!callDetail.get(7).matches("(1[0-2]|0?[1-9]):([0-5][0-9])"))
                throw new IllegalArgumentException("Time format must follow mm:hh (12 hour time)");
            if(!callDetail.get(5).matches("(am|pm|AM|PM)")||!callDetail.get(8).matches("(am|pm|AM|PM)"))
                throw new IllegalArgumentException("Time  include am/pm");


            call.setCaller(Caller);
            call.setCallee(Callee);
            call.setStartTime(startTime);
            call.setEndTime(endTime);
            bill.setCustomer(Customer);
            bill.addPhoneCall(call);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.exit(1);
        }
    }

    /**
     * This function to add argument list to array list
     * @param argumentList
     * @param args
     */
    private static void addArguments(ArrayList<String> argumentList, String[] args) {

        Collections.addAll(argumentList, args);
    }

    /**
     * This function to check -README contains in the argument list
     * @param argumentList
     * @return boolean
     */
    private static boolean checkReadme(ArrayList<String> argumentList) {
        if (argumentList.contains("-README")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This function to check -Print in the argument list
     * @param argumentList
     * @return boolean
     */

    private static boolean checkPrint(ArrayList<String> argumentList) {
        if (argumentList.contains("-print")) {
            argumentList.remove("-print");
            return true;
        } else {
            return false;
        }
    }

    /**
     * This function to check the argument of a phone call is correct or not
     * @param argumentList
     * @param number_of_argument
     * @return boolean
     */
    private static boolean checkCustomerStartEnd(ArrayList<String> argumentList, int number_of_argument) {
        if (argumentList.size() < number_of_argument || argumentList.size() > number_of_argument) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * This function to print the information of readme when -README in the argument list
     *
     */

    public static void readme() {
        System.out.println("Project 3");
        System.out.println("Please provide customer name, caller number, callee number, start time, and end time");
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
     * This function to print the information of new phone call to console
     * @param bill
     * @param call
     */
    public static void printOption(PhoneBill bill, PhoneCall call) {

        System.out.println("Customer: " + bill.getCustomer() + "\n Detail: \n" + call.toString());
        System.exit(1);

    }

    /**
     * This function to check the duplicate of input call
     * @param bill
     * @param argumentList
     * @return
     */
    private static  boolean checkDuplicate(PhoneBill bill,ArrayList<String> argumentList )
    {
        boolean isDuplicate=false;


        for (PhoneCall call : bill.getPhoneCalls())
        {
         String starTime = call.getStartTimeString();
         String _startTime = starTime.substring(starTime.length()-2,starTime.length());
         String _startTimeArgument = argumentList.get(5);
         boolean checkstartTimeAMPM= _startTime.toLowerCase().equals(_startTimeArgument.toLowerCase());
         String endTime = call.getEndTimeString();
         String _endTime = endTime.substring(endTime.length()-2,endTime.length());
         String _endTimeArgument = argumentList.get(8);

         boolean checkendTimeAMPM = _endTime.toLowerCase().equals(_endTimeArgument.toLowerCase());
            String startTimeString =call.getStartTimeString().substring(0,call.getStartTimeString().length()-2).replaceAll("\\s","");
           String _startTimeString="";
            if (argumentList.get(4).length()==4) {
                _startTimeString = argumentList.get(3) +"0"+ argumentList.get(4);
            }
            else
            {
                _startTimeString = argumentList.get(3) + argumentList.get(4);
            }

         boolean checkStartDateTime= startTimeString.equals(_startTimeString);
          String endTimeString= call.getEndTimeString().substring(0,call.getStartTimeString().length()-2).replaceAll("\\s","");
            String _endTimeString="";
          if (argumentList.get(7).length()==4) {
              _endTimeString = argumentList.get(6) + "0"+argumentList.get(7);
          }
          else
          {
              _endTimeString = argumentList.get(6) +argumentList.get(7);


          }
          boolean checkEndDateTime = endTimeString.equals(_endTimeString);


            if (call.getCaller().equals(argumentList.get(1)) && call.getCallee().equals(argumentList.get(2))
                    &&checkStartDateTime
                    &&checkEndDateTime
                    && checkstartTimeAMPM && checkendTimeAMPM)
            {
                isDuplicate=true;
                break;

            } else
            {
                isDuplicate=false   ;
                break;

            }

        }
        return isDuplicate;

    }

    /**
     * This function to check the -pretty argument is in the argument list or not
     * @param argumentList
     * @return
     */

    private static boolean checkPretty(ArrayList<String> argumentList)
    {
        int fileNameIndex;


        if (argumentList.contains("-pretty")) {
            fileNameIndex = argumentList.indexOf("-pretty") + 1;
            prettyPath = argumentList.get(fileNameIndex);
            argumentList.remove("-pretty");
            argumentList.remove(prettyPath);
            return true;
        }
        return false;
    }

}