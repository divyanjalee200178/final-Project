package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Item;
import lk.ijse.model.PlaceOrder;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceorderRepo {
    public static boolean placeOrder(PlaceOrder po) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isOrderSaved = OrderRepo.save(po.getOrder());
            if (isOrderSaved) {

              boolean isQtyUpdated = ItemRepo.placeOrderUpdate(po.getOderList());
               if(isQtyUpdated) {
                    boolean isOrderDetailSaved = OrderDeatilRepo.save(po.getOderList());
                    if (isOrderDetailSaved) {
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
