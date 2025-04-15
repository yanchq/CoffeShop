package com.example.coffeshop.presentation.order

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.example.coffeshop.R
import com.example.coffeshop.domain.usecase.GetOrderStatusUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class OrderStatusService : LifecycleService() {

    @Inject
    lateinit var getOrderStatusUseCase: GetOrderStatusUseCase

    private var job: Job? = null

    override fun onCreate() {
        super.onCreate()
        startForegroundServiceNotification()
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        val orderId = intent?.getIntExtra(EXTRA_ORDER_ID, -1) ?: -1
        if (orderId == -1) stopSelf()

        job = lifecycleScope.launch {
            getOrderStatusUseCase.invoke(orderId).collect { status ->
                showNotification("Статус заказа: $status")

                if (status == "Complete") {
                    stopSelf()
                }
            }
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }

    private fun startForegroundServiceNotification() {
        val channelId = "order_status_channel"
        val channelName = "Order Status Updates"
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            channelId, channelName, NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Отслеживание заказа")
            .setContentText("Идёт проверка статуса заказа...")
            .setSmallIcon(R.drawable.fragment_main_logo)
            .build()

        startForeground(1, notification)
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun showNotification(text: String) {
        val notification = NotificationCompat.Builder(this, "order_status_channel")
            .setContentTitle("Изменение заказа")
            .setContentText(text)
            .setSmallIcon(R.drawable.fragment_main_logo)
            .build()
        NotificationManagerCompat.from(this).notify(2, notification)
    }

    companion object {
        const val EXTRA_ORDER_ID = "extra_order_id"
    }
}
