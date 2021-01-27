package commitware.ayia.covid19.services.retrofit.insight;

import commitware.ayia.covid19.models.Globe;


public class ResponseGlobe {

    private Globe globe;

    private Throwable error;

    public ResponseGlobe(Globe globe) {
        this.globe = globe;
        this.error = null;
    }

    public ResponseGlobe(Throwable error) {
        this.error = error;
        this.globe = null;
    }

    public Globe getGlobe() {
        return globe;
    }

    public Throwable getError() {
        return error;
    }

}
