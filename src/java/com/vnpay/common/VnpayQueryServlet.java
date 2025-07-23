package com.vnpay.common;


import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * A servlet for admin purposes to query the status of a VNPAY transaction.
 * This is typically used for debugging or reconciliation.
 */
@WebServlet("/admin/vnpay-query")
public class VnpayQueryServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        // --- Step 1: Get parameters from the admin form ---
        // This servlet should be called from an admin page with a form.
        String vnp_TxnRef = req.getParameter("orderId"); // Order ID from your system
        String vnp_TransDate = req.getParameter("transactionDate"); // Transaction date in yyyyMMddHHmmss format

        // --- Step 2: Prepare VNPAY QueryDR parameters ---
        String vnp_RequestId = Config.getRandomNumber(8);
        String vnp_Version = "2.1.0";
        String vnp_Command = "querydr";
        String vnp_TmnCode = Config.vnp_TmnCode;
        String vnp_OrderInfo = "Querying transaction status for Order ID: " + vnp_TxnRef;
        String vnp_IpAddr = Config.getIpAddress(req);
        
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        // --- Step 3: Create JSON object and secure hash ---
        JsonObject vnp_Params = new JsonObject();
        vnp_Params.addProperty("vnp_RequestId", vnp_RequestId);
        vnp_Params.addProperty("vnp_Version", vnp_Version);
        vnp_Params.addProperty("vnp_Command", vnp_Command);
        vnp_Params.addProperty("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.addProperty("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.addProperty("vnp_TransactionDate", vnp_TransDate);
        vnp_Params.addProperty("vnp_CreateDate", vnp_CreateDate);
        vnp_Params.addProperty("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.addProperty("vnp_OrderInfo", vnp_OrderInfo);
        
        String hashData = String.join("|", vnp_RequestId, vnp_Version, vnp_Command, vnp_TmnCode, vnp_TxnRef, vnp_TransDate, vnp_CreateDate, vnp_IpAddr, vnp_OrderInfo);
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData);
        vnp_Params.addProperty("vnp_SecureHash", vnp_SecureHash);

        // --- Step 4: Send POST request to VNPAY API ---
        try {
            URL url = new URL(Config.vnp_ApiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.writeBytes(vnp_Params.toString());
                wr.flush();
            }

            int responseCode = con.getResponseCode();
            System.out.println("Sending 'POST' request to URL: " + url);
            System.out.println("Post Data: " + vnp_Params);
            System.out.println("Response Code: " + responseCode);

            StringBuilder response = new StringBuilder();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String output;
                while ((output = in.readLine()) != null) {
                    response.append(output);
                }
            }

            // --- Step 5: Display the result ---
            System.out.println("VNPAY Response: " + response.toString());
            
            // Forward the JSON response back to the admin page to display it
            req.setAttribute("vnpayResponse", response.toString());
            req.getRequestDispatcher("/admin/query_result.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("vnpayResponse", "{\"error\":\"" + e.getMessage() + "\"}");
            req.getRequestDispatcher("/admin/query_result.jsp").forward(req, resp);
        }
    }
}