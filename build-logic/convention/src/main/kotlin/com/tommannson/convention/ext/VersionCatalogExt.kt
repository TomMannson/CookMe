package com.tommannson.convention.ext

import org.gradle.api.artifacts.VersionCatalog

fun VersionCatalog.findVersionInt(key: String) = findVersion(key).get().requiredVersion.toInt()

fun VersionCatalog.findVersionString(key: String) = findVersion(key).get().requiredVersion