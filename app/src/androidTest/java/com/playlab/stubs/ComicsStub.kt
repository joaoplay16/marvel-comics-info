package com.playlab.stubs

import com.playlab.marvelcomicsinfo.model.Comic
import com.playlab.marvelcomicsinfo.model.Thumbnail

 object ComicsStub{
    val comics = listOf<Comic>(
        Comic(id = "01",
            "Hulk",
            Thumbnail("path", ".jpg"),
            null, null),

        Comic(id = "02",
            "Hulk 2",
            Thumbnail("path", ".jpg"),
            null, null),

        Comic(id = "03",
            "Wolverine",
            Thumbnail("path", ".jpg"),
            null, null),
    )
}