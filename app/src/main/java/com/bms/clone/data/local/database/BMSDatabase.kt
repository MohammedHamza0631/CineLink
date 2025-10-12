package com.bms.clone.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TicketEntity::class],
    version = 1,
    exportSchema = false
)

abstract class BMSDatabase: RoomDatabase() {
    abstract fun ticketDao(): TicketDao
}