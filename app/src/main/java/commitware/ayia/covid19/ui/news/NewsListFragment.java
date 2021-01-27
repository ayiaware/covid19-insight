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


import commitware.ayia.covid19.adapter.RvNewsAdapter;

import commitware.ayia.covid19.interfaces.FragmentInteraction;
import commitware.ayia.covid19.interfaces.RecyclerViewClickListener;
import commitware.ayia.covid19.utils.RVTouchListener;
import commitware.ayia.covid19.models.News;
import commitware.ayia.covid19.R;

import commitware.ayia.covid19.services.retrofit.news.NewsApiResponse;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class NewsListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private FragmentInteraction mListener;
    private SwipeRefreshLayout swipe;
    private RvNewsAdapter adapter;

    private RelativeLayout errorLayout;
    private ImageView errorImage;
    private TextView errorTitle, errorMessage;
    private Button btnRetry;

    private NewsViewModel viewModel;

    private RecyclerView rv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       // newsViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(NewsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_news_list, container, false);
        swipe = root.findViewById(R.id.swipeRefreshNews);
        rv = root.findViewById(R.id.newsRecycler);

        errorLayout = root.findViewById(R.id.errorLayout);
        errorImage =root. findViewById(R.id.errorImage);
        errorTitle = root.findViewById(R.id.errorTitle);
        errorMessage = root.findViewById(R.id.errorMessage);
        btnRetry = root.findViewById(R.id.btnRetry);

        adapter = new RvNewsAdapter(getContext());
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);



        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        rv.addOnItemTouchListener(new RVTouchListener(getActivity(), rv, new RecyclerViewClickListener() {
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


    private void loadNewsData(LiveData<NewsApiResponse> liveData) {

        errorLayout.setVisibility(View.GONE);

        refreshNews(true);

        liveData.observe(getViewLifecycleOwner(), new Observer<NewsApiResponse>() {
            @Override
            public void onChanged(NewsApiResponse apiResponse) {

                if (apiResponse == null) {
                    showErrorMessage(
                            "No Result",
                            "Retry\n");
                    return;
                }
                if (apiResponse.getError() == null) {
                    // call is successful
                    Log.i(TAG, "Data response is " + apiResponse.getNewsPosts());
                    adapter.setNews(apiResponse.getNewsPosts());
                    refreshNews(false);

                } else {
                    // call failed.
                    // Throwable e = apiResponse.getError();
                    // Toast.makeText(getActivity(), "Error is " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    // Log.e(TAG, "Error is " + e.getLocalizedMessage());
                    showErrorMessage(
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

    private void showErrorMessage(String title, String message){

        refreshNews(false);

        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
        }

        errorImage.setImageResource(R.drawable.no_result);
        errorTitle.setText(title);
        errorMessage.setText(message);

        btnRetry.setOnClickListener(v -> onRefresh());

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
    private void startIntent(News news) {
        if (mListener != null) {
            mListener.getNewsIntent(news);
        }
    }

}