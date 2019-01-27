package com.example.jobschedulerex

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startJobService(view: View) {
        //Access the job scheduler
        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

        //Get a reference to my job service implementation
        val componentName = ComponentName(this@MainActivity, MyJobService::class.java)

        //Build a job info to run MyJobService
        //The job id can be any integer, it just needs to be unique
        //You can add more constraints as needed when building your job
        jobScheduler.schedule(
            JobInfo.Builder(BACKGROUND_RANDOM_TOAST_JOB_ID, componentName)
                .setOverrideDeadline(TimeUnit.SECONDS.toSeconds(2))
                .build()
        )
    }
}
