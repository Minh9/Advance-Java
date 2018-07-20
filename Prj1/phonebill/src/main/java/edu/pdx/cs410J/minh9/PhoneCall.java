package edu.pdx.cs410J.minh9;

import edu.pdx.cs410J.AbstractPhoneCall;

/**
 * This is the phone call class
 * @ param
 * caller : caller's number
 * callee : callee's number
 * startTime: start time of the call : mm/dd/yy hh:mm
 * endTime : end time of the call : mm/dd/yy hh:mm
 */
public class PhoneCall extends AbstractPhoneCall {

  String caller;
  String callee;
  String startTime;
  String endTime;

  /**
   * Class's constructor
   * @param caller
   * @param callee
   * @param startTime
   * @param endTime
   */
  public PhoneCall(String caller, String callee, String startTime, String endTime )
  {
    //this.caller= caller;
    //this.callee= callee;
    //this.startTime= startTime;
    //this.endTime= endTime;
    setCaller(caller);
    setCallee(callee);
    setStartTime(startTime);
    setEndTime(endTime);
  }
   public PhoneCall()
   {

   }
  public void setCaller( String caller)
  {
    this.caller = caller;
  }
  public  void setCallee(String callee)
  {
    this.callee =callee;
  }
  public void setStartTime(String startTime)
  {
    this.startTime= startTime;
  }
  public void setEndTime(String endTime)
  {
    this.endTime = endTime;
  }
  /**
   * This function return the caller's number
   * @return
   */
  @Override
  public String getCaller() {
    return this.caller;
  }

  /**
   * This function return the callee 's number
   * @return
   */
  @Override
  public String getCallee() {
    return this.callee;
  }

  /**
   * This function return the start time of the call
   * @return
   */
  @Override
  public String getStartTimeString() {
    return this.startTime;
  }

  /**
   * This function return the end time of the call
   * @return
   */
  @Override
  public String getEndTimeString() {
    return this.endTime;
  }
}
