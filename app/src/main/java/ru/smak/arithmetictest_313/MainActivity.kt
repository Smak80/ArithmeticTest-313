package ru.smak.arithmetictest_313

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.smak.arithmetictest_313.ui.theme.ArithmeticTest313Theme
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    val mvm by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArithmeticTest313Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainUI(
                        mvm.lst,
                        mvm.currentExample,
                        Modifier
                            .fillMaxSize()
                            .padding(8.dp)){ ex, res ->
                        ex.check(res)
                        mvm.currentExample++
                    }
                }
            }
        }
    }
}

@Composable
fun MainUI(
    exampleList: List<Example>,
    currentExample: Int,
    modifier: Modifier = Modifier,
    onGotAnswer: (Example, Int?)->Unit = {_, _ ->},
){
    Column(modifier = modifier) {
        exampleList.forEachIndexed { index, example ->
            if (index < currentExample) {
                ExampleCard(
                    example.op1,
                    example.op2,
                    example.op.symbol,
                    modifier = Modifier.fillMaxWidth(),
                    color = when (example.isCorrect) {
                        true -> colorResource(id = R.color.green)
                        false -> colorResource(id = R.color.red)
                        else -> MaterialTheme.colorScheme.primaryContainer
                    },
                    isEditable = example.isCorrect == null,
                    onCheck = { onGotAnswer(example, it) }
                )
            }
        }
    }
}

@Composable
fun ExampleCard(
    op1: Int,
    op2: Int,
    operator: Char,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primaryContainer,
    isEditable: Boolean = true,
    onCheck: (Int?)->Unit = {}
){
    var v by remember { mutableStateOf("") }
    ElevatedCard(
        modifier = modifier,
        colors = CardDefaults.elevatedCardColors(containerColor = color )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "$op1 $operator $op2 = ",
                    modifier = Modifier.weight(0.6f),
                    fontSize = 42.sp,
                    textAlign = TextAlign.End,
                )
                OutlinedTextField(
                    value = v,
                    onValueChange = { v = if (
                        it.trim().toIntOrNull() != null || it.isBlank() || it == "-"
                    ) it.trim() else v },
                    modifier = Modifier.weight(0.4f),
                    readOnly = !isEditable,
                    textStyle = TextStyle(fontSize = 36.sp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
            }
            FilledTonalIconButton(
                onClick = { onCheck( v.toIntOrNull()) },
                enabled = isEditable,
            ) {
                Icon(
                    painter = painterResource(
                        id = R.drawable.twotone_check_circle_outline_24
                    ),
                    contentDescription = null,
                    tint = colorResource(id = R.color.blue)
                )
            }
        }
    }
}

@Preview
@Composable
fun ExampleCardPreview(){
    val opList = listOf('+', '-', '*', '/')
    val op = opList[Random.nextInt(4)]
    val (op1, op2) = when(op){
        '+',
        '-' -> Random.nextInt(1, 100) to Random.nextInt(1, 100)
        '*' -> Random.nextInt(-14, 15) to Random.nextInt(1, 15)
        '/' -> Random.nextInt(1, 15).let { denom ->
            val r = Random.nextInt(-14, 15)
            denom * r to denom
        }
        else -> 0 to 0
    }
    ExampleCard(
        op1,
        op2,
        op,
        modifier = Modifier.fillMaxWidth()
    )
}