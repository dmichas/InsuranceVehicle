package insurance.service;

import insurance.file.ReadFile;
import insurance.model.Vehicle;
import insurance.validation.Validation;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class VehicleStatus {


    public Vehicle selectVehicleByPlateCSV(String plate){
        ReadFile rfile = new ReadFile();
        List<String> list = rfile.readFile("VehiclesData.csv");
        Vehicle vehicle =  new Vehicle();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("d/M/yyyy");
        for(String row:list){
            if(plate.equals(row.substring(0,8))){
              String[] line = row.split(";");
              vehicle.setPlate(line[0]);
              LocalDate date = LocalDate.parse(line[2],format);
              vehicle.setDate(date);

            }
        }
        return vehicle;
    }

    public void getVehicleStatus(Scanner scanner) { //TAT-4735
        boolean cond = true;
        DateTimeFormatter format = DateTimeFormatter.ofPattern("d/M/yyyy");
        try {
            do {
                String plate = scanner.nextLine();
                plate = plate.toUpperCase();
                if (Validation.validateUserInput(plate, "[A-Z&&[^CDGJLQRSUVW]]{3}-[1-9][0-9]{3}")) {
                    Vehicle vehicle = selectVehicleByPlateCSV(plate);
                    if (vehicle.getPlate() != null) {
                        if (vehicle.getDate().isAfter(LocalDate.now())) {
                            System.out.println("Insurance license is active: Plate: " + vehicle.getPlate() + " Expiration date: "  + vehicle.getDate().format(format) + "\n");
                        } else {
                            System.out.println("Insurance license is expired: Plate: " + plate + " Expiration date: " + vehicle.getDate().format(format)+"\n");
                        }
                        System.out.println("Type another plate or type 'exit':");
                        cond = !plate.equalsIgnoreCase("exit");
                    } else {
                        System.out.println("There is no plate '"+plate+"', please type a plate again or type 'exit':");
                        cond = !plate.equalsIgnoreCase("exit");
                    }
                } else {
                    cond = !plate.equalsIgnoreCase("exit");
                    if(cond) {
                        System.out.println("Invalid input, please type a plate (ABC-1234) again or type 'exit':");
                    }
                }
            } while (cond);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
