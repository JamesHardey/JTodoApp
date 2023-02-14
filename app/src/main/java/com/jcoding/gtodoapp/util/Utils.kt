//package com.jcoding.gtodoapp
//
//import android.app.AlarmManager
//import android.app.AlertDialog
//import android.app.PendingIntent
//import android.content.Context
//import android.content.Intent
//import androidx.core.content.ContextCompat.getSystemService
//import java.util.*
//
//private fun scheduleNotification(applicationContext: Context) {
//    val intent = Intent(applicationContext, Notification::class.java)
//    val title = binding.titleET.text.toString()
//    val message = binding.messageET.text.toString()
//    intent.putExtra(titleExtra, title)
//    intent.putExtra(messageExtra, message)
//
//    val pendingIntent = PendingIntent.getBroadcast(
//        applicationContext,
//        notificationID,
//        intent,
//        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
//    )
//
//    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//    val time = getTime()
//    alarmManager.setExactAndAllowWhileIdle(
//        AlarmManager.RTC_WAKEUP,
//        time,
//        pendingIntent
//    )
//    showAlert(time, title, message)
//}
//
//private fun showAlert(time: Long, title: String, message: String, applicationContext: Context)
//{
//    val date = Date(time)
//    val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
//    val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)
//
//    AlertDialog.Builder(applicationContext)
//        .setTitle("Notification Scheduled")
//        .setMessage(
//            "Title: " + title +
//                    "\nMessage: " + message +
//                    "\nAt: " + dateFormat.format(date) + " " + timeFormat.format(date))
//        .setPositiveButton("Okay"){_,_ ->}
//        .show()
//}
//
//
//
//private fun getTime(): Long
//{
//    val minute = binding.timePicker.minute
//    val hour = binding.timePicker.hour
//    val day = binding.datePicker.dayOfMonth
//    val month = binding.datePicker.month
//    val year = binding.datePicker.year
//
//    val calendar = Calendar.getInstance()
//    calendar.set(year, month, day, hour, minute)
//    return calendar.timeInMillis
//}
//
//private fun createNotificationChannel()
//{
//    val name = "Notif Channel"
//    val desc = "A Description of the Channel"
//    val importance = NotificationManager.IMPORTANCE_DEFAULT
//    val channel = NotificationChannel(channelID, name, importance)
//    channel.description = desc
//    val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//    notificationManager.createNotificationChannel(channel)
//}
