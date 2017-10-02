package insurance.utilities.fileCsv;

import insurance.validation.Validation;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Csv {

    public List<String> readForecomingExpiriesCSV(Scanner scanner){
        List<String> csvList = new ArrayList<>();
        boolean cond = true;
        do {
            String days = scanner.nextLine();
            if (Validation.validateUserInput(days, "[0-9]+")) {
                try (BufferedReader buffer = new BufferedReader(new FileReader("VehiclesData.csv"))) {
                    String line;
                    while ((line = buffer.readLine()) != null) {
                        String[] str = line.split(";");
                        LocalDate d = LocalDate.parse(str[2], DateTimeFormatter.ofPattern("d/M/yyyy"));
                        LocalDate currentDate = LocalDate.now();
                        LocalDate datePlusDays = currentDate.plusDays(Integer.parseInt(days));
                        if (!d.isBefore(currentDate) && !d.isAfter(datePlusDays)) {
                            csvList.add(line);
                        }
                    }
                    cond = false;
                } catch (IOException e) {
                    System.out.println("File not found");
                }catch(NumberFormatException e){
                    System.out.println("Please enter number of days or type 'exit':");
                }
            }else {
                cond = !days.equals("exit");
                if (cond)
                    System.out.println("Please enter number of days or type 'exit':");
            }
        }while(cond);
        return csvList;
    }

    public void writeForecomingExpiriesCSV(List<String> list){
        try(BufferedWriter buffer = new BufferedWriter(new FileWriter("ForecomingExpiries.csv"))){
            for(int i = 0; i < list.size(); i++){
                buffer.write(list.get(i));
                buffer.write("\n");
            }
        }catch (FileNotFoundException e){
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void forecomingExpiriesCSV(Scanner scanner){
       List<String> list = readForecomingExpiriesCSV(scanner);
       writeForecomingExpiriesCSV(list);
    }
}
