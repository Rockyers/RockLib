# RockLib
[*Wiki*](https://github.com/Rockyers/RockLib/wiki)

## What is this?
Welcome the RockLib! This is a general use library for Minecraft plugin development!  
If you find a bug you can make an issue report! I am currently working on the documentation in the wiki section  

## Features
**Objects:**  
* [GUIs](https://github.com/Rockyers/RockLib/wiki/Basic-GUIs) - Custom object for both creating GUIs and handling the click events of that GUI
* Items - Custom object for both creating Items and handling the interact events of that Item
* HoverMessages - An object for creating HoverMessages (better known as TextComponenets)
* CommandManager - An easy to use command manager to setup commands with subcommands really easily!  

**Utilities:**
* [CC](https://github.com/Rockyers/RockLib/wiki/Extra#cc) - A formating tool mainly used for the alternat format code
* PlayerUtil - A utility that I am adding lots to! Right now used to send messages easier
* [RockRunnable](https://github.com/Rockyers/RockLib/wiki/Extra#the-rockrunnable) - It's a runnable, but with a Player argument! Used in the functionality for GUIs, Items, and Commands

**Extra:**
* ItemConstructer - A simple but powerful tool for making simple items! Used mostly in the creation of items for GUIs

## Contributing
Pull requests are welcome (Make a fork with your changes then open a pull request)! If it's a bug or suggestion please open an [issue](https://github.com/Rockyers/RockLib/issues/new). If it's something big you can dm/add me on discord: `Rockyers#1000` (until I make a discord server), and finally if you want to make a bigger dedication you can join the dev team be dming or adding me on discord.
### Note:
> This is not the best Library out there honestly,
this is just a project I'm working on for experience and the fun of it!
But in the future this might become bigger!

## Installation
You can install RockLib with the .jar that you can find in the [releases](https://github.com/Rockyers/RockLib/releases) ([Latest](https://github.com/Rockyers/RockLib/releases/latest)).
Or you can install RockLib with a build tool (Maven/Gradle)! 

If you want you can replace RELEASE with whatever version you want, the version can be found as the tag on the release

And if you do not want to add extra size to your plugin you can download the release with the same version as your using and put it into your plugins folder, and change the scope to "provided"
### Maven
```maven
<dependency>
    <groupId>me.rockyers</groupId>
    <artifactId>rocklib</artifactId>
    <version>RELEASE</version>
    <scope>compile</scope>
</dependency>
```

### Gradle

I do not personally use gradel so for now I use JitPack for this. But if someone would like to help with that contact me on discord! `Rockyers#1000`
```gradel
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
        implementation 'com.github.Rockyers:RockLib:RELEASE'
}
```
[![](https://jitpack.io/v/Rockyers/RockLib.svg)](https://jitpack.io/#Rockyers/RockLib)
***

# License
RockLib is licensed under [Creative Commons Zero v1.0 Universal](https://github.com/Rockyers/RockLib/blob/master/LICENSE.md)
