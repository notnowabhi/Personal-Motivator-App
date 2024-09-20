// File: HealthConnectUtils.kt
package com.example.personalmotivator

import android.content.Context
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.response.ReadRecordsResponse
import androidx.health.connect.client.time.TimeRangeFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch
import java.time.Instant

object HealthConnectUtils {

    // A suspend function to read step records
    suspend fun readStepRecords(context: Context): List<StepsRecord> {
        val healthConnectClient = HealthConnectClient.getOrCreate(context)
        val startTime = Instant.now().minusSeconds(60 * 60 * 24) // Last 24 hours
        val endTime = Instant.now()

        // Define the request for reading step records
        val request = ReadRecordsRequest(
            StepsRecord::class,  // Class<T> to KClass<T>
            TimeRangeFilter.between(startTime, endTime), // Time range filter
            emptySet(),                                   // Data origin filter (empty set for all origins)
            true,                                         // Ascending order
            100,                                          // Page size (maximum records to fetch)
            null                                          // Page token (null for first page)
        )

        // Run the suspend function in the background using Dispatchers.IO
        return withContext(Dispatchers.IO) {
            val response: ReadRecordsResponse<StepsRecord> = healthConnectClient.readRecords(request)
            response.records
        }
    }

    // Function to launch coroutine from Java
    fun readStepsInCoroutine(context: Context, callback: (List<StepsRecord>?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val stepRecords = readStepRecords(context)
                withContext(Dispatchers.Main) {
                    callback(stepRecords)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    callback(null)
                }
            }
        }
    }
}
