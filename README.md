# What's Wrong Problem Set

This Repo Contains the Android What's Wrong Coding Challenge

## Background

This sample app hits a very simple endpoint,

```
curl --location --request POST 'https://funtion-app-android-interview-challenge.azurewebsites.net/api/login' \
--header 'Content-Type: application/json' \
--data-raw '{
  "username": "RpTest",
  "password": "Test1234"
}'
```

With a response in the form of:

```json
{"token":"imAToken","firstName":"Right","lastName":"Point"}
```

## Tools

This application uses coroutines to handle concurrency, uses retrofit for performing REST calls and moshi for performing JSON parsing. It follows a unidirectional data flow paradigm to have the UI consume `State`s fed to it by the viewmodel to drive functionality.

## Tickets

The following bugs have been logged against the application by QA:

### WW-1: User Cannot Log Into the app

The user cannot log into the app with any credentials, attempting to do so causes a crash.

Steps to Reproduce:
1. Enter in user credentials RpTest//Test1234
2. Press Login

Observed Result: Application Crashes
Expected Result: The user is logged into the home screen

### WW-2: Login Errors go away too quickly

When a user fails to log in, a very brief message displays and does not instruct the user on what occurred.

Steps to Reproduce:
1. Enter in invalid login credentials
2. Press Login

Observed Result: A toast displays stating the error
Expected Result: A snackbar or dialog displays stating the error

### WW-3: The app does not indicate if its performing work.

When a user attempts to log in, there is no indication on the UI that anything is processing.

Steps to reproduce:
1. Enter credentials into the login screen
2. Press Login

Observed result: The UI does not indicate if the login call is in progress, and can press Login multiple times (user must wait for an error or success)
Expected result: The UI indicates an action is in progress.

### WW-4: The app accepts usernames that are less than 3 characters and passwords less than 4 characters

The platform only allows for usernames with 3 or more characters and passwords that have at least 4 characters in them.

Steps to reproduce:
1. Enter a username that is less than 3 characters
2. Enter a password that is less than 4 characters
3. Login

Observed result: No client side validation is performed
Expected result: The app performs some client-side validation before attempting to log the user in.

### WW-5: Pressing the Enter/Return key on the Password field does not log the user in

Steps to Reproduce:
1. Enter username and press the return/enter key
2. Observe that the entry moves to password
3. Enter password, and press the return/enter key

Observed Result: The keyboard dismisses
Expected Result: The app tries to log the user in