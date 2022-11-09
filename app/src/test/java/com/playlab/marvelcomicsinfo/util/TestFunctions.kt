package com.playlab.marvelcomicsinfo.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class TestFunctions {

    @Test
    fun `when page is equal to 1, returns 0`(){
        val page = 1
        val expected = 0

        assertThat(getOffsetByPage(page)).isEqualTo(expected)
    }

    @Test
    fun `when page is greater than 0, returns (page - 1) x items per page`(){
        val page = 10
        val expected = (page - 1) * Constants.ITEMS_PER_PAGE

        assertThat(getOffsetByPage(page)).isEqualTo(expected)
    }
}
