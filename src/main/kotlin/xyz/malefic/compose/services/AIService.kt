package xyz.malefic.compose.services

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

@Serializable
data class GeminiRequest(
    val contents: List<Content>,
)

@Serializable
data class Content(
    val parts: List<Part>,
)

@Serializable
data class Part(
    val text: String,
)

@Serializable
data class GeminiResponse(
    val candidates: List<Candidate>?,
)

@Serializable
data class Candidate(
    val content: Content,
)

@Serializable
data class GoalDetails(
    val estimatedCost: String,
    val targetAmount: String,
    val timeFrame: String,
    val monthlySavings: String,
    val description: String,
)

class AIService(
    private val apiKey: String,
) {
    private val client = OkHttpClient()
    private val json = Json { ignoreUnknownKeys = true }
    private val baseUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent"

    suspend fun generateGoalDetails(goalDescription: String): GoalDetails? {
        return withContext(Dispatchers.IO) {
            try {
                val prompt =
                    """
                    Create a financial plan for this goal: "$goalDescription"
                    
                    Provide a JSON response with exactly this structure:
                    {
                        "estimatedCost": "Total estimated cost in USD (e.g., $2,500)",
                        "targetAmount": "Target savings amount in USD (e.g., $2,500)", 
                        "timeFrame": "Recommended time frame in months (e.g., 12 months)",
                        "monthlySavings": "Monthly savings needed in USD (e.g., $208)",
                        "description": "A brief description of the goal and what it includes"
                    }
                    
                    Base your estimates on realistic costs and provide practical financial advice.
                    For example, if it's a vacation, consider flights, accommodation, food, activities, etc.
                    If it's a purchase, consider the item cost plus any additional expenses.
                    Only return the JSON, no other text.
                    """.trimIndent()

                val requestBody =
                    GeminiRequest(
                        contents =
                            listOf(
                                Content(
                                    parts = listOf(Part(text = prompt)),
                                ),
                            ),
                    )

                val jsonBody = json.encodeToString(GeminiRequest.serializer(), requestBody)
                val request =
                    Request
                        .Builder()
                        .url(baseUrl)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("X-goog-api-key", apiKey)
                        .post(jsonBody.toRequestBody("application/json".toMediaType()))
                        .build()

                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    responseBody?.let { body ->
                        println("Gemini API Response: $body") // Debug log
                        val geminiResponse = json.decodeFromString<GeminiResponse>(body)
                        val aiResponse =
                            geminiResponse.candidates
                                ?.firstOrNull()
                                ?.content
                                ?.parts
                                ?.firstOrNull()
                                ?.text

                        aiResponse?.let { responseText ->
                            println("AI Response Text: $responseText") // Debug log
                            // Extract JSON from the response
                            val jsonStart = responseText.indexOf('{')
                            val jsonEnd = responseText.lastIndexOf('}') + 1
                            if (jsonStart >= 0 && jsonEnd > jsonStart) {
                                val jsonString = responseText.substring(jsonStart, jsonEnd)
                                println("Extracted JSON: $jsonString") // Debug log
                                return@withContext json.decodeFromString<GoalDetails>(jsonString)
                            }
                        }
                    }
                } else {
                    println("API call failed: ${response.code} - ${response.message}")
                }

                // Fallback response if API fails
                createFallbackGoalDetails(goalDescription)
            } catch (e: Exception) {
                e.printStackTrace()
                println("Exception in generateGoalDetails: ${e.message}")
                createFallbackGoalDetails(goalDescription)
            }
        }
    }

    private fun createFallbackGoalDetails(goalDescription: String): GoalDetails =
        GoalDetails(
            estimatedCost = "$1,000",
            targetAmount = "$1,000",
            timeFrame = "6 months",
            monthlySavings = "$167",
            description = "Custom goal: $goalDescription",
        )
}
