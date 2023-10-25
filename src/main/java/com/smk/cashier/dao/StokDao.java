package com.smk.cashier.dao;

import com.smk.cashier.model.Stok;

import java.sql.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public class StockDao implements Dao<Stok, Integer> {
    private final Optional<Connection> conn;

    public StockDao() {
        conn = JdbcConnection.getConnection();
    }

    @Override
    public Optional<Stok> get(int id) {
        return conn.flatMap(c -> {
            Optional<Stok> stock = Optional.empty();

            String query = "SELECT * FROM stock WHERE id = ?;";
            try {
                PreparedStatement ps = c.prepareStatement(query);
                ps.setInt(1, id);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int stockId = rs.getInt("id");
                    String kode = rs.getString("kode");
                    int stockNum = rs.getInt("stock");

                    Stok result = new Stok();
                    result.setId(stockId);
                    result.setKodeBarang(kode);
                    result.setStokBarang(stockNum);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return stock;
        });
    }

    @Override
    public Collection<Stok> getAll() {
        return null;
    }

    @Override
    public Optional<Integer> save(Stok stock) {
        Stok nonNullB = Objects.requireNonNull(stock);
        String query = "INSERT INTO stock(kode, stock, last_modified, updated_by, created_by, date_created) "+
                "VALUES(?, ?, ?, ?, ?, ?, ?);";

        return conn.flatMap(c -> {
            Optional<Integer> generatedID = Optional.empty();
            try {
                PreparedStatement ps = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, stock.getKodeBarang());
                ps.setInt(2, stock.getStokBarang());
                ps.setDate(4, new Date(stock.getLastModified().getTime()));
                ps.setString(5, stock.getUpdatedBy());
                ps.setString(6, stock.getCreatedBy());
                ps.setDate(7, new Date(stock.getDateCreated().getTime()));

                int numberOfInsertedRows = ps.executeUpdate();
                if (numberOfInsertedRows > 0) {
                    ResultSet rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        generatedID = Optional.of(rs.getInt(1));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return generatedID;
        });
    }

    @Override
    public void update(Stok stok) {

    }

    @Override
    public void delete(Stok stok) {

    }
}