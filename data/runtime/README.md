# Module Data Implementation

This is the `:data:impl` module and contains all of the [repository][repository] and/or use case business logic. Repository implementations handle all data operations, such as fetching data from the network and caches the data to device. For example:

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

or

```kotlin
internal class GetUserUseCase @Inject constructor(
	private val repository: UserRepository
) : SuspendableUseCase<Unit, User> {
	suspend fun execute(unit: Unit): User = repository.getUser()
}
```

As a result, the rest of the application does not need to know about the database or the web service driving this repository. All that matters is calling the `getUser()` suspend function in this example will return a `User` object. The actual implementation details are hidden away in this module.

[repository]: https://developer.android.com/jetpack/guide#fetch-data
