package uz.izharul.ifadat.data.remote

object ApiClient {

    private const val IS_TESTER = true

    private const val SERVER_DEVELOPMENT = "https://mdev.uz/api/v1/mobile/izharul-ifodat/"
    private const val SERVER_PRODUCTION = "https://mdev.uz/api/v1/mobile/izharul-ifodat/"

    fun server(): String {
        return if (IS_TESTER)
            SERVER_DEVELOPMENT
        else
            SERVER_PRODUCTION
    }

}