package com.playlab.marvelcomicsinfo.model
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.playlab.marvelcomicsinfo.util.Constants.MARVEL_COMIC_TABLE
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = MARVEL_COMIC_TABLE)
class Comic (
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    @Embedded
    val thumbnail: Thumbnail,
    val description: String?,
    @Embedded
    val creators: Creators?
): Parcelable {
    override fun toString(): String {
        return "ID $id - $title"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Comic

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}