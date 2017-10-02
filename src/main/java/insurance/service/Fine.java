package insurance.service;

import insurance.model.Owner;
import insurance.utilities.database.QueryDb;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fine {

    private boolean containOwner(HashMap<Owner,Integer> hasMap, Owner owner){
        for(Map.Entry hm : hasMap.entrySet() ){
            Owner o = (Owner)hm.getKey();
            Integer tax1 = o.getTaxNumber();
            if(tax1.equals(owner.getTaxNumber())){
                return true;
            }
        }
        return false;
    }

    public HashMap<Owner,Integer> countVehiclePerOwner(Connection con){
        List<Owner> ownerList = QueryDb.selectExpiriesByOwner(con);
        HashMap<Owner,Integer> vehiclesPerOwner = new HashMap<>();
           for(int i=0; i<ownerList.size(); i++){
               int count = 0;
               Integer taxNumI=ownerList.get(i).getTaxNumber();
               if(!containOwner(vehiclesPerOwner,ownerList.get(i))) {
                   for (int j = 0; j < ownerList.size(); j++) {
                       Integer taxNumJ = ownerList.get(j).getTaxNumber();
                       if (taxNumI.equals(taxNumJ)) {
                           count++;
                       }
                   }
               }
            if(count != 0 ) {
                vehiclesPerOwner.put(ownerList.get(i), count);
            }
        }
        return vehiclesPerOwner;
    }

    public void calculateFine(double fine,Connection con){
        HashMap<Owner,Integer> o = countVehiclePerOwner(con);
        System.out.println("TOTAL FINE PER OWNER");
        System.out.println("---------------------------------------------------------------------------");
        for(Map.Entry owner : o.entrySet()){
           int tax = ((Owner)owner.getKey()).getTaxNumber();
           int vehicles = (int)owner.getValue();
           System.out.println("Owner tax number: "+tax+"\t\tUnisured vehicles: "+vehicles+"\t\tTotal fine: "+vehicles*fine+"€");
        }
    }
}
