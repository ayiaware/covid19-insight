package commitware.ayia.covid19.ui.helpline;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import commitware.ayia.covid19.adapter.RvHelplineAdapter;
import commitware.ayia.covid19.BasicApp;
import commitware.ayia.covid19.interfaces.FragmentInteraction;
import commitware.ayia.covid19.interfaces.RecyclerViewClickListener;
import commitware.ayia.covid19.utils.RVTouchListener;
import commitware.ayia.covid19.models.Helpline;
import commitware.ayia.covid19.R;


public class HelplineFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RelativeLayout errorLayout;
    private ImageView errorImage;
    private TextView errorTitle, errorMessage;
    private Button btnRetry;
    private DatabaseReference rootRef;
    private DatabaseReference demoRef;
    private FragmentInteraction mListener;

    private String TAG = "HELPLINEFRAGMENT";
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private boolean connected;
    private SwipeRefreshLayout swipe;
    private String state;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       // HelplineViewModel helplineViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(HelplineViewModel.class);
        View root = inflater.inflate(R.layout.fragment_helpline, container, false);


        recyclerView = root.findViewById(R.id.rvHelpline);
        progressBar = root.findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
//        recyclerView.addItemDecoration(dividerItemDecoration);
        progressBar.setVisibility(View.VISIBLE);

        swipe = root.findViewById(R.id.swipeView);
        swipe.setOnRefreshListener(this);

        errorLayout = root.findViewById(R.id.errorLayout);
        errorImage =root. findViewById(R.id.errorImage);
        errorTitle = root.findViewById(R.id.errorTitle);
        errorMessage = root.findViewById(R.id.errorMessage);
        btnRetry = root.findViewById(R.id.btnRetry);
        errorLayout.setVisibility(View.GONE);



        state = BasicApp.getInstance().getLocation().getState();

        // Database reference pointing to root of database
        rootRef = FirebaseDatabase.getInstance().getReference(state);

       // demoRef = rootRef.child("Kaduna");

//        DatabaseReference connection = FirebaseDatabase.getInstance().getReference(".info/connected");
//        connection.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//               connected = snapshot.getValue(Boolean.class);
//                if (connected) {
//                    Log.d(TAG, "connected");
//
//                } else {
//                    Log.d(TAG, "not connected");
//                   // progressBar.setVisibility(View.GONE);
////                    Toast.makeText(getActivity(), "Check Internet Connection", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.w(TAG, "Listener was cancelled");
//            }
//        });

        getHelplines();




        return root;
    }

    private void getHelplines()
    {
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                List<Helpline> helplines = new ArrayList<>();
              // Toast.makeText(getActivity(), "fetched data " + dataSnapshot.child("Kaduna").toString()+" count"+dataSnapshot.child("Kaduna").getChildrenCount(), Toast.LENGTH_LONG).show();

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        Helpline helpline = snapshot.getValue(Helpline.class);
                        helplines.add(helpline);

                    }
                    RvHelplineAdapter adapterHelpline = new RvHelplineAdapter(getActivity(), helplines);
                    recyclerView.setAdapter(adapterHelpline);

                    recyclerView.addOnItemTouchListener(new RVTouchListener(getActivity(), recyclerView, new RecyclerViewClickListener() {
                        @Override
                        public void onClick(View view, int position) {

                            final Helpline helpline = adapterHelpline.getmValuesFilteredList().get(position);
                            callHelpline(helpline.getFIELD1(), helpline.getFIELD3());

                        }

                        @Override
                        public void onLongClick(View view, final int position) {
                            final Helpline helpline = adapterHelpline.getmValuesFilteredList().get(position);
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("Report helpline if not reachable so that it gets taken down");
                            builder.setTitle("Report Helpline");
                            builder.setPositiveButton("report", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                 sendFeedback(getActivity(), "Contact report "+helpline.getFIELD1());
                                }
                            });
                            builder.setNeutralButton("Cancel", null);
                            AlertDialog dialog = builder.create();
                            dialog.show();

                        }
                    }));
                }
                else {
                Toast.makeText(getActivity(), "helplines not available for "+state, Toast.LENGTH_SHORT).show();
                    showErrorMessage(
                            R.drawable.ic_call_black_24dp,
                            "Helpline not available",
                            "for "+state+" state"+
                                    "\nSend Feedback to commitware@gmail.com",
                    "send");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Listener was cancelled");
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Error fetching data", Toast.LENGTH_LONG).show();
                showErrorMessage(
                        R.drawable.ic_call_black_24dp,
                        "Error fetching data",
                        "retry"+
                               "","retry");
            }



        });
      rootRef.orderByValue().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChild) {
                //Log.d(TAG, "The " + snapshot.getKey() + " dinosaur's score is " + snapshot.getValue());
                progressBar.setVisibility(View.GONE);
                errorLayout.setVisibility(View.GONE);
                swipe.setRefreshing(false);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

    }
    private void showErrorMessage(int imageView, String title, String message, String btnTxt){

        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
        }

        errorImage.setImageResource(imageView);
        errorTitle.setText(title);
        errorMessage.setText(message);
        btnRetry.setText(btnTxt);
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btnTxt.equals("retry"))
                {
                    onRefresh();
                }
                else if (btnTxt.equals("send")) {
                    sendFeedback(getActivity(),"Helplines are not available");
                }

            }
        });

    }
    @Override
    public void onAttach(Context context) {
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
    public void callHelpline(String helpline, String intent) {
        if (mListener != null) {
            mListener.getCallHelplineIntent(helpline, intent);
        }
    }

    @Override
    public void onRefresh() {

            getHelplines();



    }


    public  void sendFeedback(Context context, String query) {
        String body = null;
        try {
            body = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            body = "\n\n-----------------------------\nPlease don't remove this information\nState: "+ state+"\n"+query+"\nDevice OS: Android \n Device OS version: " +
                    Build.VERSION.RELEASE + "\n App Version: " + body + "\n Device Brand: " + Build.BRAND +
                    "\n Device Model: " + Build.MODEL + "\n Device Manufacturer: " + Build.MANUFACTURER;
        } catch (PackageManager.NameNotFoundException e) {
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"commitware@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Helpline Query from Covid19 insight Ng");
        intent.putExtra(Intent.EXTRA_TEXT, body);
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.choose_email_client)));
    }
}