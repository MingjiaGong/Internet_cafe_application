package com.example.internetcafeapplication.view.time

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.internetcafeapplication.R
import com.example.internetcafeapplication.model.Machine
import com.example.internetcafeapplication.model.OrderDto
import com.example.internetcafeapplication.ui.theme.LightBlue
import com.example.internetcafeapplication.view.store.DCItem
import com.example.internetcafeapplication.viewmodel.TimeViewModel
import com.example.internetcafeapplication.viewmodel.UserViewModel
import com.example.internetcafeapplication.viewmodel.WalletViewModel


@Composable
fun TimeHomePage(userViewModel: UserViewModel?) {

    val viewModel : TimeViewModel = viewModel()

    LaunchedEffect(true) {
        Log.d("debug",viewModel.currentOrder.value.toString())
        viewModel.getCurrentOrders(userViewModel!!.currentUser!!.value!!.uid)
    }


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
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
        ){
            Text(
                text = "My Time",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
//            Button(
//                onClick = {},
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 10.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color(0xFF98DEFF)),
//
//                ) {
//                Text(
//                    modifier = Modifier.fillMaxWidth(),
//                    text = "The best cafe \n123 street",
//                    style = TextStyle(
//                        color = Color.Blue, // Replace with dark blue color
//                        fontSize = 15.sp),
//                    textAlign = TextAlign.Start
//                )
//
//            }
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .padding(top = 50.dp)
    ){

        if(viewModel.currentOrder.value == null||viewModel.currentOrder.value.size == 0){
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
                        .padding(10.dp),
                    verticalArrangement = Arrangement.SpaceAround,
                ) {
                Text("There is no active machine.")}
            }
        }else{
            LazyColumn{
                items(viewModel.currentOrder.value) { item ->
                    orderItem(item!!,viewModel,userViewModel!!.currentUser.value!!.uid)
                }
            }
        }

    }

    val context = LocalContext.current
    LaunchedEffect(viewModel.isLoading.value) {
        if(viewModel.isLoading.value == false){
            userViewModel?.getUser(userViewModel?.currentUser!!.value!!.uid)
            Toast.makeText(context, "Deactivate successfully!", Toast.LENGTH_SHORT).show()
        }
    }

}

@Composable
fun orderItem(order: OrderDto, viewModel : TimeViewModel,uid:String){
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
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .clip(RoundedCornerShape(15.dp)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround

            ) {

                Text(
                    text = " ${order.machineName} " +
                            "\nTotal time: ${"%.2f".format(order.timeForNow)} h" +
                            "\nTotal credit: $${"%.2f".format(order.moneyForNow)}",
                    style = TextStyle(color = Color.Black) // Replace with your desired style
                    // Add other Text properties as needed
                )

                Image(
                    painter = painterResource(id = R.drawable.baseline_computer_24),
                    contentDescription = "Computer Image",
                    modifier = Modifier
                        .size(130.dp)
                )
            }

            Button(
                onClick = { viewModel.deactivate(uid,order.orderId)},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 40.dp),
                colors = ButtonDefaults.buttonColors(LightBlue),

                ) {
                Text(
                    "Deactivate",
                    color = Color.White,
                    fontSize = 15.sp
                )
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun TimeHomePagePreview(){
    TimeHomePage(null)
}
