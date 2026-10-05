package com.marketsignaldesk.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme(
                    background = Color(0xFF121212),
                    surface = Color(0xFF1E1E1E)
                )
            ) {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    HomeScreen()
                }
            }
        }
    }
}

@Composable
fun HomeScreen(viewModel: MainViewModel = viewModel()) {
    val signals by viewModel.signals.collectAsState()
    val isDemo by viewModel.isDemoMode.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Market Signal Desk", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Text("Research first. Decide yourself.", fontSize = 14.sp, color = Color.Gray)
        
        if (isDemo) {
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF3E2723))
            ) {
                Text(
                    text = "DEMO MODE ACTIVE - Showing Sample Data",
                    color = Color(0xFFFFB300),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(12.dp),
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("TOP OPPORTUNITIES", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(signals) { signal ->
                SignalCard(signal)
            }
        }
    }
}

@Composable
fun SignalCard(signal: SignalResponse) {
    val signalColor = if (signal.score >= 70) Color(0xFF00C853) else if (signal.score < 40) Color(0xFFD50000) else Color(0xFFFFAB00)

    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(signal.symbol, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.White)
                Text(signal.signal, fontWeight = FontWeight.Bold, color = signalColor)
            }
            Text("Score: ${signal.score}/100 | Price: ₹${signal.price}", color = Color.LightGray)
            
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text("Entry Zone", color = Color.Gray, fontSize = 12.sp)
                    Text(signal.entry_zone, color = Color.White, fontWeight = FontWeight.Medium)
                }
                Column {
                    Text("Stop Loss", color = Color.Gray, fontSize = 12.sp)
                    Text("₹${signal.stop_loss}", color = Color(0xFFFF5252), fontWeight = FontWeight.Medium)
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            Text("WHY THIS SIGNAL?", fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
            signal.reasons.forEach { reason ->
                Text("✓ $reason", fontSize = 12.sp, color = Color.White)
            }
        }
    }
}
