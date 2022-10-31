<div align="center">
  <img src="images/hero.gif" alt="heart-switch Demo">
</div>
<br>

<h1 align="center">heart-switch</h1>
<h3 align="center">A heart-shaped toggle switch component built using Jetpack Compose. Inspired by Tore Bernhoft's <a href="https://dribbble.com/shots/8306407-I-heart-toggle">I heart toggle</a> Dribbble shot and <a href="https://github.com/anatoliygatt">Anatoliy Gatt</a>'s <a href="https://github.com/anatoliygatt/heart-switch">React component</a>.</h3>
<br>

## Getting Started

[![Release](https://jitpack.io/v/popovanton0/heart-switch.svg)](https://jitpack.io/#popovanton0/heart-switch)
[![Project Status: Active – The project has reached a stable, usable state and is being actively developed.](https://www.repostatus.org/badges/latest/active.svg)](https://www.repostatus.org/#active)
[![Compatible with Compose — 1.3.0](https://img.shields.io/badge/Compatible%20with%20Compose-1.3.0-brightgreen)](https://developer.android.com/jetpack/androidx/releases/compose-foundation#1.3.0)

Add the following code to your project's _root_ `build.gradle` file:

```groovy
repositories {
    maven { url "https://jitpack.io" }
}
```

Next, add the dependency below to your _module_'s `build.gradle` file:

```gradle
dependencies {
    implementation "com.github.popovanton0:heart-switch:LATEST_VERSION"
}
```

## Usage

Examples are in the [source code](https://github.com/popovanton0/heart-switch/blob/main/app/src/main/java/com/popovanton0/heartswitch/demo/MainActivity.kt).

### Basic

```kotlin
@Composable
fun BasicUsage() {
    var state by remember { mutableStateOf(true) }
    HeartSwitch(
        checked = state,
        onCheckedChange = { state = it },
    )
}
```

![Basic Usage Preview](images/basic-usage.png)

### Advanced

```kotlin
@Composable
fun AdvancedUsage() {
    var state by remember { mutableStateOf(true) }
    HeartSwitch(
        checked = state,
        onCheckedChange = { state = it },
        modifier = Modifier,
        colors = HeartSwitchColors(
            checkedTrackColor = Color(0xFFE91E63),
            checkedTrackBorderColor = Color(0xFFC2185B),
            uncheckedTrackBorderColor = Color(0xFFBDBDBD)
        ),
        width = 34.dp,
        borderWidth = 2.1.dp,
        movementAnimSpec = spring(stiffness = Spring.StiffnessMediumLow),
        colorsAnimSpec = spring(stiffness = Spring.StiffnessMediumLow),
        thumb = { modifier, color ->
            Box(
                modifier = modifier
                    .shadow(12.dp, CircleShape)
                    .background(color.value, CircleShape)
            )
        },
        enabled = true,
        interactionSource = remember { MutableInteractionSource() },
    )
}
```

![Advanced Usage Preview](images/advanced-usage.png)

You can customize colors using `HeartSwitchColors` class:

```kotlin
data class HeartSwitchColors(
    val checkedThumbColor: Color = Color.White,
    val checkedTrackColor: Color = Color(0xffff708f),
    val checkedTrackBorderColor: Color = Color(0xffff4e74),
    val uncheckedThumbColor: Color = Color.White,
    val uncheckedTrackColor: Color = Color.White,
    val uncheckedTrackBorderColor: Color = Color(0xffd1d1d1),
)
```

And, you even can fully customize thumb's appearance using `thumb` composable param. Default
implementation is:

```kotlin
@Composable
fun Thumb(modifier: Modifier, color: State<Color>) = Box(
    modifier = modifier
        .shadow(6.dp, CircleShape)
        .background(color.value, CircleShape)
) 
```
