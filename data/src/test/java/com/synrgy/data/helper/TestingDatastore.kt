package com.synrgy.data.helper

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.test.TestScope
import java.io.File

fun createTestingDataStore(testScope: TestScope): DataStore<Preferences> {
    return PreferenceDataStoreFactory.create(
        produceFile = { File.createTempFile("datastore", ".preferences_pb") },
        scope = testScope
    )
}