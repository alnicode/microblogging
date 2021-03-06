package com.danicode.microblogging.model.dao.implementations;

import com.danicode.microblogging.model.dao.templates.DAOMessage;
import com.danicode.microblogging.model.dao.templates.DAOUser;
import com.danicode.microblogging.model.domain.Message;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static com.danicode.microblogging.services.ConnectionService.*;

public class DAOMessageImpl implements DAOMessage {
    private Connection externConnection;
    private final DAOUser userDao;
    private static final String
        SQL_INSERT = "INSERT INTO messages(user_id_pk, date_time, message) VALUES(?, ?, ?);",
        SQL_SELECT = "SELECT message_id, user_id_pk, date_time, message FROM messages;",
        SQL_UPDATE = "UPDATE messages SET message = ? WHERE message_id = ?;",
        SQL_DELETE = "DELETE FROM messages WHERE message_id = ?;",
        SQL_SELECT_BY_ID = "SELECT user_id_pk, date_time, message FROM messages WHERE message_id = ?;",
        SQL_SELECT_BY_USERNAME = "SELECT message_id FROM messages m INNER JOIN users AS u " +
                "ON m.user_id_pk = u.user_id WHERE UPPER(username) = UPPER(?);",
        SQL_SELECT_BY_MESSAGE = "SELECT message_id FROM messages WHERE message LIKE ?;",
        SQL_SELECT_BY_DATE_TIME = "SELECT message_id FROM messages WHERE date_time = ?",
        SQL_DELETE_USER_MESSAGES = "DELETE FROM messages WHERE user_id_pk = ?;";

    public DAOMessageImpl() {
        this.userDao = new DAOUserImpl();
    }

    public DAOMessageImpl(Connection externConnection) {
        this.externConnection = externConnection;
        this.userDao = new DAOUserImpl(externConnection);
    }

    @Override
    public int create(Message message) throws Exception {
        var conn = this.externConnection != null ? this.externConnection : getConnection();
        var stmt = conn.prepareStatement(SQL_INSERT);
        stmt.setInt(1, message.getUser().getIdUser());
        stmt.setString(2, message.getDateTime());
        stmt.setString(3, message.getMessage());
        var rowsUpdated = stmt.executeUpdate();
        close(this.externConnection, conn, stmt);
        return rowsUpdated;
    }

    @Override
    public List<Message> list() throws Exception {
        List<Message> messages = new ArrayList<>();
        var conn = this.externConnection == null ? getConnection() : this.externConnection;
        var stmt = conn.prepareStatement(SQL_SELECT);
        var rs = stmt.executeQuery();

        while (rs.next()) {
            var user = this.userDao.findById(rs.getInt("user_id_pk"));
            var message = new Message(
                    rs.getInt("message_id"), user, rs.getString("date_time"), rs.getString("message")
            );
            messages.add(message);
        }

        close(this.externConnection, conn, stmt, rs);
        return messages;
    }

    @Override
    public int update(Message message) throws Exception {
        var conn = this.externConnection != null ? this.externConnection : getConnection();
        var stmt = conn.prepareStatement(SQL_UPDATE);
        stmt.setString(1, message.getMessage());
        stmt.setInt(2, message.getIdMessage());
        var rowsUpdated = stmt.executeUpdate();
        close(this.externConnection, conn, stmt);
        return rowsUpdated;
    }

    @Override
    public int delete(int idMessage) throws Exception {
        var conn = this.externConnection != null ? this.externConnection : getConnection();
        var stmt = conn.prepareStatement(SQL_DELETE);
        stmt.setInt(1, idMessage);
        var rowsUpdated = stmt.executeUpdate();
        close(this.externConnection, conn, stmt);
        return rowsUpdated;
    }

    @Override
    public Message findById(int idMessage) throws Exception {
        var message = new Message();
        var conn = this.externConnection == null ? getConnection() : this.externConnection;
        var stmt = conn.prepareStatement(SQL_SELECT_BY_ID);
        stmt.setInt(1, idMessage);
        var rs = stmt.executeQuery();

        if (rs.next())  {
            var user = this.userDao.findById(rs.getInt("user_id_pk"));
            message = new Message(idMessage, user, rs.getString("date_time"), rs.getString("message"));
        }

        close(this.externConnection, conn, stmt, rs);
        return message;
    }

    @Override
    public List<Message> findByUsername(String username) throws Exception {
        List<Message> messages = new ArrayList<>();
        var conn = this.externConnection != null ? this.externConnection : getConnection();
        var stmt = conn.prepareStatement(SQL_SELECT_BY_USERNAME);
        stmt.setString(1, username);
        var rs = stmt.executeQuery();

        while (rs.next()) {
            var message = this.findById(rs.getInt("message_id"));
            messages.add(message);
        }

        close(this.externConnection, conn, stmt, rs);
        return messages;
    }

    @Override
    public List<Message> findByMessage(String message) throws Exception {
        List<Message> messages = new ArrayList<>();
        var conn = this.externConnection != null ? this.externConnection : getConnection();
        var stmt = conn.prepareStatement(SQL_SELECT_BY_MESSAGE);
        stmt.setString(1, "%" + message + "%");
        var rs = stmt.executeQuery();

        while (rs.next()) {
            messages.add(this.findById(rs.getInt("message_id")));
        }

        close(this.externConnection, conn, stmt, rs);
        return messages;
    }

    @Override
    public Message findByDateTime(String dateTime) throws Exception {
        Message message = null;
        var conn = this.externConnection != null ? this.externConnection : getConnection();
        var stmt = conn.prepareStatement(SQL_SELECT_BY_DATE_TIME);
        stmt.setString(1, dateTime);
        var rs = stmt.executeQuery();

        if (rs.next()) {
            message = this.findById(rs.getInt("message_id"));
        }

        close(this.externConnection, conn, stmt, rs);
        return message;
    }

    @Override
    public int deleteUserMessages(int idUser) throws Exception {
        var conn = this.externConnection == null ? getConnection() : this.externConnection;
        var stmt = conn.prepareStatement(SQL_DELETE_USER_MESSAGES);
        stmt.setInt(1, idUser);
        var rowsUpdated = stmt.executeUpdate();
        close(this.externConnection, conn, stmt);
        return rowsUpdated;
    }
}
