package com.example.jokiandroid.utility

class Date {
    var day: Int = 0
    var month: Int = 0
    var year: Int = 0

    constructor() {
        this.day = 0
        this.month = 0
        this.year = 0
    }

    constructor(day: Int, month: Int, year: Int) {
        this.day = day
        this.month = month
        this.year = year
    }

    constructor(dataString: String){
        if (dataString.contains("T")){
            val data = dataString.split("T")[0].split("-")
            this.year = data[0].toInt()
            this.month = data[1].toInt()
            // per correggere il timezone facciamo +1
            this.day = data[2].toInt()+1
            return
        }

        if(dataString.contains("-")){
            val data = dataString.split("-")
            this.year = data[0].toInt()
            this.month = data[1].toInt()
            this.day = data[2].toInt()
            return
        }

        val data = dataString
            .replace(",","")
            .split(" ")

        this.month = convertMonth(data[0])
        this.day = data[1].toInt()
        this.year = data[2].toInt()
    }

    private fun convertMonth(month: String): Int {
        when (month) {
            "Jan" -> return 1
            "Feb" -> return 2
            "Mar" -> return 3
            "Apr" -> return 4
            "May" -> return 5
            "Jun" -> return 6
            "Jul" -> return 7
            "Aug" -> return 8
            "Sep" -> return 9
            "Oct" -> return 10
            "Nov" -> return 11
            "Dec" -> return 12
        }
        return 0
    }

    override fun toString(): String {
        val month = if (month < 10) "0$month" else month.toString()
        val day = if (day < 10) "0$day" else day.toString()

        return "$year-$month-$day"
    }
}