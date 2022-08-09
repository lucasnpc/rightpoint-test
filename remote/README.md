# Module Remote

This is the `:remote` module and defines all of the application's [network/web service][network] logic.

[OkHttp][okhttp] and [Retrofit][retrofit] are utilized in order to organize and define all of the RESTful API calls that the application can make to a backend web server. Additionally, [Moshi][moshi] is utilized to parse the network responses from JSON into Kotlin classes.

The `:data` module depends on this module in order to fetch any data it may need via a backend web server. For example:

```kotlin
internal class UserRepositoryImpl @Inject constructor(
	database: YourAppDb,
	private val service: UserService
) : UserRepository {
	private val userDao = database.userDao()

	suspend fun getUser(): User {
		// Implement some logic to check
		// the database and return data
		// if the data is not null or stale.
		// However, if it is null or stale
		// then the data should fetch from
		// the network before caching to the
		// local database.
		TODO()
	}
}
```

This example demonstrates that this repository defined in the `:data` module receives an instance of a Retrofit service from the `:remote` module via dependency injection. Because of this, the only module that should require access to the `:remote` module is the `:data` module.

[network]: https://developer.android.com/jetpack/guide#fetch-data
[okhttp]: https://github.com/square/okhttp/
[retrofit]: https://github.com/square/retrofit/
[moshi]: https://github.com/square/moshi/
