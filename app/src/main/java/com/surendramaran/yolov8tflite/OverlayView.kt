package com.surendramaran.yolov8tflite

import android.app.AlertDialog
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import java.util.LinkedList
import kotlin.math.max

class OverlayView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var results = listOf<BoundingBox>()
    private var boxPaint = Paint()
    private var textBackgroundPaint = Paint()
    private var textPaint = Paint()

    private var bounds = Rect()

    init {
        initPaints()
    }

    fun clear() {
        results = listOf()
        textPaint.reset()
        textBackgroundPaint.reset()
        boxPaint.reset()
        invalidate()
        initPaints()
    }

    private fun initPaints() {
        textBackgroundPaint.color = Color.BLACK
        textBackgroundPaint.style = Paint.Style.FILL
        textBackgroundPaint.textSize = 50f

        textPaint.color = Color.WHITE
        textPaint.style = Paint.Style.FILL
        textPaint.textSize = 50f

        boxPaint.color = ContextCompat.getColor(context!!, R.color.bounding_box_color)
        boxPaint.strokeWidth = 8F
        boxPaint.style = Paint.Style.STROKE
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        results.forEach {
            val left = it.x1 * width
            val top = it.y1 * height
            val right = it.x2 * width
            val bottom = it.y2 * height

            canvas.drawRect(left, top, right, bottom, boxPaint)
            val drawableText = it.clsName

            textBackgroundPaint.getTextBounds(drawableText, 0, drawableText.length, bounds)
            val textWidth = bounds.width()
            val textHeight = bounds.height()
            canvas.drawRect(
                left,
                top,
                left + textWidth + BOUNDING_RECT_TEXT_PADDING,
                top + textHeight + BOUNDING_RECT_TEXT_PADDING,
                textBackgroundPaint
            )
            canvas.drawText(drawableText, left, top + bounds.height(), textPaint)

        }
    }

    fun setResults(boundingBoxes: List<BoundingBox>) {
        results = boundingBoxes
        invalidate()
    }
    // Обработка касаний в OverlayView
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            // Проверяем, нажато ли на область, где был обнаружен объект
            for (result in results) {
                val left = result.x1 * width
                val top = result.y1 * height
                val right = result.x2 * width
                val bottom = result.y2 * height

                if (event.x in left..right && event.y in top..bottom) {
                    // Если нажато на область объекта, показываем Toast с информацией
                    showInfo(result.clsName)
                    return true
                }
            }
        }
        return super.onTouchEvent(event)
    }
    private fun showInfo(className: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Описание персонажа из анимационного сериала Врата Штейна")
        if (className == "Okabe Rintaro"){
            builder.setMessage("Персонаж: $className \n" +
                    "Главное лицо истории, основатель «Лаборатории гаджетов будущего». Восемнадцатилетний Ринтаро Окабэ — всего лишь " +
                    "студент первого курса Токийского электротехнического университета, но уже ощущает себя не только гениальным, но и, " +
                    "как это принято у гениев, — сумасшедшим учёным." +
                    " Доказывает это он рассказами о таинственной «организации», которая неусыпно следит за студентом.")
        }
        else if (className == "Makise Kurisu"){
            builder.setMessage("Персонаж: $className \n" +
                    "Член «Лаборатории гаджетов будущего» № 004. Возраст — 18 лет. Талантливая исследовательница неврологии в американском университете." +
                    " Когда ей было 17 лет, её исследование было опубликовано во всемирно-известном журнале «Science» (в аниме название заменено на «Sciency»)." +
                    "Окабэ часто называет её ассистентом или придуманным им прозвищем — Кристина (саму Курису это очень раздражает). На первый взгляд — спокойная девушка," +
                    " краснеет, если кто-то незнакомый приближается к ней слишком близко, но в душе — полная противоположность: однажды заставила Окарина стоять на коленях три часа подряд, " +
                    "при этом грозясь ударить его книгой. " +
                    "Всегда стоит на своём, не любит проигрывать и не любит, когда кто-то пытается управлять ею.")
        }
        else if (className == "Mayuri Shiina") {
            builder.setMessage(
                "Персонаж: $className \n" +
                        "Девушка, в свои шестнадцать лет ставшая членом «Лаборатории гаджетов будущего» под номером «002», сразу за её главой. " +
                        "А всё потому, что она неизменная подруга детства Ринтаро, и за это имеет право называть его лично придуманным именем «Окарин». " +
                        "Себя же называет «Маюси». Немного легкомысленна, что может быть следствием её увлечения — косплея, для которого она любит придумывать и шить разнообразные костюмы." +
                        " Кроме того," +
                        " Ринтаро про себя шутливо удивляется: «Похоже, у неё действительно ветер в голове»." +
                        " Но, по его же словам, несмотря на всю её несерьёзность и глуповатость," +
                        " Маюри способна, как никто другой, подбодрить и будет рядом, если тебе это нужно." +
                        " Не сидит ни у кого на шее, самостоятельно подрабатывает в мэйд-кафе, где официантки наряжены в костюмы горничных.\n" +
                        " В зависимости от текущего значения отклонения в мире Альфа, умирает в определённый день, без возможности спасти её от смерти"
            )
        }
        else if (className == "Itaru Hashida") {
            builder.setMessage(
                "Персонаж: $className \n" +
                        "Известный под прозвищем друзей Дару — девятнадцатилетний подросток, который учится на первом курсе Токийского электротехнического университета. " +
                        "Также является членом «Лаборатории гаджетов будущего», занесённый в список под номером «003», где его «специализация» — хакерские взломы. " +
                        "Ведь, несмотря на юный возраст, Дару отлично разбирается и в программном, и в аппаратном обеспечениях компьютера. Но эти умения никак не помогают ему в личной жизни." +
                        " Неуверенный в себе с излишним весом, Дару просто боится людей и общается только с помощью компьютера." +
                        " Для моральной поддержки выбрал культуру отаку. Дару не социопат и не опасен для общества." +
                        " Он самостоятельно выбирает нетрадиционный образ жизни вне активного взаимодействия с другими людьми." +
                        " Он сам навешивает на себя ярлык отаку. Это юношеский протест и своеобразный вызов, за которым на самом деле кроется ранимое сердце. " +
                        "И если бы нашёлся неравнодушный человек, он бы смог расшевелить Дару, выведя его из берлоги в люди."
            )
        }
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }


    companion object {
        private const val BOUNDING_RECT_TEXT_PADDING = 8
    }
}