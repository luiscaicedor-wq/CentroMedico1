package com.felipe.myapplication;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetalleAdministrativo extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_consulta);
        TextView titulo = findViewById(R.id.txtTituloDetalle);
        TextView datos = findViewById(R.id.txtDatosDetalle);
        titulo.setText("Detalle Administrativo");
        datos.setText(getIntent().getStringExtra("datos"));
    }
}
