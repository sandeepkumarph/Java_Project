package com.wipro.ots.service;

import java.util.ArrayList;

import com.wipro.ots.entity.Order;
import com.wipro.ots.entity.TrackingUpdate;
import com.wipro.ots.util.OrderNotFoundException;
import com.wipro.ots.util.TrackingOperationException;

public class OrderTrackingService {

    private ArrayList<Order> orders;
    private ArrayList<TrackingUpdate> updates;

    public OrderTrackingService(ArrayList<Order> orders, ArrayList<TrackingUpdate> updates) {
        this.orders = orders;
        this.updates = updates;
    }

    public void placeOrder(Order order) {
        orders.add(order);
        System.out.println("Order Placed: " + order.getOrderId());
    }

    public Order findOrder(String orderId) throws OrderNotFoundException {

        for (Order o : orders) {
            if (o.getOrderId().equals(orderId)) {
                return o;
            }
        }

        throw new OrderNotFoundException("Order not Found");
    }

    public void updateOrderStatus(String orderId, String newStatus) throws OrderNotFoundException {

        Order order = findOrder(orderId);  
        order.setStatus(newStatus);

        System.out.println("Status Updated  " );
    }

    public void addTrackingUpdate(String orderId, String date, String notes)
            throws OrderNotFoundException, TrackingOperationException {

        if (notes == null || notes.trim().isEmpty()) {
            throw new TrackingOperationException("Tracking notes cannot be empty!");
        }

        Order order = findOrder(orderId);

        String updateId = "UPD" + (updates.size() + 1);

        TrackingUpdate update = new TrackingUpdate(updateId, orderId, date, notes);

        updates.add(update);

        System.out.println("Tracking Update Added for Order " + orderId + ": " + notes);
    }

    public ArrayList<TrackingUpdate> getTrackingHistory(String orderId)
            throws OrderNotFoundException {

        findOrder(orderId);

        ArrayList<TrackingUpdate> history = new ArrayList<>();

        for (TrackingUpdate t : updates) {
            if (t.getOrderId().equals(orderId)) {
                history.add(t);
            }
        }

        return history;
    }

    public void cancelOrder(String orderId) throws OrderNotFoundException {

        Order order = findOrder(orderId);

        orders.remove(order);

        updates.removeIf(t -> t.getOrderId().equals(orderId));

        System.out.println("Order not Found");
    }

    public void displayAllOrders() {

        System.out.println("\n--- All Orders ---");

        if (orders.isEmpty()) {
            System.out.println("No orders available.");
            return;
        }

        for (Order o : orders) {
            System.out.println(o);
        }
    }
}
