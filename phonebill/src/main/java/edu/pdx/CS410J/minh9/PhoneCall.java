package edu.pdx.CS410J.minh9;

import edu.pdx.cs410J.AbstractPhoneCall;

public class PhoneCall extends AbstractPhoneCall {

  String callerNumber;
  String calleeNumber;
  String startTime;
  String endTime;

  public PhoneCall(String callerNumber, String calleeNumber, String startTime, String endTime )
  {
    this.callerNumber= callerNumber;
    this.calleeNumber= calleeNumber;
    this.startTime= startTime;
    this.endTime= endTime;
  }
  @Override
  public String getCaller() {
    return this.callerNumber;
  }


  @Override
  public String getCallee() {
    return this.calleeNumber;
  }

  @Override
  public String getStartTimeString() {
    return this.startTime;
  }

  @Override
  public String getEndTimeString() {
      return this.endTime;
  }
}
