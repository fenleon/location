# Location

A one-tap master switch for the Light Phone III's location services.

## What it does

Shows a single "Use Location" row with a toggle that reflects the real,
framework-wide location state (on or off). Tapping the row opens the device's
location services screen, where the master switch lives. The tool itself can't
flip location — that's a system-only permission — so it just shows the truth
and gets you there in one tap.

## Why

The Light Phone's GPS receiver keeps sampling while location services are on,
which quietly drains the battery even when nothing is using your location. The
fix isn't a smarter app — it's turning location off when you're not using it.
This tool puts that switch one toolbox-tap away instead of buried in settings.

## Building

Requires the [light-sdk](https://github.com/lightphone/light-sdk) checkout as a
sibling directory (`../light-sdk`), which the Gradle build consumes as an
included build.

```sh
./gradlew :app:assembleRelease
```

On a real device the tool's `serverPackage` is `com.lightos` (the platform's
SDK service); flip it to `com.thelightphone.sdk.emulator` when testing on the
LightOS emulator.
