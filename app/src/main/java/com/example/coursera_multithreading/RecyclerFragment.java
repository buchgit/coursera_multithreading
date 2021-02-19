package com.example.coursera_multithreading;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.coursera_multithreading.mock.MockAdapter;
import com.example.coursera_multithreading.mock.MockGenerator;

import java.util.Random;

/*
1. implements LoaderManager.LoaderCallbacks<Cursor> with 3 his methods. <Cursor> is container for data. It may be List, JSONArray etc.
2. "к Cursor относиться как к таблице" - есть столбцы и строки.
3. in method onCreateLoader create Loader<Cursor> (with query)
4. create any Adapter - class  (extends RecyclerView.Adapter in this sample) - ContactsAdapter
5. in method onLoadFinished use this adapter (ContactsAdapter)
6. ContactsAdapter in it's 3 methods add, bind data to any ViewHolder (MockHolder)
7. Создаем лоадер, который имеет 3 метода
8. Пишет механизм отработки нажатий на элементы RecyclerView. Используем интерфейс (ContactsAdapter.onItemClickListener), который создаем в адаптере (ContactsAdapter).
9.

 */

public class RecyclerFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView recyclerView;
    //private final MockAdapter mockAdapter = new MockAdapter();
    private final ContactsAdapter contactsAdapter = new ContactsAdapter();
    private SwipeRefreshLayout refreshLayout;
    private View errorView;
    private Random random = new Random();//для имитации ошибки
    private ContactsAdapter.onItemClickListener onItemClickListener;

    public static RecyclerFragment newInstance() {
        return new RecyclerFragment();
    }

    //первый в жизненом цикле фрагмента
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ContactsAdapter.onItemClickListener){
            onItemClickListener = (ContactsAdapter.onItemClickListener)context;//context = MainActivity
        }
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
        //recyclerView.setAdapter(mockAdapter);
        recyclerView.setAdapter(contactsAdapter);
        refreshLayout.setOnRefreshListener(this);//привязка обновлятора к активити
        //mockAdapter.addData(MockGenerator.generate(20));
        contactsAdapter.setListener(onItemClickListener);
    }

    @Override
    public void onDetach() {
        onItemClickListener = null;
        super.onDetach();
    }

    //обработка нажатий на элементы

    @Override
    public void onRefresh() {
//        refreshLayout.postDelayed(new Runnable() { //метод  postDelayed можно применять на любых View-элементах, так же как на хэндлере
//            @Override
//            public void run() {
//
//                //имитация ошибки
//                int error = random.nextInt(4);
//                if (error==0){
//                    showError();
//                }else{
//                    showData();
//                }
//
//                if (refreshLayout.isRefreshing()){ //если колесо обновления крутится, тушим его
//                    refreshLayout.setRefreshing(false);
//                }
//            }
//
//            private void showData() {
//                mockAdapter.addData(MockGenerator.generate(5),true);
//                errorView.setVisibility(View.GONE);
//                recyclerView.setVisibility(View.VISIBLE);
//            }
//
//            private void showError() {
//                errorView.setVisibility(View.VISIBLE);
//                recyclerView.setVisibility(View.GONE);
//            }
//        }, 1000); //задержка

        //запускаем  лоадер
        getLoaderManager().restartLoader(0,null,this);

        if (refreshLayout.isRefreshing()) { //если колесо обновления крутится, тушим его
            refreshLayout.setRefreshing(false);
        }

    }

    //здесь создаем лоадер, система его запустит, обработает запрос и передаст результат в onLoadFinished
    @NonNull
    @Override
    public Loader onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(
                getActivity(), //context
                ContactsContract.Contacts.CONTENT_URI, //ContentProvider
                new String[] {ContactsContract.Contacts._ID,ContactsContract.Contacts.DISPLAY_NAME}, //column of query //projections
                null, //selection
                null,//selection args
                ContactsContract.Contacts._ID //sortOrder
        );
    }

    //result of query failed to here
    //needs to adapter which takes data and gives them to any ViewHolder (MockHolder)
    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        contactsAdapter.swapCursor(data);
    }

    //перезапуск лоадера во время загрузки тут
    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }

}
