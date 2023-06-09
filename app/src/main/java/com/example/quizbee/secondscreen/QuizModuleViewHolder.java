package com.example.quizbee.secondscreen;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizbee.databinding.QuestionsItemBinding;

public class QuizModuleViewHolder extends RecyclerView.ViewHolder {

    QuestionsItemBinding binding;

    public QuizModuleViewHolder(QuestionsItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
