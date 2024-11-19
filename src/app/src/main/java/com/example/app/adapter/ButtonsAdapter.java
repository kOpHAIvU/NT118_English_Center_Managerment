package com.example.app.adapter;

import android.view.LayoutInflater;
import com.example.app.R;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ButtonsAdapter extends RecyclerView.Adapter<ButtonsAdapter.ButtonViewHolder> {
    private List<String> buttonTitles;
    private List<Integer> buttonIcons;
    private OnButtonClickListener listener;

    public interface OnButtonClickListener {
        void onButtonClick(int position);
    }

    public ButtonsAdapter(List<String> buttonTitles, List<Integer> buttonIcons, OnButtonClickListener listener) {
        this.buttonTitles = buttonTitles;
        this.buttonIcons = buttonIcons;
        this.listener = listener;
    }

    public static class ButtonViewHolder extends RecyclerView.ViewHolder {
        public Button button;
        public ButtonViewHolder(View view) {
            super(view);
            button = view.findViewById(R.id.menu_login_guest);
        }
    }

    @NonNull
    @Override
    public ButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_menu_login, parent, false);
        return new ButtonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonViewHolder holder, int position) {
        holder.button.setText(buttonTitles.get(position));
        holder.button.setCompoundDrawablesWithIntrinsicBounds(buttonIcons.get(position), 0, 0, 0);

        holder.button.setOnClickListener(v -> {
            listener.onButtonClick(position); // Gọi listener khi nhấn nút
        });
    }

    @Override
    public int getItemCount() {
        return buttonTitles.size();
    }
}
