package commitware.ayia.covid19.ui.guideline;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import commitware.ayia.covid19.interfaces.FragmentInteraction;
import commitware.ayia.covid19.R;

import static commitware.ayia.covid19.utils.AppUtils.SLIDER_INTENT;

public class GuidelinesFragment extends Fragment {


    private FragmentInteraction mListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_guidelines, container, false);

        CardView cardViewSpread = root.findViewById(R.id.cardViewSpread);
        CardView cardViewQuarantine = root.findViewById(R.id.cardViewQuarantine);
        CardView cardViewPrevention = root.findViewById(R.id.cardViewPrevention);
        CardView cardViewSigns = root.findViewById(R.id.cardViewSigns);
        CardView cardViewReduce = root.findViewById(R.id.cardViewReduce);
        cardViewSpread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIntent("spread");
            }
        });
        cardViewQuarantine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIntent("quarantine");
            }
        });

        cardViewPrevention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIntent("prevention");
            }
        });
        cardViewSigns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startIntent("signs");
            }
        });

cardViewReduce.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        startIntent("reduce");
    }
});


        return root;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteraction) {
            mListener = (FragmentInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    // Now we can fire the event when the user selects something in the fragment
    private void startIntent(String slide) {
        if (mListener != null) {
            mListener.getListIntent(SLIDER_INTENT, slide);
        }
    }

}