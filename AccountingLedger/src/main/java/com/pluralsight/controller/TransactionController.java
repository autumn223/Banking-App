package com.pluralsight.controller;

import com.pluralsight.model.Transaction;
import com.pluralsight.repository.TransactionDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TransactionController {

    private final TransactionDao transactionDao;

    public TransactionController(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    // Show all transactions
    @GetMapping("/transactions")
    public String listTransactions(Model model) {
        model.addAttribute("transactions", transactionDao.findAll());
        return "transactions"; // Thymeleaf template
    }

    // Search by vendor
    @GetMapping("/transactions/search")
    public String searchByVendor(@RequestParam("vendor") String vendor, Model model) {
        List<Transaction> results = transactionDao.findByVendor(vendor);
        model.addAttribute("transactions", results);
        return "transactions"; // reuse same table view
    }
}
