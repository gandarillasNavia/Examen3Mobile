package org.ucb.appp1.scheduler

// iOS: pendiente (BGTaskScheduler). Android usa WorkManager.
actual class LogScheduler actual constructor(context: Any?) {
    actual fun schedulePeriodicalUpload() = Unit
}
