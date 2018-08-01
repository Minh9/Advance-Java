package edu.pdx.cs410J.minh9;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.text.DateFormat;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.text.ParseException;


/**
 * This is the phone call class
 * @ param
 * caller : caller's number
 * callee : callee's number
 * startTime: start time of the call : mm/dd/yy hh:mm
 * endTime : end time of the call : mm/dd/yy hh:mm
 */
public class PhoneCall extends AbstractPhoneCall implements Comparable<PhoneCall>{

  String caller;
  String callee;
  Date startTime;
  Date endTime;
  DateFormat ShortDateFormat = new SimpleDateFormat("MM/dd/yyy hh:mm a", Locale.ENGLISH);
  /**
   * Class's constructor
   * @param caller
   * @param callee
   * @param startTime
   * @param endTime
   */
  public PhoneCall(String caller, String callee, String startTime, String endTime )
  {

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
    try {
      this.startTime = ShortDateFormat.parse(startTime);
    }
    catch (Exception ex)
    {
      System.out.println(ex.getMessage());
      System.exit(1);
    }
  }
  public void setEndTime(String endTime)
  {
    try {
      this.endTime= ShortDateFormat.parse(endTime);
    }
    catch (Exception ex)
    {
      System.out.println(ex.getMessage());
      System.exit(1);
    }  }
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
    if(startTime != null)
      return (ShortDateFormat.format(startTime));
    else
      return "";
  }

  /**
   * This function return the end time of the call
   * @return
   */
  @Override
  public String getEndTimeString() {
    if(endTime != null)
      return (ShortDateFormat.format(endTime));
    else
      return "";
  }

  /**
   * This function to compare the start time this phone call and another phone call
   * @param call
   * @return
   */
  @Override
  public int compareTo(PhoneCall call) {

    Date _startTime = call.getStartTime();
    String caller = call.getCaller();


    if (this.startTime.equals(_startTime)) {
      return (this.caller.compareTo(caller));
    }

    return this.startTime.compareTo(_startTime);


  }

  /**
   * This function return the end time of the phone call
   * @return
   */
  public Date getEndTime() {
    return endTime;
  }

  /**
   * This function return the start time of the phone call
   * @return
   */
  public Date getStartTime() {
    return startTime;
  }

}
