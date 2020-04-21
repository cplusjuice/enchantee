#!/bin/bash

./gradlew build
[[ -f "~/.minecraft/mods/enchantee*.jar" ]] && rm ~/.minecraft/mods/Enchantee*.jar
cp build/libs/enchantee*.jar ~/.minecraft/mods/