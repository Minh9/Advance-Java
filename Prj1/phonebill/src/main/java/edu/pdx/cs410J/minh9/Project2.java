package edu.pdx.cs410J.minh9;

import java.util.ArrayList;
import java.util.Collections;
import java.io.IOException;
public class Project2 {

    private static ArrayList<String> argumentList;
    private static PhoneBill bill;
    private static PhoneCall call;
    private static String filePath;
    private static TextDumper dump;
    private static TextParser par;

    /**
     * This is the main class for Project 2
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
     * if -Print contains in the argument list and argument list of a call valid, then print that call
      * @param args
     */


    public static void main(String[] args) {

        if (args.length == 0) {
            System.err.println("Missing command line arguments");
            System.exit(1);
        }
        call = new PhoneCall();
        bill = new PhoneBill();
        argumentList = new ArrayList<>();
        addArguments(argumentList, args);
        if (checkReadme(argumentList)) readme();
        boolean isFile;
        isFile = checkFile(argumentList);
        boolean isPrint;
        isPrint = checkPrint(argumentList);
        boolean is_Customer_StartDate_EndDate;
        is_Customer_StartDate_EndDate = checkCustomerStartEnd(argumentList, 7);
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
            startTime = callDetail.get(3) + ";" + callDetail.get(4);
            endTime = callDetail.get(5) + ";" + callDetail.get(6);

            if (!Caller.matches("\\d{3}-\\d{3}-\\d{4}") || !Callee.matches("\\d{3}-\\d{3}-\\d{4}"))
                throw new IllegalArgumentException("Phone number format: 10 digits plus two dashes");
            if (!callDetail.get(3).matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)") || !callDetail.get(5).matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)"))
                throw new IllegalArgumentException("Date format : mm/dd/yyyy");
            if (!callDetail.get(4).matches("([01]?[0-9]|2[0-3]):[0-5][0-9]") || !callDetail.get(6).matches("([01]?[0-9]|2[0-3]):[0-5][0-9]"))
                throw new IllegalArgumentException("Time format : mm:hh (24 hour time)");


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
        System.out.println("Project 2");
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
           if (call.getCaller().equals(argumentList.get(1)) && call.getCallee().equals(argumentList.get(2))
                   && call.getStartTimeString().equals(argumentList.get(3) + ";" + argumentList.get(4))
                   && call.getEndTimeString().equals(argumentList.get(5) + ";" + argumentList.get(6)))
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

}