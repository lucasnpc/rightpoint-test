# Module Data Public

This is the `:data:public` module and contains all of the [repository][repository] and/or use case definitions. The purpose of this module is to provide a clean API for the rest of the application to use. For example:

```kotlin
interface UserRepository {
	suspend fun getUser(): User
}
```

or one of the pre-defined use case interfaces.

This is to keep from leaking implementation details to any modules consuming the APIs from the data layer.

[repository]: https://developer.android.com/jetpack/guide#fetch-data
