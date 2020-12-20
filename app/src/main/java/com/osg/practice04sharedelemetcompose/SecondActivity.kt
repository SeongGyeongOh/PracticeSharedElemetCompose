package com.osg.practice04sharedelemetcompose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedVisibilityLazyColumnDemo() {
    var itemNum by remember { mutableStateOf(0) }
    Column {
        Row(Modifier.fillMaxWidth()) {
            Button(
                { itemNum = itemNum + 1 },
                enabled = itemNum <= turquoiseColors.size - 1,
                modifier = Modifier.padding(15.dp).weight(1f)
            ) {
                Text("Add")
            }

            Button(
                { itemNum = itemNum - 1 },
                enabled = itemNum >= 1,
                modifier = Modifier.padding(15.dp).weight(1f)
            ) {
                Text("Remove")
            }
        }
        LazyColumn {
            itemsIndexed(turquoiseColors) { i, color ->
                AnimatedVisibility(
                    (turquoiseColors.size - itemNum) <= i,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    Spacer(Modifier.fillMaxWidth().height(90.dp).background(color))
                }
            }
        }

        Button(
            { itemNum = 0 },
            modifier = Modifier.align(End).padding(15.dp)
        ) {
            Text("Clear All")
        }
    }
}

private val turquoiseColors = listOf(
    Color(0xff07688C),
    Color(0xff1986AF),
    Color(0xff50B6CD),
    Color(0xffBCF8FF),
    Color(0xff8AEAE9),
    Color(0xff46CECA)
)
