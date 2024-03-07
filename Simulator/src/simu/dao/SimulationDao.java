package simu.dao;

import simu.datasource.MariaDbConnection;
import simu.framework.Kello;
import simu.model.Apteekki;
import simu.model.Asiakas;
import simu.model.OmaMoottori;
import simu.model.Palvelupiste;

import java.sql.*;

public class SimulationDao {
    public void saveResultsInDatabase() {
        Connection conn = MariaDbConnection.getConnection();
        String sql = "INSERT INTO simulation_results (simulation_time, cs_staff, shop_staff, prescription_staff, cashier_staff, served_customers, lost_customers, served_at_cs, served_at_shop, served_at_prescription, served_at_cashier, satisfied_customers, dissatisfied_customers, overall_satisfaction, cs_utilization, shop_utilization, prescription_utilization, cashier_utilization) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, Kello.getInstance().getAika());
            ps.setInt(2, OmaMoottori.aspaTyontekijat);
            ps.setInt(3, OmaMoottori.hyllyTyontekijat);
            ps.setInt(4, OmaMoottori.reseptiTyontekijat);
            ps.setInt(5, OmaMoottori.kassaTyontekijat);
            ps.setInt(6, Apteekki.getServedCustomers());
            ps.setInt(7, Apteekki.getMissedCustomers());
            ps.setInt(8, Palvelupiste.getAspaUsage());
            ps.setInt(9, Palvelupiste.getKauppaUsage());
            ps.setInt(10, Palvelupiste.getReseptiUsage());
            ps.setInt(11, Palvelupiste.getKassaUsage());
            ps.setInt(12, Asiakas.getSatisfied());
            ps.setInt(13, Asiakas.getDissatisfied());
            ps.setDouble(14, ((double) Asiakas.getSatisfied() / Asiakas.getCustomerAmount()) * 100);
            ps.setDouble(15, Palvelupiste.getAspaUtilization() * 100);
            ps.setDouble(16, Palvelupiste.getKauppaUtilization() * 100);
            ps.setDouble(17, Palvelupiste.getReseptiUtilization() * 100);
            ps.setDouble(18, Palvelupiste.getKassaUtilization() * 100);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

