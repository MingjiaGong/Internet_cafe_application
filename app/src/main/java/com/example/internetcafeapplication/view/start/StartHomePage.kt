package com.example.internetcafeapplication.view.start

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.internetcafeapplication.ui.theme.LightBlue
import com.example.internetcafeapplication.viewmodel.StartViewModel
import com.example.internetcafeapplication.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartHomePage(
    navcontroller: NavHostController,
    machineString: String,
    userViewModel: UserViewModel,
) {
    val viewModel : StartViewModel = viewModel()

    var confirmDialog by remember { mutableStateOf(false) }
    var wrongDialog by remember { mutableStateOf(false) }
    var typeDialog by remember { mutableStateOf(false) }

    var machineId by remember { mutableStateOf("") }


    LaunchedEffect(true) {
        if(machineString.length != 0){
            viewModel.getMachine(machineString)
            confirmDialog = true

        }
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Text(
                "Start From Here",
                color = Color.White,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                "Credit Available:",
                color = Color.White,
                fontSize = 25.sp,
                //fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
            Text(
                "$${userViewModel.currentUser.value!!.credit}",
                color = Color.White,
                fontSize = 25.sp,
                //fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .padding(top = 140.dp)
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
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                Text(
                    "Scan",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 10.dp)
                )

                Text(
                    "Scan the QR Code near the device",
                    modifier = Modifier.padding(start = 10.dp)
                )

                Button(
                    onClick = { navcontroller.navigate("camera") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 40.dp),
                    colors = ButtonDefaults.buttonColors(LightBlue),

                    ) {
                    Text(
                        "Scan QR Code",
                        color = Color.White,
                        fontSize = 15.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

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
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                Text(
                    "Type",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 10.dp)
                )

                Text(
                    "Type the number near the device",
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
                        "Type number",
                        color = Color.White,
                        fontSize = 15.sp
                    )
                }
            }
        }

    }

    if (confirmDialog) {
        AlertDialog(
            onDismissRequest = { confirmDialog = false },
            title = { Text(text = "Confirm Activation") },
            text = {
                Log.d("StartDebug3",viewModel.machine.value.toString())
                viewModel.isLoading.value.let { isLoading ->
                    if (isLoading) {
                        CircularProgressIndicator()
                        Log.d("StartDebug4", viewModel.isLoading.value.toString())
                    } else {
                        if (viewModel.machine.value != null) {
                            // This block will execute if machine is not null
                            val machine = viewModel.machine.value

                            Column(modifier = Modifier.fillMaxWidth().height(40.dp)) {
                                Text(
                                    "Machine: ${machine!!.name}, are you sure you want to activate this machine?",
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        } else {
                            // This block will execute if machine is null
                            Text("Machine not found.")
                        }
                    }
                }



            },
            confirmButton = {
                // Only show the confirm button if the machine value is not null
                if (viewModel.machine.value != null) {
                    Button(
                        onClick = {
                            // Handle the confirmation action here
                            confirmDialog = false
                            wrongDialog = true
                            viewModel.activeMachine(viewModel.machine.value!!.id,uid = userViewModel.currentUser.value!!.uid);
                        }
                    ) {
                        Text("Confirm")
                    }
                }
            },
            dismissButton = {
                Button(onClick = { confirmDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    if (typeDialog) {
        AlertDialog(
            onDismissRequest = { typeDialog = false },
            title = { Text(text = "Enter Machine ID") },
            text = {

                TextField(
                    value = machineId,
                    onValueChange = { machineId = it },
                    label = { Text("Machine ID") }
                )

            },
            confirmButton = {
                Button(
                    onClick = {
                        // Handle the confirmation action here
                        viewModel.getMachine(machineId)
                        //viewModel.activeMachine(machineId,uid = userViewModel.currentUser.value!!.uid);
                        typeDialog = false
                        confirmDialog = true
                        machineId = ""
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

    if (wrongDialog) {
        AlertDialog(
            onDismissRequest = { wrongDialog = false },
            title = { Text(text = "Activation") },
            text = {
                Column(modifier = Modifier.fillMaxWidth().height(40.dp)) {
                    Text(
                        viewModel.activeMessage.value,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

            },
            confirmButton = {
                Button(
                    onClick = {
                        // Handle the confirmation action here
                        wrongDialog = false
                    }
                ) {
                    Text("Confirm")
                }
            },
        )
    }



}



@Preview(showBackground = true)
@Composable
fun DCItemPreview() {
    StartHomePage(rememberNavController(), "", viewModel(),)
    //DCItem(Machine())
}


