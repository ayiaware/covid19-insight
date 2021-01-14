package commitware.ayia.covid19.services.retrofit.cases;



import commitware.ayia.covid19.models.Cases;
import commitware.ayia.covid19.models.CasesState;


public class CasesApiResponse {

    private Cases aCases;
    private CasesState casesState;
    private Throwable error;

    public CasesApiResponse(Cases aCases) {
        this.aCases = aCases;
        this.error = null;
    }

    public CasesApiResponse(Throwable error) {
        this.error = error;
        this.aCases = null;
    }

    public Cases getaCases() {
        return aCases;
    }

    public void setaCases(Cases aCases) {
        this.aCases = aCases;
    }

    public CasesState getCasesState() {
        return casesState;
    }

    public void setCasesState(CasesState casesState) {
        this.casesState = casesState;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
