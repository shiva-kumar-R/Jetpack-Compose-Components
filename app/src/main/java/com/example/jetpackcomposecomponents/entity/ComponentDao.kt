package com.example.jetpackcomposecomponents.entity

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ComponentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllComponents(components: ComponentEntity)

    @Delete
    suspend fun deleteAllComponents(components: ComponentEntity)

    @Query("SELECT * from component_table")
    suspend fun getAllComponents(): ComponentEntity?
}