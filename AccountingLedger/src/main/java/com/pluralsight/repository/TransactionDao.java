package com.pluralsight.repository;


import com.pluralsight.model.Transaction;
import java.util.List;

public interface TransactionDao {
    void addTransaction(Transaction transaction);

    void save(Transaction transaction);

    List<Transaction> findAll();

    List<Transaction> findDeposits();

    List<Transaction> findPayments();

    List<Transaction> findMonthToDate();

    List<Transaction> findPreviousMonth();

    List<Transaction> findYearToDate();

    List<Transaction> findPreviousYear();

    List<Transaction> findByVendor(String vendor);
}