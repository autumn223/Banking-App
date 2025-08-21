package com.pluralsight.controller;

import com.pluralsight.model.Transaction;
import com.pluralsight.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping("/deposit")
    public String addDeposit(@RequestParam String vendor,
                             @RequestParam String description,
                             @RequestParam BigDecimal amount) {
        service.addDeposit(vendor, description, amount);
        return "Deposit successfully added.";
    }

    @PostMapping("/payment")
    public String makePayment(@RequestParam String vendor,
                              @RequestParam String description,
                              @RequestParam BigDecimal amount) {
        service.makePayment(vendor, description, amount);
        return "Payment successfully added.";
    }

    @GetMapping("/all")
    public List<Transaction> getAllTransactions() {
        return service.getAllTransactions();
    }

    @GetMapping("/deposits")
    public List<Transaction> getDeposits() {
        return service.getDeposits();
    }

    @GetMapping("/payments")
    public List<Transaction> getPayments() {
        return service.getPayments();
    }

    @GetMapping("/month-to-date")
    public List<Transaction> getMonthToDate() {
        return service.getMonthToDate();
    }

    @GetMapping("/previous-month")
    public List<Transaction> getPreviousMonth() {
        return service.getPreviousMonth();
    }

    @GetMapping("/year-to-date")
    public List<Transaction> getYearToDate() {
        return service.getYearToDate();
    }

    @GetMapping("/previous-year")
    public List<Transaction> getPreviousYear() {
        return service.getPreviousYear();
    }

    @GetMapping("/search")
    public List<Transaction> searchByVendor(@RequestParam String vendor) {
        return service.searchByVendor(vendor);
    }
}

