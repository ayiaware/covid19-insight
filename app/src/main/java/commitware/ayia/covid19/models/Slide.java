package commitware.ayia.covid19.models;

public class Slide {
    private String heading;
            private String subHeading;
            private int image;


    public Slide(String heading, String subHeading, int image) {
        this.heading = heading;
        this.subHeading = subHeading;
        this.image = image;

    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getSubHeading() {
        return subHeading;
    }

    public void setSubHeading(String subHeading) {
        this.subHeading = subHeading;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

}
