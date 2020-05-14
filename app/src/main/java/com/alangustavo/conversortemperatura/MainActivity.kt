package com.alangustavo.conversortemperatura

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    //Instanciando a variável à classe ConvertConstrant
    private var mTemperature = ConvertConstants.IMAGEFILTER.CELSIUS
    var valueColor: Float =
        0f // Variável utilizada na condição que verifica qual cor será utilizada na barra superior

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        image_celsius.setColorFilter(resources.getColor(R.color.snow)) // Cor padrão de início da imagem Celsius -: Fahrenheit
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
        } else if (id == R.id.button_calcular && temperature.text.toString() != "0º") {
            clear()
        } else if (id in listFilter) {
            handlerFilter(id)
        }

    }

    //Método que valida se o campo editText está diferente de vazio
    fun validationOk(): Boolean {
        return edit_celsius.text.toString() != ""
    }

    //Método que apaga as informações dos campos
    private fun clear() {
        edit_celsius.setText("")
        temperature.setText("0º")
        button_calcular.setText("Converter")
    }

    //Método que verifica qual botão foi acionado e qual cenário será mostrado
    private fun handlerFilter(id: Int) {

        when (id) {

            R.id.image_fahrenheit -> {
                image_celsius.setColorFilter(resources.getColor(R.color.white))
                image_fahrenheit.setColorFilter(resources.getColor(R.color.snow))
                text_celsius.setText("Fahrenheit")
                text_fahrenheit.setText("Celsius")
                mTemperature = ConvertConstants.IMAGEFILTER.FAHRENHEIT
                colors(valueColor)


            }
            R.id.image_celsius -> {
                image_celsius.setColorFilter(resources.getColor(R.color.snow))
                image_fahrenheit.setColorFilter(resources.getColor(R.color.white))
                text_celsius.setText("Celsius")
                text_fahrenheit.setText("Fahrenheit")
                mTemperature = ConvertConstants.IMAGEFILTER.CELSIUS
                colors(valueColor)

            }

        }
    }

    //Método responsável por fazer os cálculos de conversão
    fun calculate() {
        //Caso a validação esteja "OK", a variável mTemperature tenha recebido o valor "1" e o campo temperatura seja igual a "0"
        if (validationOk() && mTemperature == 1 && temperature.text.toString() == "0º") {

            // Convertendo Celsius para Fahrenheit
            temperature.text =
                Math.round(((9 * edit_celsius.text.toString().toFloat() + 160) / 5)).toString()

            button_calcular.setText("Reafazer")// Atribuindo texto "Refazer" ao botão
            valueColor = temperature.text.toString()
                .toFloat() //Variável valueColor recebe o valor do campo temperatura
            colors(valueColor) //Chamada de método com passagem de parâmetro

            //Ao clicar no botão "Refazer" ele volta ao modo de início, um "default"
        } else if (mTemperature == 1 && temperature.text.toString() != "0º") {
            defalut()


            //Caso a validação esteja "OK", a variável mTemperature tenha recebido o valor "2" e o campo temperatura seja igual a "0"
        } else if (validationOk() && mTemperature == 2 && temperature.text.toString() == "0º") {

            // Convertendo Fahrenheit para Celsius
            temperature.text =
                Math.round(((edit_celsius.text.toString().toFloat() - 32) / 1.8)).toString()
            // Convertendo Fahrenheit para Celsius

            button_calcular.setText("Reafazer")// Atribuindo texto "Refazer" ao botão
            valueColor = temperature.text.toString()
                .toFloat() //Variável valueColor recebe o valor do campo temperatura
            colors(valueColor) //Chamada de método com passagem de parâmetro

            //Ao clicar no botão "Refazer" ele volta ao modo de início, um "default"
        } else if (mTemperature == 2 && temperature.text.toString() != "0º") {
            defalut()


            //Caso nehuma das condições seja atendida, ele retorna a mensagem de "Valor não encontrado"
        } else {
            Toast.makeText(
                applicationContext,
                getString(R.string.valores_invalidos),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun defalut() {

        button_calcular.setText("Converter")
        temperature.setText("0º")
        edit_celsius.setText("")

        view.setBackgroundColor(resources.getColor(R.color.colorAccent))
        image_celsius.setColorFilter(resources.getColor(R.color.snow))
        image_fahrenheit.setColorFilter(resources.getColor(R.color.white))
        valueColor = 0f

    }

    fun colors(valueColor: Float) {

        if (valueColor <= 15) {
            view.setBackgroundColor(resources.getColor(R.color.colorAccent))
            if (mTemperature == 1) {
                image_celsius.setColorFilter(resources.getColor(R.color.snow))
            } else {
                image_fahrenheit.setColorFilter(resources.getColor(R.color.snow))
            }

        } else if (valueColor > 15 && valueColor <= 28) {
            view.setBackgroundColor(resources.getColor(R.color.warm))
            if (mTemperature == 1) {
                image_celsius.setColorFilter(resources.getColor(R.color.warmLight))
            } else {
                image_fahrenheit.setColorFilter(resources.getColor(R.color.warmLight))
                image_celsius.setColorFilter(resources.getColor(R.color.white))
            }
        } else if (valueColor >= 29) {
            view.setBackgroundColor(resources.getColor(R.color.hot))
            if (mTemperature == 1) {
                image_celsius.setColorFilter(resources.getColor(R.color.hotLight))
            } else {
                image_fahrenheit.setColorFilter(resources.getColor(R.color.hotLight))
                image_celsius.setColorFilter(resources.getColor(R.color.white))
            }

        }
    }
}
