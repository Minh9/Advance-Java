package edu.pdx.CS410J.minh9;

import edu.pdx.cs410J.AbstractPhoneBill;
import java.util.ArrayList;
import java.util.Collection;

public class PhoneBill extends AbstractPhoneBill<PhoneCall> {

    private Collection<PhoneCall> calls = new ArrayList<>();
    String customer;
    public PhoneBill(String customer,PhoneCall call)
    {
        this.customer=customer;
        addPhoneCall(call);
    }
    @Override
    public String getCustomer() {
        return customer;
    }

    @Override
    public void addPhoneCall(PhoneCall call) {
        this.calls.add(call);
    }

    @Override
    public Collection<PhoneCall> getPhoneCalls() {
        return this.calls;
    }
}
