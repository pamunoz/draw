package com.pfariasmunoz.drawingapp.data.source

import com.pfariasmunoz.drawingapp.data.Result
import com.pfariasmunoz.drawingapp.data.source.local.Drawing

interface DrawingsDataSource {
    suspend fun getDrawings(): Result<List<Drawing>>
    suspend fun getDrawing(drawingId: Long): Result<Drawing>
    suspend fun saveDrawing(drawing: Drawing)
    suspend fun deleteAllDrawings()
    suspend fun deleteDrawing(drawingId: Long)
}