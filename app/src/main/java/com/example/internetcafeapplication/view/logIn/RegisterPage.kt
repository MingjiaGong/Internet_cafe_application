package com.example.internetcafeapplication.view.logIn

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.internetcafeapplication.ui.theme.DarkBlue
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.internetcafeapplication.model.authorization.Resource
import com.example.internetcafeapplication.viewmodel.UserViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterPage(viewModel:UserViewModel?,navcontroller: NavHostController,mainNav:NavHostController){

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repassword by remember { mutableStateOf("") }
    val context = LocalContext.current

    val authResource = viewModel?.signupFlow?.collectAsState()

//    val value by remember{viewModel.repositories}
//    LaunchedEffect(key1 = Unit){
//        viewModel.getUsers()
//    }

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
                text = "Register",
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
                .height(400.dp)
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


                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Type in") },
                )

                Text(
                    "Password",
                    modifier = Modifier
                    // Add modifiers if needed
                )


                // Password input field
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Type in") },
                    visualTransformation = PasswordVisualTransformation(),
                    // Add other parameters like 'label' if you need a floating label, etc.
                )

                Text(
                    "Reenter password",
                    modifier = Modifier
                )

                OutlinedTextField(
                    value = repassword,
                    onValueChange = { repassword = it },
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
                        onClick = {
                            if(password == repassword){
                                viewModel?.signupUser(email,password)
                            }else{
                                Toast.makeText(context,"Passwords do not match",Toast.LENGTH_SHORT).show()
                            } },
                        modifier = Modifier
                            .width(150.dp),
                        colors = ButtonDefaults.buttonColors(DarkBlue),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Save", color = Color.White)
                    }

                    Button(
                        onClick = {
                            viewModel?.cancelSignUp()
                            navcontroller.navigate("LogIn") },
                        modifier = Modifier.width(150.dp),
                        colors = ButtonDefaults.buttonColors(DarkBlue),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Cancel", color = Color.White)
                    }
                }
            }

        }

    }

    authResource?.value?.let {
        when (it) {
            is Resource.Failure -> {
                //ShowToast(message = it.exception.message.toString())
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
                Toast.makeText(context, "Sign Up Successfully", Toast.LENGTH_SHORT).show()
                viewModel.addUser()
                LaunchedEffect(Unit) {
                    mainNav.navigate("CafeNav") {
                        popUpTo("CafeNav") { inclusive = true }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPagePreview(){
    RegisterPage(null, rememberNavController(), rememberNavController())
}

