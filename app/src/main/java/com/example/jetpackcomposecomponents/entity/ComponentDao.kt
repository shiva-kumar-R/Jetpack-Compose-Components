package com.example.jetpackcomposecomponents.entity

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ComponentDao {
    @Transaction
    suspend fun updateComponents(components: List<Component>){
        deleteAllComponents(components)
        insertAllComponents(components)
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllComponents(components: List<Component>)

    @Delete
    suspend fun deleteAllComponents(components: List<Component>)

    @Query("SELECT * from component_table ORDER BY id ASC")
    suspend fun getAllComponents(): List<Component>
}