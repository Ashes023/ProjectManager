package com.ashahar.projectmanagementsystem.controller;

import com.ashahar.projectmanagementsystem.model.PlanType;
import com.ashahar.projectmanagementsystem.model.User;
import com.ashahar.projectmanagementsystem.response.PaymentLinkResponse;
import com.ashahar.projectmanagementsystem.service.UserService;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Value("${payment.api.key}")
    private String apiKey;

    @Value("${payment.api.secret}")
    private String apiSecret;

    @Autowired
    private UserService userService;

    @PostMapping("/{planType}")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(
            @PathVariable PlanType planType,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {

         User user = userService.findUserProfileByJwt(jwt);
         int amount = 799*100;

        if (planType == PlanType.ANNUALLY) {
            amount *= 12;
            amount = (int)(amount * 0.9);
        }

        try{
            RazorpayClient razorpayClient = new RazorpayClient(apiKey, apiSecret);
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", amount);
            paymentLinkRequest.put("currency", "INR");

            JSONObject customerDetails = new JSONObject();
            customerDetails.put("name", user.getUsername());
            customerDetails.put("email", user.getEmail());
            paymentLinkRequest.put("customer", customerDetails);

            JSONObject notify = new JSONObject();
            notify.put("email", true);
            paymentLinkRequest.put("notify", notify);

            paymentLinkRequest.put("callback_url", "http://localhost:5173/upgrade_plan/success?planType=" + planType);

            PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);
            String paymentLinkId = payment.get("id").toString();
            String paymentUrl = payment.get("short_url").toString();

            PaymentLinkResponse response = new PaymentLinkResponse();
            response.setPayment_link_id(paymentLinkId);
            response.setPayment_link_url(paymentUrl);
            
            return new ResponseEntity<>(response,  HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create payment link: " + e.getMessage(), e);
        }

    }

}
