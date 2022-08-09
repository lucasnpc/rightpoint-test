package com.challenge.whatswrong.data.utils

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class NetworkBoundResourceTest {
    @Test
    fun emptyDatabaseQuery() = runBlockingTest {
        val dao = FakeDao<String>(null)
        val api = FakeApi<String>("Value")

        networkBoundResource(
            query = { dao.queryData() },
            fetch = { api.fetchData() },
            save = { dao.saveData(it) }
        ).test {
            assertThat(expectItem()).isEqualTo(Resource.Loading)
            assertThat(expectItem()).isEqualTo(Resource.Success("Value"))
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun successfulDatabaseQuery() = runBlockingTest {
        val dao = FakeDao<String>("Value")
        val api = FakeApi<String>("Ignored")

        networkBoundResource(
            query = { dao.queryData() },
            fetch = { api.fetchData() },
            save = { dao.saveData(it) },
            shouldFetch = { false }
        ).test {
            assertThat(expectItem()).isEqualTo(Resource.Success("Value"))
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun successfulFetch() = runBlockingTest {
        val dao = FakeDao<String>("Value 1")
        val api = FakeApi<String>("Value 2")

        networkBoundResource(
            query = { dao.queryData() },
            fetch = { api.fetchData() },
            save = { dao.saveData(it) }
        ).test {
            assertThat(expectItem()).isEqualTo(Resource.Loading)
            assertThat(expectItem()).isEqualTo(Resource.Success("Value 2"))
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun failedFetch() = runBlockingTest {
        val error = IOException("Network error")
        val dao = FakeDao<String>("Value 1")
        val api = FakeApi<String>("Ignored", error)

        networkBoundResource(
            query = { dao.queryData() },
            fetch = { api.fetchData() },
            save = { dao.saveData(it) }
        ).test {
            assertThat(expectItem()).isEqualTo(Resource.Loading)
            assertThat(expectItem()).isEqualTo(Resource.Failure(error))
            cancelAndIgnoreRemainingEvents()
        }
    }
}
