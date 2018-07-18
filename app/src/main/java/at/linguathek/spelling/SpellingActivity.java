package at.linguathek.spelling;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import at.linguathek.spelling.Data.Statement;
import at.linguathek.spelling.Data.Task;

import static android.view.View.GONE;

public class SpellingActivity extends AppCompatActivity {

    TextView letter1, letter2, letter3, letter4, letter5, letter6, letter7, letter8, answer1, answer2, answer3, answer4, answer5, answer6, answer7, answer8;
    String TAG;
    String rightA1, rightA2, rightA3, rightA4, rightA5, rightA6, rightA7, rightA8;
    int quizCount;

    String[] answerString1 = {"A", "B", "C", "D", "E", "F", "G", "H"};
    String[] answerString2 = {"A", "B", "C", "D", "E"};
    String[] answerString3 = {"A", "B", "C", "D", "E", "F"};
    String[] quizArray = {};

    Task spellingQuizTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelling);


        letter1 = findViewById(R.id.letter1TV);
        letter2 = findViewById(R.id.letter2TV);
        letter3 = findViewById(R.id.letter3TV);
        letter4 = findViewById(R.id.letter4TV);
        letter5 = findViewById(R.id.letter5TV);
        letter6 = findViewById(R.id.letter6TV);
        letter7 = findViewById(R.id.letter7TV);
        letter8 = findViewById(R.id.letter8TV);
        answer1 = findViewById(R.id.answer1TV);
        answer2 = findViewById(R.id.answer2TV);
        answer3 = findViewById(R.id.answer3TV);
        answer4 = findViewById(R.id.answer4TV);
        answer5 = findViewById(R.id.answer5TV);
        answer6 = findViewById(R.id.answer6TV);
        answer7 = findViewById(R.id.answer7TV);
        answer8 = findViewById(R.id.answer8TV);

        letter1.setOnDragListener(new ChoiceDragListener());
        letter2.setOnDragListener(new ChoiceDragListener());
        letter3.setOnDragListener(new ChoiceDragListener());
        letter4.setOnDragListener(new ChoiceDragListener());
        letter5.setOnDragListener(new ChoiceDragListener());
        letter6.setOnDragListener(new ChoiceDragListener());
        letter7.setOnDragListener(new ChoiceDragListener());
        letter8.setOnDragListener(new ChoiceDragListener());
        answer1.setOnDragListener(new ChoiceDragListener());
        answer2.setOnDragListener(new ChoiceDragListener());
        answer3.setOnDragListener(new ChoiceDragListener());
        answer4.setOnDragListener(new ChoiceDragListener());
        answer5.setOnDragListener(new ChoiceDragListener());
        answer6.setOnDragListener(new ChoiceDragListener());
        answer7.setOnDragListener(new ChoiceDragListener());
        answer8.setOnDragListener(new ChoiceDragListener());

        letter1.setOnTouchListener(new ChoiceTouchListener());
        letter2.setOnTouchListener(new ChoiceTouchListener());
        letter3.setOnTouchListener(new ChoiceTouchListener());
        letter4.setOnTouchListener(new ChoiceTouchListener());
        letter5.setOnTouchListener(new ChoiceTouchListener());
        letter6.setOnTouchListener(new ChoiceTouchListener());
        letter7.setOnTouchListener(new ChoiceTouchListener());
        letter8.setOnTouchListener(new ChoiceTouchListener());
        answer1.setOnTouchListener(new ChoiceTouchListener());
        answer2.setOnTouchListener(new ChoiceTouchListener());
        answer3.setOnTouchListener(new ChoiceTouchListener());
        answer4.setOnTouchListener(new ChoiceTouchListener());
        answer5.setOnTouchListener(new ChoiceTouchListener());
        answer6.setOnTouchListener(new ChoiceTouchListener());
        answer7.setOnTouchListener(new ChoiceTouchListener());
        answer8.setOnTouchListener(new ChoiceTouchListener());

        loadData();
        resetGame();
        newGame();


    }

    private final class ChoiceTouchListener implements View.OnTouchListener {
        @SuppressLint("NewApi")
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            } else {
                return false;
            }
        }
    }

    @SuppressLint("NewApi")
    private class ChoiceDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:

                    //handle the dragged view being dropped over a drop view
                    View view = (View) event.getLocalState();
                    //view dragged item is being dropped on
                    TextView dropTarget = (TextView) v;
                    //view being dragged and dropped
                    TextView dropped = (TextView) view;

                    String temp = dropped.getText().toString();
                    dropped.setText(dropTarget.getText().toString());
                    dropTarget.setText(temp);
                    //Toast.makeText(SpellingActivity.this, "Changed", Toast.LENGTH_SHORT).show();

                    checkAnswer();

                    //Toast.makeText(getApplicationContext(), dropTarget.getText().toString() + "is not " + dropped.getText().toString(), Toast.LENGTH_LONG).show();
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    break;
                default:
                    break;
            }
            return true;
        }
    }

    private void checkAnswer() {
        //Toast.makeText(this, "checking Answer", Toast.LENGTH_SHORT).show();
        if (((answer1.getVisibility() == GONE) || (answer1.getText().toString().equals(rightA1))) &&
                ((answer2.getVisibility() == GONE) || (answer2.getText().toString().equals(rightA2))) &&
                ((answer3.getVisibility() == GONE) || (answer3.getText().toString().equals(rightA3))) &&
                ((answer4.getVisibility() == GONE) || (answer4.getText().toString().equals(rightA4))) &&
                ((answer5.getVisibility() == GONE) || (answer6.getText().toString().equals(rightA5))) &&
                ((answer6.getVisibility() == GONE) || (answer6.getText().toString().equals(rightA6))) &&
                ((answer7.getVisibility() == GONE) || (answer7.getText().toString().equals(rightA7))) &&
                ((answer8.getVisibility() == GONE) || (answer8.getText().toString().equals(rightA8)))) {
            Toast.makeText(this, "Hooray!!", Toast.LENGTH_SHORT).show();
            resetGame();
            newGame();
        } else {
            Toast.makeText(this, "Nope", Toast.LENGTH_SHORT).show();
        }
    }

    private void newGame() {
        /*Random rand = new Random();
        int n = rand.nextInt(3) + 1;
        Toast.makeText(this, "number " + n, Toast.LENGTH_SHORT).show();
        switch (n) {
            case 1:
                quizArray = answerString1;
                break;
            case 2:
                quizArray = answerString2;
                break;
            case 3:
                quizArray = answerString3;
                break;
        }
        List<String> myArray = new ArrayList<>(Arrays.asList(quizArray));
        Collections.shuffle(myArray);
*/
        Random random = new Random();
        int randomNum = random.nextInt(spellingQuizTask.getStatements().size());
        Statement spellingQuiz = spellingQuizTask.getStatements().get(randomNum);

        List<String> rightAnswersArray = new ArrayList<>();
        rightAnswersArray.addAll(Arrays.asList(spellingQuiz.getRightAnswers()));
        try {
            rightA1 = rightAnswersArray.get(0);
        } catch (Exception e) {
        }

        try {
            rightA2 = rightAnswersArray.get(1);
        } catch (Exception e) {
        }

        try {
            rightA3 = rightAnswersArray.get(2);
        } catch (Exception e) {
        }
        try {
            rightA4 = rightAnswersArray.get(3);
        } catch (Exception e) {
        }
        try {
            rightA5 = rightAnswersArray.get(4);
        } catch (Exception e) {
        }
        try {
            rightA6 = rightAnswersArray.get(5);
        } catch (Exception e) {
        }
        try {
            rightA7 = rightAnswersArray.get(6);
        } catch (Exception e) {
        }
        try {
            rightA8 = rightAnswersArray.get(7);
        } catch (Exception e) {
        }
        Toast.makeText(this, "huhhu" + rightA1 + rightA2 + rightA3 + rightA4 + rightA5 + rightA6 + rightA7 + rightA8, Toast.LENGTH_SHORT).show();
        try {
            letter1.setText((rightAnswersArray.get(0)));
        } catch (Exception e) {
            letter1.setVisibility(GONE);
            answer1.setVisibility(GONE);
        }

        try {
            letter2.setText((rightAnswersArray.get(1)));
        } catch (Exception e) {
            letter2.setVisibility(GONE);
            answer2.setVisibility(GONE);
        }

        try {
            letter3.setText((rightAnswersArray.get(2)));
        } catch (Exception e) {
            letter3.setVisibility(GONE);
            answer3.setVisibility(GONE);
        }

        try {
            letter4.setText((rightAnswersArray.get(3)));
        } catch (Exception e) {
            letter4.setVisibility(GONE);
            answer4.setVisibility(GONE);
        }

        try {
            letter5.setText((rightAnswersArray.get(4)));
        } catch (Exception e) {
            letter5.setVisibility(GONE);
            answer5.setVisibility(GONE);
        }

        try {
            letter6.setText((rightAnswersArray.get(5)));
        } catch (Exception e) {
            letter6.setVisibility(GONE);
            answer6.setVisibility(GONE);
        }

        try {
            letter7.setText((rightAnswersArray.get(6)));
        } catch (Exception e) {
            letter7.setVisibility(GONE);
            answer7.setVisibility(GONE);
        }

        try {
            letter8.setText((rightAnswersArray.get(7)));
        } catch (Exception e) {
            letter8.setVisibility(GONE);
            answer8.setVisibility(GONE);
        }


    }

    private void resetGame() {
        answer1.setVisibility(View.VISIBLE);
        answer1.setText("_");
        answer2.setVisibility(View.VISIBLE);
        answer2.setText("_");
        answer3.setVisibility(View.VISIBLE);
        answer3.setText("_");
        answer4.setVisibility(View.VISIBLE);
        answer4.setText("_");
        answer5.setVisibility(View.VISIBLE);
        answer5.setText("_");
        answer6.setVisibility(View.VISIBLE);
        answer6.setText("_");
        answer7.setVisibility(View.VISIBLE);
        answer7.setText("_");
        answer8.setVisibility(View.VISIBLE);
        answer8.setText("_");
        letter1.setVisibility(View.VISIBLE);
        letter2.setVisibility(View.VISIBLE);
        letter3.setVisibility(View.VISIBLE);
        letter4.setVisibility(View.VISIBLE);
        letter5.setVisibility(View.VISIBLE);
        letter6.setVisibility(View.VISIBLE);
        letter7.setVisibility(View.VISIBLE);
        letter8.setVisibility(View.VISIBLE);
    }

    private void loadData() {
        Resources res = getResources();
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(res.openRawResource(R.raw.spelling)))) {
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException ioe) {
            Log.e("TAG", "Fehler beim Lesen der JSON Datei: " + ioe.getMessage());
        }
        spellingQuizTask = parseJson(builder.toString());
        List<Statement> filteredStatements = new ArrayList<>();
        for (Statement s : spellingQuizTask.getStatements()) {
            filteredStatements.add(s);
        }
        spellingQuizTask.setStatements(filteredStatements);
        Log.e("TAG", "" + spellingQuizTask);
    }

    private Task parseJson(String s) {
        Gson gson = new Gson();
        Task quizTaskFromExternal = gson.fromJson(s, Task.class);
        return quizTaskFromExternal;
    }
}
