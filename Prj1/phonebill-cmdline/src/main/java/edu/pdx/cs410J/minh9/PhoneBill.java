package edu.pdx.cs410J.minh9;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * This is the phone bill class.
 * @param calls : the collections of call of this phone bill
 * @param customer : customer of this bill
 */
public class PhoneBill extends AbstractPhoneBill<PhoneCall> {

    private ArrayList<PhoneCall> calls = new ArrayList<>();
    String customer;

    /**
     * Class 's constructor
     * @param customer
     * @param call
     */
    public PhoneBill(String customer,PhoneCall call)
    {
        //this.customer=customer;
        setCustomer(customer);
        addPhoneCall(call);
    }
    /**
     * add this constructor in project2
     */
    public PhoneBill(String customer)
    {
        this.customer= customer;
    }

    public void setCustomer(String customer)
    {
        this.customer=customer;
    }

    public PhoneBill()
    {

    }

    /**
     * this function return the customer of the bill
     * @return
     */
    @Override
    public String getCustomer() {
        return customer;
    }

    /**
     * this function to add phone calls to this phone bill
     * @param call
     */
    @Override
    public void addPhoneCall(PhoneCall call) {
        this.calls.add(call);
    }

    /**
     * this function return all the phone calls of this bills
     * @return
     */
    @Override
    public ArrayList<PhoneCall> getPhoneCalls() {
    Collections.sort(calls);

        return this.calls;
    }




}
