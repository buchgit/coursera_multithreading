package com.example.coursera_multithreading;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.coursera_multithreading.mock.MockAdapter;
import com.example.coursera_multithreading.mock.MockGenerator;

import java.util.Random;

/*
метод  postDelayed можно применять на всех View-элементах, так же как на хэндлере
 */

public class RecyclerFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView recyclerView;
    private final MockAdapter mockAdapter = new MockAdapter();
    private SwipeRefreshLayout refreshLayout;
    private View errorView;
    private Random random = new Random();//для имитации ошибки

    public static RecyclerFragment newInstance() {
        return new RecyclerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.rec_view);
        refreshLayout = view.findViewById(R.id.refresher);
        errorView = view.findViewById(R.id.view_error);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mockAdapter);
        refreshLayout.setOnRefreshListener(this);//привязка обновлятора к активити
        //mockAdapter.addData(MockGenerator.generate(20));
    }

    @Override
    public void onRefresh() {
        refreshLayout.postDelayed(new Runnable() { //метод  postDelayed можно применять на любых View-элементах, так же как на хэндлере
            @Override
            public void run() {

                //имитация ошибки
                int error = random.nextInt(4);
                if (error==0){
                    showError();
                }else{
                    showData();
                }

                if (refreshLayout.isRefreshing()){ //если колесо обновления крутится, тушим его
                    refreshLayout.setRefreshing(false);
                }
            }

            private void showData() {
                mockAdapter.addData(MockGenerator.generate(5),true);
                errorView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            private void showError() {
                errorView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        }, 1000); //задержка

    }
}
