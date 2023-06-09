package com.example.quizbee.secondscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.quizbee.ResultActivity;
import com.example.quizbee.databinding.ActivityQuestionsBinding;
import com.example.quizbee.models.Question;
import com.example.quizbee.models.QuizModule;
import com.example.quizbee.network.QuizBeeApi;
import com.example.quizbee.network.QuizBeeApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionsActivity extends AppCompatActivity {
    ActivityQuestionsBinding binding;
    ArrayList<Question> questionsList = new ArrayList<>();
    QuizModuleAdapter quizModuleAdapter;
    int currentQuestionNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("QuizBee");
        fetchData();
        setQuizModuleAdapter();
        setUpQuestionsRv();
        handleNextBtn();
        handlePreviousBtn();
        submitButton();
    }

    public void setQuizModuleAdapter() {
        quizModuleAdapter = new QuizModuleAdapter();
        quizModuleAdapter.setData(questionsList);
        quizModuleAdapter.setOnItemActionListener(new OnItemActionListener() {
            @Override
            public void onClick(Question question) {
                fetchQuestionsData(question);
            }
        });
    }

    public void setUpQuestionsRv() {
        binding.questionsRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.questionsRv.setAdapter(quizModuleAdapter);
    }

    public void fetchData() {
        QuizBeeApi api = new QuizBeeApi();
        QuizBeeApiService service = api.createQuizBeeService();
        Call<List<QuizModule>> call = service.fetchApiDetails();
        call.enqueue(new Callback<List<QuizModule>>() {
            @Override
            public void onResponse(Call<List<QuizModule>> call, Response<List<QuizModule>> response) {
                List<QuizModule> quizModules = response.body();
                quizModuleAdapter.setData(quizModules.get(0).getQuestions());
                questionsList = quizModules.get(0).getQuestions();
                fetchQuestionsData(questionsList.get(0));
            }

            @Override
            public void onFailure(Call<List<QuizModule>> call, Throwable t) {
                Toast.makeText(QuestionsActivity.this, "Failed to load the data", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void handleNextBtn() {
        binding.nextBtn.setOnClickListener(v -> {
                currentQuestionNumber = quizModuleAdapter.currentQuestionPosition;
                currentQuestionNumber++;
                if (currentQuestionNumber == questionsList.size() - 1) {
                    binding.nextBtn.setVisibility(View.GONE);
                    binding.submitBtn.setVisibility(View.VISIBLE);
                } else {
                    binding.nextBtn.setVisibility(View.VISIBLE);
                    binding.submitBtn.setVisibility(View.GONE);
                }
                Question question = questionsList.get(currentQuestionNumber);
                fetchQuestionsData(question);
                quizModuleAdapter.currentQuestionPosition = currentQuestionNumber;
                quizModuleAdapter.notifyDataSetChanged();
        });
    }

    private void handlePreviousBtn() {
        binding.previousBtn.setOnClickListener(v -> {
            try {
                currentQuestionNumber = quizModuleAdapter.currentQuestionPosition;
                currentQuestionNumber--;
                if (currentQuestionNumber == questionsList.size()   ) {
                    binding.previousBtn.setVisibility(View.GONE);
                } else {
                    binding.previousBtn.setVisibility(View.VISIBLE);
                }
                Question question = questionsList.get(currentQuestionNumber);
                fetchQuestionsData(question);
                quizModuleAdapter.currentQuestionPosition = currentQuestionNumber;
                quizModuleAdapter.notifyDataSetChanged();

            } catch (Exception exception) {
                Toast.makeText(this, "There is no questions", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void submitButton() {
        binding.submitBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, ResultActivity.class);
            startActivity(intent);
        });
    }

    public void fetchQuestionsData(Question question) {
        binding.questionTxt.setText(question.getQuestion());
        binding.radioBtnOne.setText(question.getAnswers().get(0));
        binding.radioBtnTwo.setText(question.getAnswers().get(1));
        binding.radioBtnThree.setText(question.getAnswers().get(2));
        binding.radioBtnFour.setText(question.getAnswers().get(3));
    }
}