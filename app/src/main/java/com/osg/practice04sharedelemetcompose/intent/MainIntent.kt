package com.osg.practice04sharedelemetcompose.intent

sealed class MainIntent {
    object FirstScreen: MainIntent()
    object SecondScreen: MainIntent()
}