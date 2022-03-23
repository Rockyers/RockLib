# RockLib

## What is this?
Welcome the RockLib! This is a general use library for Minecraft plugin development!

If you find a bug you can make an issue report! I am currently working on the documentation in the wiki section

(Check the wiki or the badge below to get install instructions)

## Contributing
Pull requests are welcome! If it's a bug please open an [issue](https://github.com/Rockyers/RockLib/issues/new). If it's something big you can dm/add me on discord: `Rockyers#1000` (until I make a discord server), and finally if you want 
to make a bigger dedication you can join the dev team, to join the dev team dm/add me on discord.
### Note:
> This is not the best Library out there honestly,
this is just a project I'm working on for experience and the fun of it!
But in the future this might become bigger!

## Installation
You can install RockLib with the .jar that you can find in the [releases](https://github.com/Rockyers/RockLib/releases) ([Latest](https://github.com/Rockyers/RockLib/releases/latest)).
Or you can install RockLib with a build tool (Maven/Gradle)! 

Make sure to replace "Version" with the version of RockLib you are using, you can find it as the tag for the release that you are using.
### Maven
```maven
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.Rockyers</groupId>
    <artifactId>RockLib</artifactId>
    <version>Version</version>
</dependency>
```

### Gradle
```gradel
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
        implementation 'com.github.Rockyers:RockLib:Version'
}
```
[![](https://jitpack.io/v/Rockyers/RockLib.svg)](https://jitpack.io/#Rockyers/RockLib)
