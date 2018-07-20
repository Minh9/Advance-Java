package edu.pdx.cs410J.minh9;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * This class read information of existing phone bill in a file and return object
 */
public class TextParser implements PhoneBillParser {

    private String filename;

    /**
     * Constructor
     * @param filename
     */
    public TextParser(String filename) {
        this.filename = filename;
    }

    /**
     * read the phone bill information in existing file to an bill object
     * @return phone bill
     */
    @Override
    public AbstractPhoneBill parse()  {
        Scanner inputFile = null;
        PhoneBill bill = null;
        PhoneCall call = new PhoneCall();
        ArrayList<String> callDetail;
        File file = new File(filename);

        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("New file is created at :"+ filename);
            } catch (IOException e) {

                System.out.println(e.getMessage());
                System.exit(1);
            }
        } else {
            try {
                inputFile = new Scanner(file);
            } catch (FileNotFoundException e) {

                System.out.println(e.getMessage());
                System.exit(1);
            }
            if (!inputFile.hasNext())
                return null;

            bill = new PhoneBill(inputFile.nextLine());
            while (inputFile.hasNextLine()) {
                callDetail = new ArrayList<>();
                Collections.addAll(callDetail, inputFile.nextLine().split(";"));
                call.setStartTime(callDetail.get(2)+";" + callDetail.get(3));
                call.setEndTime(callDetail.get(4) + ";"+callDetail.get(5));
                call.setCaller(callDetail.get(0));
                call.setCallee(callDetail.get(1));
                bill.addPhoneCall(call);

            }
        }
        return bill;
    }
}