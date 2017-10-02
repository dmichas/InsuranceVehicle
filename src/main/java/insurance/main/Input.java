package insurance.main;

import insurance.service.Expiries;
import insurance.service.Fine;
import insurance.service.ForecomingExpiries;
import insurance.service.VehicleStatus;
import insurance.utilities.database.Database;
import insurance.validation.Validation;
import java.sql.Connection;
import java.util.Scanner;

public class Input {

    public void runVehicleStatus(Scanner scanner){  //F1
                System.out.println("Type a vehicle plate (format ABC-1234): ");
                VehicleStatus vs = new VehicleStatus();
                vs.getVehicleStatus(scanner);
    }

    public void runForecomingExpiries(Scanner scanner){ //F2

        String export = "";
        String days = "";
        boolean cond = false;

        System.out.println("---Enter export type:\n" +
                "*1 File\n" +
                "*2 Console"
        );
        try {
            export = scanner.nextLine();
            do {
                if (!Validation.validateUserInput(export, "[12]")) {
                    System.out.println("Invalid input, please enter an export option or type 'exit':");
                    export = scanner.nextLine();
                    cond = !export.equalsIgnoreCase("exit");
                }else {
                    cond = false;
                }
            } while(cond);

            if (!export.equalsIgnoreCase("exit")) {
                System.out.println("Please enter number of days:");
                days = scanner.nextLine();
                do {
                    if (Validation.validateUserInput(days, "[0-9]+")) {
                        ForecomingExpiries fe = new ForecomingExpiries();
                        switch (export) {
                            case "1":
                                System.out.println("Type a name of file:");
                                String fileName = scanner.nextLine();
                                fe.writeForecomingExpiriesCSV(days, fileName + ".csv");
                                break;
                            case "2":
                                fe.printForecomingExpiries(days, "VehiclesData.csv");
                                break;
                            default:
                                break;
                        }
                    }else {
                        System.out.println("Please enter number of days or type 'exit':");
                        days = scanner.nextLine();
                        cond = !days.equalsIgnoreCase("exit");
                    }
                } while (cond);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void runExpiriesByPlate(Scanner scanner,Connection con){ //F3
        boolean cond = false;
        String export = "";

        System.out.println("---Enter export type:\n" +
                "*1 File\n" +
                "*2 Console"
        );
        try {
            export = scanner.nextLine();
            do {
                if (!Validation.validateUserInput(export, "[12]")) {
                    System.out.println("Invalid input, please enter an export option or type 'exit':");
                    export = scanner.nextLine();
                    cond = !export.equalsIgnoreCase("exit");
                } else {
                    cond = false;
                }
            } while (cond);

        }catch (Exception e){
            e.printStackTrace();
        }

        Expiries expiries = new Expiries();

        switch (export) {
            case "1":
                System.out.println("Type a name of file:");
                String fileName = scanner.nextLine();
                expiries.writeExpiriesCSV(fileName+".csv",con);
                break;
            case "2":
                expiries.printExpiriesList(con);
                break;
            default:
                break;
        }

    }

    public void runFinePerOwner(Scanner scanner, Connection con){  //f4
        boolean cond = false;
        System.out.println("Please type fine amount:");
        String fine = scanner.nextLine();
        do{
            if(!Validation.validateUserInput(fine,"[0-9[[.]{1}]]+")){
                System.out.println("Invalid input, please type fine or 'exit':");
                fine = scanner.nextLine();
                cond = !fine.equalsIgnoreCase("exit");
            } else {
                cond = false;
            }
        } while (cond);

        if(!fine.equalsIgnoreCase("exit")){
            double f = Double.parseDouble(fine);
            Fine finePerOwner = new Fine();
            finePerOwner.calculateFine(f,con);
        }
    }

    public  void runFunctionalities(){

        Database db = new Database();
        boolean cond = true;

        db.dbConnect();

        System.out.println("---Select functionality to perform:\n" +
                "*1 Vehicle Insurance status\n" +
                "*2 Forecoming Expiries\n" +
                "*3 Expiries by plate\n" +
                "*4 Fine per Owner"
        );
        try(Scanner scanner = new Scanner(System.in)) {
            do {
                String option = scanner.nextLine();
                switch (option) {
                    case "1":
                        runVehicleStatus(scanner);
                        cond = false;
                        break;
                    case "2":
                        runForecomingExpiries(scanner);
                        cond = false;
                        break;
                    case "3":
                        runExpiriesByPlate(scanner,db.getConnection());
                        cond = false;
                        break;
                    case "4":
                        runFinePerOwner(scanner,db.getConnection());
                        cond = false;
                        break;
                    default:
                        if (!option.equals("exit")) {
                            System.out.println("Please select one option again or type 'exit':");
                        } else {
                            cond = false;
                        }
                        break;
                }
            }while(cond);
        }
        db.dbClose();
    }
}
