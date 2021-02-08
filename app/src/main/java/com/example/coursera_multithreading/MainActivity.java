package com.example.coursera_multithreading;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

}

/*
RecyclerView может содержать в себе разные ViewHolder
 */

