package com.example.jobschedulerex

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.work.*
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.GooglePlayDriver
import com.firebase.jobdispatcher.Trigger
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onToastMessageEvent(event: ToastEvent) {
        Toast.makeText(this, event.message, Toast.LENGTH_SHORT).show()
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

    fun startJobDispatcher(view: View) {
        val firebaseJobDispatcher = FirebaseJobDispatcher(GooglePlayDriver(this))
        firebaseJobDispatcher.mustSchedule(
            firebaseJobDispatcher.newJobBuilder()
                .setTag(BACKGROUND_RANDOM_TOAST_JOB_TAG)
                .setService(FirebaseJobService::class.java)
                .setTrigger(
                    Trigger.executionWindow(
                        0, //Can start immediately
                        1 //Wait at most one second
                    )
                )
                .build()
        )
    }

    //Use PeriodicWorkRequestBuilder for recurring tasks
    fun startWorkManager(view: View) {
        val logWorker = OneTimeWorkRequestBuilder<LogWorker>().build()
        WorkManager.getInstance().enqueue(logWorker)
    }
}
