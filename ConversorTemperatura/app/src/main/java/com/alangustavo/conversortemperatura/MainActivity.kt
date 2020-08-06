package com.alangustavo.conversortemperatura

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alangustavo.conversortemperatura.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        //Método que faz a chamada dos eventos de click das imagens
        setListeners()

        //Método das variáveis observadas pela MainViewModel
        observer()

        //Evento de click do botão cácular
        button_calcular.setOnClickListener {
            val edit = edit_celsius.text.toString()
            mViewModel.validationOk(edit)
        }


        //Deixando a supportActionBar em moddo invisível "hide"
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
    }

    //Eventos de clique dos botões
    override fun onClick(view: View) {
        val id = view.id

        //Lista para armazenar o id dos botões de imagem
        val listFilter = listOf(
            R.id.image_fahrenheit,
            R.id.image_celsius
        )

        //Condição que verifica qual o botão acionado e qual será sua ação
        if (id in listFilter) {
            mViewModel.handlerFilter(id)
        }

    }

    // Evento de clique para as imagens
    fun setListeners() {
        image_celsius.setOnClickListener(this)
        image_fahrenheit.setOnClickListener(this)
    }

    //Método observer, faz a chamada de todos os eventos observados pela MainViewModel
    fun observer() {
        mViewModel.edit.observe(this, Observer {
            val edit = edit_celsius.text.toString()

            if (it && temperature.text.toString() == "0º") {
                mViewModel.calculate(edit)

            } else if (it && temperature.text.toString() != "0º") {
                edit_celsius.setText("")
                mViewModel.clear()
            } else {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.valores_invalidos),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        mViewModel.temperature.observe(this, Observer {
            temperature.text = it
        })

        mViewModel.button.observe(this, Observer {
            button_calcular.text = it
        })

        mViewModel.celsius.observe(this, Observer {
            text_celsius.text = it
        })
        mViewModel.textFahrenheit.observe(this, Observer {
            text_fahrenheit.text = it
        })

        mViewModel.imageCelsius.observe(this, Observer {
            image_celsius.setColorFilter(it)
        })

        mViewModel.imageFahrenheit.observe(this, Observer {
            image_fahrenheit.setColorFilter(it)
        })

        mViewModel.view.observe(this, Observer {
            view_header.setBackgroundColor(it)
        })

    }


}
