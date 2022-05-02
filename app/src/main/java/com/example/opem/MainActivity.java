package com.example.opem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    TextRecognizer txtr= TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
    Button rec;
    ImageView img;
    InputImage recImage;
    protected static final int actionResu=1;
    ArrayList<String> recRe=new ArrayList<>();
    TextView rectxt;
    String tee;

    void getImg(){//this devil is the method
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"choose an image"),actionResu);

    }
//result ok is a preserved number
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&&requestCode==actionResu){
            img.setImageURI(data.getData());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rec=findViewById(R.id.rec);
        img= findViewById(R.id.img);
        rectxt=findViewById(R.id.recTxt);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImg();
            }
        });
        rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapDrawable drawable =(BitmapDrawable) img.getDrawable();
                Bitmap bit=drawable.getBitmap();
                recImage=InputImage.fromBitmap(bit,0);
                Task<Text> result = txtr.process(recImage).addOnSuccessListener(new OnSuccessListener<Text>() {
                    @Override
                    public void onSuccess(@NonNull Text text) {
                        Toast.makeText(getApplicationContext(),"Task completed SUCCESSFULLY",Toast.LENGTH_LONG).show();
                        rectxt.setText(text.getText().toString());



                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
                //rectxt.setText(result.getResult().toString());

            }
        });
    }
}