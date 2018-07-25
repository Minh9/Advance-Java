package edu.pdx.cs410J.minh9;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class write the information of the input phone bill to a file
 */
public class TextDumper implements PhoneBillDumper {
    String filepath;

    /**
     * Constructor
     * @param path
     */
    TextDumper(String path){

        this.filepath =path;

    }

    /**
     * Write the information of bill to a file
     * @param bill
     * @throws IOException
     */
    @Override
    public void dump(AbstractPhoneBill bill) throws IOException{

        PrintWriter writer;
        try {
            writer = new PrintWriter(filepath);
        } catch (IOException ex) {
            throw ex  ;
        }
        writer.println(bill.getCustomer());
        for (PhoneCall call : ((PhoneBill) bill).getPhoneCalls()) {
            writer.println(call.getCaller() + " " + call.getCallee() + " " + call.getStartTimeString() + " " +
                    call.getEndTimeString());
        }
        writer.close();
    }
}
