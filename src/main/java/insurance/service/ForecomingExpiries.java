package insurance.service;

import insurance.file.ReadFile;
import insurance.file.WriteFile;
import insurance.model.Owner;
import insurance.model.Vehicle;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ForecomingExpiries {

    public LocalDate[] datePlusDays(String days){
        LocalDate currentDate = LocalDate.now();
        LocalDate datePlusDays = currentDate.plusDays(Integer.parseInt(days));
        LocalDate[] dates = {currentDate, datePlusDays};
        return dates;
    }

    public HashMap<Vehicle, Owner> findForecomingExpiriesCSV(LocalDate[] dates, String file){
        HashMap<Vehicle, Owner> vehicleOwner = new HashMap<Vehicle,Owner>();
        ReadFile rfile = new ReadFile();
        List<String> list = rfile.readFile("VehiclesData.csv");
       try {
           if (!list.isEmpty()) {
               for (String row : list) {
                   if(!row.isEmpty()) {
                       String[] items = row.split(";");
                       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                       LocalDate date = LocalDate.parse(items[2], formatter); // input date like 16/12/2017 and return 2017-12-6
                       if (!date.isBefore(dates[0]) && !date.isAfter(dates[1])) {
                           Vehicle vehicle = new Vehicle();
                           Owner owner = new Owner();
                           vehicle.setPlate(items[0]);
                           vehicle.setDate(date);
                           owner.setTaxNumber(Integer.parseInt(items[1]));
                           vehicleOwner.put(vehicle, owner);
                       }
                   }
               }
           }
       }catch(Exception e){
            System.out.println("Error");
       }
        return vehicleOwner;
    }

    public void writeForecomingExpiriesCSV(String days,String file){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate[] dates = datePlusDays(days);
        HashMap<Vehicle,Owner>  vehicleOwner = findForecomingExpiriesCSV(dates,file);
        List<String> list = new ArrayList<>();
        if(!vehicleOwner.isEmpty()) {
            for (Map.Entry m : vehicleOwner.entrySet()) {
                Vehicle v = (Vehicle) m.getKey();
                Owner o = (Owner) m.getValue();
                list.add(v.getPlate() + ";" + o.getTaxNumber() + ";" + v.getDate().format(formatter));
            }
            WriteFile wfile = new WriteFile();
            wfile.writeFile(list, file);
            System.out.println(file+" is written.");
        }else{
            System.out.println("Forecoming expiries not found");
        }
    }

    public void printForecomingExpiries(String days, String file /*Database db*/){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate[] dates = datePlusDays(days);
        HashMap<Vehicle, Owner> vehicleOwner = findForecomingExpiriesCSV(dates, file);
        System.out.println("Expire in the next " + days + " days (" + dates[0].format(format) + "-" + dates[1].format(format) + "):");
        System.out.println("\n\tPLATE\t\tTAX NUMBER\t\tEXPIRATION DATE");
        System.out.println("-------------------------------------------------");
        int i = 1;
        if(!vehicleOwner.isEmpty()) {
            for (Map.Entry vo : vehicleOwner.entrySet()) {
                Vehicle v = (Vehicle) vo.getKey();
                Owner o = (Owner) vo.getValue();
                System.out.println(i+". "+v.getPlate() + "\t\t" + o.getTaxNumber() + "\t\t" + v.getDate().format(format));
                i++;
            }
        }else{
            System.out.println("Forecoming expiries not found");
        }
    }

}