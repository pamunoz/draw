package com.pfariasmunoz.drawingapp.data.source.local

import android.arch.persistence.room.*

/**
 * Data Access Object for the drawings table.
 */
@Dao
interface DrawingsDao {

    /**
     * Insert a drawing in the database. If the drawing already exists, replace it.
     *
     * @param drawing the task to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserDrawing(drawing: Drawing)

    /**
     * Select all drawings from the drawings table.
     *
     * @return all drawings.
     */
    @Query("SELECT * FROM drawings")
    fun getDrawings(): List<Drawing>

    /**
     * Select a drawing by id.
     *
     * @param drawingId the drawing id.
     * @return the drawing with drawingId.
     */
    @Query("SELECT * FROM drawings WHERE _id = :drawingId")
    fun getDraingById(drawingId: Long): Drawing?

    /**
     * Delete a drawing by id.
     *
     * @return the number of drawings deleted. This should always be 1.
     */
    @Query("DELETE FROM users WHERE _id = :drawingId")
    fun deleteDrawingById(drawingId: Long): Int

    /**
     * Update a drawing.
     *
     * @param drawing user to be updated
     * @return the number of drawings updated. This should always be 1.
     */
    @Update
    fun updateDrawing(drawing: Drawing): Int

    /**
     * Delete all drawings.
     */
    @Query("DELETE FROM drawings") fun deleteDrawings()
}