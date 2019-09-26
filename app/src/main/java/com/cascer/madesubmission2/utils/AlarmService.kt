package com.cascer.madesubmission2.utils

import android.app.AlarmManager
import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.util.Log
import com.cascer.madesubmission2.R
import com.cascer.madesubmission2.data.network.ErrorData
import com.cascer.madesubmission2.data.response.movie.MovieResponse
import com.cascer.madesubmission2.viewmodel.MainViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*


class AlarmService : JobService() {

    private val viewModel: MainViewModel by inject()

    override fun onStartJob(params: JobParameters?): Boolean {
//        viewModel.getMovieReleasedToday(applicationContext, today())
        requestData(params)
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        return true
    }

    private fun requestData(params: JobParameters?) {
        val compositeDisposable = CompositeDisposable()
        viewModel.getMovieReleasedToday(
            today(), object : ApiObserver<MovieResponse>(compositeDisposable) {
                override fun onSuccess(data: MovieResponse) {
                    if (data.results != null && data.results.isNotEmpty()) {
                        val result = data.results[0]
                        val title = result.title ?: ""
                        val message = applicationContext.getString(R.string.release_today_message)
                        NotificationHelper().showReleaseNotification(
                            applicationContext, title, message, result
                        )
                    }
                    jobFinished(params, false)
                    Log.d("SUCCESS_GET_MOVIE_TODAY", "Success")
                }

                override fun onError(e: ErrorData) {
                    jobFinished(params, false)
                    Log.d("ERROR_GET_MOVIE_TODAY", e.message)
                }
            })
    }

    private fun today(): String {
        val calendar = Calendar.getInstance()
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(calendar.time)
    }

    fun startJob(context: Context) {
        val serviceComponent = ComponentName(context, AlarmService::class.java)

        val builder = JobInfo.Builder(JOB_ID, serviceComponent)
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
        builder.setRequiresDeviceIdle(false)
        builder.setRequiresCharging(false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            builder.setImportantWhileForeground(true)
        }
        builder.setPeriodic(AlarmManager.INTERVAL_DAY)

        val job =
            context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        job.schedule(builder.build())
    }

    fun cancelJob(context: Context) {
        val job = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        job.cancel(JOB_ID)
    }
}