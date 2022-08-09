# Module Data Injection

This is the `:data:injection` module and contains all of the data layer's dependency injection wiring code. For example:

```kotlin
@InstallIn(ViewModelComponent::class)
@Module
abstract class UserModule {
	@Binds
	fun bindsUserRepository(impl: UserRepositoryImpl): UserRepository

	@Binds
	fun bindsGetUserUseCase(useCase: GetUserUseCase): SuspendableUseCase<Unit, User>
}
```

This provides a layer of separation between the public API, the API implementations and the dependency injection code needed to provide the API to the rest of the application.
