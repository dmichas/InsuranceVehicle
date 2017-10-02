package insurance.utilities.fileCsv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadCsv {


    public List<String> readFile(String file, String delimiter){
        List<String> list = new ArrayList<>();
        try (BufferedReader buffer = new BufferedReader(new FileReader(file))) {
            String line;
            try {
                while ((line = buffer.readLine()) != null) {
                        list.add(line);
                    }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }catch(FileNotFoundException e) {
            System.out.println(file+" doesn't exist");
        }catch(IOException e){
            e.printStackTrace();
        }
        return list;
    }
}
