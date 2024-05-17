package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Customer;
import lk.ijse.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepo {
    public static List<Customer> getAll() throws SQLException{
        String sql="SELECT * FROM Customer";

        PreparedStatement pstm= DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet=pstm.executeQuery();

        List<Customer>cusList=new ArrayList<>();

        while(resultSet.next()){
            String id=resultSet.getString(1);
            String name=resultSet.getString(2);
            String address=resultSet.getString(3);
            String email=resultSet.getString(4);
            String tel=resultSet.getString(5);

            Customer customer=new Customer(id,name,address,email,tel);
            cusList.add(customer);
        }
        return cusList;
    }

    public static List<String> getIds() throws SQLException{
        String sql="SELECT id FROM Customer";
        PreparedStatement pstm=DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> idList=new ArrayList<>();

        ResultSet resultSet=pstm.executeQuery();
        while(resultSet.next()){
            String id =resultSet.getString(1);
            idList.add(id);
        }
        return idList;
    }
    public static Customer searchById(String id) throws SQLException {
        String sql = "SELECT * FROM Customer WHERE id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String cu_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String email=resultSet.getString(4);
            String tel = resultSet.getString(5);

            Customer customer = new Customer(cu_id, name, address, email,tel);

            return customer;
        }

        return null;
    }

    public static Customer searchByNumber(String tele) throws SQLException {
        String sql = "SELECT * FROM Customer WHERE number = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, tele);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String cu_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String email=resultSet.getString(4);
            String tel = resultSet.getString(5);

            Customer customer = new Customer(cu_id, name, address, email,tel);

            return customer;
        }

        return null;
    }



}
