package edu.pdx.cs410J.minh9;

import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.util.ArrayList;


public class PhoneBillRestClient extends HttpRequestHelper {
    private static final String WEB_APP = "phonebill";
    private static final String SERVLET = "calls";

    private final String url;


    /**
     * Creates a client to the Phone Bill REST service running on the given host and port
     *
     * @param hostName The name of the host
     * @param port     The port
     */
    public PhoneBillRestClient(String hostName, int port) {
        this.url = String.format("http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET);
    }


    /**
     * Get all the calls
     * @return
     * @throws IOException
     */
    public Response getAllCalls() throws IOException {
        return get(this.url);
    }

    /**
     * Get all the calls from a range of start date and end date
     * @param customer
     * @param start
     * @param end
     * @return
     * @throws IOException
     */
    public Response getRangeCalls(String customer, String start, String end) throws IOException {
        return get(this.url, "customer", customer, "startTime", start, "endTime", end);
    }

    /**
     * Post a new phone call to servlet
     * @param customer
     * @param caller
     * @param callee
     * @param start
     * @param end
     * @return
     * @throws IOException
     */

    public Response addCall(String customer, String caller, String callee, String start, String end) throws IOException {
        return post(this.url, "customer", customer, "caller", caller, "callee", callee, "startTime", start, "endTime", end);
    }
}
