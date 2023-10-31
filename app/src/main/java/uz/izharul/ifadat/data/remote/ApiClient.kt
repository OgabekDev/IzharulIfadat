package uz.izharul.ifadat.data.remote

object ApiClient {

    private const val IS_TESTER = true

    private const val SERVER_DEVELOPMENT = "BASE_URL"
    private const val SERVER_PRODUCTION = "BASE_URL"

    fun server(): String {
        return if (IS_TESTER)
            SERVER_DEVELOPMENT
        else
            SERVER_PRODUCTION
    }

}
