package commitware.ayia.covid19.ui.news;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import commitware.ayia.covid19.Adapter.RecyclerViewAdapterNews;

import commitware.ayia.covid19.interfaces.OnFragmentListenerMain;
import commitware.ayia.covid19.interfaces.RecyclerViewClickListener;
import commitware.ayia.covid19.listeners.RecyclerViewTouchListener;
import commitware.ayia.covid19.models.News;
import commitware.ayia.covid19.R;

import commitware.ayia.covid19.service.Retrofit.RestApiResponse;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FragmentNews extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private OnFragmentListenerMain mListener;
    private SwipeRefreshLayout swipe;
    private RecyclerViewAdapterNews adapter;

    private RelativeLayout errorLayout;
    private ImageView errorImage;
    private TextView errorTitle, errorMessage;
    private Button btnRetry;

    private NewsViewModel viewModel;

    RecyclerView rv;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       // newsViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(NewsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_news, container, false);
        swipe = root.findViewById(R.id.swipeRefreshNews);
        rv = root.findViewById(R.id.newsRecycler);

        errorLayout = root.findViewById(R.id.errorLayout);
        errorImage =root. findViewById(R.id.errorImage);
        errorTitle = root.findViewById(R.id.errorTitle);
        errorMessage = root.findViewById(R.id.errorMessage);
        btnRetry = root.findViewById(R.id.btnRetry);

        adapter = new RecyclerViewAdapterNews(getContext());
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);



        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        rv.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(), rv, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                final News news = adapter.getNews().get(position);
                startIntent(news);
            }
            @Override
            public void onLongClick(View view, final int position) {

            }
        }));

        loadNewsData(viewModel.getNewsData());


    }
    private void loadNewsData(LiveData<RestApiResponse> liveData) {

        errorLayout.setVisibility(View.GONE);

        refreshNews(true);

        liveData.observe(getViewLifecycleOwner(), new Observer<RestApiResponse>() {
            @Override
            public void onChanged(RestApiResponse apiResponse) {

                if (apiResponse == null) {
                    showErrorMessage(
                            R.drawable.no_result,
                            "No Result",
                            "Retry\n");
                    return;
                }
                if (apiResponse.getError() == null) {
                    // call is successful
                    Log.i(TAG, "Data response is " + apiResponse.getPosts());
                    adapter.setNews(apiResponse.getPosts());
                    refreshNews(false);

                } else {
                    // call failed.
                    // Throwable e = apiResponse.getError();
                    // Toast.makeText(getActivity(), "Error is " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    // Log.e(TAG, "Error is " + e.getLocalizedMessage());

                    showErrorMessage(
                            R.drawable.no_result,
                            "Network Error",
                            "Check Network\n");



                }

            }


        });
        swipe.setOnRefreshListener(this);
    }

    private void refreshNews(boolean isRefresh) {
        if (isRefresh) {
            swipe.setRefreshing(true);
        } else {
            swipe.setRefreshing(false);
        }
    }





    @Override
    public void onRefresh() {

        loadNewsData(viewModel.getNewsData());
    }

    private void showErrorMessage(int imageView, String title, String message){

        refreshNews(false);

        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
        }

        errorImage.setImageResource(imageView);
        errorTitle.setText(title);
        errorMessage.setText(message);

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentListenerMain) {
            mListener = (OnFragmentListenerMain) context;
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
    private void startIntent(News news) {
        if (mListener != null) {
            mListener.getNewsIntent(news);
        }
    }

}