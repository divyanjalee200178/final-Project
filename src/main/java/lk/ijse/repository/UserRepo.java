package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Employee;
import lk.ijse.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserRepo {
    public static boolean update(User user) throws SQLException {
        String sql = "UPDATE users SET name = ?, password = ? WHERE userId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, user.getUserId());
        pstm.setObject(2, user.getName());
        pstm.setObject(3, user.getPassword());


        return pstm.executeUpdate() > 0;
    }
}
