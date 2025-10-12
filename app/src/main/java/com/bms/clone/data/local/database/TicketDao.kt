package com.bms.clone.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TicketDao {
    @Insert
    suspend fun insertTicket(ticket: TicketEntity)

    @Query("SELECT * FROM tickets ORDER BY bookingDate DESC")
    fun getAllTickets(): Flow<List<TicketEntity>>

    @Query("SELECT * FROM tickets WHERE ticketId = :ticketId")
    suspend fun getTicketById(ticketId: String): TicketEntity?

    @Delete
    suspend fun deleteTicket(ticket: TicketEntity)
}