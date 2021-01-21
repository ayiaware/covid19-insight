package commitware.ayia.covid19.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CasesWrapperState {

    @SerializedName("data")
    public Cases cases;

    public Cases getCases() {
        return cases;
    }

    public void setCases(Cases cases) {
        this.cases = cases;
    }

    public static class Cases {

        @SerializedName("states")
        private List<CasesState> cases;

        public Cases(List<CasesState> cases) {
            this.cases = cases;
        }

        public List<CasesState> getCases() {
            return cases;
        }

        public void setCases(List<CasesState> cases) {
            this.cases = cases;
        }
    }

}