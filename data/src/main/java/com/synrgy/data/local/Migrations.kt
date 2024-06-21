package com.synrgy.data.local

import android.util.Log
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_2_3 = object:Migration(2,3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        try{

            db.execSQL("ALTER TABLE  UserDataModel ADD COLUMN profileImg TEXT DEFAULT NULL")
            db.execSQL("""
            CREATE TABLE IF NOT EXISTS MovieDataModel (
                id INTEGER PRIMARY KEY NOT NULL,
                poster_path TEXT NOT NULL,
                title TEXT NOT NULL,
                overview TEXT NOT NULL
            )
        """.trimIndent())
            Log.d("Migration", "migrate 2 3: success")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("Migration", "migrate 2 3: ${e.message}")
        }
    }
}