package edu.pdx.cs410J.minh9;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.io.IOException;


public class Messages {
    public static String getMappingCount(int count) {
        return String.format("Server contains %d phone calls.", count);
    }

    public static String printPrettyCall(AbstractPhoneCall call) {
        PrettyPrinter pp = new PrettyPrinter();
        try {
            return pp.prettyPrintWeb(call);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String missingRequiredParameter(String parameterName) {
        return String.format("The required parameter \"%s\" is missing", parameterName);
    }

    public static String checkDateFormat(String parameterName) {
        return String.format("%s should be in the format MM/dd/yyyy hh:mm (am/pm)", parameterName);
    }

    public static String addedCall(String call) {
        return String.format(" %s has been added", call);
    }

    public static String checkCustomer(String parameterName) {
        return String.format("%s customer is not matched !", parameterName);
    }

}
