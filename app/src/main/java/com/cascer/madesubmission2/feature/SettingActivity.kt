package com.cascer.madesubmission2.feature

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.cascer.madesubmission2.R
import com.cascer.madesubmission2.data.response.MyTime
import com.cascer.madesubmission2.manager.StorageManager
import com.cascer.madesubmission2.utils.*
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.setting_label)

        switch_daily.isChecked = StorageManager(this).getBoolean(KEY_DAILY_REMINDER_STATE)
        switch_release.isChecked = StorageManager(this).getBoolean(KEY_RELEASE_REMINDER_STATE)

        switch_release.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AlarmReceiver().setPeriodicAlarm(
                    this,
                    TYPE_RELEASED_TODAY,
                    MyTime(8, 0, 0),
                    "Released today reminder"
                )
                StorageManager(this).save(KEY_RELEASE_REMINDER_STATE, true)
            } else {
                AlarmReceiver().cancelAlarm(this, TYPE_RELEASED_TODAY)
                AlarmService().cancelJob(this)
                StorageManager(this).save(KEY_RELEASE_REMINDER_STATE, false)
            }
        }

        switch_daily.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AlarmReceiver().setPeriodicAlarm(
                    this,
                    TYPE_DAILY_REMINDER,
                    MyTime(7, 0, 0),
                    getString(R.string.daily_reminder_label)
                )
                StorageManager(this).save(KEY_DAILY_REMINDER_STATE, true)
            } else {
                AlarmReceiver().cancelAlarm(this, TYPE_DAILY_REMINDER)
                StorageManager(this).save(KEY_DAILY_REMINDER_STATE, false)
            }
        }

        row_change_language.setOnClickListener { startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS)) }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
