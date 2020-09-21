package com.example.webservicesimagealdo.principal;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.webservicesimagealdo.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LOGIN extends AppCompatActivity implements View.OnClickListener {
  private  Typeface california;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogin =(Button)findViewById(R.id.btnLogin);
        final EditText usuarioT =(EditText)findViewById(R.id.Usuario);
        final EditText claveT =(EditText)findViewById(R.id.claveUsuario);
        TextView etqUsuario=(TextView)findViewById(R.id.etqUsuario);
        TextView etqPass=(TextView) findViewById(R.id.etqpass);
        Button btnLimpiar=(Button)findViewById(R.id.btnCancelar) ;
        String fuente1 ="fuentes/california.ttf";
        this.california= Typeface.createFromAsset(getAssets(),fuente1);
        etqUsuario.setTypeface(california);
        etqPass.setTypeface(california);
        btnLogin.setOnClickListener(this);
btnLimpiar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        CancelarLimpiarCajas();
    }
});
    }

    @Override
    public void onClick(View v) {

        Button btnLogin =(Button)findViewById(R.id.btnLogin);
        final EditText usuarioT =(EditText)findViewById(R.id.Usuario);
        final EditText claveT =(EditText)findViewById(R.id.claveUsuario);
        final String usuario = usuarioT.getText().toString();
        final String clave = claveT.getText().toString();
        Response.Listener<String> respuesta = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonRespuesta = new JSONObject(response);
                    boolean ok = jsonRespuesta.getBoolean("success");
                    if (ok==true)
                    {
                        String nombre = jsonRespuesta.getString("nombre");
                        String permisos = jsonRespuesta.getString("permisos");
                        String usuario=jsonRespuesta.getString("usuario");
                        String status=jsonRespuesta.getString("status");
                        Intent bienvenido = new Intent(LOGIN.this, MainActivity.class);
                        Intent bienvenido2 = new Intent(LOGIN.this, Main2Activity.class);
if(status.equals("Habilitado"))
{
    if(permisos.equals("Administrador"))
    {
        bienvenido.putExtra("nombre",nombre);
        bienvenido.putExtra("usuario",usuario);
        bienvenido.putExtra("permisos",permisos);
        LOGIN.this.startActivity(bienvenido);
        LOGIN.this.finish();
        //imprimirMensaje("Eres Administrador");
    }
    else if(permisos.equals("Usuario"))
    {
        try
        {
            imprimirMensaje("Eres Usuario");
            bienvenido2.putExtra("nombre",nombre);
            bienvenido2.putExtra("usuario",usuario);
            bienvenido2.putExtra("permisos",permisos);
            LOGIN.this.startActivity(bienvenido2);
            LOGIN.this.finish();
        }
        catch (Exception ex)
        {
            imprimirMensaje("Ha ocurrido un error"+ex.toString());

        }

    }
    else {
        imprimirMensaje("No tiene Privilegios");
    }
}
else if(status.equals("Deshabilitado"))
{
    imprimirMensaje("Es un Usuario Deshabilitado, Contacte al Administrador");
}
else {

    imprimirMensaje("No es un Usuario Activo o no Existe");
}


                    }else {
                        AlertDialog.Builder alerta=new AlertDialog.Builder(LOGIN.this);
                        alerta.setMessage("Fallo en el Login")
                                .setNegativeButton("Reintentar",null)
                                .create()
                                .show();

                    }

                }catch (JSONException e){
                    e.getMessage();

                }
            }
        };
        LoginRequest r = new LoginRequest(usuario,clave,respuesta);
        RequestQueue cola = Volley.newRequestQueue(LOGIN.this);
        cola.add(r);
    }

    public void CancelarLimpiarCajas()
    {
         final EditText usuarioT =(EditText)findViewById(R.id.Usuario);
        final EditText claveT =(EditText)findViewById(R.id.claveUsuario);
        usuarioT.setText("");
        claveT.setText("");
    }

    public void imprimirMensaje(String msj)
    {
        Toast.makeText(getBaseContext(),msj,Toast.LENGTH_LONG).show();
    }
}
