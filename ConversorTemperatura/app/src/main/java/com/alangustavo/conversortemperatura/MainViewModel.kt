package com.alangustavo.conversortemperatura

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val mMain = application.applicationContext

    // Variável utilizada na condição que verifica qual cor será utilizada na barra superior
    var valueColor: Int = 0

    //Instanciando a variável à classe ConvertConstrant
    private var mTemperature = ConvertConstants.IMAGEFILTER.CELSIUS

    private var mEdit = MutableLiveData<Boolean>()
    var edit = mEdit

    private var mButton = MutableLiveData<String>()
    var button = mButton

    private var mRepository = Repository()

    private var mTemp = MutableLiveData<String>()
    var temperature = mTemp

    private var textCelsius = MutableLiveData<String>()
    var celsius = textCelsius

    private var textFahrenheit = MutableLiveData<String>()
    var fahrenheit = textFahrenheit

    private var mImageCelsius = MutableLiveData<Int>()
    var imageCelsius = mImageCelsius

    private var mImageFahrenheit = MutableLiveData<Int>()
    var imageFahrenheit = mImageFahrenheit

    private var mView = MutableLiveData<Drawable>()
    var view = mView




    init {
        temperature.value = "0º"
        button.value = "Converter"
        view.value = ContextCompat.getDrawable(mMain, R.drawable.shape_background)
    }

    //Método responsável por fazer os cálculos de conversão
    fun calculate(edit: String) {
        //Caso a validação esteja "OK", a variável mTemperature tenha recebido o valor "1" e o campo temperatura seja igual a "0"
        if (mTemperature == 1 && temperature.value == "0º") {

            // Convertendo Celsius para Fahrenheit
            temperature.value = String.format("%.2f",(((9 * edit.toFloat() + 160) / 5))) + "º"
            valueColor = Math.round(((9 * edit.toFloat() + 160) / 5))

            button.value = "Refazer"// Atribuindo texto "Refazer" ao botão
            chooseColor(valueColor)

        } //Caso a validação esteja "OK", a variável mTemperature tenha recebido o valor "2" e o campo temperatura seja igual a "0"
        else if (mTemperature == 2 && temperature.value == "0º") {

            // Convertendo Fahrenheit para Celsius
            temperature.value = String.format("%.2f", ((((edit.toFloat() - 32) / 1.8)))) + "º"
            valueColor = Math.round(((edit.toFloat() - 32) / 1.8)).toInt()
            // Convertendo Fahrenheit para Celsius
            button.value = "Refazer"// Atribuindo texto "Refazer" ao botão
            chooseColor(valueColor)
        }
    }


    //Método que valida se o campo editText está diferente de vazio
    fun validationOk(edit: String) {
        val campo = mRepository.edit(edit)
        mEdit.value = campo
    }

    //Método que apaga as informações dos campos
    fun clear() {
        if (mTemperature == 1) {
            temperature.value = "0º"
            button.value = "Converter"
            view.value = ContextCompat.getDrawable(mMain, R.drawable.shape_background)
            imageCelsius.value = ContextCompat.getColor(mMain, R.color.snow)
            imageFahrenheit.value = ContextCompat.getColor(mMain, R.color.white)
            valueColor = 0
        } else {
            temperature.value = "0º"
            button.value = "Converter"
            view.value = ContextCompat.getDrawable(mMain, R.drawable.shape_background)
            imageCelsius.value = ContextCompat.getColor(mMain, R.color.white)
            imageFahrenheit.value = ContextCompat.getColor(mMain, R.color.snow)
            valueColor = 0
        }

    }

    //Método que verifica qual botão foi acionado e qual cenário será mostrado

    fun handlerFilter(id: Int): Boolean {

        if (id == R.id.image_fahrenheit) {

            celsius.value = "Fahrenheit"
            fahrenheit.value = "Celsius"
            mTemperature = ConvertConstants.IMAGEFILTER.FAHRENHEIT
            chooseColor(valueColor)
            return true

        } else {

            celsius.value = "Celsius"
            fahrenheit.value = "Fahrenheit"
            mTemperature = ConvertConstants.IMAGEFILTER.CELSIUS
            chooseColor(valueColor)
            return true

        }

    }

    fun chooseColor(valueColor: Int) {
        if (valueColor <= 15 && mTemperature == 1) {

            view.value = ContextCompat.getDrawable(mMain, R.drawable.shape_background)
            imageCelsius.value = ContextCompat.getColor(mMain, R.color.snow)
            imageFahrenheit.value = ContextCompat.getColor(mMain, R.color.white)

        } else if (valueColor >= 16 && valueColor <= 25 && mTemperature == 1) {

            view.value = ContextCompat.getDrawable(mMain, R.drawable.shape_background_orange)
            imageCelsius.value = ContextCompat.getColor(mMain, R.color.warmLight)
            imageFahrenheit.value = ContextCompat.getColor(mMain, R.color.white)

        } else if (valueColor > 25 && mTemperature == 1) {

            imageCelsius.value = ContextCompat.getColor(mMain, R.color.hotLight)
            view.value = ContextCompat.getDrawable(mMain, R.drawable.shape_background_red)
            imageFahrenheit.value = ContextCompat.getColor(mMain, R.color.white)

        } else if (valueColor <= 15 && mTemperature == 2) {

            view.value = ContextCompat.getDrawable(mMain, R.drawable.shape_background)
            imageCelsius.value = ContextCompat.getColor(mMain, R.color.white)
            imageFahrenheit.value = ContextCompat.getColor(mMain, R.color.snow)

        } else if (valueColor >= 16 && valueColor <= 25 && mTemperature == 2) {

            view.value = ContextCompat.getDrawable(mMain, R.drawable.shape_background_orange)
            imageCelsius.value = ContextCompat.getColor(mMain, R.color.white)
            imageFahrenheit.value = ContextCompat.getColor(mMain, R.color.warmLight)

        } else if (valueColor > 25 && mTemperature == 2) {

            imageCelsius.value = ContextCompat.getColor(mMain, R.color.white)
            view.value = ContextCompat.getDrawable(mMain, R.drawable.shape_background_red)
            imageFahrenheit.value = ContextCompat.getColor(mMain, R.color.hotLight)
        }
    }

    /*
    fun fade(view: View){
        val animation = AlphaAnimation(0f,1f)
        animation.duration = 2000L
        animation.repeatMode = Animation.REVERSE
        animation.repeatCount = 0
        view.startAnimation(animation)

    }

     */
}

