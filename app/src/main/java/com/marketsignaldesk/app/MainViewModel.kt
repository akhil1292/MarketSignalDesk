package com.marketsignaldesk.app

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class SignalResponse(
    val symbol: String,
    val signal: String,
    val score: Int,
    val price: Double,
    val entry_zone: String,
    val stop_loss: Double,
    val target: Double,
    val reasons: List<String>
)

class MainViewModel : ViewModel() {
    private val _signals = MutableStateFlow<List<SignalResponse>>(emptyList())
    val signals: StateFlow<List<SignalResponse>> = _signals

    private val _isDemoMode = MutableStateFlow(true)
    val isDemoMode: StateFlow<Boolean> = _isDemoMode

    init {
        loadDemoData()
    }

    private fun loadDemoData() {
        _isDemoMode.value = true
        _signals.value = listOf(
            SignalResponse("RELIANCE", "STRONG BUY", 86, 1482.40, "₹1470 - ₹1485", 1442.0, 1565.0, listOf("Above EMA 20", "MACD bullish", "20-day breakout")),
            SignalResponse("HDFCBANK", "WATCH", 65, 1640.20, "₹1630 - ₹1650", 1600.0, 1720.0, listOf("Consolidating near support", "RSI neutral")),
            SignalResponse("TCS", "BUY", 74, 3920.50, "₹3900 - ₹3930", 3850.0, 4100.0, listOf("Volume confirmation", "Positive momentum"))
        )
    }
}
