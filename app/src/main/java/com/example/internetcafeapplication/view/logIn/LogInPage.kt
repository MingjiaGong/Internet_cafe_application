package com.example.internetcafeapplication.view.logIn

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.internetcafeapplication.ui.theme.DarkBlue
import com.example.internetcafeapplication.ui.theme.LightBlue
import com.example.internetcafeapplication.viewmodel.UserViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.internetcafeapplication.model.authorization.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInPage(viewModel: UserViewModel?, navcontroller: NavHostController, mainNav:NavHostController){
//    val value by remember{viewModel.repositories}
//    LaunchedEffect(key1 = Unit){
//        viewModel.getUsers()
//    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    //val context = LocalContext.current

    val authResource = viewModel?.loginFlow?.collectAsState()

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
                text = "Log In",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .padding(top = 120.dp),

    ){
        Card(
            modifier = Modifier
                .height(300.dp)
                .padding(5.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(Color.White),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                verticalArrangement = Arrangement.SpaceEvenly

            ) {
                Text(
                    "Email",
                    modifier = Modifier
                )

                // Username input field
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Type in") },
                )

                Text(
                    "Password",
                    modifier = Modifier
                )


                // Password input field
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Type in") },
                    visualTransformation = PasswordVisualTransformation(),
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        onClick = { viewModel?.loginUser(email,password) },
                        modifier = Modifier
                            .width(150.dp),
                        colors = ButtonDefaults.buttonColors(DarkBlue),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Log in", color = Color.White)
                    }

//                    Button(
//                        onClick = { /* TODO: Handle forget password */ },
//                        modifier = Modifier.width(150.dp),
//                        colors = ButtonDefaults.buttonColors(DarkBlue),
//                        shape = RoundedCornerShape(8.dp)
//                    ) {
//                        Text("Forget", color = Color.White)
//                    }
                }
            }

        }

        Text(
            "Register           ",
            color = LightBlue,
//            style = TextStyle(textDecoration = TextDecoration.Underline),
            fontSize = 25.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clickable {
                    viewModel?.cancelSignIn()
                    navcontroller.navigate("Register") }

        )
        Divider(
            modifier = Modifier
                .width(170.dp)
                .padding(start = 20.dp)
                .height(1.dp),
            color = LightBlue
        )


        authResource?.value?.let {
            when (it) {
                is Resource.Failure -> {
                    //ShowToast(message = it.exception.message.toString())
                    val context = LocalContext.current
                    Toast.makeText(context, it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is Resource.Success -> {
                    val context = LocalContext.current
                    Toast.makeText(context, "Log In Successfully", Toast.LENGTH_SHORT).show()

                    LaunchedEffect(Unit) {
                        mainNav.navigate("CafeNav") {
                            popUpTo("CafeNav") { inclusive = true }
                        }
                    }
                }
            }
        }

//        LazyColumn(){
//            items(value.users){ user ->
//                Row(){
//                    Text(user.id, modifier = Modifier.padding(horizontal = 10.dp))
//                    Text(user.uid, modifier = Modifier.padding(horizontal = 10.dp))
//                    Text(user.email, modifier = Modifier.padding(horizontal = 10.dp))
//                }
//
//            }
//        }

    }

}

@Preview(showBackground = true)
@Composable
fun LogInPagePreview(){
    LogInPage(null,rememberNavController(), rememberNavController())
}

