package com.pluralsight.repository;

import com.pluralsight.model.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class JdbcTransactionDao implements TransactionDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTransactionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper for mapping ResultSet to Transaction objects
    private final RowMapper<Transaction> mapper = new RowMapper<Transaction>() {
        @Override
        public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Transaction(
                    rs.getInt("id"),
                    rs.getDate("transaction_date").toLocalDate(),
                    rs.getTime("transaction_time").toLocalTime(),
                    rs.getString("description"),
                    rs.getString("vendor"),
                    rs.getBigDecimal("amount")
            );
        }
    };

    // Add a new transaction
    @Override
    public void addTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (transaction_date, transaction_time, description, vendor, amount) " +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                transaction.getTransactionDate(),
                transaction.getTransactionTime(),
                transaction.getDescription(),
                transaction.getVendor(),
                transaction.getAmount());
    }

    @Override
    public void save(Transaction transaction) {
        // save() does the same as addTransaction() in this DAO
        addTransaction(transaction);
    }

    // Get all transactions
    @Override
    public List<Transaction> findAll() {
        String sql = "SELECT * FROM transactions";
        return jdbcTemplate.query(sql, mapper);
    }

    // Get deposits (amount > 0)
    @Override
    public List<Transaction> findDeposits() {
        String sql = "SELECT * FROM transactions WHERE amount > 0";
        return jdbcTemplate.query(sql, mapper);
    }

    // Get payments (amount < 0)
    @Override
    public List<Transaction> findPayments() {
        String sql = "SELECT * FROM transactions WHERE amount < 0";
        return jdbcTemplate.query(sql, mapper);
    }

    // Current month transactions
    @Override
    public List<Transaction> findMonthToDate() {
        String sql = "SELECT * FROM transactions " +
                "WHERE MONTH(transaction_date) = MONTH(CURDATE()) " +
                "AND YEAR(transaction_date) = YEAR(CURDATE())";
        return jdbcTemplate.query(sql, mapper);
    }

    // Previous month transactions
    @Override
    public List<Transaction> findPreviousMonth() {
        String sql = "SELECT * FROM transactions " +
                "WHERE MONTH(transaction_date) = MONTH(CURDATE() - INTERVAL 1 MONTH) " +
                "AND YEAR(transaction_date) = YEAR(CURDATE() - INTERVAL 1 MONTH)";
        return jdbcTemplate.query(sql, mapper);
    }

    // Current year transactions
    @Override
    public List<Transaction> findYearToDate() {
        String sql = "SELECT * FROM transactions " +
                "WHERE YEAR(transaction_date) = YEAR(CURDATE())";
        return jdbcTemplate.query(sql, mapper);
    }

    // Previous year transactions
    @Override
    public List<Transaction> findPreviousYear() {
        String sql = "SELECT * FROM transactions " +
                "WHERE YEAR(transaction_date) = YEAR(CURDATE() - INTERVAL 1 YEAR)";
        return jdbcTemplate.query(sql, mapper);
    }

    // Search transactions by vendor (case-insensitive)
    @Override
    public List<Transaction> findByVendor(String vendor) {
        String sql = "SELECT * FROM transactions WHERE LOWER(vendor) LIKE ?";
        return jdbcTemplate.query(sql, mapper, "%" + vendor.toLowerCase() + "%");
    }
}
