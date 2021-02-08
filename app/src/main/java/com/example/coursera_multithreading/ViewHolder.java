package com.example.coursera_multithreading;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

class ListObjectViewHolder extends RecyclerView.ViewHolder {
    TextView tvText;
    ImageView ivIcon;
    Button button;

    public ListObjectViewHolder(View itemView) {
        super(itemView);
        tvText = itemView.findViewById(R.id.tvText);
        ivIcon = itemView.findViewById(R.id.ivIcon);
        button = itemView.findViewById(R.id.button);
    }

    public void bind(final ListObject item) {
        tvText.setText(item.getText());
        ivIcon.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), item.getIcon()));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), item.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
