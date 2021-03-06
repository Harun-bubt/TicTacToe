package sub.game.tictactoe;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    private boolean player1Turn = true;

    private int roundCount;

    private int player1Points;
    private int player2Points;
    TextView infoTextView;
    boolean firstMove = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        infoTextView = findViewById(R.id.infoText);


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        if(firstMove)
        {

            firstMove =  false;
            resetBoard();
            infoTextView.setText("Game Running");
        }

        /*if (player1Turn) {
            ((Button) v).setText("X");

        } else {
           // ((Button) v).setText("O");

        }*/
        ((Button) v).setText("X");

        roundCount++;

        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
                    computerMove();
                }
            }, 500);


        }

    }
    public void computerMove()
    {
        while(true)
        {
            int i =  new Random().nextInt(3);
            int j =  new Random().nextInt(3);
            String status = buttons[i][j].getText().toString();
            if(status.equals("X") || status.equals("O"))
            {
                continue;

            }else
            {
                buttons[i][j].setText("O");
                break;
            }

        }
        roundCount++;

        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
           player1Turn = !player1Turn;
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                buttons[i][0].setTextColor(Color.parseColor("#FF0000"));
                buttons[i][1].setTextColor(Color.parseColor("#FF0000"));
                buttons[i][2].setTextColor(Color.parseColor("#FF0000"));
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                buttons[0][i].setTextColor(Color.parseColor("#FF0000"));
                buttons[1][i].setTextColor(Color.parseColor("#FF0000"));
                buttons[2][i].setTextColor(Color.parseColor("#FF0000"));
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            buttons[0][0].setTextColor(Color.parseColor("#FF0000"));
            buttons[1][1].setTextColor(Color.parseColor("#FF0000"));
            buttons[2][2].setTextColor(Color.parseColor("#FF0000"));
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            buttons[0][2].setTextColor(Color.parseColor("#FF0000"));
            buttons[1][1].setTextColor(Color.parseColor("#FF0000"));
            buttons[2][0].setTextColor(Color.parseColor("#FF0000"));
            return true;
        }

        return false;
    }

    private void player1Wins() {
        player1Points++;
        infoTextView.setText("You Win");
        updatePointsText();
        //resetBoard();
        firstMove = true;
    }

    private void player2Wins() {
        player2Points++;
        infoTextView.setText("Computer Win");
        updatePointsText();
        //resetBoard();
        firstMove = true;
    }

    private void draw() {
        infoTextView.setText("Draw");
        //resetBoard();
        firstMove = true;
    }

    private void updatePointsText() {

    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setTextColor(Color.parseColor("#FFFFFF"));
            }
        }

        roundCount = 0;
        player1Turn = true;
        infoTextView.setText("Start Playing");
    }

    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }

}