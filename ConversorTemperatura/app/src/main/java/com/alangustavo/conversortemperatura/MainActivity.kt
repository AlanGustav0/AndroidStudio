package com.alangustavo.conversortemperatura

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity(), View.OnClickListener {

    //Instanciando a variável à classe ConvertConstrant
    private var mTemperature = ConvertConstants.IMAGEFILTER.CELSIUS

    // Variável utilizada na condição que verifica qual cor será utilizada na barra superior
    var valueColor: Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Cor padrão de início da imagem Celsius -: Fahrenheit
        image_celsius.setColorFilter(ContextCompat.getColor(this, R.color.snow))
        image_celsius.setOnClickListener(this) // evento de clique para a imagem
        image_fahrenheit.setOnClickListener(this) //evento de clique para a imagem
        button_calcular.setOnClickListener(this) //evento de clique o botão

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
        if (id == R.id.button_calcular) {
            calculate()
        } else if (id == R.id.button_calcular && text_temperature.text.toString() != "0º") {
            clear()
        } else if (id in listFilter) {
            handlerFilter(id)
        }
    }

    //Método que valida se o campo editText está diferente de vazio
    private fun validationOk(): Boolean {
        return edit_celsius.text.toString() != ""
    }

    //Método que apaga as informações dos campos
    private fun clear() {
        edit_celsius.setText("")
        text_temperature.text = "0º"
        button_calcular.text = getString(R.string.converter)
    }

    //Método que verifica qual botão foi acionado e qual cenário será mostrado
    private fun handlerFilter(id: Int) {

        when (id) {
            R.id.image_fahrenheit -> {
                image_celsius.setColorFilter(ContextCompat.getColor(this, R.color.white))
                image_fahrenheit.setColorFilter(ContextCompat.getColor(this, R.color.snow))
                text_celsius.text = getString(R.string.fahrenheit)
                text_fahrenheit.text = getString(R.string.celsius)
                mTemperature = ConvertConstants.IMAGEFILTER.FAHRENHEIT
                colors(valueColor)
            }
            R.id.image_celsius -> {
                image_celsius.setColorFilter(ContextCompat.getColor(this, R.color.snow))
                image_fahrenheit.setColorFilter(ContextCompat.getColor(this, R.color.white))
                text_celsius.text = getString(R.string.celsius)
                text_fahrenheit.text = getString(R.string.fahrenheit)
                mTemperature = ConvertConstants.IMAGEFILTER.CELSIUS
                colors(valueColor)
            }
        }
    }

    //Método responsável por fazer os cálculos de conversão
    private fun calculate() {
        //Caso a validação esteja "OK", a variável mTemperature tenha recebido o valor "1" e o campo temperatura seja igual a "0"
        if (validationOk() && mTemperature == 1 && text_temperature.text.toString() == "0º") {

            // Convertendo Celsius para Fahrenheit
            val temp = edit_celsius.text.toString().toFloat()
            text_temperature.text = ((9 * temp + 160) / 5).roundToInt().toString()

            // Atribuindo texto "Refazer" ao botão
            button_calcular.text = getString(R.string.refazer)

            //Variável valueColor recebe o valor do campo temperatura
            valueColor = text_temperature.text.toString().toFloat()
            //Chamada de método com passagem de parâmetro
            colors(valueColor)

            //Ao clicar no botão "Refazer" ele volta ao modo de início, um "default"
        } else if (mTemperature == 1 && text_temperature.text.toString() != "0º") {
            default()

            //Caso a validação esteja "OK", a variável mTemperature tenha recebido o valor "2" e o campo temperatura seja igual a "0"
        } else if (validationOk() && mTemperature == 2 && text_temperature.text.toString() == "0º") {

            // Convertendo Fahrenheit para Celsius
            val temp = edit_celsius.text.toString().toFloat()
            text_temperature.text = ((temp - 32) / 1.8).roundToInt().toString()
            // Convertendo Fahrenheit para Celsius

            // Atribuindo texto "Refazer" ao botão
            button_calcular.text = getString(R.string.refazer)

            //Variável valueColor recebe o valor do campo temperatura
            valueColor = text_temperature.text.toString().toFloat()
            //Chamada de método com passagem de parâmetro
            colors(valueColor)

            //Ao clicar no botão "Refazer" ele volta ao modo de início, um "default"
        } else if (mTemperature == 2 && text_temperature.text.toString() != "0º") {
            default()
            //Caso nehuma das condições seja atendida, ele retorna a mensagem de "Valor não encontrado"
        } else {
            Toast.makeText(
                applicationContext,
                getString(R.string.valores_invalidos),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun default() {

        button_calcular.text = getString(R.string.converter)
        text_temperature.text = "0º"
        edit_celsius.setText("")

        view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent))
        image_celsius.setColorFilter(ContextCompat.getColor(this, R.color.snow))
        image_fahrenheit.setColorFilter(ContextCompat.getColor(this, R.color.white))
        valueColor = 0f

    }

    private fun colors(valueColor: Float) {
        if (valueColor <= 15) {
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent))
            if (mTemperature == 1) {
                image_celsius.setColorFilter(ContextCompat.getColor(this, R.color.snow))
            } else {
                image_fahrenheit.setColorFilter(ContextCompat.getColor(this, R.color.snow))
            }
        } else if (valueColor > 15 && valueColor <= 28) {
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.warm))
            if (mTemperature == 1) {
                image_celsius.setColorFilter(ContextCompat.getColor(this, R.color.warmLight))
            } else {
                image_fahrenheit.setColorFilter(ContextCompat.getColor(this, R.color.warmLight))
                image_celsius.setColorFilter(ContextCompat.getColor(this, R.color.white))
            }
        } else if (valueColor >= 29) {
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.hot))
            if (mTemperature == 1) {
                image_celsius.setColorFilter(ContextCompat.getColor(this, R.color.hotLight))
            } else {
                image_fahrenheit.setColorFilter(ContextCompat.getColor(this, R.color.hotLight))
                image_celsius.setColorFilter(ContextCompat.getColor(this, R.color.white))
            }
        }
    }
}