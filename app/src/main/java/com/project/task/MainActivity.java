package com.project.task;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    double height  , restitution ;
    ArrayList<Double> point;
    EditText inputHeight , inputRest ;
    Button button;
    ProgressBar progressBar;
    GraphView graph;
    List<DataPoint> dataPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        graph = (GraphView) findViewById(R.id.graph);
        button = findViewById(R.id.plot);
        inputHeight = findViewById(R.id.height);
        inputRest = findViewById(R.id.restitution);
        progressBar = findViewById(R.id.progressBar);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                graph.removeAllSeries();
                validateValues();

            }
        });
    }

    private  void calculatePoints(double h , double r){

        dataPoints = new ArrayList<>();
        int n=0;
        while(h > 1) {


            dataPoints.add(new DataPoint( n , h));
            dataPoints.add(new DataPoint(n+1 , 0));
            h = r * r * h;

            n++;

        }

        showGraph();

    }

    private  void validateValues(){
        if(inputHeight.getText().length()!= 0){

            if(inputRest.getText().length()!= 0){

                if (Double.parseDouble(inputRest.getText().toString()) <= 1){
                    height = Double.parseDouble(inputHeight.getText().toString());
                    restitution = Double.parseDouble(inputRest.getText().toString());
                    progressBar.setVisibility(View.VISIBLE);
                    calculatePoints(height,restitution);


                }
                else {
                    Toast.makeText(getApplicationContext(),"Restitution value must be between 0 to 1",Toast.LENGTH_LONG).show();
                }


            }
            else {
                Toast.makeText(getApplicationContext(),"Invalid value for restitution",Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(getApplicationContext(),"Invalid value for height",Toast.LENGTH_LONG).show();
        }
    }

    private  void showGraph(){


        progressBar.setVisibility(View.INVISIBLE);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints.toArray(new DataPoint[dataPoints.size()]));
        graph.addSeries(series);
        

    }
}
