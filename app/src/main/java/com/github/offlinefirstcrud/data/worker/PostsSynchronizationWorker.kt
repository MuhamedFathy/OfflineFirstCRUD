package com.github.offlinefirstcrud.data.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class PostsSynchronizationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            Log.d(PostsSynchronizationWorker::class.java.simpleName, "doWork() Success")
            Result.success()
        } catch (e: Exception) {
            Log.e(PostsSynchronizationWorker::class.java.simpleName, "doWork() Error")
            Result.retry()
        }
    }
}
