package insurance.file;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WriteFile {

    public void writeFile(List<String> list, String file) {
        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < list.size(); i++) {
                buffer.write(list.get(i));
                buffer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
