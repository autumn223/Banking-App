
package com.pluralsight.service;

import com.pluralsight.model.Transaction;
import com.pluralsight.repository.TransactionDao;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionDao repository;

    public TransactionService(TransactionDao repository) {
        this.repository = repository;
    }

    // Add a deposit transaction
    public void addDeposit(String vendor, String description, BigDecimal amount) {
        Transaction t = new Transaction(LocalDate.now(), LocalTime.now(), description, vendor, amount);
        repository.addTransaction(t); // matches your DAO
    }

    // Make a payment transaction
    public void makePayment(String vendor, String description, BigDecimal amount) {
        Transaction t = new Transaction(LocalDate.now(), LocalTime.now(), description, vendor, amount.negate());
        repository.addTransaction(t); // matches your DAO
    }

    // Retrieve all transactions
    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    // Retrieve all deposits (amount > 0)
    public List<Transaction> getDeposits() {
        return repository.findDeposits();
    }

    // Retrieve all payments (amount < 0)
    public List<Transaction> getPayments() {
        return repository.findPayments();
    }

    // Retrieve transactions for current month
    public List<Transaction> getMonthToDate() {
        return repository.findMonthToDate();
    }

    // Retrieve transactions for previous month
    public List<Transaction> getPreviousMonth() {
        return repository.findPreviousMonth();
    }

    // Retrieve transactions for current year
    public List<Transaction> getYearToDate() {
        return repository.findYearToDate();
    }

    // Retrieve transactions for previous year
    public List<Transaction> getPreviousYear() {
        return repository.findPreviousYear();
    }

    // Search transactions by vendor
    public List<Transaction> searchByVendor(String vendor) {
        return repository.findByVendor(vendor);
    }
}


