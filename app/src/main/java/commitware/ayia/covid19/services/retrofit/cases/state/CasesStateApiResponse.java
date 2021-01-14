package commitware.ayia.covid19.services.retrofit.cases.state;

import java.util.List;

import commitware.ayia.covid19.models.CasesState;


public class CasesStateApiResponse {

    private CasesState aCases;
    private Throwable error;

    public CasesStateApiResponse(List<CasesState> aCases) {
        this.aCases = aCases.get(0);
        this.error = null;
    }

    public CasesStateApiResponse(Throwable error) {
        this.error = error;
        this.aCases = null;
    }

    public CasesState getaCases() {
        return aCases;
    }

    public void setaCases(CasesState aCases) {
        this.aCases = aCases;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
