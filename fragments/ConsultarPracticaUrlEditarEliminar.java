package com.example.webservicesimagealdo.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.Image;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.webservicesimagealdo.R;
import com.example.webservicesimagealdo.entidades.Practica;
import com.example.webservicesimagealdo.entidades.Usuario;
import com.example.webservicesimagealdo.entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConsultarPracticaUrlEditarEliminar.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConsultarPracticaUrlEditarEliminar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConsultarPracticaUrlEditarEliminar extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private final int MIS_PERMISOS = 100;
    private static final int COD_SELECCIONA = 10;
    private static final int COD_FOTO = 20;

    private static final String CARPETA_PRINCIPAL = "misImagenesApp/";
    private static final String CARPETA_IMAGEN = "imagenes";
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;
    private String path;
    File fileImagen;
    Bitmap bitmap;
    JsonObjectRequest jsonObjectRequest;
    StringRequest stringRequest;
EditText Campodocumento,CampoId,CampoUsuario,CampoExperimento,CampoProtolo,CampoObservacion,CampoResultados,CampoConclusion,CampoHorayFecha,CampoIdImagen;
    Spinner spinnerstatus;
    ProgressDialog pDialog;
    ImageButton btnConsultar;
    ImageView campoImagen;
    Button btnActualizar, btnEliminar,btnFoto;

    public ConsultarPracticaUrlEditarEliminar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConsultarPracticaUrlEditarEliminar.
     */
    // TODO: Rename and change types and number of parameters
    public static ConsultarPracticaUrlEditarEliminar newInstance(String param1, String param2) {
        ConsultarPracticaUrlEditarEliminar fragment = new ConsultarPracticaUrlEditarEliminar();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista= inflater.inflate(R.layout.fragment_consultar_practica_url_editar_eliminar, container, false);
Campodocumento=(EditText)vista.findViewById(R.id.txtDocumento);
CampoUsuario=(EditText)vista.findViewById(R.id.txtUsuario);
CampoExperimento=(EditText)vista.findViewById(R.id.txtExperimento);
CampoProtolo=(EditText)vista.findViewById(R.id.txtProtocolo);
CampoObservacion=(EditText)vista.findViewById(R.id.txtObservacion);
CampoResultados=(EditText)vista.findViewById(R.id.txtResultados);
CampoConclusion=(EditText)vista.findViewById(R.id.txtConclusion);
CampoHorayFecha=(EditText)vista.findViewById(R.id.txthorayfecha);
CampoId=(EditText)vista.findViewById(R.id.txtID);
btnFoto=(Button)vista.findViewById(R.id.btnFoto);
btnActualizar=(Button)vista.findViewById(R.id.btnGuardarPractica);
btnConsultar=(ImageButton)vista.findViewById(R.id.btnBuscarPractica);
btnEliminar=(Button)vista.findViewById(R.id.btnCancelarPractica);
spinnerstatus=(Spinner)vista.findViewById(R.id.spStatusPractica);
campoImagen=(ImageView)vista.findViewById(R.id.imgFoto);
CampoIdImagen=(EditText)vista.findViewById(R.id.txtIdImagen);
        String [] valoresStatus={"Habilitado","Deshabilitado"};
        ArrayAdapter<String> adapterStatus=new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item,valoresStatus);
        adapterStatus.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerstatus.setAdapter(adapterStatus);

btnConsultar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
CargarDatosWebService();
    }
});
btnFoto.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
mostrarDialogOpciones();
    }
});
btnActualizar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       ActualizarDatos();
    }
});
btnEliminar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
EliminarPractica();
    }
});
        if(solicitaPermisosVersionesSuperiores()){
            campoImagen.setEnabled(true);
        }else{
            campoImagen.setEnabled(false);
        }

    return vista; }

private void CargarDatosWebService()
{
    pDialog=new ProgressDialog(getContext());
    pDialog.setMessage("Cargando...");
    pDialog.show();

    final String ip=getString(R.string.ip);

    String url=ip+"/ejemploBDRemota/wsJSONConsultarPracticaUrl.php?documento="+Campodocumento.getText().toString();
    jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>(){
        @Override
        public void onResponse(JSONObject response) {
            pDialog.hide();
                        Practica practica=new Practica();
            JSONArray json=response.optJSONArray("experimento");
            JSONObject jsonObject=null;
            try {
                jsonObject=json.getJSONObject(0);
                practica.setId(jsonObject.optInt("id"));
                practica.setFechayhora(jsonObject.optString("fecha"));
                practica.setUsuario(jsonObject.optString("usuario"));
                practica.setExperimento(jsonObject.optString("experimento"));
                practica.setProtocolo(jsonObject.optString("protocolo"));
                practica.setObservaciones(jsonObject.optString("observaciones"));
                practica.setResultados(jsonObject.optString("resultados"));
                practica.setConclusiones(jsonObject.optString("conclusiones"));
                practica.setRutaImagen(jsonObject.optString("ruta"));
                practica.setIdIamgen(jsonObject.optInt("idImagen"));
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Ocurrrio un Error al enviar los parametros de consulta"+e.toString(), Toast.LENGTH_LONG).show();

            }
            try {
                CampoId.setText(practica.getId() + "");
CampoHorayFecha.setText(practica.getFechayhora());
CampoUsuario.setText(practica.getUsuario());
CampoExperimento.setText(practica.getExperimento());
CampoProtolo.setText(practica.getProtocolo());
CampoObservacion.setText(practica.getObservaciones());
CampoResultados.setText(practica.getResultados());
CampoConclusion.setText(practica.getConclusiones());
CampoIdImagen.setText(practica.getIdIamgen()+"");
                String urlImagen=ip+"/ejemploBDRemota/"+practica.getRutaImagen();

                cargarWebServiceImagen(urlImagen);
            }
            catch (Exception ex)
            {
                Toast.makeText(getContext(), "Ocurrrio un Error al cargar los datos en las cajas o la imagen"+ex.toString(), Toast.LENGTH_LONG).show();
            }


        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getContext(), "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
            System.out.println();
            pDialog.hide();
            Log.d("ERROR: ", error.toString());
        }
    });

    // request.add(jsonObjectRequest);
    VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
}

private void ActualizarDatos()
{
pDialog=new ProgressDialog(getContext());
pDialog.setMessage("Cargando...");
pDialog.show();
    final String ip=getString(R.string.ip);

    String url=ip+"/ejemploBDRemota/wsJSONUpdateMovilPractica.php?";
    stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
@Override
public void onResponse(String response) {
    pDialog.hide();

    if (response.trim().equalsIgnoreCase("actualiza"))
    {

        Toast.makeText(getContext(),"Se ha Actualizado con exito",Toast.LENGTH_SHORT).show();
    }
    else
        {
        Toast.makeText(getContext(),"No se ha Actualizado ",Toast.LENGTH_SHORT).show();
        Log.i("RESPUESTA: ",""+response);
        }

}
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    })
    {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            //String documento=txtDocumento.getText().toString();
            String id=CampoId.getText().toString();
            String experimento=CampoExperimento.getText().toString();
            String Protolo=CampoProtolo.getText().toString();
            String Observacion=CampoObservacion.getText().toString();
            String Resultados=CampoResultados.getText().toString();
            String Conclusion=CampoConclusion.getText().toString();
            String status=spinnerstatus.getSelectedItem().toString();
            String imagen=convertirImgString(bitmap);
            String idImagen=CampoIdImagen.getText().toString();

            Map<String,String> parametros=new HashMap<>();
            parametros.put("id",id);
            parametros.put("experimento",experimento);
            parametros.put("protocolo",Protolo);
            parametros.put("observaciones",Observacion);
            parametros.put("resultados",Resultados);
            parametros.put("conclusiones",Conclusion);
            parametros.put("status",status);
            parametros.put("imagen",imagen);
            parametros.put("idImagen",idImagen);
            return parametros;
        }
    };
    VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
}
public void EliminarPractica()
{
    pDialog=new ProgressDialog(getContext());
    pDialog.setMessage("Cargando...");
    pDialog.show();

    String ip=getString(R.string.ip);

    String url=ip+"/ejemploBDRemota/wsJSONDeleteMovilPractica.php?";
    stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            pDialog.hide();
            if (response.trim().equalsIgnoreCase("elimina")){
                Toast.makeText(getContext(),"Se ha Eliminado con exito",Toast.LENGTH_SHORT).show();
            }else{
                if (response.trim().equalsIgnoreCase("noExiste")){
                    Toast.makeText(getContext(),"No se encuentra la persona ",Toast.LENGTH_SHORT).show();
                    Log.i("RESPUESTA: ",""+response);
                }else{
                    Toast.makeText(getContext(),"No se ha Eliminado ",Toast.LENGTH_SHORT).show();
                    Log.i("RESPUESTA: ",""+response);
                }

            }

        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getContext(),"No se ha podido conectar",Toast.LENGTH_SHORT).show();
            pDialog.hide();
        }

    })
    {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            String id = CampoId.getText().toString();
            String idImagen=CampoIdImagen.getText().toString();

            Map<String, String> parametros = new HashMap<>();
            parametros.put("id", id);
parametros.put("idImagen",idImagen);
            return parametros;
        }
        };
    VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
}

    private void cargarWebServiceImagen(String urlImagen) {
        urlImagen=urlImagen.replace(" ","%20");

        ImageRequest imageRequest=new ImageRequest(urlImagen, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                bitmap=response;//SE MODIFICA
                campoImagen.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"Error al" +
                        " cargar la imagen",Toast.LENGTH_SHORT).show();
                Log.i("ERROR IMAGEN","Response -> "+error);
            }
        });
        //  request.add(imageRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(imageRequest);
    }
    private String convertirImgString(Bitmap bitmap) {

        ByteArrayOutputStream array=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[] imagenByte=array.toByteArray();
        String imagenString= Base64.encodeToString(imagenByte,Base64.DEFAULT);

        return imagenString;
    }
    private void mostrarDialogOpciones() {
        final CharSequence[] opciones={"Tomar Foto","Elegir de Galeria","Cancelar"};
        final AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle("Elige una Opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar Foto")){
                    abriCamara();
                }else{
                    if (opciones[i].equals("Elegir de Galeria")){
                        Intent intent=new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent,"Seleccione"),COD_SELECCIONA);
                    }else{
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        builder.show();
    }
    private void abriCamara() {
        File miFile=new File(Environment.getExternalStorageDirectory(),DIRECTORIO_IMAGEN);
        boolean isCreada=miFile.exists();

        if(isCreada==false){
            isCreada=miFile.mkdirs();
        }

        if(isCreada==true){
            Long consecutivo= System.currentTimeMillis()/1000;
            String nombre=consecutivo.toString()+".jpg";

            path=Environment.getExternalStorageDirectory()+File.separator+DIRECTORIO_IMAGEN
                    +File.separator+nombre;//indicamos la ruta de almacenamiento

            fileImagen=new File(path);

            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(fileImagen));

            ////
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
            {
                String authorities=getContext().getPackageName()+".provider";
                Uri imageUri= FileProvider.getUriForFile(getContext(),authorities,fileImagen);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            }else
            {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagen));
            }
            startActivityForResult(intent,COD_FOTO);

            ////

        }

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case COD_SELECCIONA:
                Uri miPath=data.getData();
                campoImagen.setImageURI(miPath);

                try {
                    bitmap=MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),miPath);
                    campoImagen.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case COD_FOTO:
                MediaScannerConnection.scanFile(getContext(), new String[]{path}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {
                                Log.i("Path",""+path);
                            }
                        });

                bitmap= BitmapFactory.decodeFile(path);
                campoImagen.setImageBitmap(bitmap);

                break;
        }
        bitmap=redimensionarImagen(bitmap,600,800);
    }

    private Bitmap redimensionarImagen(Bitmap bitmap, float anchoNuevo, float altoNuevo) {

        int ancho=bitmap.getWidth();
        int alto=bitmap.getHeight();

        if(ancho>anchoNuevo || alto>altoNuevo){
            float escalaAncho=anchoNuevo/ancho;
            float escalaAlto= altoNuevo/alto;

            Matrix matrix=new Matrix();
            matrix.postScale(escalaAncho,escalaAlto);

            return Bitmap.createBitmap(bitmap,0,0,ancho,alto,matrix,false);

        }else{
            return bitmap;
        }

    }

    //PERMISOS
    private boolean solicitaPermisosVersionesSuperiores() {
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M){//validamos si estamos en android menor a 6 para no buscar los permisos
            return true;
        }

        //validamos si los permisos ya fueron aceptados
        if((getContext().checkSelfPermission(WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)&&getContext().checkSelfPermission(CAMERA)==PackageManager.PERMISSION_GRANTED){
            return true;
        }


        if ((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)||(shouldShowRequestPermissionRationale(CAMERA)))){
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MIS_PERMISOS);
        }

        return false;//implementamos el que procesa el evento dependiendo de lo que se defina aqui
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==MIS_PERMISOS){
            if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){//el dos representa los 2 permisos
                Toast.makeText(getContext(),"Permisos aceptados",Toast.LENGTH_SHORT);
                campoImagen.setEnabled(true);//se vincula el evento a la imagen
            }
        }else{
            solicitarPermisosManual();
        }
    }


    private void solicitarPermisosManual() {
        final CharSequence[] opciones={"si","no"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(getContext());//estamos en fragment
        alertOpciones.setTitle("¿Desea configurar los permisos de forma manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("si")){
                    Intent intent=new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri=Uri.fromParts("package",getContext().getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(),"Los permisos no fueron aceptados",Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
    }


    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(getContext());
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
            }
        });
        dialogo.show();
    }











    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
