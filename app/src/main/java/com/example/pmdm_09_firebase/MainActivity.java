package com.example.pmdm_09_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText txtFrase;
    private TextView lblFrase;
    private Button btnSave;

    private ArrayList<Persona> personas;

    private FirebaseDatabase database;

    private DatabaseReference refFrase;
    private DatabaseReference refPersona;
    private DatabaseReference refPersonas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtFrase = findViewById(R.id.txtFrase);
        lblFrase = findViewById(R.id.lblFrase);
        btnSave = findViewById(R.id.btnSave);

        personas = new ArrayList<>();
        crearPersonas();

        //si no encuentra la url de la base de datos: accedes a realtime database en la consola de firebase, copias la url del proyecto y se lo copias al get instance
        database = FirebaseDatabase.getInstance("https://pmdm-09-firebase-default-rtdb.europe-west1.firebasedatabase.app");

        //Inicializaciones
        refFrase = database.getReference("frase"); //puedes sin nada o con varios poniendo / frase/frase
        refPersona= database.getReference("persona");
        refPersonas = database.getReference("personas");




        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Escribir (y guardarlo en firebase como nodo en la base de datos)
                refFrase.setValue(txtFrase.getText().toString());
                //para hacer el objeto
                int random= (int)(Math.random()*100);
                Persona p= new Persona(txtFrase.getText().toString(), random);
                refPersona.setValue(p);

                refPersonas.setValue(personas);
            }
        });



        //Leer
        refPersonas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Necesitaremos un generic cuando traigamos un conjunto de objetos,
                // en el generic no se hace ninguna de las clases que nos dicen
                //si quisieramos borrar algo del arraylist no se haría desde el firebase, se cambiaria el arraylist y lo vuelves a subir
                if(snapshot.exists()){
                    GenericTypeIndicator<ArrayList<Persona>> gti = new GenericTypeIndicator<ArrayList<Persona>>() {};
                    ArrayList<Persona> lista = snapshot.getValue(gti);
                    Toast.makeText(MainActivity.this, "Descargados: "+lista.size(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        refPersona.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Persona p = snapshot.getValue(Persona.class);
                    Toast.makeText(MainActivity.this, p.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        refFrase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //asegurate de que el snapshot existe!!!
                if(snapshot.exists()){
                    String frase = snapshot.getValue(String.class); //tienes que castearlo porque no sabe que tipo de valor le das
                    lblFrase.setText(frase);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void crearPersonas() {
        for (int i = 0; i <1000; i++) {
            personas.add(new Persona("Persona "+1,(int)(Math.random()*100)));
        }
    }
}