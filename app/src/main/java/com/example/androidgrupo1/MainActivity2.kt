package com.example.androidgrupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MainActivity2 : AppCompatActivity() {
    var txtNombre: EditText?=null
    var txtApellido: EditText?=null
    var txtMensaje: EditText?=null
    var tvId: EditText?=null
    var id: String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        txtNombre = findViewById(R.id.txtNombre)
        txtApellido = findViewById(R.id.txtApellido)
        txtMensaje = findViewById(R.id.txtMensaje)

         id = intent.getStringExtra("id").toString()
        tvId?.setText(id)

        val queue= Volley.newRequestQueue(this)
        val url="http://10.40.38.58:8080/mensajes/registro.php?id=$id"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,url,null,
            Response.Listener { response ->
                txtNombre?.setText(response.getString("nombre"))
                txtApellido?.setText(response.getString("apellido"))
                txtMensaje?.setText(response.getString("mensaje"))
            },Response.ErrorListener { error ->
                Toast.makeText(this,error.toString(),Toast.LENGTH_LONG).show()
            }
        )
        queue.add(jsonObjectRequest)
    }
    fun clickRegresar(view:View){
        var intent= Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
    fun clickBorrar(view:View) {
        val url = "http://10.40.38.58:8080/mensajes/borrar.php"
        val queue= Volley.newRequestQueue(this)
        var resultadoPost=object:StringRequest(Request.Method.POST,url,
            Response.Listener<String> { response ->
                Toast.makeText(this,"Usuario Borrado Exitosamente",Toast.LENGTH_LONG).show()
            },Response.ErrorListener { error ->
                Toast.makeText(this,"Error $error",Toast.LENGTH_LONG).show()
            }){
            override fun getParams(): MutableMap<String, String> {
                val parametros=HashMap<String,String>()
                parametros.put("id",id.toString())
                return parametros
            }
        }
        queue.add(resultadoPost)

    }
    fun clickEditar(view:View) {
        val url = "http://10.40.38.58:8080/mensajes/editar.php"
        val queue= Volley.newRequestQueue(this)
        var resultadoPost=object:StringRequest(Request.Method.POST,url,
            Response.Listener<String> { response ->
                Toast.makeText(this,"Usuario Editado Exitosamente",Toast.LENGTH_LONG).show()
            },Response.ErrorListener { error ->
                Toast.makeText(this,"Error $error",Toast.LENGTH_LONG).show()
            }){
            override fun getParams(): MutableMap<String, String> {
                val parametros=HashMap<String,String>()
                parametros.put("id",id.toString())
                parametros.put("nombre",txtNombre?.text.toString())
                parametros.put("apellido",txtApellido?.text.toString())
                parametros.put("mensaje",txtMensaje?.text.toString())
                return parametros
            }
        }
        queue.add(resultadoPost)
    }

}