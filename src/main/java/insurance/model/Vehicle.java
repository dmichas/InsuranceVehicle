package insurance.model;

import java.time.LocalDate;

public class Vehicle implements Comparable<Vehicle> {

    private String plate;
    private LocalDate date;

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPlate() {
        return plate;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public int compareTo(Vehicle vehicle) { //  vehicle1.compareTo(vehicle2)
        String plateId = vehicle.getPlate();
        int result = 0;
        int i = 0;
        while(result == 0 ){
            if((int)this.plate.charAt(i) < (int)plateId.charAt(i)){
                result = -1;
            }else if((int)this.plate.charAt(i) > (int)plateId.charAt(i)){
                result = 1;
            }else{
                result = 0;
            }
            i++;
        }
        return result;
    }
}
