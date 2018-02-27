package tdm2018.ittepic.edu.tdm2018_u2_22_sqlite_bdinterna;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Agregar extends AppCompatActivity {

    final int REQUEST_CODE_GALLERY = 999;
    public static int idusuario;
    EditText nombre,email,telefono;
    Button guardar;
    SQLite dbms;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        dbms = new SQLite(this, "BASE2", null,1);
        nombre=(EditText)findViewById(R.id.etNombre);
        email=(EditText)findViewById(R.id.etCorreo);
        telefono=(EditText)findViewById(R.id.etCelular);
        guardar=(Button)findViewById(R.id.btAgregar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarDatos();
            }
        });

    }
    public void insertarDatos(){
        try{
            if(nombre !=null){
                dbms.insertDataUser(
                        nombre.getText().toString().trim(),
                        email.getText().toString().trim(),
                        telefono.getText().toString().trim()
                );
                Toast.makeText(this, "Se agrego el contacto", Toast.LENGTH_LONG).show();
                asignarIdUsuario();
                Intent ventanaEstudiante2 = new Intent(Agregar.this,MainActivity.class);
                startActivity(ventanaEstudiante2);
            }else{mensajes("alerta","no debe esta vacio ningun campo ni calendario");}
        }catch (SQLException e){
            mensajes("Sucedio un error",e.getMessage());
        }
    }

    public void asignarIdUsuario(){
        try {

            SQLiteDatabase tabla = dbms.getReadableDatabase();
            String SQL = "SELECT * FROM USUARIO WHERE NOMBRE = '"+nombre.getText().toString()+"'";
            Cursor resultado = tabla.rawQuery(SQL,null);
            if(resultado.moveToFirst()==true){
                idusuario = resultado.getInt(0);
            }

        }catch (Exception e){
            mensajes("error",e.getMessage());
        }
    }

    private void mensajes(String titulo, String mensaje){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle(titulo).setMessage(mensaje);
        alerta.show();
    }

}


