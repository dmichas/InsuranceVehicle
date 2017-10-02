package insurance.utilities.database;

import insurance.model.Owner;
import insurance.model.Vehicle;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QueryDb {

    private Connection con;

    public static List<Vehicle> selectExpiries(Connection con){
        ResultSet result = null;
        List<Vehicle> vehicleList = new ArrayList<Vehicle>();
        try{
            String query = "SELECT PlateNum,InsuranceEnded FROM Vehicle WHERE InsuranceEnded < CURDATE() ORDER BY TaxNum";
            Statement stmt = con.createStatement();
            result = stmt.executeQuery(query);
            if (result != null) {
                while (result.next()) {
                    Vehicle vehicle = new Vehicle();
                    vehicle.setPlate(result.getString("PlateNum"));
                    vehicle.setDate(result.getDate("InsuranceEnded").toLocalDate());
                    vehicleList.add(vehicle);
                }
            }
        }catch(SQLException e){
            System.out.println("SQL error");
        }
        return vehicleList;
    }

    public static List<Owner> selectExpiriesByOwner(Connection con){
        ResultSet result = null;
        List<Owner> ownerList = new ArrayList<Owner>();
        try{
            String query = "SELECT TaxNum FROM Vehicle WHERE InsuranceEnded < CURDATE()";
            Statement stmt = con.createStatement();
            result = stmt.executeQuery(query);
            if (result != null) {
                while (result.next()) {
                    Owner owner = new Owner();
                    owner.setTaxNumber(result.getInt("TaxNum"));
                    ownerList.add(owner);
                }
            }
        }catch(SQLException e){
            System.out.println("SQL error");
        }
        return ownerList;
    }
}
