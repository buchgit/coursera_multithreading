package com.example.coursera_multithreading;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.collection.CircularArray;
import androidx.recyclerview.widget.*;

/*
Порядок создания:
1. Подключить в Gradle
implementation 'com.android.support:recyclerview-v7:26.1.0'

2. после инициализации RecyclerView нужно указать ему LayoutManager
mRecyclerView = findViewById(R.id.recyclerView);
mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
mRecyclerView.setAdapter(mAdapter);

3. Создаем класс адаптера. В адаптере наследуемся от RecyclerView.Adapter
public class ListViewAdapter extends RecyclerView.Adapter<ListObjectViewHolder>

4. В адаптере, по умолчанию, нам нужно переопределить всего 3 метода:

onCreateViewHolder -    метод, который вызывается, когда нужно создать ViewHolder
onBindViewHolder -      метод, который вызывается, когда отображается item на определенной позиции.
                        В нем нужно обновлять отображаемые данные
getItemCount -          текущее количество элементов в списке

//Реализация методов:
onCreateViewHolder
    @Override
    public ListObjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      return new ListObjectViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.li_object, parent, false));
    }
onBindViewHolder (делать это в методе bind самого ViewHolder)
    @Override
    public void onBindViewHolder(ListObjectViewHolder holder, int position) {
        holder.bind(mListObjects.get(position));
    }
getItemCount
    @Override
    public int getItemCount() {
      return mListObjects.size();
    }

5. создать класс ViewHolder

*/

public class MainActivity extends AppCompatActivity {

    private static RecyclerView mRecyclerView;
    private static ListViewAdapter mAdapter;
    private static CircularArray<ListObject> listObjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fillListObjects();



        mRecyclerView = findViewById(R.id.rec_view);
        //mRecyclerView.setHasFixedSize(true);

        //добавляем полоску-разделитель между строчками
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));  //в один столбик
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        //mRecyclerView.setLayoutManager(new GridLayoutManager(this,GridLayoutManager.VERTICAL));

        mAdapter = new ListViewAdapter();
        mAdapter.setmListObjects(listObjects);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void fillListObjects() {
        listObjects = new CircularArray<>();
        listObjects.addFirst(new ListObject("number 1", R.drawable.android_1));
        listObjects.addFirst(new ListObject("number 2", R.drawable.android_2));
        listObjects.addFirst(new ListObject("number 3", R.drawable.android_3));
        listObjects.addFirst(new ListObject("number 4", R.drawable.android_4));
        listObjects.addFirst(new ListObject("number 6", R.drawable.android_6));
        listObjects.addFirst(new ListObject("number 7", R.drawable.android_7));
        listObjects.addFirst(new ListObject("number 8", R.drawable.android_8));
        listObjects.addFirst(new ListObject("number 9", R.drawable.android_9));
        listObjects.addFirst(new ListObject("number 10", R.drawable.android_10));
        listObjects.addFirst(new ListObject("number 11", R.drawable.android_11));
        listObjects.addFirst(new ListObject("number 12", R.drawable.android_12));
        listObjects.addFirst(new ListObject("number 13", R.drawable.android_13));
        listObjects.addFirst(new ListObject("number 14", R.drawable.android_14));
        listObjects.addFirst(new ListObject("number 15", R.drawable.android_15));
    }
}


/*
RecyclerView может содержать в себе разные ViewHolder
 */

