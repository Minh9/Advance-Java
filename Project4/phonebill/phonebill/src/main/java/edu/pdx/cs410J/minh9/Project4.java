package edu.pdx.cs410J.minh9;

import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collections;


/**
 * This is the main class of project 4
 * This will parse the command line and communicate with the server
 *
 */

public class Project4 {

    public static final String MISSING_ARGS = "Missing command line arguments";

    public static void main(String... args) {
        ArrayList<String> argumentList = new ArrayList<String>();
        Collections.addAll(argumentList, args);

        StringBuilder hostName = new StringBuilder();
        StringBuilder portString = new StringBuilder();
        String customer = null;
        String caller = null;
        String callee = null;
        String startDate = null;
        String startTime = null;
        String startAMPM = null;
        String endDate = null;
        String endTime = null;
        String endAMPM = null;
        String start = null;
        String end = null;
        boolean isPrint = false;
        boolean isSearch = false;

        if (argumentList.size() == 0) {
            usage(MISSING_ARGS);
        }

        checkReadMe(argumentList);
        isPrint = checkPrint(argumentList);
        isSearch = checkSearch(argumentList);
        checkHostAndPort(hostName, portString, argumentList);

        int port;
        try {
            port = Integer.parseInt(portString.toString());
        } catch (NumberFormatException ex) {
            usage(  portString + "\" must be an integer");
            return;
        }

        for (String arg : argumentList) {
            if (customer == null) {
                customer = arg;
            } else if (caller == null) {
                caller = arg;
                checkTelephone(caller );
            } else if (callee == null) {
                callee = arg;
                checkTelephone(callee);
            } else if (startDate == null) {
                startDate = arg;
                checkDate(startDate);
            } else if (startTime == null) {
                startTime = arg;
                checkTime(startTime);
            } else if (startAMPM == null) {
                startAMPM = arg;
                checkAMPM(startAMPM);
            } else if (endDate == null) {
                endDate = arg;
                checkDate(endDate);
            } else if (endTime == null) {
                endTime = arg;
                checkTime(endTime);
            } else if (endAMPM == null) {
                endAMPM = arg;
                checkAMPM(endAMPM);
            }
        }


        if (isSearch) {
            start = caller + " " + callee + " " + startDate;
            end = startTime + " " + startAMPM + " " + endDate;
            checkArgListSize(argumentList, 7);

        } else {
            start = startDate + " " + startTime + " " + startAMPM;
            end = endDate + " " + endTime + " " + endAMPM;
            checkArgListSize(argumentList, 9);
        }

        PhoneBillRestClient client = new PhoneBillRestClient(hostName.toString(), port);
        HttpRequestHelper.Response response = null;

        try {
            if (argumentList.size() == 1) {
                response = client.getAllCalls();
            } else if (isSearch) {
                response = client.getRangeCalls(customer, start, end);
            } else {
                response = client.addCall(customer, caller, callee, start, end);
            }
            checkResponseCode(HttpURLConnection.HTTP_OK, response);

        } catch (IOException ex) {
            error( "Error" +ex);
            return;
        }

        if (isPrint && !isSearch) {
            System.out.println(String.format("Call from %s to %s from %s to %s.", caller, callee, start, end));
        }

        System.out.println(response.getContent());

        System.exit(0);
    }

    /**
     * Check the telephone format
     * @param telephone
     */

    private static void checkTelephone(String telephone)
    {
        try {
            if (!telephone.matches("\\d{3}-\\d{3}-\\d{4}"))
                throw new IllegalArgumentException("Phone number format: 10 digits plus two dashes");
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
            System.exit(1 );
        }
    }

    /**
     * Check the date format
     * @param Date
     */
    private static void checkDate(String Date)
    {
        try {
            if (!Date.matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)"))
                throw new IllegalArgumentException("Date format : mm/dd/yyyy");
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
            System.exit(1);
        }
    }

    /**
     * Check the time format
     * @param Time
     */
    private static void checkTime(String Time)
    {
        try
        {
            if(!Time.matches("(1[0-2]|0?[1-9]):([0-5][0-9])"))
                throw new IllegalArgumentException("Time format must follow mm:hh (12 hour time)");
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
            System.exit(1);

        }

    }

    /**
     * Check AM/PM in the the argument
     * @param time
     */
    private static void checkAMPM(String time)
    {
        try {
            if (!time.matches("(am|pm|AM|PM)"))
                throw new IllegalArgumentException("Time  include am/pm");
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
            System.exit(1);

        }
    }

    /**
     * Check -README
     * if -README then print out the README content
     * @param argumentList
     */
    private static void checkReadMe(ArrayList<String> argumentList) {
        if (argumentList.contains("-README")) {
            printREADME();
            System.exit(1);
        }
    }

    /**
     * Print out the readme content
     */
    private static void printREADME() {

        usage("Project 4 - README \n");
    }

    /**
     * Check the -print
     * If true then remove the -print out of the argument list
     * @param argumentList
     * @return
     */
    private static boolean checkPrint(ArrayList<String> argumentList) {
        if (argumentList.contains("-print")) {
            argumentList.remove("-print");
            return true;
        }
        return false;
    }

    /**
     * Check the -search in argument list
     * if true then remove the -search out of the list
     * @param argumentList
     * @return
     */

    private static boolean checkSearch(ArrayList<String> argumentList) {
        if (argumentList.contains("-search")) {
            argumentList.remove("-search");
            return true;
        }
        return false;
    }

    /**
     * Check the port and host
     * if true remove them out of the argument list
     * if only port or host, print out an appropriate message
     * @param host
     * @param port
     * @param argumentList
     */
    private static void checkHostAndPort(StringBuilder host, StringBuilder port, ArrayList<String> argumentList) {
        if (argumentList.contains("-host") && argumentList.contains("-port")) {
            int hostIndex = argumentList.indexOf("-host") + 1;
            int portIndex = argumentList.indexOf("-port") + 1;
            host.append(argumentList.get(hostIndex));
            port.append(argumentList.get(portIndex));
            argumentList.remove(hostIndex);
            argumentList.remove(hostIndex - 1);
            portIndex = argumentList.indexOf("-port") + 1;
            argumentList.remove(portIndex);
            argumentList.remove(portIndex - 1);
        } else if (argumentList.contains("-host")) {
            usage("Missing port!");
        } else if (argumentList.contains("-port")) {
            usage("Missing the hostname!");
        }
    }

    /**
     * Check the size of the argument list
     * @param argumentList
     * @param size
     */
    private static void checkArgListSize(ArrayList<String> argumentList, int size) {
        if (argumentList.size() < size) {
            usage("Invalid arguments.");
        } else if (argumentList.size() > size) {
            usage("Invalid arguments.");
        }
    }


    private static void checkResponseCode(int code, HttpRequestHelper.Response response) {
        if (response.getCode() != code) {
            error(String.format("Expected HTTP code %d, got code %d.\n\n%s", code,
                    response.getCode(), response.getContent()));
        }
    }

    private static void error(String message) {
        PrintStream err = System.err;
        err.println("** " + message);

        System.exit(1);
    }

    /**
     * Prints usage information for this program and exits
     *
     * @param message An error message to print
     */
    private static void usage(String message) {
        PrintStream err = System.err;
        err.println("** " + message);
        err.println();
        err.println("usage: java edu.pdx.cs410J.<login-id>.Project4 [options] <args>");
        err.println("args are (in this order)");
        err.println("  customer   Person whose phone bill we're modeling");
        err.println("  callerNumber   Phone number of caller");
        err.println("  calleeNumber     Phone number of person who was called");
        err.println("  startTime   Date and time call began");
        err.println("  endTime   Date and time call began");
        err.println();
        err.println("options are (options may appear in any order)");
        err.println("-host hostname   Host computer on which the server runs");
        err.println("-port port   Port on which the server is listening");
        err.println("-isSearch   Phone calls should be isSearched for");
        err.println("-print Prints a description of the new phone call");
        err.println("-README prints a README for this project and exits");
        err.println();

        System.exit(1);
    }
}