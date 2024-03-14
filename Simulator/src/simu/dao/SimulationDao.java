package simu.dao;

import simu.datasource.MariaDbConnection;
import simu.framework.Kello;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulationDao {
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

    public List<String> getBarData(int id) {
        Connection conn = MariaDbConnection.getConnection();
        String sql = "SELECT * FROM SIMULATION_RESULTS WHERE id = ?";
        List<String> results = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int combinedStaff = rs.getInt("cs_staff") + rs.getInt("shop_staff") + rs.getInt("prescription_staff") + rs.getInt("cashier_staff");
                results.add("Simulation Time: " + rs.getDouble("simulation_time"));
                results.add("Combined staff: " + combinedStaff);
                results.add("Served Customers: " + rs.getInt("served_customers"));
                results.add("Lost Customers: " + rs.getInt("lost_customers"));
            }

            return results;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

