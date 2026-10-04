# Location

<p align="center"><a href="https://ko-fi.com/fenleon">
  <picture><source media="(prefers-color-scheme: dark)" srcset="art/coffee-hand-filled-alpha-white-steam.png"><img src="art/coffee-hand-filled-alpha-white.png" alt="Hand holding Coffee" height="50" style="vertical-align: middle;"></picture>
  <picture><source media="(prefers-color-scheme: dark)" srcset="art/buy-me-a-coffee-alpha-white.png"><img src="art/buy-me-a-coffee-alpha-black.png" alt="Buy Me A Coffee" height="40" style="vertical-align: middle;"></picture>
  <img src="art/ok-hand-filled-alpha-white.png" alt="OK Hand" height="50" style="vertical-align: middle;"></a></p>

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
fix, without Light's help, is turning location off when you're not using it.
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

<p align="center">Support my work by leaving me a <a href="https://ko-fi.com/fenleon">tip</a> or <a href="https://github.com/sponsors/fenleon">sponsoring me</a>. A little goes a long way.</p>
