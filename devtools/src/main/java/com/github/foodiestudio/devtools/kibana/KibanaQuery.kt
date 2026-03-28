package com.github.foodiestudio.devtools.kibana

import java.net.URL

private class KibanaQuery(
    private val siteUrl: String,
    private val index: String,
) : KibanaParamsScope {
    private var filterMap = mutableMapOf<String, String>()

    private var columnFields: Array<out String>? = null

    val url: URL
        get() {
            val filters = filterMap.map { (key, value) ->
                "('\$state':(store:appState),meta:(alias:!n,disabled:!f,index:'$index',key:$key,negate:!f,params:(query:'$value'),type:phrase),query:(match_phrase:($key:'$value')))"
            }.joinToString(",")
            val columns =
                if (columnFields.isNullOrEmpty()) "" else "columns:!(${columnFields?.joinToString(",") ?: ""}),"
            return URL(
                "$siteUrl?_g=(filters:!(),refreshInterval:(pause:!t,value:0),time:(from:now-24h,to:now))&_a=(" + columns +
                        "filters:!($filters),index:'$index',interval:auto,query:(language:kuery,query:''),sort:!())"
            )
        }

    override fun toggleColumn(vararg names: String) {
        columnFields = names
    }

    override fun filter(action: () -> Pair<String, String>) {
        val (key, value) = action()
        filterMap[key] = value
    }
}

interface KibanaParamsScope {
    /**
     * toggle column in the result table
     */
    fun toggleColumn(vararg names: String)

    /**
     * filter data match the fields
     */
    fun filter(action: () -> Pair<String, String>)
}

/**
 * build kibana query for index [index] on [siteUrl] in last 24 hours
 *
 * @return url
 */
fun kibanaQuery(siteUrl: String, index: String, params: KibanaParamsScope.() -> Unit): URL {
    return KibanaQuery(siteUrl, index).apply(params).url
}