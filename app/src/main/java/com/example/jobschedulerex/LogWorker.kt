package com.example.jobschedulerex

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlin.random.Random

/**
 * If the current process is still active, then an executor will be invoked right away to execute this work, otherwise the JobScheduler, JobDispatcher, or AlarmManager
 * + Broadcast Receivers will be invoked.
 */
class LogWorker(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {

    //Do work runs on a worker thread
    override fun doWork(): Result {
        Log.d("LogWorker: ", Random.nextInt().toString())
        //Indicates success or failure for this specific piece of work
        return Result.success()
    }
}