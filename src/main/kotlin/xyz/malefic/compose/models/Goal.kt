package xyz.malefic.compose.models

import kotlinx.serialization.Serializable

@Serializable
data class Goal(
    val id: String = System.currentTimeMillis().toString(),
    val name: String,
    val description: String,
    val targetAmount: String,
    val currentAmount: String = "$0.00",
    val timeFrame: String,
    val monthlySavings: String,
    val estimatedCost: String,
    val createdAt: Long = System.currentTimeMillis(),
    val isCompleted: Boolean = false,
) {
    fun getProgressPercentage(): Int {
        val current = currentAmount.replace("$", "").replace(",", "").toDoubleOrNull() ?: 0.0
        val target = targetAmount.replace("$", "").replace(",", "").toDoubleOrNull() ?: 1.0
        return if (target > 0) ((current / target) * 100).toInt() else 0
    }

    fun getRemainingAmount(): String {
        val current = currentAmount.replace("$", "").replace(",", "").toDoubleOrNull() ?: 0.0
        val target = targetAmount.replace("$", "").replace(",", "").toDoubleOrNull() ?: 0.0
        val remaining = target - current
        return "$${String.format("%.2f", remaining)}"
    }
}
