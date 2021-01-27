package commitware.ayia.covid19.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StatesWrapper {

    @SerializedName("data")
    public States states;

    public States getStates() {
        return states;
    }

    public static class States {

        @SerializedName("states")
        private final List<State> list;

        public States(List<State> list) {
            this.list = list;
        }

        public List<State> getList() {
            return list;
        }

    }


//    public static class State {
//
//        @SerializedName("confirmedCases")
//        private String cases;
//
//        @SerializedName("discharged")
//        private String recovered;
//
//        @SerializedName("death")
//        private String deaths;
//
//        @SerializedName("casesOnAdmission")
//        private String active;
//
//        @SerializedName("state")
//        private String state;
//
//
//        public String getCases() {
//            return cases;
//        }
//
//        public String getRecovered() {
//            return recovered;
//        }
//
//        public String getDeaths() {
//            return deaths;
//        }
//
//        public String getActive() {
//            return active;
//        }
//
//        public String getState() {
//            return state;
//        }
//    }



}