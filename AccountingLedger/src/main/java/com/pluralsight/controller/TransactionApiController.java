package com.pluralsight.controller;

import com.pluralsight.model.Transaction;
import com.pluralsight.service.TransactionService;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "http://localhost:63342") // <-- allow your page to call this API
public class TransactionApiController {

    private final TransactionService service;

    public TransactionApiController(TransactionService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<Transaction> getAll() { return service.getAllTransactions(); }

    @GetMapping("/deposits")
    public List<Transaction> getDeposits() { return service.getDeposits(); }

    @GetMapping("/payments")
    public List<Transaction> getPayments() { return service.getPayments(); }

    @GetMapping("/search")
    public List<Transaction> searchByVendor(@RequestParam String vendor) {
        return service.searchByVendor(vendor);
    }

    @PostMapping("/deposit")
    public void addDeposit(@RequestParam String vendor,
                           @RequestParam String description,
                           @RequestParam BigDecimal amount) {
        service.addDeposit(vendor, description, amount);
    }

    @PostMapping("/payment")
    public void makePayment(@RequestParam String vendor,
                            @RequestParam String description,
                            @RequestParam BigDecimal amount) {
        service.makePayment(vendor, description, amount);
    }
}
