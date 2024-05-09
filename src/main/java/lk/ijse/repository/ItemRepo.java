package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Item;
import lk.ijse.model.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemRepo {

    public static Item searchByCode(String code) throws SQLException {
        String sql = "SELECT * FROM Item WHERE code = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, code);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String it_code= resultSet.getString(1);
            String description = resultSet.getString(2);
            double unitPrice =Double.parseDouble(resultSet.getString(3));
            int qty= Integer.parseInt(resultSet.getString(4));
            String location = resultSet.getString(5);

            Item item = new Item(code, description, qty, unitPrice, location);

            return item;
        }

        return null;
    }
    public static boolean delete(String code) throws SQLException {
        String sql = "DELETE FROM Item WHERE code = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, code);

        return pstm.executeUpdate() > 0;
    }

    public static List<Item> getAllItem() throws SQLException {
        String sql = "SELECT * FROM Item";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Item> ItemList = new ArrayList<>();

        while (resultSet.next()) {
            String code = resultSet.getString(1);
            String description = resultSet.getString(2);
            double unitPrice = Double.parseDouble(resultSet.getString(3));
            int qty = (int)resultSet.getDouble(4);
            String location = resultSet.getString(5);

            Item item = new Item(code, description, qty, unitPrice, location);
            ItemList.add(item);
        }
        return ItemList;
    }

    public static boolean update(Item item) throws SQLException {
        String sql = "UPDATE Item SET description = ?, unitPrice = ?, qtyOnHand = ?, location=? WHERE code= ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, item.getDescription());
        pstm.setObject(2, item.getUnitPrice());
        pstm.setObject(3, item.getQtyOnHand());
        pstm.setObject(4, item.getLocation());
        pstm.setObject(5, item.getCode());

        return pstm.executeUpdate() > 0;
    }
    public static boolean save(Item item) throws SQLException {
        String sql = "INSERT INTO Item VALUES(?, ?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, item.getCode());
        pstm.setObject(2, item.getDescription());
        pstm.setObject(3, item.getUnitPrice());
        pstm.setObject(4, item.getQtyOnHand());

        return pstm.executeUpdate() > 0;
    }

    private static boolean updateQty(String itemCode, int qty) throws SQLException {
        String sql = "UPDATE Item SET qtyOnHand = qtyOnHand - ? WHERE code = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setInt(1, qty);
        pstm.setString(2, itemCode);

        return pstm.executeUpdate() > 0;
    }



    public static List<String> getCodes() throws SQLException {
        String sql = "SELECT code FROM Item";
        ResultSet resultSet = DbConnection.getInstance()
                .getConnection()
                .prepareStatement(sql)
                .executeQuery();

        List<String> codeList = new ArrayList<>();
        while (resultSet.next()) {
            codeList.add(resultSet.getString(1));
        }
        return codeList;
    }

    public static boolean placeOrderUpdate(List<OrderDetail> oderList) throws SQLException {
        for(OrderDetail orderDetail : oderList){
            System.out.println("place order update >>>" +oderList);
            boolean isupdated = updateQty(orderDetail.getCode(),orderDetail.getQty());
            if (isupdated){
                return true;
            }
        }

        return false;

    }
/*
    public static boolean updateQty(List<OrderDetail> oderList) throws SQLException {
        String sql = "UPDATE Item SET qtyOnHand = qtyOnHand - ? WHERE code = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setInt(1, oderList.get(getAllItem().indexOf()));
        pstm.setString(2, itemCode);

        return pstm.executeUpdate() > 0;
    }*/
}
