package simu.dao;

import simu.datasource.MariaDbConnection;
import simu.framework.Kello;

import java.sql.*;
import java.util.*;

/**
 * The SimulationDao class provides methods for interacting with the simulation database.
 */

public class SimulationDao {

    /**
     * Saves simulation results into the database.
     *
     * @param aspaTyontekijat        Number of customer service staff.
     * @param hyllyTyontekijat       Number of shelf staff.
     * @param reseptiTyontekijat     Number of prescription staff.
     * @param kassaTyontekijat       Number of cashier staff.
     * @param servedCustomers        Number of served customers.
     * @param missedCustomers        Number of missed customers.
     * @param aspaUsage              Number of customers served by customer service.
     * @param hyllyUsage             Number of customers served by shelf staff.
     * @param reseptiUsage           Number of customers served by prescription staff.
     * @param kassaUsage             Number of customers served by cashier.
     * @param satisfied              Number of satisfied customers.
     * @param dissatisfied           Number of dissatisfied customers.
     * @param overallSatisfaction    Overall satisfaction percentage.
     * @param aspaUtilization        Customer service utilization percentage.
     * @param kauppaUtilization      Shop utilization percentage.
     * @param reseptiUtilization     Prescription utilization percentage.
     * @param kassaUtilization       Cashier utilization percentage.
     */
    public void saveResultsInDatabase(int aspaTyontekijat, int hyllyTyontekijat, int reseptiTyontekijat, int kassaTyontekijat, int servedCustomers, int missedCustomers, int aspaUsage, int hyllyUsage, int reseptiUsage, int kassaUsage, int satisfied, int dissatisfied, double overallSatisfaction, double aspaUtilization, double kauppaUtilization, double reseptiUtilization, double kassaUtilization) {
        Connection conn = MariaDbConnection.getConnection();
        String sql = "INSERT INTO simulation_results (simulation_time, cs_staff, shop_staff, prescription_staff, cashier_staff, served_customers, lost_customers, served_at_cs, served_at_shop, served_at_prescription, served_at_cashier, satisfied_customers, dissatisfied_customers, overall_satisfaction, cs_utilization, shop_utilization, prescription_utilization, cashier_utilization) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, Kello.getInstance().getAika());
            ps.setInt(2, aspaTyontekijat);
            ps.setInt(3, hyllyTyontekijat);
            ps.setInt(4, reseptiTyontekijat);
            ps.setInt(5, kassaTyontekijat);
            ps.setInt(6, servedCustomers);
            ps.setInt(7, missedCustomers);
            ps.setInt(8, aspaUsage);
            ps.setInt(9, hyllyUsage);
            ps.setInt(10, reseptiUsage);
            ps.setInt(11, kassaUsage);
            ps.setInt(12, satisfied);
            ps.setInt(13, dissatisfied);
            ps.setDouble(14, overallSatisfaction);
            ps.setDouble(15, aspaUtilization);
            ps.setDouble(16, kauppaUtilization);
            ps.setDouble(17, reseptiUtilization);
            ps.setDouble(18, kassaUtilization);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a list of satisfaction percentages from the database.
     * Satisfaction percentages are used to determine the height of the barchart in history.
     *
     * @return A list of satisfaction percentages.
     */
    public List<Double> getSatisfaction() {
        Connection conn = MariaDbConnection.getConnection();
        String sql = "SELECT overall_satisfaction FROM simulation_results";
        List<Double> satisfaction = new ArrayList<>();
        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                double sat = rs.getDouble(1);
                satisfaction.add(sat);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return satisfaction;
    }

    /**
     * Retrieves simulation data for a specific ID from the database.
     *
     * @param id The ID of the simulation data to retrieve.
     * @return A list containing details of the simulation data.
     */
    public List<String> getBarData(int id) {
        Connection conn = MariaDbConnection.getConnection();
        String sql = "SELECT * FROM SIMULATION_RESULTS WHERE id = ?";
        List<String> results = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                results.add("ID: " + rs.getInt("id"));
                results.add("Simulation Time: " + rs.getDouble("simulation_time"));
                results.add("CS Staff: " + rs.getInt("cs_staff"));
                results.add("Shop Staff: " + rs.getInt("shop_staff"));
                results.add("Prescription Staff: " + rs.getInt("prescription_staff"));
                results.add("Cashier Staff: " + rs.getInt("cashier_staff"));
                results.add("Served Customers: " + rs.getInt("served_customers"));
                results.add("Lost Customers: " + rs.getInt("lost_customers"));
                results.add("Served at CS: " + rs.getInt("served_at_cs"));
                results.add("Served at Shop: " + rs.getInt("served_at_shop"));
                results.add("Served at Prescription: " + rs.getInt("served_at_prescription"));
                results.add("Served at Cashier: " + rs.getInt("served_at_cashier"));
                results.add("Satisfied Customers: " + rs.getInt("satisfied_customers"));
                results.add("Dissatisfied Customers: " + rs.getInt("dissatisfied_customers"));
                results.add("Overall Satisfaction: " + rs.getDouble("overall_satisfaction")+ "%");
                results.add("CS Utilization: " + rs.getDouble("cs_utilization")+ "%");
                results.add("Shop Utilization: " + rs.getDouble("shop_utilization")+ "%");
                results.add("Prescription Utilization: " + rs.getDouble("prescription_utilization")+ "%");
                results.add("Cashier Utilization: " + rs.getDouble("cashier_utilization")+ "%");
            }

            return results;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

