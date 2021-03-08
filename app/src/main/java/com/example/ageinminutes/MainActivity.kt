package com.example.ageinminutes

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.core.util.rangeTo
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.time.days


class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        //This calls the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_main)

        //Hiding toolbar and actionbar
        //setSupportActionBar(findViewById(R.id.toolbar))
        //actionBar?.hide()

        // OnClickListener is set to the button for launching the DatePicker Dialog.
        btnDatePicker.setOnClickListener {view ->
            clickDatePicker(view)
        }
    }

    /**
     * The function to show the DatePicker Dialog.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun clickDatePicker(view: View){
        /**
         * This Gets a calendar using the default time zone and locale.
         * The calender returned is based on the current time
         * in the default time zone.
         */

        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR) // Returns the value of the given calendar field. This indicates YEAR
        val month = myCalendar.get(Calendar.MONTH) // This indicates the Month
        val day = myCalendar.get(Calendar.DAY_OF_MONTH) // This indicates the Day of month
        val age = myCalendar.get(Calendar.DAY_OF_YEAR) // This indicates the day of year

        /**
         * Creates a new date picker dialog for the specified date using the parent
         * context's default date picker dialog theme.
         */
        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDayOfMonth ->
                /**
                 * The listener used to indicate the user has finished selecting a date.
                 */

                /**
                 *Here the selected date is set into format i.e : day/Month/Year
                 * And the month is counted in java is 0 to 11 so we need to add +1 so it can be as selected.
                 * */
                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"

                // Selected date is set to the TextView to make it visible to user what was selected.
                tvSelectedDate.setText(selectedDate)

                /**
                 * Here we have taken an instance of Date Formatter as it will format our
                 * selected date in the format which we pass it as an parameter and Locale.
                 * Here I have passed the format as dd/MM/yyyy.
                 */
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

                /**
                 * The formatter will parse the selected date in to Date object
                 * so we can simply get date in to milliseconds.
                 */
                val theDate = sdf.parse(selectedDate)

                /** Here we grabbed the time in milliSeconds from Date object
                 *  Since we know that the formula is milliseconds can be converted to seconds by dividing it by 1000.
                 *  And the seconds can be converted to minutes by dividing it by 60.
                 *  We change the selected date into minutes, hours, & days.
                 */
                val selectedDateInMinutes = theDate!!.time / 60000
                val selectedDateInHours = theDate!!.time / 3600000
                val selectedDateInDays = theDate!!.time / 86400000


                // Here we parsed the current date with the Date Formatter which is used above.
                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))


                // Current date in minutes, hours, years.
                val currentDateToMinutes = currentDate!!.time / 60000
                val currentDateToHours = currentDate!!.time / 3600000
                val currentDateToDays = currentDate!!.time / 86400000


                /**
                 * Subtract the selectedDateToMinutes from currentDateToMinutes
                 * which will provide the difference in minutes as well as calculate
                 * hours and days.
                 */
                val differenceInMinutes = currentDateToMinutes - selectedDateInMinutes
                val differenceInHours = currentDateToHours - selectedDateInHours
                val differenceInDays = currentDateToDays - selectedDateInDays
                //var differenceInAge = Period.between(LocalDate.of(Integer.parseInt()))


                // Set the difference in minutes, hours, days & years to textview and display it.
                tvSelectedDateInMinutes.setText(differenceInMinutes.toString())
                tvSelectedDateInHours.setText(differenceInHours.toString())
                tvSelectedDateInDays.setText(differenceInDays.toString())
                //tvSelectedDateInAge.setText(differenceInAge.toString())

                // parse the date with a suitable formatter
                //val from = LocalDate.parse("04112005", DateTimeFormatter.ofPattern("ddMMyyyy"))
         //       val from = LocalDate.of(selectedYear, selectedMonth, selectedDayOfMonth)

                // get today's date
                //val today = LocalDate.now()
                // calculate the period between those two
                //    var period = Period.between(from, today)
                // and print it in a human-readable way
                /*println("The difference between " + selectedDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
                        + " and " + today.format(DateTimeFormatter.ISO_LOCAL_DATE) + " is "
                        + period.getYears() + " years, " + period.getMonths() + " months and "
                        + period.getDays() + " days")*/
                //tvSelectedDateInAge.setText(period.years.toString())

            },
            year,
            month,
            day
        )

        /**
         * Sets the maximal date supported by this in  milliseconds
         * since January 1, 1970 00:00:00 in time zone.
         *
         * @param maxDate is the maximum supported date.
         */
        //86400000 is the milliseconds of 24 Hours which is used to restrict the user to select today and future days
        dpd.datePicker.setMaxDate(Date().time - 86400000)
        dpd.show() // This is used to show the datePicker Dialog
    }
}

