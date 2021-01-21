package commitware.ayia.covid19.services.retrofit.cases.state;

import java.util.List;

import commitware.ayia.covid19.AppController;
import commitware.ayia.covid19.models.CasesState;


public class ApiResponseState {

    private CasesState cases;
    private List<CasesState> casesList;

    private Throwable error;

    public ApiResponseState(List<CasesState> casesList) {

        this.casesList = casesList;

        for (CasesState cases: casesList) {

            if(cases.getState().equals(AppController.getInstance().getState()))
                this.cases = cases;

        }
        this.error = null;
    }

    public ApiResponseState(Throwable error) {
        this.error = error;
        this.cases = null;
    }

    public CasesState getCases() {
        return cases;
    }

    public void setCases(CasesState cases) {
        this.cases = cases;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public List<CasesState> getCasesList() {
        return casesList;
    }

    public void setCasesList(List<CasesState> casesList) {
        this.casesList = casesList;
    }
}
