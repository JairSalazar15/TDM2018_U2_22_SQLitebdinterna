package tdm2018.ittepic.edu.tdm2018_u2_22_sqlite_bdinterna;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Actualizar extends AppCompatActivity {
    public static int idusuario,varafi;
    DatePickerDialog.OnDateSetListener date;
    EditText nombre,email,telefono;

    String nombreact;
    Button actualizar,eliminar;
    SQLITE2 dbms;
    Integer var;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar);
        var = MainActivity.variable;
        dbms = new SQLITE2(this, "BASE2", null,1);

        nombre=(EditText)findViewById(R.id.etNombre2);
        email=(EditText)findViewById(R.id.etCorreo2);
        telefono=(EditText)findViewById(R.id.etCelular2);
        actualizar=(Button)findViewById(R.id.btActualizar);
        eliminar=(Button)findViewById(R.id.btEliminar);

        buscarConcepto();
        nombreact = nombre.getText().toString();

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarDatos();
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarDatos();
            }
        });
    }

    private void buscarConcepto(){
        try{
            SQLiteDatabase tabla = dbms.getReadableDatabase();
            String SQL = "SELECT * FROM USUARIO ORDER BY NOMBRE ASC";
            Cursor resultado = tabla.rawQuery(SQL,null);
            if(resultado.moveToFirst()==true){
                for(int i=0;i<= var;i++){
                    idusuario = resultado.getInt(0);
                    nombre.setText(resultado.getString(1));
                    email.setText(resultado.getString(2));
                    telefono.setText(resultado.getString(3));
                    resultado.moveToNext();
                }
            } else
                tabla. close();
        }catch (SQLiteException e){
            mensajes("Error en busqueda",e.getMessage());
        }
    }

    public void actualizarDatos(){
        try{
            dbms.updateDataUser(
                    nombreact.toString(),
                    nombre.getText().toString().trim(),
                    email.getText().toString().trim(),
                    telefono.getText().toString().trim()
            );
            Toast.makeText(this, "Se actualizo el contacto", Toast.LENGTH_LONG).show();
            Intent ventanaEstudiante = new Intent(Actualizar.this,MainActivity.class);
            startActivity(ventanaEstudiante);

        }catch (SQLException e){
            mensajes("Sucedio un error",e.getMessage());
        }
    }

    public void eliminarDatos(){
        try{
            SQLiteDatabase tabla = dbms.getWritableDatabase();
            String SQL = "DELETE FROM USUARIO WHERE NOMBRE= '"+nombre.getText().toString()+"'";
            tabla.execSQL(SQL);
            tabla.close();
            Toast.makeText(this, "Contacto eliminado", Toast.LENGTH_LONG).show();
            Intent ventanaEstudiante = new Intent(Actualizar.this,MainActivity.class);
            startActivity(ventanaEstudiante);

        }catch (SQLiteException e) {
            mensajes("ERROR",e.getMessage());
        }
    }

    private void mensajes(String titulo, String mensaje){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle(titulo).setMessage(mensaje);
        alerta.show();
    }

}

