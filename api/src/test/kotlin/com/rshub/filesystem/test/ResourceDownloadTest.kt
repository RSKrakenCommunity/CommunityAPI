package com.rshub.filesystem.test

import com.rshub.api.pathing.ResourceUpdater
import org.junit.jupiter.api.Test

class ResourceDownloadTest {

    @Test
    fun `download test`() {
        ResourceUpdater.downloadWeb()
    }

}