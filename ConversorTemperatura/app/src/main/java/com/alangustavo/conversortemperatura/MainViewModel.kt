package com.alangustavo.conversortemperatura


import android.graphics.Color
import android.graphics.ColorFilter
import android.media.Image
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class MainViewModel : ViewModel() {

    //Instanciando a variável à classe ConvertConstrant
    private var mTemperature = ConvertConstants.IMAGEFILTER.CELSIUS

    private var mEdit = MutableLiveData<Boolean>()
    var edit = mEdit

    private var mButton = MutableLiveData<String>()
    var button = mButton

    private var mRepository = Repository()

    var valueColor: Float = 0f

    private var mTemp = MutableLiveData<String>()
    var temperature = mTemp

    private var textCelsius = MutableLiveData<String>()
    var celsius = textCelsius

    private var textFahrenheit = MutableLiveData<String>()
    var fahrenheit = textFahrenheit

    private var mImageCelsius = MutableLiveData<Int>()
    var imageCelsius = mImageCelsius

    private var mImageFahrenheit = MutableLiveData<Int>()
    var imageFahrenheit = mImageCelsius

    init {
        temperature.value = "0º"
        button.value = "Converter"
        imageCelsius.value = R.color.colorPrimaryDark


    }


    //Método responsável por fazer os cálculos de conversão
    fun calculate(edit: String) {
        //Caso a validação esteja "OK", a variável mTemperature tenha recebido o valor "1" e o campo temperatura seja igual a "0"
        if (mTemperature == 1 && temperature.value == "0º") {

            // Convertendo Celsius para Fahrenheit
            temperature.value = Math.round(((9 * edit.toFloat() + 160) / 5)).toString()

            button.value = "Refazer"// Atribuindo texto "Refazer" ao botão

        } //Caso a validação esteja "OK", a variável mTemperature tenha recebido o valor "2" e o campo temperatura seja igual a "0"
        else if (mTemperature == 2 && temperature.value == "0º") {

            // Convertendo Fahrenheit para Celsius
            temperature.value = Math.round(((edit.toFloat() - 32) / 1.8)).toString()
            // Convertendo Fahrenheit para Celsius

            button.value = "Refazer"// Atribuindo texto "Refazer" ao botão
        }
    }


    //Método que valida se o campo editText está diferente de vazio
    fun validationOk(edit: String) {
        val campo = mRepository.edit(edit)
        mEdit.value = campo
    }

    //Método que apaga as informações dos campos
    fun clear() {
        temperature.value = "0º"
        button.value = "Converter"
    }

    //Método que verifica qual botão foi acionado e qual cenário será mostrado
    fun handlerFilter(id: Int) {

        if (id == R.id.image_fahrenheit) {
            imageCelsius.value = R.color.colorPrimaryDark
            imageFahrenheit.value = R.color.snow
            celsius.value = "Fahrenheit"
            fahrenheit.value = "Celsius"
            mTemperature = ConvertConstants.IMAGEFILTER.FAHRENHEIT
            //colors(valueColor)


        } else {
            imageCelsius.value = R.color.snow
            imageFahrenheit.value = R.color.white
            celsius.value = "Celsius"
            fahrenheit.value = "Fahrenheit"
            mTemperature = ConvertConstants.IMAGEFILTER.CELSIUS
            //colors(valueColor)

        }

    }
}
