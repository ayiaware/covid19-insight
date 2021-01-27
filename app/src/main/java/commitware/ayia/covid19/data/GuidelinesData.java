package commitware.ayia.covid19.data;

import java.util.ArrayList;
import java.util.List;

import commitware.ayia.covid19.BasicApp;
import commitware.ayia.covid19.R;
import commitware.ayia.covid19.models.Guideline;

public class GuidelinesData {

  private List<Guideline> guidelines;


  private final BasicApp context;


    public GuidelinesData(String name) {

        context = BasicApp.getInstance();

        switch (name){
            case "spread":
                guidelines = howSpread();
                break;
            case "quarantine":
                guidelines = quarantine();
                break;
            case "prevention":
                guidelines = prevention();
                break;
            case "signs":
                guidelines = signs();
                break;
            case "reduce":
                guidelines = reduce();
                break;

        }
    }


    public List<Guideline> getGuidelines() {
        return guidelines;
    }

    private List<Guideline> howSpread() {
        List<Guideline> guidelineList = new ArrayList<>();
        guidelineList.add(new Guideline(
                context.getResources().getString(R.string.infection1_heading),
                context.getResources().getString(R.string.infection1),
                R.drawable.waterdrop));
        guidelineList.add(new Guideline(context.getResources().getString(R.string.infection2_heading),
                context.getResources().getString(R.string.infection2),R.drawable.closecontact));
        guidelineList.add(new Guideline(context.getResources().getString(R.string.infection3_heading),
                context.getResources().getString(R.string.infection3),R.drawable.surface));
        return guidelineList;
    }

    public List<Guideline> quarantine()
    {
        List<Guideline> guidelineList = new ArrayList<>();
        guidelineList.add(new Guideline(
                context.getResources().getString(R.string.quarantine1_heading),
                context.getResources().getString(R.string.quarantine1_body),
                R.drawable.quarantinedays));
        guidelineList.add(new Guideline(context.getResources().getString(R.string.quarantine2_heading),
                context. getResources().getString(R.string.quarantine2_body),
                R.drawable.stayhome));
        guidelineList.add(new Guideline(context.getResources().getString(R.string.quarantine3_heading),
                context.getResources().getString(R.string.quarantine3_body),
                R.drawable.spread));
        guidelineList.add(new Guideline(context.getResources().getString(R.string.quarantine4_heading),
                context.getResources().getString(R.string.quarantine4_body),
                R.drawable.illness));

        return guidelineList;
    }

    public List<Guideline> prevention() {
        List<Guideline> guidelineList = new ArrayList<>();

        guidelineList.add(new Guideline(
                context.getResources().getString(R.string.prevention1_heading),
                context.getResources().getString(R.string.prevention1),
                R.drawable.hand));
        guidelineList.add(new Guideline(context.getResources().getString(R.string.prevention2_heading),
                context. getResources().getString(R.string.prevention2),
                R.drawable.shake));
        guidelineList.add(new Guideline(context.getResources().getString(R.string.prevention3_heading),
                context.getResources().getString(R.string.prevention3),
                R.drawable.sanitizer));
        guidelineList.add(new Guideline(context.getResources().getString(R.string.prevention4_heading),
                context.getResources().getString(R.string.prevention4),
                R.drawable.surface));

        return guidelineList;
    }

    public List<Guideline> signs()
    {
        List<Guideline> guidelineList = new ArrayList<>();
        guidelineList.add(new Guideline(
                context.getResources().getString(R.string.symptom1_heading),
                context.getResources().getString(R.string.symptom1),
                R.drawable.temperature));
        guidelineList.add(new Guideline(context.getResources().getString(R.string.symptom2_heading),
                context.getResources().getString(R.string.symptom2),
                R.drawable.sneeze));
        guidelineList.add(new Guideline(context.getResources().getString(R.string.symptom3_heading),
                context.getResources().getString(R.string.symptom3),
                R.drawable.cough));
        guidelineList.add(new Guideline(context.getResources().getString(R.string.symptom4_heading),
                context.getResources().getString(R.string.symptom4),
                R.drawable.headache));
        guidelineList.add(new Guideline(context.getResources().getString(R.string.symptom5_heading),
                context.getResources().getString(R.string.symptom5),
                R.drawable.breathing));
        return guidelineList;
    }
    public List<Guideline> reduce()
    {
        List<Guideline> guidelineList = new ArrayList<>();
        guidelineList.add(new Guideline(
                context.getResources().getString(R.string.reduce1_heading),
                context. getResources().getString(R.string.reduce1),
                R.drawable.sneeze));
        guidelineList.add(new Guideline(context.getResources().getString(R.string.reduce2_heading),
                context.getResources().getString(R.string.reduce2),
                R.drawable.stayhome2));
        guidelineList.add(new Guideline(context.getResources().getString(R.string.reduce3_heading),
                context.getResources().getString(R.string.reduce3),
                R.drawable.sneeze));
        guidelineList.add(new Guideline(context.getResources().getString(R.string.reduced4_heading),
                context.getResources().getString(R.string.reduce4),
                R.drawable.facemask));
        guidelineList.add(new Guideline(context.getResources().getString(R.string.reduce5_heading),
                context.getResources().getString(R.string.reduce5),
                R.drawable.stayhome2));
        return guidelineList;
    }

}
