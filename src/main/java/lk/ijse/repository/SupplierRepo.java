package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Customer;
import lk.ijse.model.Employee;
import lk.ijse.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierRepo {
    public static Supplier searchById(String id) throws SQLException{
        String sql="SELECT * FROM Supplier WHERE id=?";

        Connection connection=DbConnection.getInstance().getConnection();
        PreparedStatement pstm=connection.prepareStatement(sql);
        pstm.setObject(1,id);

        ResultSet resultSet=pstm.executeQuery();
        if(resultSet.next()){
            String sup_id=resultSet.getString(1);
            String name=resultSet.getString(2);
            String address=resultSet.getString(3);
            String email=resultSet.getString(4);
            String tel=resultSet.getString(5);

            Supplier supplier=new Supplier(sup_id,name,address,email,tel);

            return supplier;

        }
        return null;
    }

    public static boolean update(Employee employee) throws SQLException {
        String sql = "UPDATE Supplier SET name = ?, address = ?,email=?, number = ? WHERE id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, employee.getName());
        pstm.setObject(2, employee.getAddress());
        pstm.setObject(3, employee.getTel());
        pstm.setObject(4,employee.getEmail());
        pstm.setObject(5, employee.getId());

        return pstm.executeUpdate() > 0;
    }
    public static List<Supplier> getAll() throws SQLException {
        String sql = "SELECT * FROM Supplier";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<Supplier> supplierListList = new ArrayList<>();

        ResultSet resultSet=pstm.executeQuery();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String email=resultSet.getString(4);
            String tel = resultSet.getString(5);

           Supplier supplier= new Supplier(id, name, address, email, tel);
            supplierListList.add(supplier);
        }
        return supplierListList;
    }

    public static boolean delete(String id) throws SQLException{
        String sql="DELETE FROM Supplier WHERE id=?";

        Connection connection=DbConnection.getInstance().getConnection();
        PreparedStatement pstm=connection.prepareStatement(sql);
        pstm.setObject(1,id);

        return pstm.executeUpdate()>0;
    }


    public static List<String> getIds() throws SQLException {
        String sql = "SELECT id FROM Supplier";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            idList.add(id);
        }
        return idList;
    }

    public static Supplier searchByNumber(String tele) throws SQLException{
        String sql="SELECT * FROM Supplier WHERE number=?";

        Connection connection=DbConnection.getInstance().getConnection();
        PreparedStatement pstm=connection.prepareStatement(sql);
        pstm.setObject(1,tele);

        ResultSet resultSet=pstm.executeQuery();
        if(resultSet.next()){
            String sup_id=resultSet.getString(1);
            String name=resultSet.getString(2);
            String address=resultSet.getString(3);
            String email=resultSet.getString(4);
            String tel=resultSet.getString(5);

            Supplier supplier=new Supplier(sup_id,name,address,email,tel);

            return supplier;

        }
        return null;
    }


}
