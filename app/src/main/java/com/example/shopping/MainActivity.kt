package com.example.shopping

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopping.common.TEST_ID
import com.example.shopping.presentation.navigation.App
import com.example.shopping.presentation.screens.utils.NotificationPermissionRequest
import com.example.shopping.ui.theme.ShoppingAppTheme
import com.google.firebase.auth.FirebaseAuth
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() , PaymentResultListener {
    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Checkout.preload(applicationContext)
        val co = Checkout()

        co.setKeyID(TEST_ID)
        setContent {
            ShoppingAppTheme {

                 Modifier.fillMaxSize().background(color = Color.Black)

                MainScreen(firebaseAuth = firebaseAuth)
                //   HomeScreenUi()
//
            }
        }
    }




    @Composable
    fun MainScreen(firebaseAuth: FirebaseAuth) {
        val showSplash = remember { mutableStateOf(true) }

        // Trigger splash screen delay
        LaunchedEffect(Unit) {
            Handler(Looper.getMainLooper()).postDelayed({
                showSplash.value = false
            }, 3000)
        }

        if (showSplash.value) {
            SplashScreen()
        } else {
            NotificationPermissionRequest()
            App(firebaseAuth, {payTest() })
        }
    }

    @Composable
    fun SplashScreen() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black), // Use your splash screen color
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Icon from the drawable folder
                Image(
                    painter = painterResource(id = R.drawable.shoppinglady), // Replace with your drawable icon name
                    contentDescription = "App Icon",
                    modifier = Modifier.size(300.dp) // Set the desired icon size
                )
                Spacer(modifier = Modifier.height(16.dp)) // Space between icon and text

                // Text below the icon
                BasicText(
                    text = "Welcome to the Clothing Store",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color.White,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }

    fun payTest(

    ) {

            /*
        *  You need to pass the current activity to let Razorpay create CheckoutActivity
        * */
        val activity:Activity = this
        val co = Checkout()

        try {
            val options = JSONObject()
            options.put("name","Razorpay Corp")
            options.put("description","Demoing Charges")
            //You can omit the image option to fetch the image from the dashboard
            options.put("image","http://example.com/image/rzp.jpg")
            options.put("theme.color", "#3399cc");
            options.put("currency","INR");
            options.put("order_id", "order_DBJOWzybf0sJbb");
            options.put("amount","50000")//pass amount in currency subunits

            val retryObj = JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            val prefill = JSONObject()
            prefill.put("email","gaurav.kumar@example.com")
            prefill.put("contact","9876543210")

            options.put("prefill",prefill)
            co.open(activity,options)

            } catch (e: Exception) {
                Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }

    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show()
    }
}