package org.example.menaandfeena_finalproject.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.menaandfeena_finalproject.Api.ApiResponse;
import org.example.menaandfeena_finalproject.DTO.In.OrderPaymentRequestDTO;
import org.example.menaandfeena_finalproject.DTO.In.PaymentRequestDTO;
import org.example.menaandfeena_finalproject.Service.OrderService;
import org.example.menaandfeena_finalproject.Service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final OrderService orderService;

    // In-app payment: the user submits card details for a specific order on our own page.
    // The amount is read from the order on the server, never from this request body.
    @PostMapping("/order/{orderId}")
    public ResponseEntity<?> payForOrder(@PathVariable Integer orderId, @RequestParam Integer userId, @RequestBody @Valid OrderPaymentRequestDTO orderPaymentRequest) {
        return ResponseEntity.status(200).body(orderService.payOrderWithCard(orderId, userId, orderPaymentRequest));
    }

    @PostMapping("/order/{orderId}/sync")
    public ResponseEntity<?> syncOrderPayment(@PathVariable Integer orderId, @RequestParam Integer userId) {
        return ResponseEntity.status(200).body(orderService.syncOrderPayment(orderId, userId));
    }

    @PostMapping("/card")
    public ResponseEntity<?> processPayment(@RequestBody @Valid PaymentRequestDTO paymentRequest) {
        return paymentService.processPayment(paymentRequest);
    }

    @GetMapping("/get-status/{id}")
    public ResponseEntity<?> getPaymentStatus(@PathVariable String id) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(paymentService.getPaymentStatus(id));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllPayments() {
        return ResponseEntity.status(200).body(paymentService.getAllPayments());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePayment(@PathVariable Integer id) {
        paymentService.deletePayment(id);
        return ResponseEntity.status(200).body(new ApiResponse("Payment deleted"));
    }
}
