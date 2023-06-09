package com.example.quizbee.secondscreen;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizbee.databinding.QuestionsItemBinding;
import com.example.quizbee.models.Question;
import com.example.quizbee.models.QuizModule;

import java.util.List;

public class QuizModuleAdapter extends RecyclerView.Adapter<QuizModuleViewHolder> {

    int currentQuestionPosition = 0;

    List<Question> questions;

    OnItemActionListener onItemActionListener;

     void setData(List<Question> questions) {
        this.questions = questions;
        notifyDataSetChanged();
    }

    public void setOnItemActionListener(OnItemActionListener onItemActionListener) {
        this.onItemActionListener = onItemActionListener;
    }

    @NonNull
    @Override
    public QuizModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QuestionsItemBinding binding = QuestionsItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        QuizModuleViewHolder quizModuleViewHolder = new QuizModuleViewHolder(binding);
        return quizModuleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuizModuleViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Question question = questions.get(position);
        holder.binding.questionNumTxt.setText(String.valueOf(position + 1));
        holder.binding.getRoot().setOnClickListener(v -> {
            currentQuestionPosition = position;
            notifyDataSetChanged();
            onItemActionListener.onClick(question);
        });
        if(currentQuestionPosition == position) {
            holder.binding.questionNumTxt.setTextColor(Color.parseColor("#FF5722"));
        } else {
            holder.binding.questionNumTxt.setTextColor(Color.parseColor("#050505"));
        }
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}
