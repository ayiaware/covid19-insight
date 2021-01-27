package commitware.ayia.covid19.services.retrofit.insight.state;

import java.util.ArrayList;
import java.util.List;

import commitware.ayia.covid19.BasicApp;
import commitware.ayia.covid19.models.Insight;
import commitware.ayia.covid19.models.State;


public class ResponseState {

    private State insight;

    private List<State> states;

    private List<Insight> insightList;

    private Throwable error;

    public ResponseState(List<State> states) {

        this.states = states;

        insightList = new ArrayList<>();

        for (State state: states) {

          // doing this because i wanna avoid a boiler plated code i am sure there is a better way to do this

            if(state.getName().equals(BasicApp.getInstance().getLocation().getState()))
                this.insight = state;

            insightList.add( new Insight(
                    state.getName(), Integer.parseInt(state.getCases()),state.getTodayCases(),
                    state.getDeaths(), state.getTodayDeaths(), state.getRecovered(), state.getActive(),
                    state.getCritical(), "", state.getUpdated(), state.getTested()));


        }

        this.error = null;
    }

    public ResponseState(Throwable error) {
        this.error = error;
        this.insight = null;
        this.insightList = null;
    }

    public State getInsight() {
        return insight;
    }

    public void setInsight(State insight) {
        this.insight = insight;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    public List<Insight> getInsightList() {
        return insightList;
    }
}
