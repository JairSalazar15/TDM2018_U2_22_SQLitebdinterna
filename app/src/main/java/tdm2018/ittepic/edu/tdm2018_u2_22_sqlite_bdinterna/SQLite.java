package tdm2018.ittepic.edu.tdm2018_u2_22_sqlite_bdinterna;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by Jair on 27/02/2018.
 */

public class SQLite extends SQLiteOpenHelper {
    public SQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sq) {
        sq.execSQL("CREATE TABLE USUARIO(IDUSUARIO INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE VARCHAR(200) , EMAIL VARCHAR(200), TELEFONO VARCHAR(200))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void insertDataUser(String nombre, String email,String telefono){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO USUARIO (nombre, email,telefono) VALUES (? , ? , ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, nombre);
        statement.bindString(2, email);
        statement.bindString(3, telefono);
        statement.executeInsert();

    }


    public void updateDataUser(String actual, String nombre, String email,String telefono){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE USUARIO SET NOMBRE = ?, EMAIL = ? ,TELEFONO = ? WHERE  NOMBRE ='"+actual+"'";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, nombre);
        statement.bindString(2, email);
        statement.bindString(3, telefono);
        statement.executeInsert();
    }

}
