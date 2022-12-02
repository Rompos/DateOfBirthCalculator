package com.rompos.dobcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    //private val tvSelectedDate : TextView? = null
    //private var tvAgeInMinutes : TextView? = null
    //private var tvAgeInHours : TextView? = null
    //private var tvAgeInDays : TextView? = null
    //private var tvAgeInYears : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //reference the btnDatePicker from the layout
        val btnDatePicker: Button = findViewById(R.id.btnDatePicker)
        //set onclicklistener to btnDatePicker

        btnDatePicker.setOnClickListener {
            //call clickDatePicker when this button is clicked
            clickDatePicker()
        }
    }

    private fun clickDatePicker() {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)//31.05

        //get the id of the textviews from the layout
        val tvSelectedDate: TextView? = findViewById(R.id.tvSelectedDate)
        val tvAgeInMinutes: TextView? = findViewById(R.id.tvAgeInMinutes)
        val tvAgeInHours: TextView? = findViewById(R.id.tvAgeInHours)
        val tvAgeInDays: TextView? = findViewById(R.id.tvAgeInDays)
        val tvAgeInYears: TextView? = findViewById(R.id.tvAgeInYears)

        val dpd = DatePickerDialog(
            this, { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                //Toast.makeText(this,"Day $selectedDayOfMonth, Month ${selectedMonth+1},Year $selectedYear, ",Toast.LENGTH_LONG).show()

                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                // Selected date it set to the TextView to make it visible to user.
                tvSelectedDate?.text = selectedDate

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                // The formatter will parse the selected date in to Date object
                // so we can simply get date in to milliseconds.
                val theDate = sdf.parse(selectedDate)

                //if only the date is not empty run this block(null safety implementation)
                theDate?.let {
                    val selectedDateInMinutes = theDate.time / 60000 // it is the same as getTime()
                    //60000 because get time returns the number of milliseconds since january 1970 oo:oo:oo GMT
                    //we divide with 1000 to get seconds from milliseconds and also with 60 to get minutes from seconds and
                    //if we do it in one step we get 60000 (60 * 1000 = 60000)

                    val selectedDateInHours = selectedDateInMinutes / 60

                    val selectedDateInDays = selectedDateInHours / 24

                    val selectedDateInYears = selectedDateInDays * 0.002738

                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

                    //if only the current date is not empty run this block(null safety implementation)
                    currentDate?.let {
                        //current Date in minutes
                        val currentDateInMinutes = currentDate.time / 60000
                        //60000 because get time returns the number of milliseconds since january 1970 oo:oo:oo GMT
                        //we divide with 1000 to get seconds from milliseconds and also with 60 to get minutes from seconds and
                        //if we do it in one step we get 60000 (60 * 1000 = 60000)

                        val currentDateInHours = currentDateInMinutes / 60

                        val currentDateInDays = currentDateInHours / 24

                        val currentDateInYears = currentDateInDays * 0.002738

                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes

                        val differenceInHours = currentDateInHours - selectedDateInHours

                        val differenceInDays = currentDateInDays - selectedDateInDays

                        val differenceInYears = currentDateInYears - selectedDateInYears

                        tvAgeInMinutes?.text = differenceInMinutes.toString()

                        tvAgeInHours?.text = differenceInHours.toString()

                        tvAgeInDays?.text = differenceInDays.toString()

                        tvAgeInYears?.text = differenceInYears.toString()

                    }
                }

            },
            year, month, day
        )
        //3.6 million milliseconds in 1 hour multiply with 24 and we get 86.4 millions
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }

}

