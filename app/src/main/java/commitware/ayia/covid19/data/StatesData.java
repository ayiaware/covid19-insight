package commitware.ayia.covid19.data;

import java.util.ArrayList;
import java.util.List;

import commitware.ayia.covid19.models.Location;

public class StatesData {

        private static final String[] states = {
                "Lagos","Kano","Abuja", "Katsina", "Bauchi", "Borno", "Jigawa", "Ogun", "Gombe",
                "Kaduna", "Sokoto", "Edo", "Zamfara", "Oyo", "Kwara", "Osun", "Rivers", "Kebbi",
                "Nasarawa", "Delta", "Adamawa", "Yobe", "Plateau", "Ondo", "Taraba", "Akwa Ibom",
                "Ekiti", "Enugu", "Niger", "Ebonyi", "Bayelsa", "Benue", "Imo", "Anambra", "Abia",
                "Kogi", "Cross River"};

        private static final List<Location> list;

        static  {
            list = new ArrayList<>();
            for (String state : states) {
                list.add(new Location(state, "Nigeria", "Africa", "NG"));
            }
        }

        public static List<Location> getList(){
            return list;
        }


}
