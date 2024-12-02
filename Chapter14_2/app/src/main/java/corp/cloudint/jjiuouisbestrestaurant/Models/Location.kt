package corp.cloudint.jjiuouisbestrestaurant.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "location")
class Location {
    fun Location() {}

    fun Location(name: String, lat: Double, long: Double){
        this.name = name;

    }


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var id: Long? = null;

    @ColumnInfo
    var name: String = ""

    @ColumnInfo
    var lat: Double = 0.0

    @ColumnInfo
    var long: Double = 0.0
}