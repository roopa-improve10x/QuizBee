package com.example.quizbee;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.quizbee.models.QuizModule;
import com.example.quizbee.network.QuizBeeApi;
import com.example.quizbee.network.QuizBeeApiService;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void fetchData() throws IOException {
       QuizBeeApiService service = new QuizBeeApi().createQuizBeeService();
       Call<List<QuizModule>> call = service.fetchApiDetails();
       List<QuizModule> quizModules = call.execute().body();
       assertNotNull(quizModules);
       assertFalse(quizModules.isEmpty());
       System.out.println(new Gson().toJson(quizModules));
    }
}