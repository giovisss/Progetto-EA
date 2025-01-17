    package com.example.jokiandroid.repository

    import TokenManager
    import android.util.Log
    import com.example.jokiandroid.model.Admin
    import com.example.jokiandroid.model.Game
    import com.example.jokiandroid.service.UserApiService
    import com.google.gson.Gson
    import com.google.gson.JsonSyntaxException
    import com.google.gson.reflect.TypeToken
    import kotlinx.coroutines.Dispatchers
    import kotlinx.coroutines.withContext

    class CartRepository() {
        private val gson = Gson()

        suspend fun getUserCart(): List<Game> {
            return withContext(Dispatchers.IO) {
                val response = RetrofitInstance.createApi(UserApiService::class.java, TokenManager.getToken()).getUserCart()
                Log.d("CartRepository", "Response code: ${response.code()}")
                if (response.isSuccessful) {
                    val responseBody: List<Game>? = response.body()
                    if (responseBody != null) {
                        val jsonString = gson.toJson(responseBody)
                        Log.d("CartRepository", "Response body: $jsonString")

                        try {
                            val type = object : TypeToken<List<GameResponse>>() {}.type
                            val gameResponses: List<GameResponse> = gson.fromJson(jsonString, type)

                            val games = gameResponses.map { gameResponse ->
                                Game(
                                    id = gameResponse.id,
                                    title = gameResponse.title,
                                    price = gameResponse.price,
                                    description = gameResponse.description,
                                    imagePath = gameResponse.imagePath,
                                    stock = gameResponse.stock,
                                    genre = gameResponse.genre,
                                    developer = gameResponse.developer,
                                    publisher = gameResponse.publisher,
                                    releaseDate = gameResponse.releaseDate,
                                    admin = Admin(
                                        id = gameResponse.admin.id,
                                        username = gameResponse.admin.username,
                                        email = gameResponse.admin.email
                                    )
                                )
                            }
                            games
                        } catch (e: JsonSyntaxException) {
                            Log.e("CartRepository", "Error parsing JSON", e)
                            emptyList()
                        }
                    } else {
                        Log.e("CartRepository", "Response body is null")
                        emptyList()
                    }
                } else {
                    Log.e("CartRepository", "Error response: ${response.errorBody()?.string()}")
                    throw Exception("Impossibile ottenere il carrello utente: ${response.code()}")
                }
            }
        }

        suspend fun addGameToCart(game: Game): Boolean{
            return withContext(Dispatchers.IO) {
                val response = RetrofitInstance.createApi(UserApiService::class.java, TokenManager.getToken()).addGameToCart(game.id)
                Log.d("CartRepository", "Game ID: ${game.id}")
                Log.d("CartRepository", "Response code: ${response.code()}")
                if (response.isSuccessful) {
                    true
                } else {
                    Log.e("CartRepository", "Error response: ${response.errorBody()?.string()}")
                    false
                }
            }
        }

        suspend fun removeGameFromCart(gameId: String): Boolean {
            return withContext(Dispatchers.IO) {
                val response = RetrofitInstance.createApi(UserApiService::class.java, TokenManager.getToken()).removeGameFromCart(gameId)
                Log.d("CartRepository", "Game ID: $gameId")
                Log.d("CartRepository", "Response code: ${response.code()}")
                if (response.isSuccessful) {
                    true
                } else {
                    Log.e("CartRepository", "Error response: ${response.errorBody()?.string()}")
                    false
                }
            }
        }

        suspend fun removeAllGamesFromCart(): Boolean {
            return withContext(Dispatchers.IO) {
                val response = RetrofitInstance.createApi(UserApiService::class.java, TokenManager.getToken()).removeAllGamesFromCart()
                Log.d("CartRepository", "Response code: ${response.code()}")
                if (response.isSuccessful) {
                    true
                } else {
                    Log.e("CartRepository", "Error response: ${response.errorBody()?.string()}")
                    false
                }
            }
        }

        suspend fun checkout(): Boolean {
            return withContext(Dispatchers.IO) {
                val response = RetrofitInstance.createApi(UserApiService::class.java, TokenManager.getToken()).checkout()
                Log.d("CartRepository", "Response code: ${response.code()}")
                if (response.isSuccessful) {
                    true
                } else {
                    Log.e("CartRepository", "Error response: ${response.errorBody()?.string()}")
                    false
                }
            }
        }
    }

    data class GameResponse(
        val id: String,
        val title: String,
        val description: String,
        val price: Double,
        val imagePath: String,
        val genre: String,
        val developer: String,
        val publisher: String,
        val releaseDate: String,
        val stock: Int,
        val admin: AdminResponse
    )

    data class AdminResponse(
        val id: String,
        val username: String,
        val email: String
    )