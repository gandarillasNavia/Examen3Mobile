package org.ucb.appp1

import android.app.Application
import com.google.firebase.database.FirebaseDatabase
import org.ucb.appp1.di.initKoinAndroid
import org.ucb.appp1.exam.NotificationHelper
import org.ucb.appp1.scheduler.LogScheduler

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // E25-4: persistencia offline de Realtime Database (debe ir antes de cualquier uso de la base)
        try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        } catch (e: Exception) {
            println("RTDB persistence: ${e.message}")
        }

        // E50-5: canal de notificaciones (Android 8+)
        NotificationHelper.createChannel(this)

        initKoinAndroid(this)

        val logScheduler = LogScheduler(this)
        logScheduler.schedulePeriodicalUpload()
    }
}
