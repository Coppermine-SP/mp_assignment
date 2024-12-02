package corp.cloudint.jjiuouisbestrestaurant.Contexts

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import corp.cloudint.jjiuouisbestrestaurant.Models.Location

@Dao
interface RoomLocationDAO{
    @Query("SELECT * FROM location")
    fun selectAll() : MutableSet<Location>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(x: Location)

    @Delete
    fun delete(x: Location)
}

@Database(entities = arrayOf(Location::class), version = 1, exportSchema = false)
abstract class LocationDbContext : RoomDatabase() {
    abstract fun RoomLocationDAO() : RoomLocationDAO
}