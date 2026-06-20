package org.example.menaandfeena_finalproject.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.menaandfeena_finalproject.Api.ApiException;
import org.example.menaandfeena_finalproject.DTO.In.OrderPaymentRequestDTO;
import org.example.menaandfeena_finalproject.DTO.In.PaymentRequestDTO;
import org.example.menaandfeena_finalproject.DTO.Out.MoyasarChargeOutDTO;
import org.example.menaandfeena_finalproject.DTO.Out.PaymentOutDTO;
import org.example.menaandfeena_finalproject.Model.Payment;
import org.example.menaandfeena_finalproject.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    @Value("${moyasar.api.key}")
    private String apiKey;

    private static final String MOYASAR_API_URL = "https://api.moyasar.com/v1/payments/";

    private final PaymentRepository paymentRepository;

    // يرجع كل عمليات الدفع المحفوظة عندنا في قاعدة البيانات، وليس من Moyasar.
    public List<PaymentOutDTO> getAllPayments() {
        List<PaymentOutDTO> paymentOutDTOS = new ArrayList<>();

        for (Payment payment : paymentRepository.findAll()) {
            paymentOutDTOS.add(new PaymentOutDTO(payment.getId(), payment.getAmount(), payment.getPlatformFee(), payment.getSellerAmount(), payment.getStatus(), payment.getTransactionId(), payment.getPaymentUrl(), payment.getDescription()));
        }

        return paymentOutDTOS;
    }

    // يحذف عملية دفع محلية من قاعدة البيانات.
    public void deletePayment(Integer id) {
        Payment payment = paymentRepository.findPaymentById(id);

        if (payment == null) {
            throw new ApiException("Payment not found");
        }

        paymentRepository.delete(payment);
    }

    // هذا ميثود عام لتجربة الدفع مع Moyasar مباشرة.
    // يرسل بيانات البطاقة إلى Moyasar ويرجع الرد الخام كما هو.
    public ResponseEntity<String> processPayment(PaymentRequestDTO paymentRequest) {
        String url = "https://api.moyasar.com/v1/payments";
        String callbackUrl = paymentRequest.getCallbackurl() == null ? "http://localhost:8080/api/v1/payment/callback" : paymentRequest.getCallbackurl();
        String description = paymentRequest.getDescription() == null ? "" : paymentRequest.getDescription();

        // Moyasar يحتاج بيانات البطاقة بصيغة form-urlencoded.
        String requestBody =
                "source[type]=card"
                        + "&source[name]=" + URLEncoder.encode(paymentRequest.getName(), StandardCharsets.UTF_8)
                        + "&source[number]=" + URLEncoder.encode(paymentRequest.getNumber(), StandardCharsets.UTF_8)
                        + "&source[cvc]=" + URLEncoder.encode(paymentRequest.getCvc(), StandardCharsets.UTF_8)
                        + "&source[month]=" + URLEncoder.encode(paymentRequest.getMonth(), StandardCharsets.UTF_8)
                        + "&source[year]=" + URLEncoder.encode(paymentRequest.getYear(), StandardCharsets.UTF_8)
                        + "&amount=" + paymentRequest.getAmount()
                        + "&currency=" + URLEncoder.encode(paymentRequest.getCurrency(), StandardCharsets.UTF_8)
                        + "&description=" + URLEncoder.encode(description, StandardCharsets.UTF_8)
                        + "&callback_url=" + URLEncoder.encode(callbackUrl, StandardCharsets.UTF_8);

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(apiKey, "");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        // هنا يتم استدعاء Moyasar، والكنترولر يرجع نفس الحالة ونفس الرد.
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    // يجلب حالة الدفع من Moyasar ويرجع JSON الخام كنص.
    // مفيد في Postman إذا نريد نشوف رد Moyasar كامل.
    public String getPaymentStatus(String paymentId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(apiKey, "");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(MOYASAR_API_URL + paymentId, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }

    // هذا فقط Wrapper واضح، يرجع بيانات الدفع بعد قراءتها بدل JSON الخام.
    public MoyasarChargeOutDTO getPaymentStatusDetails(String paymentId) {
        return fetchPayment(paymentId);
    }

    // يجلب عملية دفع واحدة من Moyasar باستخدام رقم الدفع الخاص بـ Moyasar.
    // مهم: هذا الرقم يكون محفوظ عندنا في Payment.transactionId وليس Payment.id.
    public MoyasarChargeOutDTO fetchPayment(String moyasarPaymentId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(apiKey, "");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.exchange(MOYASAR_API_URL + moyasarPaymentId, HttpMethod.GET, entity, String.class);
            JsonNode node = new ObjectMapper().readTree(response.getBody());
            // نأخذ فقط الحقول التي نحتاجها في مزامنة دفع الطلب.
            String id = node.path("id").asText(null);
            String status = node.path("status").asText(null);
            Integer amount = node.path("amount").isMissingNode() || node.path("amount").isNull() ? null : node.path("amount").asInt();
            String transactionUrl = node.path("source").path("transaction_url").asText(null);
            return new MoyasarChargeOutDTO(id, status, amount, transactionUrl);
        } catch (HttpClientErrorException e) {
            String responseBody = e.getResponseBodyAsString();
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED || e.getStatusCode() == HttpStatus.FORBIDDEN) {
                throw new ApiException("Moyasar authentication failed while fetching payment " + moyasarPaymentId + ": " + responseBody);
            }
            throw new ApiException("Moyasar rejected payment status request for " + moyasarPaymentId + ": " + e.getStatusCode() + " " + responseBody);
        } catch (Exception e) {
            throw new ApiException("Could not get Moyasar payment status for " + moyasarPaymentId + ": " + e.getMessage());
        }
    }

    // ينفذ الدفع بالبطاقة عن طريق Moyasar لصفحة الدفع داخل التطبيق.
    // amount لازم يكون بالهللات، ويرجع رقم دفع Moyasar والحالة ورابط 3DS إذا وجد.
    public MoyasarChargeOutDTO chargeCard(Integer amount, String currency, OrderPaymentRequestDTO card, String description) {
        String url = "https://api.moyasar.com/v1/payments";
        String callbackUrl = "http://localhost:8080/api/v1/payment/callback";

        // هذا طلب الدفع الحقيقي الخاص بالسوق، والمبلغ يتم تجهيزه في OrderService.
        String requestBody =
                "source[type]=card"
                        + "&source[name]=" + URLEncoder.encode(card.getName(), StandardCharsets.UTF_8)
                        + "&source[number]=" + URLEncoder.encode(card.getNumber(), StandardCharsets.UTF_8)
                        + "&source[cvc]=" + URLEncoder.encode(card.getCvc(), StandardCharsets.UTF_8)
                        + "&source[month]=" + URLEncoder.encode(card.getMonth(), StandardCharsets.UTF_8)
                        + "&source[year]=" + URLEncoder.encode(card.getYear(), StandardCharsets.UTF_8)
                        + "&amount=" + amount
                        + "&currency=" + URLEncoder.encode(currency, StandardCharsets.UTF_8)
                        + "&description=" + URLEncoder.encode(description == null ? "" : description, StandardCharsets.UTF_8)
                        + "&callback_url=" + URLEncoder.encode(callbackUrl, StandardCharsets.UTF_8);

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(apiKey, "");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            JsonNode node = new ObjectMapper().readTree(response.getBody());
            // إذا رجع Moyasar الحالة initiated، فهذا الرابط هو صفحة 3DS التي يفتحها المستخدم.
            String id = node.path("id").asText(null);
            String status = node.path("status").asText(null);
            Integer responseAmount = node.path("amount").isMissingNode() || node.path("amount").isNull() ? null : node.path("amount").asInt();
            String transactionUrl = node.path("source").path("transaction_url").asText(null);
            return new MoyasarChargeOutDTO(id, status, responseAmount, transactionUrl);
        } catch (Exception e) {
            throw new ApiException("Could not process Moyasar card payment: " + e.getMessage());
        }
    }
}
