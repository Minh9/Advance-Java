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
                Collections.addAll(callDetail, inputFile.nextLine().split("\\s*\\s* "));


                if (callDetail.size()!=8)
                {
                    System.out.println("Using file is not right format");
                    System.exit(1);
                }
                if (!callDetail.get(0).matches("\\d{3}-\\d{3}-\\d{4}") || !callDetail.get(1).matches("\\d{3}-\\d{3}-\\d{4}"))
                    throw new IllegalArgumentException("Phone number format: 10 digits plus two dashes in existed file");
                if (!callDetail.get(2).matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)") || !callDetail.get(5).matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)"))
                    throw new IllegalArgumentException("Date format : mm/dd/yyyy in existed file");

                if(!callDetail.get(3).matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")||!callDetail.get(6).matches("([01]?[0-9]|2[0-3]):[0-5][0-9]"))
                    throw new IllegalArgumentException("Time format must follow mm:hh (12 hour time)");
                if(!callDetail.get(4).matches("(am|pm|AM|PM)")&&!callDetail.get(7).matches("(am|pm|AM|PM)"))
                    throw new IllegalArgumentException("Time must include am/pm");

                call.setStartTime(callDetail.get(2)+" " + callDetail.get(3) +" "+ callDetail.get(4));
                call.setEndTime(callDetail.get(5) + " "+callDetail.get(6) + " " + callDetail.get(7));

                call.setCaller(callDetail.get(0));
                call.setCallee(callDetail.get(1));
                bill.addPhoneCall(call);

            }
        }
        return bill;
    }


}