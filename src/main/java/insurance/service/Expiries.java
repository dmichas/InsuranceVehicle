package insurance.service;

import insurance.file.WriteFile;
import insurance.model.Vehicle;
import insurance.utilities.database.QueryDb;
import java.sql.Connection;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Expiries {

    public List<Vehicle> sortExpiriesList(Connection con){
        List<Vehicle> vehicleList = QueryDb.selectExpiries(con);
        if(!vehicleList.isEmpty()){
            for(int i = 0; i < vehicleList.size(); i++){
                for(int j = 1; j < (vehicleList.size()-i); j++){
                    if((vehicleList.get(j-1).compareTo(vehicleList.get(j)) > 0)){
                        Vehicle vehicleTemp = vehicleList.get(j-1);
                        vehicleList.set(j-1, vehicleList.get(j));
                        vehicleList.set(j,vehicleTemp);
                    }
                }

            }
        }
        return vehicleList;
    }

    public void printExpiriesList(Connection con){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<Vehicle> vehicleList = sortExpiriesList(con);
        if(vehicleList != null){
            System.out.println("EXPIRIES BY PLATE:");
            for(int i = 0; i < vehicleList.size(); i++){
                Vehicle vehicleItem = vehicleList.get(i);
                System.out.println((i+1)+". Plate: "+vehicleItem.getPlate()+" Expired: "+vehicleItem.getDate().format(formatter));
            }
        }else{
            System.out.println("No results found");
        }
    }

    public void writeExpiriesCSV(String file,Connection con){                       //plate;date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<Vehicle> vehicleList = sortExpiriesList(con);
        List<String> expiriesCSV = new ArrayList<String>();
        if(vehicleList != null) {
            for(int i=0; i<vehicleList.size(); i++){
                String plate = vehicleList.get(i).getPlate();
                String date = vehicleList.get(i).getDate().format(formatter);
                String row = plate+";"+date;
                expiriesCSV.add(row);
            }
            WriteFile wfile = new WriteFile();
            wfile.writeFile(expiriesCSV, file);
            System.out.println(file + " is written");
        }else{
            System.out.println("No results found");
        }
    }



}
