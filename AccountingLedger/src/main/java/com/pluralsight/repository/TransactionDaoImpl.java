package com.pluralsight.repository;

import com.pluralsight.model.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TransactionDaoImpl implements TransactionDao {

    private final JdbcTemplate jdbcTemplate;

    public TransactionDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper to map SQL results to Transaction objects
    private final RowMapper<Transaction> transactionMapper = new RowMapper<Transaction>() {
        @Override
        public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
            Transaction t = new Transaction();
            t.setId(rs.getInt("id"));
            t.setTransactionDate(rs.getDate("transaction_date").toLocalDate());
            t.setTransactionTime(rs.getTime("transaction_time").toLocalTime());
            t.setDescription(rs.getString("description"));
            t.setVendor(rs.getString("vendor"));
            t.setAmount(rs.getBigDecimal("amount"));
            return t;
        }
    };

    @Override
    public void addTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (transaction_date, transaction_time, description, vendor, amount) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                transaction.getTransactionDate(),
                transaction.getTransactionTime(),
                transaction.getDescription(),
                transaction.getVendor(),
                transaction.getAmount());
    }

    @Override
    public void save(Transaction transaction) {
        addTransaction(transaction); // same as addTransaction
    }

    @Override
    public List<Transaction> findAll() {
        String sql = "SELECT * FROM transactions";
        return jdbcTemplate.query(sql, transactionMapper);
    }

    @Override
    public List<Transaction> findDeposits() {
        String sql = "SELECT * FROM transactions WHERE amount > 0";
        return jdbcTemplate.query(sql, transactionMapper);
    }

    @Override
    public List<Transaction> findPayments() {
        String sql = "SELECT * FROM transactions WHERE amount < 0";
        return jdbcTemplate.query(sql, transactionMapper);
    }

    @Override
    public List<Transaction> findMonthToDate() {
        String sql = "SELECT * FROM transactions WHERE MONTH(transaction_date) = MONTH(CURDATE()) AND YEAR(transaction_date) = YEAR(CURDATE())";
        return jdbcTemplate.query(sql, transactionMapper);
    }

    @Override
    public List<Transaction> findPreviousMonth() {
        String sql = "SELECT * FROM transactions WHERE MONTH(transaction_date) = MONTH(CURDATE() - INTERVAL 1 MONTH) AND YEAR(transaction_date) = YEAR(CURDATE() - INTERVAL 1 MONTH)";
        return jdbcTemplate.query(sql, transactionMapper);
    }

    @Override
    public List<Transaction> findYearToDate() {
        String sql = "SELECT * FROM transactions WHERE YEAR(transaction_date) = YEAR(CURDATE())";
        return jdbcTemplate.query(sql, transactionMapper);
    }

    @Override
    public List<Transaction> findPreviousYear() {
        String sql = "SELECT * FROM transactions WHERE YEAR(transaction_date) = YEAR(CURDATE() - INTERVAL 1 YEAR)";
        return jdbcTemplate.query(sql, transactionMapper);
    }

    @Override
    public List<Transaction> findByVendor(String vendor) {
        String sql = "SELECT * FROM transactions WHERE LOWER(vendor) LIKE ?";
        return jdbcTemplate.query(sql, transactionMapper, "%" + vendor.toLowerCase() + "%");
    }
}
