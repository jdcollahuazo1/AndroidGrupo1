package com.example.androidgrupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {

    var txtNombre: EditText?=null
    var txtApellido: EditText?=null
    var txtMensaje: EditText?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtNombre = findViewById(R.id.txtNombre)
        txtApellido = findViewById(R.id.txtApellido)
        txtMensaje = findViewById(R.id.txtMensaje)
    }
    fun clickBtnInsertar(view: View){
       val url="http://192.168.100.20:8080/mensajes/insertar.php"
       val queue = Volley.newRequestQueue(this)
       var resultadoP = object: StringRequest(Request.Method.POST,url,
        Response.Listener<String> { response ->
            Toast.makeText(this,"Usuario Insertado Exitosamente",Toast.LENGTH_LONG).show()
        },Response.ErrorListener { error ->
                Toast.makeText(this,"Error $error",Toast.LENGTH_LONG).show()
            }){
            override fun getParams(): MutableMap<String, String> {
                val parametros=HashMap<String,String>()
                parametros.put("nombre",txtNombre?.text.toString())
                parametros.put("apellido",txtApellido?.text.toString())
                parametros.put("mensaje",txtMensaje?.text.toString())
                return parametros
            }
        }
        queue.add(resultadoP)
    }

    fun clickBtnAPIRest(view: View){
        var intent= Intent(this,MainActivity3::class.java)
        startActivity(intent)
    }

    /*------------------------------------------*/
    fun clickVer(view: View) {
        var txtId=findViewById<EditText>(R.id.txtId)
        var intent= Intent(this,MainActivity2::class.java)
        intent.putExtra("id",txtId.text.toString())
        startActivity(intent)
    }
}