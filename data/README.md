# Data

In order to help facilitate a scalable architecture the data layer has been broken down into 3 separate modules with different responsibilities:

* [`:data:public`][1]: The public module contains all of the public facing APIs of the data layer. Because we want to promote the use of inversion of control this module will typically contain interfaces for various repositories or use cases.
* [`:data:impl`][2]: The impl module contains all of the implementations of the APIs defined in the public module. Ideally this module would not be exposed to other modules that need to access the data layer but rather the public module is used to keep a clear separation of concerns.
* [`:data:injection`][3]: The injection module contains all of the dependency injection wiring code needed to make sure these APIs are available to any class in the dependency graph that references them. The reason for this module is to decouple the dependency injection code from the APIs making it easier to transition to a different dependency injection framework (or none at all) if necessary.

For example, a way of taking advantage of this structure using Dagger/Hilt is as follows:

## Public

Defined in the `public` module:

```kotlin
data class User(val id: String)

interface UserRepository {
	suspend fun getUser(): User
}
```

## Implementation

Defined in the `impl` module:

```kotlin
class UserRepositoryImpl @Inject constructor(
	private val api: UserApi,
	private val dao: UserDao
) : UserRepository {
	override suspend fun getUser(): User {
		// Implementation here, omitted for brevity
	}
}
```

## Injection

Defined in the `injection` module:

```kotlin
@InstallIn(SingletonComponent::class)
@Module
abstract class UserModule {
	@Binds
	abstract fun bindsUserRepository(impl: UserRepositoryImpl): UserRepository
}
```

## Explanation

Separating out the code in this structure provides a couple of benefits:

1. The `public` module is the only module that is a required as a dependency for other modules to access the data layer. This is helpful in cases, such as the one above, where annotation processing is going to be invoked on some of the classes, like `UserRepositoryImpl`. If an outside module is only depending on the `public` module, which doesn't invoke any annotation processing, then we avoid unnecessarily recompiling a lot of code when we make changes to that outside module.
2. This structure makes it easier to provide fake implementations. We can swap out the `impl` and `injection` modules for `fake` and `fake-injection` modules, which is especially useful for testing.
3. Dependency injection is no longer tightly coupled to the rest of the code, making it easier to transition to the dependency injection code. For example, you could easily change the Dagger `@Module` to a Koin implementation like this:

```kotlin
val userModule = module {
	factory<UserRepository> { UserRepositoryImpl(get(), get()) }
}
```

[1]: /public/README.md
[2]: /impl/README.md
[3]: /injection/README.md
