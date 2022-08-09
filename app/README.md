# Module App

This is the `:app` module and defines base application classes that are **not** feature specific. For example, this module contains the [`Application`][application] class as well as the launcher [`Activity`][activity]. While these 2 classes are important for defining overall application start-up logic they do not contain any feature specific business logic. All feature specific logic should be defined in its own feature project module. The `:app` module should depend on these feature modules but any common logic shared between different feature modules should be defined in the `:core` module.

[application]: https://developer.android.com/reference/android/app/Application
[activity]: https://developer.android.com/reference/android/app/Activity
