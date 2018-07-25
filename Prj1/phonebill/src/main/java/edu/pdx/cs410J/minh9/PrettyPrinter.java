package edu.pdx.cs410J.minh9;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * This class implement the PrettyPrinter class
 */

public class PrettyPrinter implements PhoneBillDumper {

    private String prettyPath;
    private long duration;

    /**
     * Constructor
     */
    public PrettyPrinter()
    {

    }

    /**
     * Constructor
     * @param prettyPath
     */
    public PrettyPrinter(String prettyPath) {
        this.prettyPath = prettyPath;
    }

    /**
     * This function will print the content to the console
     * @param bill
     */
    public void dumpOut(AbstractPhoneBill bill) {

        System.out.println("" +
                "|--  |  | |--| |" +"\\" +  "  | |--  |--| | |  | \n" +
                "|__| |--| |  | |" +" \\" + " | |__  |--| | |  |\n"  +
                "|    |  | |__| |" +  "  \\" +"| |__  |__| | |__|__ \n");
        System.out.format("\n\nCustomer: %s \n",bill.getCustomer());
        System.out.format("Number of calls: %d  in this bill\n\n", bill.getPhoneCalls().size());
        System.out.format(" Caller        Callee        Start Time          End Time              Duration(minutes) \n");

        for (PhoneCall call : ((PhoneBill) bill).getPhoneCalls()) {
            duration = call.getEndTime().getTime() - call.getStartTime().getTime();
            duration = TimeUnit.MILLISECONDS.toMinutes(duration);
            System.out.format(" %s  %s  %s  %s  %s \n", call.getCaller(),
                    call.getCallee(), call.getStartTimeString(), call.getEndTimeString(), duration);

        }
    }

    /**
     * This function will print the content of bill to a file
     * @param bill
     * @throws IOException
     */
    public void dump(AbstractPhoneBill bill) throws IOException {
        PrintWriter writer;


        try {
            writer = new PrintWriter(prettyPath);
        } catch (IOException e) {
            throw e;
        }

        writer.format("" +
                "|--  |  | |--| |" +"\\" +  "  | |--  |--| | |  | \n" +
                "|__| |--| |  | |" +" \\" + " | |__  |--| | |  |\n"  +
                "|    |  | |__| |" +  "  \\" +"| |__  |__| | |__|__ \n");
        writer.format("\n\nCustomer: %s \n",bill.getCustomer());
        writer.format("Number of calls: %d  in this bill\n\n", bill.getPhoneCalls().size());
        writer.format(" Caller        Callee        Start Time          End Time              Duration(minutes) \n");
        for (PhoneCall call : ((PhoneBill) bill).getPhoneCalls()) {
            duration = call.getEndTime().getTime() - call.getStartTime().getTime();
            duration = TimeUnit.MILLISECONDS.toMinutes(duration);
            writer.format(" %s  %s  %s  %s  %s \n", call.getCallee(),
                    call.getCaller(), call.getStartTimeString(), call.getEndTimeString(), duration);
        }
        writer.close();
    }


}
