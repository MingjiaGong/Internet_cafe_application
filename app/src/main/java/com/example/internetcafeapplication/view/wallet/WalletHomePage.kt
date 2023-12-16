package com.example.internetcafeapplication.view.wallet
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.internetcafeapplication.ui.theme.LightBlue
import com.example.internetcafeapplication.viewmodel.StartViewModel
import com.example.internetcafeapplication.viewmodel.UserViewModel
import com.example.internetcafeapplication.viewmodel.WalletViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


@Composable
fun WalletHomePage(userViewModel: UserViewModel?) {
    var typeDialog by remember { mutableStateOf(false) }
    val viewModel : WalletViewModel = viewModel()



    val gradientBrush = Brush.linearGradient(
        colors = listOf(
            Color(0xFF89B8F0),
            Color(0xFFDCF0F7)
        ),
    )

    Box(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(brush = gradientBrush)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Text(
                "My Wallet",
                color = Color.White,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(15.dp))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .padding(top = 50.dp)
    ){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .padding(5.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(20.dp),
                verticalArrangement = Arrangement.SpaceAround,
            ) {
                Text(
                    "Available Credit",
                    fontSize = 25.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 10.dp)
                )

                Text(
                    "$${userViewModel?.currentUser?.value?.credit}",
                    fontSize = 40.sp,

                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 10.dp)
                )

                Button(
                    onClick = { typeDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 40.dp),
                    colors = ButtonDefaults.buttonColors(LightBlue),

                    ) {
                    Text(
                        "+Add fund",
                        color = Color.White,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
    val amounts = listOf(1, 5, 10, 20, 50, 100)
    val chunkedAmounts = amounts.chunked(3)
    var selectedAmount by remember { mutableStateOf<Int>(1) }
    val coroutineScope = rememberCoroutineScope()
    if (typeDialog) {
        AlertDialog(
            onDismissRequest = { typeDialog = false },
            title = { Text(text = "Add Credit") },
            text = {


                Column {
                    // Iterate over the chunked list to create rows of buttons
                    chunkedAmounts.forEach { rowAmounts ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            rowAmounts.forEach { amount ->
                                val isSelected = amount == selectedAmount
                                Button(
                                    onClick = {
                                        selectedAmount = amount
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        if (isSelected) LightBlue else Color.LightGray,

                                    )
                                ) {
                                    Text("\$$amount")
                                }
                            }
                        }
                    }
                }

            },
            confirmButton = {
                Button(
                    onClick = {
                        // Handle the confirmation action here
                        typeDialog = false
                        viewModel.updateUser(userViewModel?.currentUser!!.value!!, selectedAmount.toDouble())
                            //userViewModel.updateUser(userViewModel?.currentUser!!.value!!.uid)
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(onClick = { typeDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
    val context = LocalContext.current
    LaunchedEffect(viewModel.isLoading.value) {
        if(viewModel.isLoading.value == false){
            userViewModel?.getUser(userViewModel?.currentUser!!.value!!.uid)
            Toast.makeText(context, "Add credit successfully!", Toast.LENGTH_SHORT).show()
        }
    }


}
@Preview(showBackground = true)
@Composable
fun WalletHomePagePreview(){
    WalletHomePage(null)
}


