package com.danicode.microblogging.model.dao.implementations;

import com.danicode.microblogging.services.ConnectionService;
import com.danicode.microblogging.model.dao.templates.DAOUser;
import com.danicode.microblogging.model.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class DAOUserImpl implements DAOUser {
    private Connection externConnection = null;
    private static final String
        SQL_INSERT = "INSERT INTO users(name, last_name, email, username, password) VALUES(?, ?, ?, ?, ?);",
        SQL_SELECT = "SELECT * FROM users;",
        SQL_UPDATE = "UPDATE users SET name=?, last_name=?, email=?, username=?, password=? WHERE user_id=?;",
        SQL_DELETE = "DELETE FROM users WHERE user_id=?;",
        SQL_SELECT_BY_ID = "SELECT * FROM users WHERE user_id=?;",
        SQL_SELECT_BY_USERNAME = "SELECT * FROM users WHERE UPPER(username)=UPPER(?);",
        SQL_SELECT_BY_EMAIL = "SELECT * FROM users WHERE email=?;";

    public DAOUserImpl() { }

    public DAOUserImpl(Connection externConnection) {
        this.externConnection = externConnection;
    }

    @Override
    public int create(User user) throws Exception {
        Connection conn = this.externConnection == null ? ConnectionService.getConnection() : this.externConnection;
        PreparedStatement pstmt = conn.prepareStatement(SQL_INSERT);
        pstmt.setString(1, user.getName());
        pstmt.setString(2, user.getLastName());
        pstmt.setString(3, user.getEmail());
        pstmt.setString(4, user.getUsername());
        pstmt.setString(5, user.getPassword());

        var rowsUpdated = pstmt.executeUpdate();
        ConnectionService.close(this.externConnection, conn, pstmt);

        return rowsUpdated;
    }

    @Override
    public List<User> list() throws Exception {
        List<User> users = new ArrayList<>();
        var conn = this.externConnection != null ? this.externConnection : ConnectionService.getConnection();
        var pstmt = conn.prepareStatement(SQL_SELECT);
        var rs = pstmt.executeQuery();

        while (rs.next()) {
            var user = new User(
                    rs.getInt("user_id"), rs.getString("name"), rs.getString("last_name"),
                    rs.getString("email"), rs.getString("username"), rs.getString("password")
            );
            users.add(user);
        }

        ConnectionService.close(this.externConnection, conn, pstmt, rs);

        return users;
    }

    @Override
    public int update(User user) throws Exception {
        var conn = this.externConnection == null ? ConnectionService.getConnection() : this.externConnection;
        var pstmt = conn.prepareStatement(SQL_UPDATE);
        pstmt.setString(1, user.getName());
        pstmt.setString(2, user.getLastName());
        pstmt.setString(3, user.getEmail());
        pstmt.setString(4, user.getUsername());
        pstmt.setString(5, user.getPassword());
        pstmt.setInt(6, user.getIdUser());

        var rowsUpdated = pstmt.executeUpdate();
        ConnectionService.close(this.externConnection, conn, pstmt);

        return rowsUpdated;
    }

    @Override
    public int delete(int idUser) throws Exception {
        var conn = this.externConnection != null ? this.externConnection : ConnectionService.getConnection();
        var pstmt = conn.prepareStatement(SQL_DELETE);
        pstmt.setInt(1, idUser);
        var rowsUpdated = pstmt.executeUpdate();
        ConnectionService.close(this.externConnection, conn, pstmt);

        return rowsUpdated;
    }

    @Override
    public User findById(int idUser) throws Exception {
        User user = null;
        var conn = this.externConnection == null ? ConnectionService.getConnection() : this.externConnection;
        var pstmt = conn.prepareStatement(SQL_SELECT_BY_ID);
        pstmt.setInt(1, idUser);
        var rs = pstmt.executeQuery();

        if (rs.next()) {
            user = new User(
                    rs.getInt("user_id"), rs.getString("name"), rs.getString("last_name"),
                    rs.getString("email"), rs.getString("username"), rs.getString("password")
            );
        }
        ConnectionService.close(this.externConnection, conn, pstmt, rs);
        return user;
    }

    @Override
    public User findByUsername(String username) throws Exception {
        return this.findByString(SQL_SELECT_BY_USERNAME, username);
    }

    @Override
    public User findByEmail(String email) throws Exception {
        return this.findByString(SQL_SELECT_BY_EMAIL, email);
    }

    private User findByString(String query, String value) throws Exception {
        User user = null;
        var conn = this.externConnection == null ? ConnectionService.getConnection() : this.externConnection;
        var pstmt = conn.prepareStatement(query);
        pstmt.setString(1, value);
        var rs = pstmt.executeQuery();

        if (rs.next()) {
            user = new User(
                    rs.getInt("user_id"), rs.getString("name"), rs.getString("last_name"),
                    rs.getString("email"), rs.getString("username"), rs.getString("password")
            );
        }
        ConnectionService.close(this.externConnection, conn, pstmt, rs);
        return user;
    }
}