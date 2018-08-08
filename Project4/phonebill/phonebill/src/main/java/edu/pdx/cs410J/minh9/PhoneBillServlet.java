package edu.pdx.cs410J.minh9;

import edu.pdx.cs410J.ParserException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.text.ParseException;


public class PhoneBillServlet extends HttpServlet {
    private PhoneBill bill;

    /**
     * Client send request by customer, start and end
     * If customer isnot match, it will not return data
     * If only customer is provided, it will return all data of that customer
     * If start time and end time are provided, it return all the data in the bill of that customer with that range
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        String customer = getParameter("customer", request);
        String start = getParameter("startTime", request);
        String end = getParameter("endTime", request);

        if (bill == null) {
            writeEmptyBill(response);
        } else {
            if (customer != null && !customer.equals(bill.getCustomer())) {
                writeIncorrectCustomer(customer, response);
            } else {
                if (customer != null && start == null && end == null) {
                    writeValue(customer, response);
                } else if (customer != null && start != null & end != null) {
                    writeRangeValues(start, end, response);
                } else {
                    writeAllMappings(response);
                }
            }
        }
    }

    /**
     * Post request by customer, caller, callee, start, and end request parameters.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");

        String customer = getParameter("customer", request);
        if (customer == null) {
            missingRequiredParameter(response, "customer");
            return;
        }

        String caller = getParameter("caller", request);
        if (caller == null) {
            missingRequiredParameter(response, "caller");
            return;
        }

        String callee = getParameter("callee", request);
        if (callee == null) {
            missingRequiredParameter(response, "callee");
            return;
        }


        String start = getParameter("startTime", request);
        if (start == null) {
            missingRequiredParameter(response, "startTime");
            return;
        }


        String end = getParameter("endTime", request);
        if (end == null) {
            missingRequiredParameter(response, "endTime");
            return;
        }

        if (bill == null) {
            bill = new PhoneBill(customer);
        } else if (!bill.getCustomer().equals(customer)) {
            writeIncorrectCustomer(customer, response);
            return;
        }
        PhoneCall call = new PhoneCall(caller, callee, start, end);
        bill.addPhoneCall(call);

        PrintWriter pw = response.getWriter();
        pw.println(Messages.addedCall(call.toString()));
        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Write error message when argument is missed
     * @param response
     * @param parameterName
     * @throws IOException
     */

    private void missingRequiredParameter(HttpServletResponse response, String parameterName)
            throws IOException {
        PrintWriter pw = response.getWriter();
        pw.println(Messages.missingRequiredParameter(parameterName));
        pw.flush();

        response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
    }


    /**
     * Check the date format
     * @param date
     * @return
     * @throws ParseException
     */

    private Date checkDate(String date) throws ParseException {
        date = date.replace("pm", "PM").replace("am", "AM");
        DateFormat ShortDateFormat = new SimpleDateFormat("MM/dd/yyy hh:mm a", Locale.ENGLISH);

        return  ShortDateFormat.parse(date);
    }


    /**
     * Write given key to HTTP response
     * @param key
     * @param response
     * @throws IOException
     */

    private void writeValue(String key, HttpServletResponse response) throws IOException {
        ArrayList<PhoneCall> value = bill.getPhoneCalls();

        PrintWriter pw = response.getWriter();
        pw.println(Messages.getMappingCount(value != null ? value.size() : 0));
        for (PhoneCall call : value) {
            pw.println(Messages.printPrettyCall(call));
        }

        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Write the given key that is within the range
     * @param startKey
     * @param endKey
     * @param response
     * @throws IOException
     */

    private void writeRangeValues(String startKey, String endKey, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        Date start = null;
        Date end = null;
        try {
            start = checkDate(startKey);
        } catch (ParseException e) {
            writeIncorrectDateFormat("startTime", response);
            return;
        }
        try {
            end = checkDate(endKey);
        } catch (ParseException e) {
            writeIncorrectDateFormat("endTime", response);
            return;
        }
        ArrayList<PhoneCall> rangeCalls = bill.getPhoneCallsRange(start, end);

        if (rangeCalls.size() == 0) {
            pw.println("There are no calls that start within this range.");
        } else {
            for (PhoneCall call : rangeCalls) {
                pw.println(Messages.printPrettyCall(call));
            }
        }
        pw.flush();
        response.setStatus(HttpServletResponse.SC_OK);


    }

    /**
     * If there is no calls in the bill yet, print out a message
     * @param response
     * @throws IOException
     */

    private void writeEmptyBill(HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        pw.println("The phone bill is empty! Please add some call.");
        pw.flush();

        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    /**
     * Write all key/value pair
     * @param response
     * @throws IOException
     */
    private void writeAllMappings(HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        pw.println(Messages.getMappingCount(bill.getPhoneCalls().size()));

        pw.println(bill.toString());
        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * if date is not correct format, print out a message
     * @param date
     * @param response
     * @throws IOException
     */
    private void writeIncorrectDateFormat(String date, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        pw.println(Messages.checkDateFormat(date));
        pw.flush();
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }


    /**
     * If customer is not matched with the customer in the bill, print out a message
     * @param customer
     * @param response
     * @throws IOException
     */
    private void writeIncorrectCustomer(String customer, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        pw.println(Messages.checkCustomer(customer));
        pw.flush();

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }


    private String getParameter(String name, HttpServletRequest request) {
        String value = request.getParameter(name);
        if (value == null || "".equals(value)) {
            return null;

        } else {
            return value;
        }
    }

}
