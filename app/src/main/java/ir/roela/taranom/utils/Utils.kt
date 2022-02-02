package ir.roela.taranom.utils

import ir.roela.taranom.model.Poem

object Utils {

    fun fillPoem(poem: Poem): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append(poem.b_1.m_1)
        stringBuilder.append("\n")
        stringBuilder.append("\u2748\u2748\u2748")
        stringBuilder.append("\n")
        stringBuilder.append(poem.b_1.m_2)
        stringBuilder.append("\n\n")
        stringBuilder.append(poetHashtag(poem.poet))
        return stringBuilder.toString()
    }

    fun poetHashtag(poet: String): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("#").append(poet.trim().replace(" ", "_"))
        return stringBuilder.toString()
    }

    /*private fun loadJSONFromAsset(context: Context):Any? {
        try {
            val inputStream = context.assets.open("poem.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val json = String(buffer, charset("UTF-8"))
            val jsonObject = JSONObject(json)

        } catch (e: Exception) {
            Log.e(App.TAG, e.message.toString())
        }
        return null
    }*/
}