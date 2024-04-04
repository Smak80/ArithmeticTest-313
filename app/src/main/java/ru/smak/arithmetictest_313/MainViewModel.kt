package ru.smak.arithmetictest_313

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val lst = mutableStateListOf<Example>()
    var currentExample by mutableIntStateOf(1)

    init{
        repeat(5){
            lst.add(Example())
        }
    }
}